package models;

public class SistemaOperacional {
    private Integer id;
    private String distribuicao;
    private String versionamento;

    public SistemaOperacional(){}
    public SistemaOperacional(Integer id, String distribuicao, String versionamento) {
        this.id = id;
        this.distribuicao = distribuicao;
        this.versionamento = versionamento;
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

    @Override
    public String toString(){
        return """
            Sistema Operacional: {
               "id": %d,
               "distribuição" : %s,
               "versionamento": %s
            }    
        """.formatted(this.id, this.distribuicao, this.versionamento);
    }
}
