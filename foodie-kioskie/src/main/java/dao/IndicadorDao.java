package dao;

import conexao.Conexao;
import models.Indicador;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class IndicadorDao {
    private Conexao conexao;
    private JdbcTemplate sqlServer;

    public IndicadorDao(){
        this.conexao = new Conexao();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
    }

    public Indicador getIndicadorById(Integer id){
        Boolean existeIndicador = this.existeIndicadorPorId(id);

        if(existeIndicador){
            return sqlServer.queryForObject("SELECT * FROM Indicadores WHERE id = ?", new BeanPropertyRowMapper<>(Indicador.class), id);
        }

        return null;
    }

    public List<Indicador> getAllIndicador(){
        return sqlServer.query("SELECT * FROM Indicadores", new BeanPropertyRowMapper<>(Indicador.class));
    }

    public Boolean existeIndicadorPorId(Integer id){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM Indicador WHERE id = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );           
                """, new Object[] {id}, Integer.class);

        return retornoQuery == 1 ? true : false;
    }
}
