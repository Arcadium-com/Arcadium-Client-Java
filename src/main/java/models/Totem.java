package models;
import oshi.hardware.UsbDevice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Totem {
    private Integer id;
    private Integer fkIndicadores;
    private Integer fkStatus;
    private Integer fkEmpresa;
    private Integer fkSistemaOperacional;
    private Date dtInstalacao;
    private Integer RAMTotal;
    private Double CPUTotal;
    private Integer discoTotal;
    private Integer USB;
    private List<UsbDevice> listaUsbs;
    private List<Dados> dadosTotem;

    public Totem(Integer fkIndicadores, Integer fkStatus, Integer fkEmpresa, Integer fkSistemaOperacional, Date dtInstalacao, Integer RAMTotal, Double CPUTotal, Integer discoTotal, Integer usb) {
        this.fkIndicadores = fkIndicadores;
        this.fkStatus = fkStatus;
        this.fkEmpresa = fkEmpresa;
        this.fkSistemaOperacional = fkSistemaOperacional;
        this.dtInstalacao = dtInstalacao;
        this.RAMTotal = RAMTotal;
        this.CPUTotal = CPUTotal;
        this.discoTotal = discoTotal;
        this.USB = usb;
        this.listaUsbs = new ArrayList<UsbDevice>();
        this.dadosTotem = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getFkIndicadores() {
        return fkIndicadores;
    }

    public void setFkIndicadores(Integer fkIndicadores) {
        this.fkIndicadores = fkIndicadores;
    }

    public Integer getFkStatus() {
        return fkStatus;
    }

    public void setFkStatus(Integer fkStatus) {
        this.fkStatus = fkStatus;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getFkSistemaOperacional() {
        return fkSistemaOperacional;
    }

    public void setFkSistemaOperacional(Integer fkSistemaOperacional) {
        this.fkSistemaOperacional = fkSistemaOperacional;
    }

    public Date getDtInstalacao() {
        return dtInstalacao;
    }

    public void setDtInstalacao(Date dtInstalacao) {
        this.dtInstalacao = dtInstalacao;
    }

    public Integer getRAMTotal() {
        return RAMTotal;
    }

    public void setRAMTotal(Integer RAMTotal) {
        this.RAMTotal = RAMTotal;
    }

    public Double getCPUTotal() {
        return CPUTotal;
    }

    public void setCPUTotal(Double CPUTotal) {
        this.CPUTotal = CPUTotal;
    }

    public Integer getDiscoTotal() {
        return discoTotal;
    }

    public void setDiscoTotal(Integer discoTotal) {
        this.discoTotal = discoTotal;
    }

    public List<UsbDevice> getListaUsbs() {
        return listaUsbs;
    }

    public List<Dados> getDadosTotem() {
        return dadosTotem;
    }

    @Override
    public String toString(){
        return """
            Totem: {
               "id": %d,
               "fkIndicadores" : %d,
               "fkStatus" : %d,
               "fkEmpresa" : %d,
               "fkSistemaOperacional" : %d,
               "dtInstalação" : %s,
               "RAMTotal" : %d,
               "CPUTotal" : %.2f,
               "discoTotal" : %d,
               "USB" : %d,
            }    
        """.formatted(
                this.id,
                this.fkIndicadores,
                this.fkStatus,
                this.fkEmpresa,
                this.fkSistemaOperacional,
                this.dtInstalacao,
                this.RAMTotal,
                this.CPUTotal,
                this.USB
        );
    }
}

