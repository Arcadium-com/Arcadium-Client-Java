package dao;

import conexao.Conexao;
import models.Empresa;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmpresaDao {
    Conexao conexao = new Conexao();
    JdbcTemplate mysql = conexao.getConexaoDoBanco();

    public Empresa getEmpresaPorId(Integer id){
        return mysql.queryForObject("SELECT * FROM Empresa WHERE id = ?", new BeanPropertyRowMapper<>(Empresa.class), id);
    }

    public Boolean existeEmpresaPorId(Integer id){
        return mysql.queryForObject("SELECT EXISTS(SELECT 1 FROM Empresa WHERE id = ?) AS existe", new Object[] {id}, Boolean.class);
    }

}
