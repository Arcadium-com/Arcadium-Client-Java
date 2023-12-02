package arcadium.com.models;

import java.time.LocalTime;

public class AlertaSlack {
    private Integer id;
    private LocalTime horaAlertaSlack;
    private Integer fkTotem;

    public AlertaSlack(){}

    public AlertaSlack(Integer id, LocalTime horaAlertaSlack, Integer fkTotem) {
        this.id = id;
        this.horaAlertaSlack = horaAlertaSlack;
        this.fkTotem = fkTotem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHoraAlertaSlack() {
        return horaAlertaSlack;
    }

    public void setHoraAlertaSlack(LocalTime horaAlertaSlack) {
        this.horaAlertaSlack = horaAlertaSlack;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }
}
