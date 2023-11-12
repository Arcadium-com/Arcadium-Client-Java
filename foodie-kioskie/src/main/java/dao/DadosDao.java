package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import conexao.Conexao;
import models.Dados;
import models.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class DadosDao {
    private Looca looca;
    private Conexao conexao;
    private JdbcTemplate mysql;
    private JdbcTemplate sqlServer;

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
}
