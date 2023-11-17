package arcadium.com.dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.sistema.Sistema;
import arcadium.com.conexao.Conexao;
//import arcadium.com.models.Dados;
import arcadium.com.models.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
//import java.util.ArrayList;
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

        String enderecoMAC = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac();

        Boolean existTotemPorEnderecoMac = this. existTotemPorEnderecoMac(enderecoMAC);

        if(!existTotemPorEnderecoMac){
            Disco disco = looca.getGrupoDeDiscos().getDiscos().get(0);
            Memoria memoria = looca.getMemoria();

            // Formatação do valor do disco e memória. Dividindo o valor por 1000000000 e arrendondando.
            Integer totalDisco = Math.round(disco.getTamanho() / 1000000000);
            Integer totalRAM = Math.round(memoria.getTotal() / 1000000000);

            Double totalCPU = looca.getProcessador().getNumeroCpusLogicas().doubleValue();

            sqlServer.update("""
            INSERT INTO totem(fksistemaoperacional, fkEmpresa, dtInstalacao, RAMtotal, CPUtotal, DISCOTotal, enderecoMAC)
            VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)
            """,
                fkSO,
                fkEmpresa,
                totalRAM,
                totalCPU,
                totalDisco,
                enderecoMAC
            );

            mysql.update("""
            INSERT INTO totem(fksistemaoperacional, fkEmpresa, dtInstalacao, RAMtotal, CPUtotal, DISCOTotal, enderecoMAC)
            VALUES (1, ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?)
            """,
                fkEmpresa,
                totalRAM,
                totalCPU,
                totalDisco,
                enderecoMAC
            );

            return this.getUltimoTotemAdicionadoPorFkEmpresa(fkEmpresa);
        }

        return this.getTotemPorEnderecoMac();
    }

    public Boolean existTotemPorEnderecoMac(String enderecoMAC){
        var retornoQuery = sqlServer.queryForObject("""
                SELECT CAST(
                    CASE
                        WHEN (SELECT 1 WHERE EXISTS(SELECT * FROM totem WHERE enderecoMAC = ?)) = 1 THEN 1
                        ELSE 0
                        end as BIT
                    );
        """, new Object[] {enderecoMAC}, Integer.class);
        return retornoQuery == 1 ? true : false;
    }

    public Totem getTotemPorEnderecoMac(){
        String enderecoMAC = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac();

        return sqlServer.queryForObject("SELECT * FROM totem WHERE enderecoMAC = ?", new BeanPropertyRowMapper<>(Totem.class), enderecoMAC);
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
