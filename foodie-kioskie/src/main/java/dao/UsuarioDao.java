package dao;

import conexao.Conexao;
import models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsuarioDao {
    Conexao conexao = new Conexao();
    JdbcTemplate mysql = conexao.getConexaoDoBanco();

    public Usuario entrarNaConta(String email, String senha){
        if (email.isEmpty() || senha.isEmpty()){
            System.out.println("Preencha os campos solicitados.");
            return null;
        } else {
            var usuario = mysql.query("SELECT * FROM Usuario WHERE email = ? AND senha = ?", new BeanPropertyRowMapper<>(Usuario.class), email, senha);

            if(usuario.size() == 0){
                System.out.println("Email e/ou Senha incorretos.");
                return null;
            } else {
                return usuario.get(0);
            }
        }
    }
}
