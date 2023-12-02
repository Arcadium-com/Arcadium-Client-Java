package arcadium.com.models;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Dados {
    private LocalDateTime dtHora;
    private Double valorDisco;
    private Double valorMemoriaRAM;
    private Double valorCPU;
    private Integer USB;
    private Integer fkTotem;

    public Dados(){}

    public Dados(LocalDateTime dtHora, Double valorDisco, Double valorMemoriaRAM, Double valorCPU, Integer USB, Integer fkTotem) {
        this.dtHora = dtHora;
        this.valorDisco = valorDisco;
        this.valorMemoriaRAM = valorMemoriaRAM;
        this.valorCPU = valorCPU;
        this.USB = USB;
        this.fkTotem = fkTotem;
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
