package models;

import java.util.ArrayList;
import java.util.List;

public class Indicador {
    private Integer id;
    private Double limiteCpu;
    private Double limiteRam;
    private Double limiteDisco;
    private List<Totem> totens;

    public Indicador(){
        this.totens = new ArrayList<>();
    }

    public Indicador(Integer id, Double limiteCpu, Double limiteRam, Double limiteDisco) {
        this.id = id;
        this.limiteCpu = limiteCpu;
        this.limiteRam = limiteRam;
        this.limiteDisco = limiteDisco;
        this.totens = new ArrayList<>();
    }

    public void adicionaTotem(Totem t){
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

    public Double getLimiteCpu() {
        return limiteCpu;
    }

    public void setLimiteCpu(Double limiteCpu) {
        this.limiteCpu = limiteCpu;
    }

    public Double getLimiteRam() {
        return limiteRam;
    }

    public void setLimiteRam(Double limiteRam) {
        this.limiteRam = limiteRam;
    }

    public Double getLimiteDisco() {
        return limiteDisco;
    }

    public void setLimiteDisco(Double limiteDisco) {
        this.limiteDisco = limiteDisco;
    }

    @Override
    public String toString() {
        return """
        Id: %d
        Limite da CPU: %.2f
        Limite da RAM: %.2f
        Limite do Disco: %.2f
        """.formatted(
            this.id,
            this.limiteCpu,
            this.limiteRam,
            this.limiteDisco
        );
    }
}
