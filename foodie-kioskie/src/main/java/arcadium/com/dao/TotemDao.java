package arcadium.com.dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.sistema.Sistema;
import arcadium.com.conexao.Conexao;
import arcadium.com.models.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.net.InetAddress;
import java.util.List;

public class TotemDao {
    private Looca looca;
    private Sistema sistema;
    private Conexao conexao;
    private JdbcTemplate sqlServer;
    private JdbcTemplate mysql;
    public TotemDao(){
        this.looca = new Looca();
        this.sistema = new Sistema();
        this.conexao = new Conexao();
        this.sqlServer = conexao.getConexaoDoBancoSqlServer();
        this.mysql = conexao.getConexaoDoBancoMySql();
    }

    public Totem inserirTotemNoBanco(Integer fkEmpresa){
        SistemaOperacionalDao soDao = new SistemaOperacionalDao();
        soDao.inserirSistemaOperacionalNoBanco(sistema);

        String distribuicaoSO = sistema.getFabricante();
        String versaoSO = sistema.getSistemaOperacional();

        soDao.inserirSistemaOperacionalNoBanco(sistema);
        Integer fkSO = soDao.getSistemaOperacionalPorDistribuicaoPorVersionamento(distribuicaoSO, versaoSO).get(0).getId();

        String hostname = "";

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new Error(e);
        }

        Boolean existTotemPorHostname = this.existTotemPorHostname();

        if(!existTotemPorHostname){
            Disco disco = looca.getGrupoDeDiscos().getDiscos().get(0);
            Memoria memoria = looca.getMemoria();

            // Formatação do valor do disco e memória. Dividindo o valor por 1000000000 e arrendondando.
            Integer totalDisco = Math.round(disco.getTamanho() / 1000000000);
            Integer totalRAM = Math.round(memoria.getTotal() / 1000000000);

            Double totalCPU = looca.getProcessador().getNumeroCpusLogicas().doubleValue();

            sqlServer.update("""
            INSERT INTO totem(fksistemaoperacional, fkEmpresa, dtInstalacao, RAMtotal, CPUtotal, DISCOTotal, hostname, fkstatus)
            VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, 1)
            """,
                    fkSO,
                    fkEmpresa,
                    totalRAM,
                    totalCPU,
                    totalDisco,
                    hostname
            );

            mysql.update("""
            INSERT INTO totem(id, dtInstalacao, RAMtotal, CPUtotal, DISCOTotal, hostname)
            VALUES (1, CURRENT_TIMESTAMP(), ?, ?, ?, ?)
            """,
                    totalRAM,
                    totalCPU,
                    totalDisco,
                    hostname
            );

            return this.getUltimoTotemAdicionadoPorFkEmpresa(fkEmpresa);
        }

        return this.getTotemPorHostname();
    }

    public Boolean existTotemPorHostname(){
        String hostname;

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new Error(e);
        }

        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM totem WHERE hostname = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );
        """, new Object[] {hostname}, Integer.class);
        return retornoQuery == 1 ? true : false;
    }

    public Totem getTotemPorHostname(){
        String hostname = "";

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
           throw new Error(e);
        }

        if(!existTotemPorHostname()){
            return null;
        }

        return sqlServer.queryForObject("SELECT * FROM totem WHERE hostname = ?", new BeanPropertyRowMapper<>(Totem.class), hostname);
    }

    public Totem getTotemPorId(Integer idTotem){
        return sqlServer.queryForObject("SELECT * FROM totem WHERE id = ?", new BeanPropertyRowMapper<>(Totem.class), idTotem
        );
    }
    public Totem getTotemPorFkEmpresa(Integer fkEmpresa){
        return sqlServer.queryForObject("SELECT * FROM totem WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Totem.class), fkEmpresa
        );
    }

    public Totem getUltimoTotemAdicionadoPorFkEmpresa(Integer fkEmpresa){
        return sqlServer.queryForObject("SELECT TOP 1 * FROM totem WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Totem.class), fkEmpresa);
    }

    public List<Totem> getTodosOsTotensPorEmpresa(Integer fkEmpresa){
        return sqlServer.query("SELECT * FROM totem WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Totem.class), fkEmpresa);
    }

    public List<Totem> getTodosOsTotensPorFkIndicador(Integer fkIndicadores){
        return sqlServer.query("SELECT * FROM totem WHERE fkindicadores = ?", new BeanPropertyRowMapper<>(Totem.class), fkIndicadores);
    }

}
