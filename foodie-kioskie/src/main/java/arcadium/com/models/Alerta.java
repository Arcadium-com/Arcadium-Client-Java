package arcadium.com.models;

import java.time.LocalDateTime;

public class Alerta {
    private LocalDateTime dtHoraAlerta;
    private LocalDateTime dtHoraConclusaoAlerta;
    private Integer fkTotem;

    public Alerta(){}

    public Alerta(LocalDateTime dtHoraAlerta, LocalDateTime dtHoraConclusaoAlerta, Integer fkTotem) {
        this.dtHoraAlerta = dtHoraAlerta;
        this.dtHoraConclusaoAlerta = dtHoraConclusaoAlerta;
        this.fkTotem = fkTotem;
    }

    public LocalDateTime getDtHoraAlerta() {
        return dtHoraAlerta;
    }

    public void setDtHoraAlerta(LocalDateTime dtHoraAlerta) {
        this.dtHoraAlerta = dtHoraAlerta;
    }

    public LocalDateTime getDtHoraConclusaoAlerta() {
        return dtHoraConclusaoAlerta;
    }

    public void setDtHoraConclusaoAlerta(LocalDateTime dtHoraConclusaoAlerta) {
        this.dtHoraConclusaoAlerta = dtHoraConclusaoAlerta;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

    @Override
    public String toString() {
        return """
        Data e Hora do Alerta: %s
        Data e Hora Prevista para Conclusão: %s
        FkTotem %d
        """.formatted(
            this.dtHoraAlerta,
            this.dtHoraConclusaoAlerta,
            this.fkTotem
        );
    }
}
