package arcadium.com.models;

import java.util.ArrayList;
import java.util.List;

public class Indicador {
    private Integer id;
    private Integer fkEmpresa;
    private Double limiteCpu;
    private Double limiteRam;
    private Double limiteDisco;
    private Integer indicadorUsb;

    public Indicador(){

    }

    public Indicador(Integer id, Integer fkEmpresa, Double limiteCpu, Double limiteRam, Double limiteDisco, Integer indicadorUsb) {
        this.id = id;
        this.fkEmpresa = fkEmpresa;
        this.limiteCpu = limiteCpu;
        this.limiteRam = limiteRam;
        this.limiteDisco = limiteDisco;
        this.indicadorUsb = indicadorUsb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
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

    public Integer getIndicadorUsb() {
        return indicadorUsb;
    }

    public void setIndicadorUsb(Integer indicadorUsb) {
        this.indicadorUsb = indicadorUsb;
    }

    @Override
    public String toString() {
        return """
        Id: %d
        Fk da Empresa: %d
        Limite da CPU: %.2f
        Limite da RAM: %.2f
        Limite do Disco: %.2f
        Indicador do USB: %d
        """.formatted(
            this.id,
            this.fkEmpresa,
            this.limiteCpu,
            this.limiteRam,
            this.limiteDisco,
            this.indicadorUsb
        );
    }
}
