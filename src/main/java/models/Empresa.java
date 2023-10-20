package models;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private Integer id;
    private String nomeResponsavel;
    private String nomeFantasia;
    private String cnpj;
    private String tel;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private Integer fkEstado;
    private List<Totem> totens;
    private List<Usuario> usuarios;

    public Empresa(){}

    public Empresa(String nomeResponsavel, String nomeFantasia, String cnpj, String tel, String tipoLogradouro, String logradouro, String numero, String bairro, String cidade, Integer fkEstado) {
        this.nomeResponsavel = nomeResponsavel;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.tel = tel;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.fkEstado = fkEstado;
        this.totens = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void adicionarTotem(Totem t){
        this.totens.add(t);
    }
    public void adicionarUsuario(Usuario u){
        this.usuarios.add(u);
    }

    public Integer getId() {
        return id;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getFkEstado() {
        return fkEstado;
    }

    public void setFkEstado(Integer fkEstado) {
        this.fkEstado = fkEstado;
    }

    public List<Totem> getTotens(){
        return this.totens;
    }
    public List<Usuario> getUsuario(){
        return this.usuarios;
    }
}
