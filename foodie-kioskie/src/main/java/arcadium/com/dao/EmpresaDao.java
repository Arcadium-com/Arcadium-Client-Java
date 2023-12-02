package arcadium.com.dao;

import arcadium.com.conexao.Conexao;
import arcadium.com.models.Empresa;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmpresaDao {
    Conexao conexao = new Conexao();
    JdbcTemplate sqlServer = conexao.getConexaoDoBancoSqlServer();

    public Empresa getEmpresaPorId(Integer id){
        return sqlServer.queryForObject("SELECT * FROM Empresa WHERE id = ?", new BeanPropertyRowMapper<>(Empresa.class), id);
    }

    public Boolean existeEmpresaPorId(Integer id){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM Empresa WHERE id = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );
        """, new Object[] {id}, Integer.class);
        return retornoQuery == 1 ? true : false;
    }

}