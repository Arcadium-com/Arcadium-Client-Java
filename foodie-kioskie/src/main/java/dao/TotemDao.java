package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
//import models.Dados;
import models.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
//import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.List;

public class TotemDao {
    Looca looca = new Looca();
    Sistema sistema = new Sistema();
    Conexao conexao = new Conexao();
    JdbcTemplate mysql = conexao.getConexaoDoBanco();

    public Totem inserirTotemNoBanco(Integer fkEmpresa){
        SistemaOperacionalDao soDao = new SistemaOperacionalDao();
        soDao.inserirSistemaOperacionalNoBanco(sistema);

        String distribuicaoSO = sistema.getFabricante();
        String versaoSO = sistema.getSistemaOperacional();

        soDao.inserirSistemaOperacionalNoBanco(sistema);
        Integer fkSO = soDao.getSistemaOperacionalPorDistribuicaoPorVersionamento(distribuicaoSO, versaoSO).get(0).getId();

        String enderecoMAC = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac();

        var totemComEnderecoMAC = this.getTotemPorEnderecoMac(enderecoMAC);

        if(totemComEnderecoMAC == null){
            Disco disco = looca.getGrupoDeDiscos().getDiscos().get(0);
            Memoria memoria = looca.getMemoria();

            // Formatação do valor do disco e memória. Dividindo o valor por 1000000000 e arrendondando.
            Integer totalDisco = Math.round(disco.getTamanho() / 1000000000);
            Integer totalRAM = Math.round(memoria.getTotal() / 1000000000);

            Double totalCPU = looca.getProcessador().getNumeroCpusLogicas().doubleValue();

            mysql.update("""
            INSERT INTO totem(fksistemaoperacional, fkEmpresa, dtInstalacao, RAMtotal, CPUtotal, DISCOTotal, enderecoMAC)
            VALUES (?, ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?)
            """,
                fkSO,
                fkEmpresa,
                totalRAM,
                totalCPU,
                totalDisco,
                enderecoMAC
            );

            return this.getUltimoTotemAdicionadoPorFkEmpresa(fkEmpresa);
        }

        return totemComEnderecoMAC;
    }

    public Totem getTotemPorEnderecoMac(String enderecoMAC){
        List<Totem> retornoQuery = mysql.query("SELECT * FROM totem WHERE enderecoMAC = ?", new BeanPropertyRowMapper<>(Totem.class), enderecoMAC);

        if(retornoQuery.size() == 0){
            return null;
        }

        return retornoQuery.get(0);
    }

    public Totem getUltimoTotemAdicionadoPorFkEmpresa(Integer fkEmpresa){
        return mysql.query("SELECT * FROM totem WHERE fkEmpresa = ? LIMIT 1", new BeanPropertyRowMapper<>(Totem.class), fkEmpresa).get(0);
    }

    public List<Totem> getTodosOsTotensPorEmpresa(Integer fkEmpresa){
        return mysql.query("SELECT * FROM totem WHERE fkEmpresa = ?", new BeanPropertyRowMapper<>(Totem.class), fkEmpresa);
    }

//    public List<Dados> getDadosDeTodosOsTotensPorEmpresa(Integer fkEmpresa){
//        List<Totem> listaTotens = this.getTodosOsTotensPorEmpresa(fkEmpresa);
//        List<Dados> listaDadosTotens = new ArrayList<>();
//
//        if(listaTotens.size() == 0){
//            return null;
//        }
//
//        for (Totem t : listaTotens) {
//            Dados retornoSql = mysql.queryForObject("SELECT * FROM dados WHERE fktotem = ?", new BeanPropertyRowMapper<>(Dados.class), t.getId());
//            listaDadosTotens.add(retornoSql);
//        }
//
//        return listaDadosTotens;
//    }

}
