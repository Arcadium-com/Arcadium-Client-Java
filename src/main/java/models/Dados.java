package models;

import java.time.LocalDateTime;
import java.util.Date;

public class Dados {
    private Date dtHora;
    private Double valorDisco;
    private Double valorMemoriaRAM;
    private Double valorCPU;
    private Integer USB;
    private Integer fkTotem;
    private Integer fkLog;

    public Dados(Date dtHora, Double valorDisco, Double valorMemoriaRAM, Double valorCPU, Integer USB, Integer fkTotem, Integer fkLog) {
        this.dtHora = dtHora;
        this.valorDisco = valorDisco;
        this.valorMemoriaRAM = valorMemoriaRAM;
        this.valorCPU = valorCPU;
        this.USB = USB;
        this.fkTotem = fkTotem;
        this.fkLog = fkLog;
    }

    public Date getDtHora() {
        return dtHora;
    }

    public void setDt_hora(Date dt_hora) {
        this.dtHora = dt_hora;
    }

    public Double getValorDisco() {
        return valorDisco;
    }

    public void setValorDisco(Double valorDisco) {
        this.valorDisco = valorDisco;
    }

    public Double getValorMemoriaRAM() {
        return valorMemoriaRAM;
    }

    public void setValorMemoriaRAM(Double valorMemoriaRAM) {
        this.valorMemoriaRAM = valorMemoriaRAM;
    }

    public Double getValorCPU() {
        return valorCPU;
    }

    public void setValorCPU(Double valorCPU) {
        this.valorCPU = valorCPU;
    }

    public Integer getUSB() {
        return USB;
    }

    public void setUSB(Integer USB) {
        this.USB = USB;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

    public Integer getFkLog() {
        return fkLog;
    }

    public void setFkLog(Integer fkLog) {
        this.fkLog = fkLog;
    }
}
