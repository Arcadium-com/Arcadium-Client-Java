package models;

public class Permissao {
    private Integer id;
    private String autoridade;

    public Permissao(String autoridade) {
        this.autoridade = autoridade;
    }

    public Integer getId() {
        return id;
    }

    public String getAutoridade() {
        return autoridade;
    }

    public void setAutoridade(String autoridade) {
        this.autoridade = autoridade;
    }

    @Override
    public String toString(){
        return """
            Permissão: {
               "id": %d,
               "autoridade" : %s
            }    
        """.formatted(this.id, this.autoridade);
    }
}
