package dao;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import models.SistemaOperacional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SistemaOperacionalDao {
    Conexao conexao = new Conexao();
    JdbcTemplate mysql = conexao.getConexaoDoBanco();

    public void inserirSistemaOperacionalNoBanco(Sistema sistema){
        String distribuicao = sistema.getFabricante();
        String versionamento = sistema.getSistemaOperacional();

        var existeSONoBanco = this.getSistemaOperacionalPorDistribuicaoPorVersionamento(distribuicao, versionamento);

        if (existeSONoBanco.size() == 0){
            mysql.update("INSERT INTO sistemaOperacional(id, distribuicao, versionamento) VALUES (null, ?, ?)",  distribuicao, versionamento);
        }
    }

    public List<SistemaOperacional> getTodosSistemasOperacionais(){
        return mysql.query("SELECT * FROM sistemaOperacional", new BeanPropertyRowMapper<>(SistemaOperacional.class));
    }

    public List<SistemaOperacional> getSistemaOperacionalPorDistribuicaoPorVersionamento(String distribuicao, String versionamento){
        var sistemaOperacional = mysql.query("SELECT * FROM sistemaOperacional WHERE distribuicao = ? AND versionamento = ?", new BeanPropertyRowMapper<>(SistemaOperacional.class), distribuicao, versionamento);

        return sistemaOperacional;
    }
}
