public class Teste {
    private Integer id;
    private String nome;

    public Teste(){}

    public Teste(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return """
                   Teste{
                   "id": "%d",
                   "nome": "%s"
                }
               """.formatted(this.id, this.nome);
    }
}
