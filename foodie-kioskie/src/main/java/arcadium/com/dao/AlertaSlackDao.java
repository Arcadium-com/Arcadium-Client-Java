package arcadium.com.dao;

import arcadium.com.conexao.Conexao;
import arcadium.com.models.AlertaSlack;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AlertaSlackDao {
    private Conexao conexao;
    private JdbcTemplate mysql;

    public AlertaSlackDao() {
        this.conexao = new Conexao();
        this.mysql = conexao.getConexaoDoBancoMySql();
    }

    public void inserirAlertaSlack(AlertaSlack aS){
        mysql.update("INSERT INTO alertaSlack(horaAlertaSlack, fkTotem) VALUES (?, ?)", aS.getHoraAlertaSlack(), aS.getFkTotem());
    }

    public List<AlertaSlack> getAllAlertaSlack(){
        return mysql.query("SELECT * FROM alertaSlack", new BeanPropertyRowMapper<>(AlertaSlack.class));
    }
}
