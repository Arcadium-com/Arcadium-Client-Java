package arcadium.com.dao;

import arcadium.com.jira.IntegracaoJira;
import arcadium.com.models.*;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import arcadium.com.conexao.Conexao;
import org.checkerframework.checker.units.qual.A;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.awt.*;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import arcadium.com.slack.IntegracaoSlack;

public class DadosDao {
    private Looca looca;
    private Conexao conexao;
    private JdbcTemplate mysql;
    private JdbcTemplate sqlServer;
    private Log logger;
    private Integer logInsercao;

    public DadosDao() {
        this.looca = new Looca();
        this.conexao = new Conexao();
        this.mysql = conexao.getConexaoDoBancoMySql();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
        this.logger = new Log();
        this.logInsercao = 0;
    }

    public void inserirDadosNoBanco(Totem totem) {
        List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
        Disco disco = discos.get(0);
        Memoria memoria = looca.getMemoria();
        Processador cpu = looca.getProcessador();

        Double usoDoDisco = disco.getEscritas().doubleValue() / 1000000;
        Integer discoTotal = totem.getDiscoTotal();
        Double valorDisco = (usoDoDisco / discoTotal) * 100;
        Double valorRAM = ((memoria.getEmUso().doubleValue() / 1000000000) / totem.getRAMTotal()) * 100;
        Double valorCPU = cpu.getUso().doubleValue();
        Integer totalUsbsConectados = looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().size();

        try {
            sqlServer.update("""
                    INSERT INTO dados(dt_hora, valorDisco, valorMemoriaRAM, valorCPU, USB,  fktotem)
                    VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)
                    """, valorDisco, valorRAM, valorCPU, totalUsbsConectados, totem.getId());

            mysql.update("""
                    INSERT INTO dados(dt_hora, valorDisco, valorMemoriaRAM, valorCPU, USB,  fktotem)
                    VALUES (CURRENT_TIMESTAMP(), ?, ?, ?, ?, 1)
                    """, valorDisco, valorRAM, valorCPU, totalUsbsConectados);

            Dados ultimoDado = this.getUltimoDadoAdicionado();
            totem.adicionaDados(ultimoDado);

            if (logInsercao == 0) {
                logger.log("A aplicação está inserindo dados no banco.");
                logInsercao++;
            }

            sendMessages(totem, ultimoDado);
        } catch (Exception e) {
            logger.log("Erro na inserção de dados.");
            logger.log("Exceção => " + e);
//            System.out.println("Houve um erro na conexão com o banco de dados.");
//            System.exit(0);
        }


    }

    public Dados getUltimoDadoAdicionado() {
        return sqlServer.queryForObject("SELECT TOP 1 * FROM dados ORDER BY dt_hora DESC", new BeanPropertyRowMapper<>(Dados.class));
    }

    public List<Dados> getDadosDoTotemPorFkTotem(Integer idTotem) {
        return sqlServer.query("SELECT * FROM dados WHERE fktotem = ?", new BeanPropertyRowMapper<>(Dados.class), idTotem);
    }

    public List<Dados> getUltimos5RegistrosDoTotemPorId(Integer idTotem) {
        return sqlServer.query("SELECT TOP 5 * FROM dados WHERE fktotem = ?  ORDER BY dt_hora DESC", new BeanPropertyRowMapper<>(Dados.class), idTotem);
    }

