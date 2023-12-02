package arcadium.com.dao;

import arcadium.com.conexao.Conexao;
import arcadium.com.models.Indicador;
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
        return sqlServer.queryForObject("SELECT * FROM Indicadores WHERE id = ?", new BeanPropertyRowMapper<>(Indicador.class), id);
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

    public Boolean existeIndicadorPorFkEmpresa(Integer fkEmpresa){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM Indicadores WHERE fkEmpresa = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );     
                """, new Object[] {fkEmpresa}, Integer.class);

        return retornoQuery == 1 ? true : false;
    }

    public void inserirIndicador(Indicador i){
        sqlServer.update("INSERT INTO Indicadores(fkEmpresa, LimiteCPU, LimiteRAM, LimiteDISCO, IndicadorUSB) VALUES(?, ?, ?, ?, ?)", i.getFkEmpresa(), i.getLimiteCpu(), i.getLimiteRam(), i.getLimiteDisco(), i.getIndicadorUsb());
    }

    public void atualizarIndicadorPorFkEmpresa(Indicador i){
        sqlServer.update("UPDATE Indicadores SET LimiteCPU = ?, LimiteRAM = ?, LimiteDISCO = ?, IndicadorUSB = ? WHERE fkEmpresa = ?", i.getLimiteCpu(), i.getLimiteRam(), i.getLimiteDisco(), i.getIndicadorUsb(), i.getFkEmpresa());
    }

    public void atualizarLimiteDaCpu(Indicador i){
        sqlServer.update("UPDATE Indicadores SET LimiteCPU = ? WHERE id = ?", i.getLimiteCpu(), i.getFkEmpresa());
    }

    public void atualizarLimiteDaRam(Indicador i){
        sqlServer.update("UPDATE Indicadores SET LimiteRAM = ? WHERE id = ?", i.getLimiteRam(), i.getFkEmpresa());
    }

    public void atualizarLimiteDoDisco(Indicador i){
        sqlServer.update("UPDATE Indicadores SET LimiteDISCO = ? WHERE id = ?", i.getLimiteDisco(), i.getFkEmpresa());
    }

    public void atualizarIndicadorDeUsbs(Indicador i){
        sqlServer.update("UPDATE Indicadores SET IndicadorUSB = ? WHERE id = ?", i.getIndicadorUsb(), i.getFkEmpresa());
    }

    public Indicador getIndicadorPorFkEmpresa(Integer fkEmpresa) {
        return sqlServer.queryForObject("SELECT * FROM Indicadores WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Indicador.class), fkEmpresa);
    }
}
