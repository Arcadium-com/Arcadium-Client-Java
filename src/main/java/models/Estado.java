package models;

public class Estado {
    private Integer id;
    private String sigla;

    public Estado(String sigla) {
        this.sigla = sigla;
    }

    public Integer getId() {
        return id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
