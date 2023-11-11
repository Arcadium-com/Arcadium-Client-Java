package service;

import dao.DadosDao;
import dao.IndicadorDao;
import dao.TotemDao;
import dao.UsuarioDao;
import models.Dados;
import models.Indicador;
import models.Totem;
import models.Usuario;

import java.util.List;

public class FoodieKioskie {
    private UsuarioDao usuarioDao;
    private TotemDao totemDao;
    private DadosDao dadosDao;
    private IndicadorDao indicadorDao ;

    public FoodieKioskie(){
        this.usuarioDao = new UsuarioDao();
        this.totemDao =  new TotemDao();
        this.dadosDao = new DadosDao();
        this.indicadorDao = new IndicadorDao();
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

    public Totem obterTotemPorFkEmpresa(Integer fkEmpresa){
        if(fkEmpresa > 0 ){
            Totem totem = totemDao.getTotemPorFkEmpresa(fkEmpresa);
            List<Dados> dadosTotem = dadosDao.getDadosDoTotemPorFkTotem(totem.getId());

            for (Dados d : dadosTotem){
                totem.adicionaDados(d);
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
            Indicador indicador = indicadorDao.getIndicadorById(id);
            var listaTotens = totemDao.getTodosOsTotensPorFkIndicador(id);

            for (Totem t : listaTotens) {
                indicador.adicionaTotem(t);
            }

            return indicador;
        }

        return null;
    }
}

