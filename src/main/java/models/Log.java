package models;

import models.Dados;

import java.util.List;

public class Log {
    private Integer id;
    private String log;

    private List<Dados> listaDados;

    public Log(String log) {
        this.log = log;
    }

    public Integer getId() {
        return id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<Dados> getListaDados() {
        return listaDados;
    }

    public void setListaDados(List<Dados> listaDados) {
        this.listaDados = listaDados;
    }
}