    public void sendMessages(Totem totem, Dados dados) {
        Boolean chamadoCritico = false;
        Boolean alertarNoSlack = false;
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        Empresa empresa = sqlServer.queryForObject("SELECT * FROM Empresa WHERE id = ?", new BeanPropertyRowMapper<>(Empresa.class), totem.getFkEmpresa());
        String ufEmpresa = sqlServer.queryForObject("SELECT sigla FROM Estado WHERE id = ?", new Object[]{empresa.getFkEstado()}, String.class);
        String endereco = String.format(
                "%s, %s, %s - %s, %s",
                empresa.getLogradouro(),
                empresa.getNumero(),
                empresa.getBairro(),
                empresa.getCidade(),
                ufEmpresa
        );

        SistemaOperacional soTotem = sqlServer.queryForObject("SELECT * FROM sistemaOperacional WHERE id = ?", new BeanPropertyRowMapper<>(SistemaOperacional.class), totem.getFkSistemaOperacional());

        Indicador indicador = sqlServer.queryForObject("SELECT * FROM Indicadores WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Indicador.class), totem.getFkEmpresa());

        Double limiteDisco = indicador.getLimiteDisco();
        Double limiteCPU = indicador.getLimiteCpu();
        Double limiteRAM = indicador.getLimiteRam();
        Integer indicadorUSB = indicador.getIndicadorUsb();
        String dtFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(dados.getDtHora());

        String mensagem = "Alerta ";
        String informacoesTotem = String.format("""
                Data da Ocorrencia: %s
                Id do Totem: %d
                Localização do Totem: %s
                Sistema Operacional do Totem: %s - %s""",
                dtFormatada,
                totem.getId(),
                endereco,
                soTotem.getVersionamento(), soTotem.getDistribuicao()
        );

        if (dados.getValorDisco() >= (limiteDisco * 0.8)) {
            if (dados.getValorDisco() >= (limiteDisco * 0.9)){
                mensagem += "Ruim DISCO";
            } else if (dados.getValorDisco() >= limiteDisco){
                mensagem += "Crítico DISCO";
                chamadoCritico = true;
            } else {
                mensagem += "Médio DISCO";
            }

            mensagem +=
                """
                %s
                Uso do DISCO: %.2f%%
                Limite Esperado: %.2f%%
                            
                """.formatted(informacoesTotem, dados.getValorDisco(), indicador.getLimiteDisco());
            alertarNoSlack = true;

        }

        if (dados.getValorCPU() >= (limiteCPU * 0.8)) {
            if (dados.getValorCPU() >= (limiteCPU * 0.9)){
                mensagem += "Ruim CPU";
            } else if (dados.getValorCPU() >= limiteCPU){
                mensagem += "Crítico CPU";
                chamadoCritico = true;
            } else {
                mensagem += "Médio CPU";
            }

            mensagem +=
                """
                
                %s
                Uso da CPU: %.2f%%
                Limite Esperado: %.2f%%                    
                """.formatted(informacoesTotem, dados.getValorCPU(), indicador.getLimiteCpu());
            alertarNoSlack = true;

        }

        if (dados.getValorMemoriaRAM() >= (limiteRAM * 0.8)) {
            if (dados.getValorMemoriaRAM() >= (limiteRAM * 0.9)){
                mensagem += "Ruim RAM";
            } else if (dados.getValorMemoriaRAM() >= limiteRAM){
                mensagem += "Crítico RAM";
                chamadoCritico = true;
            } else {
                mensagem += "Médio RAM";
            }
            mensagem +=
                """
                                
                %s
                Uso da RAM: %.2f%%
                Limite Esperado: %.2f%%
                                    
                """.formatted(informacoesTotem, dados.getValorMemoriaRAM(), indicador.getLimiteRam());
            alertarNoSlack = true;

        }

        if (dados.getUSB() > indicadorUSB || dados.getUSB() < indicadorUSB) {

            mensagem += """
                    Crítico USB
                                
                    %s
                    Quantidade de USBs: %d
                    Quantidade Esperada: %d
                                
                    """.formatted(informacoesTotem, dados.getUSB(), indicadorUSB);
            chamadoCritico = true;
            alertarNoSlack = true;

        }

        if(alertarNoSlack){
            LocalTime horaAtual = LocalTime.now();
            List<AlertaSlack> alertasSlackTotem = totem.getAlertasSlack();
            IntegracaoSlack slack = new IntegracaoSlack();
            Integer qtdAlertasSlack = alertasSlackTotem.size();

            if(qtdAlertasSlack == 0){
                slack.sendMessage(mensagem);
                AlertaSlack alertaSlack = new AlertaSlack(1, horaAtual, 1);
                totem.adicionaAlertaSlack(alertaSlack);
                mysql.update("INSERT INTO alertaSlack(horaAlertaSlack, fkTotem) VALUES (?, 1)", horaAtual);

            } else {
                AlertaSlack ultimoAlerta = alertasSlackTotem.get(qtdAlertasSlack - 1);
                Boolean horaAtualEhMaior = getHoraEhMaior(ultimoAlerta.getHoraAlertaSlack().plusMinutes(30), horaAtual);

                if (horaAtualEhMaior){
                    slack.sendMessage(mensagem);
                    totem.adicionaAlertaSlack(new AlertaSlack((qtdAlertasSlack + 1), horaAtual, 1));
                    mysql.update("INSERT INTO alertaSlack(horaAlertaSlack, fkTotem) VALUES (?, 1)", horaAtual);
                }
            }
        }

        if (chamadoCritico) {
            List<Alerta> alertas = totem.getAlertas();
            Boolean dtEhMaior = true;
            Boolean horaAtualMaiorHoraConclusao = true;
            Boolean dtEhIgual = false;

            if (alertas.size() > 0) {
                Alerta ultimoAdicionado = alertas.get(alertas.size() - 1);
                String dataConclusaoFormatado = formatadorData.format(ultimoAdicionado.getDtHoraConclusaoAlerta());
                String horaConclusaoFormatada = formatadorHora.format(ultimoAdicionado.getDtHoraConclusaoAlerta());

                LocalDate dataConclusao = LocalDate.parse(dataConclusaoFormatado);
                LocalDate dataAtual = LocalDate.now();

                // Verifica se a data atual é igual a data de previsão para conclusão do alerta
                dtEhMaior = this.getDataEhMaior(dataConclusao, dataAtual);
                dtEhIgual = dataConclusao.equals(dataAtual);

                // Transforma a hora de previsão para conclusão do alerta em LocalTime
                LocalTime horaConclusao = LocalTime.parse(horaConclusaoFormatada);
                LocalTime horaAtual = LocalTime.now();

                // Verifica se a hora atual é maior que a hora para previsão de conclusão do alerta
                horaAtualMaiorHoraConclusao = this.getHoraEhMaior(horaConclusao, horaAtual);
            }

            // Se a data atual for igual a data de conclusão e a hora atual for maior que a hora de conclusão
            // OU se a data atual for maior que a hora de conclusão
            if ((dtEhIgual && horaAtualMaiorHoraConclusao) || dtEhMaior){
                IntegracaoJira.criarChamado(totem, empresa.getTel(), endereco, soTotem, dados, indicador);


                LocalDateTime dtHora = LocalDateTime.now();
                LocalDateTime dtHoraConclusao = LocalDateTime.now().plusDays(1);
                LocalTime hrSlack = LocalTime.now();

                Alerta alerta = new Alerta(dtHora, dtHoraConclusao, 1);

                mysql.update("INSERT INTO alerta(dtHoraAlerta, dtHoraConclusaoAlerta, fkTotem) VALUES (?, ?, ?)", dtHora, dtHoraConclusao, alerta.getFkTotem());

                totem.adicionaAlerta(alerta);
            }
        }
    }

    public Boolean getHoraEhMaior(LocalTime horaConclusao, LocalTime horaAtual){
        Boolean horaEhMaior = false;

        if(horaAtual.getHour() >= horaConclusao.getHour()){
            if (horaAtual.getMinute() >= horaConclusao.getMinute() || horaAtual.getHour() > horaConclusao.getHour()){
                horaEhMaior = true;
            }
        }

        return horaEhMaior;
    }

    public Boolean getDataEhMaior(LocalDate dataComparacao, LocalDate dataAtual){
        Boolean dataEhMaior = false;

        if(dataAtual.getMonthValue() >= dataComparacao.getMonthValue()){
            if(dataAtual.getDayOfMonth() > dataComparacao.getDayOfMonth() || dataAtual.getMonthValue() > dataComparacao.getMonthValue()){
                dataEhMaior = true;
            }
        }

        return dataEhMaior;
    }
}
