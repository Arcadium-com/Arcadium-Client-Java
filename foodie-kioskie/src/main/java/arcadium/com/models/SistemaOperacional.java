package arcadium.com.models;

import java.util.ArrayList;
import java.util.List;

public class SistemaOperacional {
    private Integer id;
    private String distribuicao;
    private String versionamento;
    private List<Totem> totens;

    public SistemaOperacional(){}

    public SistemaOperacional(Integer id, String distribuicao, String versionamento) {
        this.id = id;
        this.distribuicao = distribuicao;
        this.versionamento = versionamento;
        this.totens = new ArrayList<>();
    }

    public void adicionarTotem(Totem t){
        this.totens.add(t);
    }

    public void removeTotem(Totem t){
        this.totens.remove(t);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistribuicao() {
        return distribuicao;
    }

    public void setDistribuicao(String distribuicao) {
        this.distribuicao = distribuicao;
    }

    public String getVersionamento() {
        return versionamento;
    }

    public void setVersionamento(String versionamento) {
        this.versionamento = versionamento;
    }

    public List<Totem> getTotens() {
        return totens;
    }

}
