package dao;

import conexao.Conexao;
import models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsuarioDao {
    private Conexao conexao;
    private JdbcTemplate mysql;
    private JdbcTemplate sqlServer;

    public UsuarioDao(){
        this.conexao = new Conexao();
        this.mysql = conexao.getConexaoDoBancoMySql();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
    }

    public Usuario entrarNaConta(String email, String senha){
        if(existeUsuarioPorEmailESenha(email, senha)){
            return sqlServer.queryForObject("SELECT * FROM Usuario WHERE email = ? AND senha = ?", new BeanPropertyRowMapper<>(Usuario.class), email, senha);
        }

        return null;
    }

    public Boolean existeUsuarioPorEmailESenha(String email, String senha){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM Usuario WHERE email = ? AND senha = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );       
        """, new Object[]{email, senha}, Integer.class);
        return retornoQuery == 1 ? true : false;
    }
}
