package arcadium.com.dao;

import arcadium.com.jira.IntegracaoJira;
import arcadium.com.models.Indicador;
import arcadium.com.models.Log;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import arcadium.com.conexao.Conexao;
import arcadium.com.models.Dados;
import arcadium.com.models.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import arcadium.com.slack.IntegracaoSlack;

public class DadosDao {
    private Looca looca;
    private Conexao conexao;
    private JdbcTemplate mysql;
    private JdbcTemplate sqlServer;
    Integer logInsercao = 0;

    public DadosDao(){
        this.looca = new Looca();
        this.conexao = new Conexao();
        this.mysql = conexao.getConexaoDoBancoMySql();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
    }

    public void inserirDadosNoBanco(Totem totem){
        List<Disco> discos = looca.getGrupoDeDiscos().getDiscos();
        Disco disco = discos.get(0);
        Memoria memoria = looca.getMemoria();
        Processador cpu = looca.getProcessador();

        Double usoDoDisco = disco.getEscritas().doubleValue() / 1000000;
        Integer discoTotal = totem.getDiscoTotal();
        Double valorDisco = (usoDoDisco / discoTotal) * 100;
        Double valorRAM =  ((memoria.getEmUso().doubleValue() / 1000000000) / totem.getRAMTotal()) * 100;
        Double valorCPU = cpu.getUso().doubleValue();
        Integer totalUsbsConectados = looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().size();

        sqlServer.update("""
            INSERT INTO dados(dt_hora, valorDisco, valorMemoriaRAM, valorCPU, USB,  fktotem)
            VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)
            """, valorDisco, valorRAM, valorCPU, totalUsbsConectados, totem.getId());

        mysql.update("""
            INSERT INTO dados(dt_hora, valorDisco, valorMemoriaRAM, valorCPU, USB,  fktotem)
            VALUES (CURRENT_TIMESTAMP(), ?, ?, ?, ?, 1)
            """, valorDisco, valorRAM, valorCPU, totalUsbsConectados);

        totem.adicionaDados(this.getUltimoDadoAdicionado());
        if (logInsercao==0) {

            Log logger = new Log();
            logger.log("A aplicação está inserindo dados no banco.");
            logInsercao++;

        }
        sendMessages(totem);
    }

    public Dados getUltimoDadoAdicionado(){
        return sqlServer.queryForObject("SELECT TOP 1 * FROM dados ORDER BY dt_hora DESC", new BeanPropertyRowMapper<>(Dados.class));
    }

    public List<Dados> getDadosDoTotemPorFkTotem(Integer idTotem){
        return sqlServer.query("SELECT * FROM dados WHERE fktotem = ?", new BeanPropertyRowMapper<>(Dados.class), idTotem);
    }

    public List<Dados> getUltimos5RegistrosDoTotemPorId(Integer idTotem){
        return sqlServer.query("SELECT TOP 5 * FROM dados WHERE fktotem = ?  ORDER BY dt_hora DESC", new BeanPropertyRowMapper<>(Dados.class), idTotem);
    }

    public void sendMessages(Totem totem) {
        Boolean chamadoCritico = false;
        List<Dados> dados = totem.getDadosTotem();

        Indicador discoIndicador = mysql.queryForObject("SELECT (select LimiteDisco from Indicadores WHERE id = ?) FROM totem", new BeanPropertyRowMapper<>(Indicador.class), totem.getFkIndicadores());

        Indicador cpuIndicador = mysql.queryForObject("SELECT (select LimiteCPU from Indicadores WHERE id = ?) FROM totem", new BeanPropertyRowMapper<>(Indicador.class), totem.getFkIndicadores());

        Indicador ramIndicador = mysql.queryForObject("SELECT (select LimiteRAM from Indicadores WHERE id = ?) FROM totem", new BeanPropertyRowMapper<>(Indicador.class), totem.getFkIndicadores());

        Double limiteDisco = discoIndicador.getLimiteDisco();
        Double limiteCPU = cpuIndicador.getLimiteCpu();
        Double limiteRAM = ramIndicador.getLimiteRam();

        String mensagem = "Alerta: ";
        if(dados.get(0).getValorDisco() >= limiteDisco){
            mensagem += "Crítico DISCO";
            chamadoCritico = true;
        } else if (dados.get(0).getValorCPU() >= limiteCPU){
            mensagem += "Crítico CPU";
            chamadoCritico = true;
        } else if (dados.get(0).getValorMemoriaRAM() >= limiteRAM){
            mensagem += "Crítico RAM";
            chamadoCritico = true;
        }
        if(chamadoCritico){
            System.out.println("Chamado critico!!!");
            System.out.printf("VALOR DISCO: %.2f || LIMITE DISCO: %.2f || %s", dados.get(0).getValorDisco(), limiteDisco, dados.get(0).getValorDisco() >= limiteDisco);
            System.out.printf("VALOR CPU: %.2f || LIMITE CPU: %.2f || %s", dados.get(0).getValorCPU(), limiteCPU, dados.get(0).getValorCPU() >= limiteCPU);
            System.out.printf("VALOR RAM: %.2f || LIMITE RAM: %.2f || %s", dados.get(0).getValorMemoriaRAM(), limiteRAM, dados.get(0).getValorMemoriaRAM() >= limiteRAM);
            IntegracaoJira.criarChamado(totem, dados.get(0).getValorDisco(), dados.get(0).getValorCPU(), dados.get(0).getValorMemoriaRAM());
        }else{
            System.out.println("Chamado não critico!!");
        }
        //IntegracaoSlack slack = new IntegracaoSlack();
        //slack.sendMessage(mensagem);
    }
}