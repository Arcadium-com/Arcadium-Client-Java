package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dados {
    private LocalDateTime dtHora;
    private Double valorDisco;
    private Double valorMemoriaRAM;
    private Double valorCPU;
    private Integer USB;
    private Integer fkTotem;
    private Integer fkLog;

    public Dados(){}

    public Dados(LocalDateTime dtHora, Double valorDisco, Double valorMemoriaRAM, Double valorCPU, Integer USB, Integer fkTotem, Integer fkLog) {
        this.dtHora = dtHora;
        this.valorDisco = valorDisco;
        this.valorMemoriaRAM = valorMemoriaRAM;
        this.valorCPU = valorCPU;
        this.USB = USB;
        this.fkTotem = fkTotem;
        this.fkLog = fkLog;
    }

    public LocalDateTime getDtHora() {
        return dtHora;
    }

    public void setDt_hora(LocalDateTime dt_hora) {
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

    @Override
    public String toString() {
        DateTimeFormatter formatadorDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        return String.format("""
            ID do Totem: %d
            Data do Registro: %s
            Uso do Disco: %.2f%%
            Uso da Memória RAM: %.2f%%
            Uso da CPU: %.2f%%
            Quantidade de USBs Conectados: %d
            """,
            this.fkTotem,
            formatadorDataHora.format(this.dtHora),
            this.valorDisco,
            this.valorMemoriaRAM,
            this.valorCPU,
            this.USB
        );

        // Quantidade de USBs Conectados: %d
        // Descrição do Log: %s

    }
}
