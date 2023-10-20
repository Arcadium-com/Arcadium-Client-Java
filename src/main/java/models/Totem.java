package models;
import org.springframework.cglib.core.Local;
import oshi.hardware.UsbDevice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Totem {
    private Integer id;
    private Integer fkIndicadores;
    private Integer fkStatus;
    private Integer fkEmpresa;
    private Integer fkSistemaOperacional;
    private LocalDate dtInstalacao;
    private Integer RAMTotal;
    private Double CPUTotal;
    private Integer discoTotal;
    private String enderecoMAC;
    private Integer USB;
    private List<UsbDevice> listaUsbs;
    private List<Dados> dadosTotem;

    public Totem(){}

    public Totem(Integer id, Integer fkIndicadores, Integer fkStatus, Integer fkEmpresa, Integer fkSistemaOperacional, LocalDate dtInstalacao, Integer RAMTotal, Double CPUTotal, Integer discoTotal, String enderecoMAC, Integer usb) {
        this.fkIndicadores = fkIndicadores;
        this.fkStatus = fkStatus;
        this.fkEmpresa = fkEmpresa;
        this.fkSistemaOperacional = fkSistemaOperacional;
        this.dtInstalacao = dtInstalacao;
        this.RAMTotal = RAMTotal;
        this.CPUTotal = CPUTotal;
        this.discoTotal = discoTotal;
        this.enderecoMAC = enderecoMAC;
        this.USB = usb;
        this.listaUsbs = new ArrayList<UsbDevice>();
        this.dadosTotem = new ArrayList<>();
    }

    public void adicionarUsb(UsbDevice u){
        this.listaUsbs.add(u);
    }

    public void adicionarDados(Dados d){
        this.dadosTotem.add(d);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getDtInstalacao() {
        return dtInstalacao;
    }

    public void setDtInstalacao(LocalDate dtInstalacao) {
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

    public String getEnderecoMAC() {
        return enderecoMAC;
    }

    public void setEnderecoMAC(String enderecoMAC) {
        this.enderecoMAC = enderecoMAC;
    }

    public List<UsbDevice> getListaUsbs() {
        return listaUsbs;
    }

    public List<Dados> getDadosTotem() {
        return dadosTotem;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatadorDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return """
           Id do Totem: %d
           Data de Instalação do FoodieKioskie: %s
           Total da Memória RAM: %d
           Total de Núcleos CPU: %.0f
           Total do Disco (HD/SSD): %d
           Endereço MAC: %s
        """.formatted(
                this.id,
                // this.fkIndicadores,
                // this.fkStatus,
                //this.fkEmpresa,
                //this.fkSistemaOperacional,
                formatadorDataHora.format(this.dtInstalacao),
                this.RAMTotal,
                this.CPUTotal,
                this.discoTotal,
                this.enderecoMAC
                //this.USB
        );
    }
}

