package arcadium.com.dao;
import com.github.britooo.looca.api.group.sistema.Sistema;
import arcadium.com.conexao.Conexao;
import arcadium.com.models.SistemaOperacional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SistemaOperacionalDao {
    private Conexao conexao;
    private JdbcTemplate sqlServer;
    private JdbcTemplate mysql;

    public SistemaOperacionalDao(){
        this.conexao = new Conexao();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
        this.mysql = conexao.getConexaoDoBancoMySql();
    }

    public void inserirSistemaOperacionalNoBanco(Sistema sistema){
        String distribuicao = sistema.getFabricante();
        String versionamento = sistema.getSistemaOperacional();

        Boolean existeSONoBanco = this.existeSistemaOperacionalPorDistribuicaoEVersionamento(distribuicao, versionamento);

        if (!existeSONoBanco){
            sqlServer.update("INSERT INTO sistemaOperacional(distribuicao, versionamento) VALUES (?, ?)",  distribuicao, versionamento);
            mysql.update("INSERT INTO sistemaOperacional(id, distribuicao, versionamento) VALUES (null, ?, ?)",  distribuicao, versionamento);
        }
    }

    public List<SistemaOperacional> getTodosSistemasOperacionais(){
        return sqlServer.query("SELECT * FROM sistemaOperacional", new BeanPropertyRowMapper<>(SistemaOperacional.class));
    }

    public List<SistemaOperacional> getSistemaOperacionalPorDistribuicaoPorVersionamento(String distribuicao, String versionamento){
        var sistemaOperacional = sqlServer.query("SELECT * FROM sistemaOperacional WHERE distribuicao = ? AND versionamento = ?", new BeanPropertyRowMapper<>(SistemaOperacional.class), distribuicao, versionamento);

        return sistemaOperacional;
    }

    public Boolean existeSistemaOperacionalPorDistribuicaoEVersionamento(String distribuicao, String versionamento){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM sistemaOperacional WHERE distribuicao = ? AND versionamento = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT  
                    );
                                
                """, new Object[] {distribuicao, versionamento}, Integer.class);
        return retornoQuery == 1 ? true : false;
    }
}
