package arcadium.com.models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Totem {
    private Integer id;
    private Integer fkStatus;
    private Integer fkEmpresa;
    private Integer fkSistemaOperacional;
    private LocalDate dtInstalacao;
    private Integer RAMTotal;
    private Double CPUTotal;
    private Integer discoTotal;
    private String hostname;
    private List<Dados> dadosTotem;
    private List<Alerta> alertas;
    private List<AlertaSlack> alertasSlack;

    public Totem(){
        this.dadosTotem = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.alertasSlack = new ArrayList<>();
    }

    public Totem(Integer id, Integer fkEmpresa, Integer fkStatus, Integer fkSistemaOperacional, LocalDate dtInstalacao, Integer RAMTotal, Double CPUTotal, Integer discoTotal, String hostname) {
        this.id = id;
        this.fkEmpresa = fkEmpresa;
        this.fkStatus = fkStatus;
        this.fkSistemaOperacional = fkSistemaOperacional;
        this.dtInstalacao = dtInstalacao;
        this.RAMTotal = RAMTotal;
        this.CPUTotal = CPUTotal;
        this.discoTotal = discoTotal;
        this.hostname = hostname;
        this.dadosTotem = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.alertasSlack = new ArrayList<>();
    }

    public void adicionaDados(Dados d){
        this.dadosTotem.add(d);
    }

    public void deletaDados(Dados d){
        this.dadosTotem.remove(d);
    }

    public void adicionaAlerta(Alerta a){
        this.alertas.add(a);
    }

    public void adicionaAlertaSlack(AlertaSlack aS){
        this.alertasSlack.add(aS);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<Dados> getDadosTotem() {
        return dadosTotem;
    }
    public List<Alerta> getAlertas() {
        return alertas;
    }

    public List<AlertaSlack> getAlertasSlack() {
        return alertasSlack;
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
           Hostname: %s
        """.formatted(
            this.id,
            formatadorDataHora.format(this.dtInstalacao),
            this.RAMTotal,
            this.CPUTotal,
            this.discoTotal,
            this.hostname
        );
    }
}

