package arcadium.com.dao;

import arcadium.com.conexao.Conexao;
import arcadium.com.models.StatusTotem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatusTotemDao {
    private Conexao conexao;
    private JdbcTemplate sqlServer;
    private JdbcTemplate mysql;

    public StatusTotemDao(){
        this.conexao = new Conexao();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
        this.mysql = conexao.getConexaoDoBancoMySql();
    }

    public StatusTotem getStatusTotemByName(String nome){
        return sqlServer.queryForObject("SELECT * FROM statusTotem WHERE statusTotem = ?", new BeanPropertyRowMapper<>(StatusTotem.class), nome.toLowerCase());
    }



}
