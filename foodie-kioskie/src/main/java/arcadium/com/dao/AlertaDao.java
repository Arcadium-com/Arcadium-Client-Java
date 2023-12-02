package arcadium.com.dao;

import arcadium.com.conexao.Conexao;
import arcadium.com.models.Alerta;
import org.checkerframework.checker.units.qual.A;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AlertaDao {
    private Conexao conexao;
    private JdbcTemplate mysql;

    public AlertaDao() {
        this.conexao = new Conexao();
        this.mysql = conexao.getConexaoDoBancoMySql();
    }

    public void inserirAlerta(Alerta a){
        mysql.update("INSERT INTO alerta(dtHoraAlerta, dtHoraConclusaoAlerta, fkTotem) VALUES (?, ?, ?)", a.getDtHoraAlerta(), a.getDtHoraConclusaoAlerta() ,a.getFkTotem());
    }

    public List<Alerta> getAllAlerta(){
        return mysql.query("SELECT * FROM alerta", new BeanPropertyRowMapper<>(Alerta.class));
    }
}
