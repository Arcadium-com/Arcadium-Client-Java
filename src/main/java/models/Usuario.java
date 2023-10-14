package models;

public class Usuario {
    private Integer id;
    private String email;
    private String senha;
    private Integer fkPermissao;
    private Integer fkEmpresa;
    public Usuario(String email, String senha, Integer fkPermissao, Integer fkEmpresa){
        this.email = email;
        this.senha = senha;
        this.fkPermissao = fkPermissao;
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getFkPermissao() {
        return fkPermissao;
    }

    public void setFkPermissao(Integer fkPermissao) {
        this.fkPermissao = fkPermissao;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    @Override
    public String toString(){
        String senha = "";

        for (int i = 0; i < this.senha.length(); i++) {
            senha += "*";
        }
        return """
            Permissão: {
               "id": %d,
               "email" : %s,
               "senha": %s,
               "fkPermissao": %d,
               "fkEmpresa": %d,
            }    
        """.formatted(this.id, this.email, senha, this.fkPermissao, this.fkEmpresa);

    }
}
