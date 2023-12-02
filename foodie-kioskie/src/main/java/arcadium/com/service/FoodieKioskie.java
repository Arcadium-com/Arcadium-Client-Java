package arcadium.com.service;

import arcadium.com.dao.*;
import arcadium.com.models.*;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class FoodieKioskie {
    private EmpresaDao empresaDao;
    private UsuarioDao usuarioDao;
    private TotemDao totemDao;
    private DadosDao dadosDao;
    private IndicadorDao indicadorDao ;
    private AlertaDao alertaDao;
    private AlertaSlackDao alertaSlackDao;
    private StatusTotemDao statusDao;

    public FoodieKioskie(){
        this.empresaDao = new EmpresaDao();
        this.usuarioDao = new UsuarioDao();
        this.totemDao =  new TotemDao();
        this.dadosDao = new DadosDao();
        this.indicadorDao = new IndicadorDao();
        this.alertaDao = new AlertaDao();
        this.alertaSlackDao = new AlertaSlackDao();
        this.statusDao = new StatusTotemDao();
    }


    public Usuario entrarNaConta(String email, String senha){
        if(email.isEmpty() || senha.isEmpty()){
            System.out.println("Preencha os campos solicitados.");
            return null;
        } else {
            var usuario = usuarioDao.entrarNaConta(email, senha);

            if(usuario == null){
                System.out.println("Email e/ou Senha incorretos.");
            }

            return usuario;
        }

    }

    public void inserirTotemNoBanco(Integer fkEmpresa){
        if(fkEmpresa == null){
            System.out.println("FkEmpresa precisa possuir um valor");
        } else if(fkEmpresa < 0){
            System.out.println("FkEmpresa precisa ser maior que 0.");
        } else {
            totemDao.inserirTotemNoBanco(fkEmpresa);
            Totem totemCadastrado = totemDao.getUltimoTotemAdicionadoPorFkEmpresa(fkEmpresa);
            statusDao.getStatusTotemById(totemCadastrado.getFkStatus());
        }
    }

    public void inserirDadosObtidosDoTotemNoBanco(Totem totem){
        dadosDao.inserirDadosNoBanco(totem);
    }

    public void exibirTodosOsTotens(Integer fkEmpresa){
        List<Totem> listaTotens = totemDao.getTodosOsTotensPorEmpresa(fkEmpresa);

        for (Totem t : listaTotens) {
            System.out.println(t);
        }
    }

    public void exibirUltimos5RegistrosDoTotem(Integer fkTotem){
        List<Dados> dadosTotem = dadosDao.getUltimos5RegistrosDoTotemPorId(fkTotem);

        for (Dados d : dadosTotem){
            System.out.println(d);
        }
    }

    public Totem obterTotemPorHostname(){
        Totem totem = totemDao.getTotemPorHostname();

        if(totem != null){
            List<Alerta> alertasTotem = alertaDao.getAllAlerta();
            List<AlertaSlack> alertasSlackTotem = alertaSlackDao.getAllAlertaSlack();

            for (Alerta alerta : alertasTotem) {
                totem.adicionaAlerta(alerta);
            }
            for (AlertaSlack alertaSlack : alertasSlackTotem) {
                totem.adicionaAlertaSlack(alertaSlack);
            }
        }

        return totem;
    }

    public Totem obterTotemPorFkEmpresa(Integer fkEmpresa){
        if(fkEmpresa > 0 ){
            Totem totem = totemDao.getTotemPorFkEmpresa(fkEmpresa);
            List<Dados> dadosTotem = dadosDao.getDadosDoTotemPorFkTotem(totem.getId());
            List<Alerta> alertasTotem = alertaDao.getAllAlerta();
            List<AlertaSlack> alertaSlacksTotem = alertaSlackDao.getAllAlertaSlack();

            for (Dados d : dadosTotem){
                totem.adicionaDados(d);
            }

            for (Alerta alerta : alertasTotem) {
                totem.adicionaAlerta(alerta);
            }

            for (AlertaSlack alertaSlack : alertaSlacksTotem) {
                totem.adicionaAlertaSlack(alertaSlack);
            }

            return totem;
        } else if(fkEmpresa == null){
            System.out.println("Preencha o campo solicitado.");
        } else {
            System.out.println("Insira valores maiores que 0.");
        }

        return null;
    }
    public Indicador obterIndicadorPorId(Integer id){
        if(id == null){
            System.out.println("Preencha o campo solicitado.");
        } else if(id <= 0){
            System.out.println("Insira valores maiores que 0.");
        } else {
            return indicadorDao.getIndicadorById(id);
        }

        return null;
    }

    public void exibirIndicadorDaEmpresa(Integer fkEmpresa){
        Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);

        if(existeIndicador){
            Indicador indicadorDaEmpresa = indicadorDao.getIndicadorPorFkEmpresa(fkEmpresa);
            System.out.println(indicadorDaEmpresa);
        } else {
            System.out.println("Nenhum indicador encontrado.");
        }
    }

    public void atualizarIndicadorDaEmpresa(Integer fkEmpresa, Double limiteCpu, Double limiteRam, Double limiteDisco, Integer indicadorUSB){
        if(limiteCpu == null || limiteRam == null || limiteDisco == null || indicadorUSB == null){
            System.out.println("Preencha os campos solicitados.");
        } else if(limiteCpu <= 0.0 || limiteRam <= 0.0 || limiteDisco <= 0.0 || indicadorUSB < 0){
            System.out.println("Insira valores maiores que 0.");
        } else {
            Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);
            Indicador indicador = new Indicador(null, fkEmpresa, limiteCpu, limiteRam, limiteDisco, indicadorUSB);

            if(existeIndicador){
                indicadorDao.atualizarIndicadorPorFkEmpresa(indicador);
                System.out.println("Indicador atualizado com sucesso.");

            } else {
                indicadorDao.inserirIndicador(indicador);
                System.out.println("Indicador criado com sucesso.");

            }
        }
    }

    public void atualizarIndicadorDaCpu(Integer fkEmpresa, Double limiteCpu){
        if (limiteCpu == null){
            System.out.println("Preencha o campo solicitado");
        } else if (limiteCpu <= 0) {
            System.out.println("O valor do limite precisa ser maior que 0.");
        } else {
            Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);

            if (existeIndicador){
                Indicador indicador = indicadorDao.getIndicadorPorFkEmpresa(fkEmpresa);
                indicador.setLimiteCpu(limiteCpu);
                indicadorDao.atualizarLimiteDaCpu(indicador);
                System.out.println("Indicador atualizado com sucesso.");

            } else {
                System.out.println("Não existe nenhum indicador cadastrado. Realize o cadastramento do indicador para realizar essa operação.");
            }
        }
    }

    public void atualizarIndicadorDaRam(Integer fkEmpresa, Double limiteRam){
        if (limiteRam == null){
            System.out.println("Preencha o campo solicitado");
        } else if (limiteRam <= 0) {
            System.out.println("O valor do limite precisa ser maior que 0.");
        } else {
            Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);

            if (existeIndicador){
                Indicador indicador = indicadorDao.getIndicadorPorFkEmpresa(fkEmpresa);
                indicador.setLimiteRam(limiteRam);
                indicadorDao.atualizarLimiteDaRam(indicador);
                System.out.println("Indicador atualizado com sucesso.");

            } else {
                System.out.println("Não existe nenhum indicador cadastrado. Realize o cadastramento do indicador para realizar essa operação.");
            }
        }
    }

    public void atualizarIndicadorDoDisco(Integer fkEmpresa, Double limiteDisco){
        if (limiteDisco == null){
            System.out.println("Preencha o campo solicitado");
        } else if (limiteDisco <= 0) {
            System.out.println("O valor do limite precisa ser maior que 0.");
        } else {
            Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);

            if (existeIndicador){
                Indicador indicador = indicadorDao.getIndicadorPorFkEmpresa(fkEmpresa);
                indicador.setLimiteDisco(limiteDisco);
                indicadorDao.atualizarLimiteDoDisco(indicador);
                System.out.println("Indicador atualizado com sucesso.");

            } else {
                System.out.println("Não existe nenhum indicador cadastrado. Realize o cadastramento do indicador para realizar essa operação.");
            }
        }
    }

    public void atualizarIndicadorDeUsb(Integer fkEmpresa, Integer indicadorUSB){
        if (indicadorUSB == null){
            System.out.println("Preencha o campo solicitado");
        } else if (indicadorUSB <= 0) {
            System.out.println("O valor do limite precisa ser maior que 0.");
        } else {
            Boolean existeIndicador = indicadorDao.existeIndicadorPorFkEmpresa(fkEmpresa);

            if (existeIndicador){
                Indicador indicador = indicadorDao.getIndicadorPorFkEmpresa(fkEmpresa);
                indicador.setIndicadorUsb(indicadorUSB);
                indicadorDao.atualizarIndicadorDeUsbs(indicador);
                System.out.println("Indicador atualizado com sucesso.");
            } else {
                System.out.println("Não existe nenhum indicador cadastrado. Realize o cadastramento do indicador para realizar essa operação.");
            }
        }
    }
}

