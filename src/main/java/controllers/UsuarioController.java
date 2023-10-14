package controllers;

import database.Conexao;
import models.Empresa;
import models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsuarioController {
    private Conexao conexaoBd;
    private JdbcTemplate templateBd;

    public UsuarioController(Conexao conexaoBd) {
        this.conexaoBd = conexaoBd;
        templateBd = conexaoBd.getConexaoDoBanco();
    }

    public void cadastraUsuario(String email, String senha, Integer fkPermissao, Integer fkEmpresa){
        templateBd.update("INSERT INTO Usuario VALUES (null, ?, ?, ?, ?)", email, senha, fkPermissao, fkEmpresa);
        Usuario usuario = new Usuario(email, senha, fkPermissao, fkEmpresa);

    }
}
