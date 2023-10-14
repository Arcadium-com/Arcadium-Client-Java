package models;

import java.util.ArrayList;
import java.util.List;

public class Indicadores {
    private Integer id;
    private Double limiteCPU;
    private Double limiteRAM;
    private Double limiteDisco;
    private List<Totem> listaTotens;

    public Indicadores(Double limiteCPU, Double limiteRAM, Double limiteDisco) {
        this.limiteCPU = limiteCPU;
        this.limiteRAM = limiteRAM;
        this.limiteDisco = limiteDisco;
        this.listaTotens = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Double getLimiteCPU() {
        return limiteCPU;
    }

    public void setLimiteCPU(Double limiteCPU) {
        this.limiteCPU = limiteCPU;
    }

    public Double getLimiteRAM() {
        return limiteRAM;
    }

    public void setLimiteRAM(Double limiteRAM) {
        this.limiteRAM = limiteRAM;
    }

    public Double getLimiteDisco() {
        return limiteDisco;
    }

    public void setLimiteDisco(Double limiteDisco) {
        this.limiteDisco = limiteDisco;
    }

    public List<Totem> getListaTotens() {
        return listaTotens;
    }

}
