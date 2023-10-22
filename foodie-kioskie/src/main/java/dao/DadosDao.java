package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import models.Dados;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DadosDao {
    Looca looca = new Looca();
    Conexao conexao = new Conexao();
    JdbcTemplate mysql = conexao.getConexaoDoBanco();

    public void inserirDadosNoBanco(Integer fkTotem){
        Double usoDoDisco = looca.getGrupoDeDiscos().getDiscos().get(0).getEscritas().doubleValue() / 1000000;
        Integer discoTotal = Integer.parseInt(looca.getGrupoDeDiscos().getDiscos().get(0).getTamanho().toString().substring(0, 3));
        Double valorDisco = (usoDoDisco / discoTotal) * 100;
        Double valorRAM =  (looca.getMemoria().getEmUso().doubleValue() / looca.getMemoria().getTotal().doubleValue()) * 100;
        Double valorCPU = looca.getProcessador().getUso().doubleValue();

        mysql.update(""" 
            INSERT INTO dados(dt_hora, valorDisco, valorMemoriaRAM, valorCPU,  fktotem)
            VALUES (CURRENT_TIMESTAMP(), ?, ?, ?, ?)
            """, valorDisco, valorRAM, valorCPU, fkTotem);
    }

    public List<Dados> getUltimos5RegistrosDoTotemPorId(Integer idTotem){
        return mysql.query("SELECT * FROM dados WHERE fktotem = ?  ORDER BY dt_hora DESC LIMIT 5", new BeanPropertyRowMapper<>(Dados.class), idTotem);
    }
}
