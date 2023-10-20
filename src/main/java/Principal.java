import dao.DadosDao;
import dao.TotemDao;
import dao.UsuarioDao;
import models.Dados;
import models.Totem;
import models.Usuario;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Principal {
    public static void main(String[] args) {
        Scanner leitorInteiros = new Scanner(System.in);
        Scanner leitorComLinha = new Scanner(System.in);
        Menu menu = new Menu();
        Integer opcaoDigitada;

        do{
            menu.exibirMenu();
            opcaoDigitada = leitorInteiros.nextInt();

            switch (opcaoDigitada){
                case 1 -> {
                    menu.exibirTitulo("Entrar");
                    System.out.print("Email: ");
                    String email = leitorComLinha.nextLine();
                    System.out.print("Senha: ");
                    String senha = leitorComLinha.nextLine();

                    UsuarioDao usuarioDao = new UsuarioDao();
                    Usuario usuario = usuarioDao.entrarNaConta(email, senha);

                    if(usuario != null){
                        TotemDao totemDao = new TotemDao();
                        Totem totemCadastrado = totemDao.inserirTotemNoBanco(usuario.getFkEmpresa());
                        DadosDao dadosDao = new DadosDao();
                        Timer timer = new Timer();

                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                dadosDao.inserirDadosNoBanco(totemCadastrado.getId());

                            }
                        }, 10000, 5000);

                        MenuTelaDados menuTelaDados = new MenuTelaDados();

                        do{
                            menuTelaDados.exibirMenu();
                            opcaoDigitada = leitorInteiros.nextInt();

                            switch (opcaoDigitada) {
                                case 1 -> {
                                    List<Totem> listaTotens = totemDao.getTodosOsTotensPorEmpresa(usuario.getFkEmpresa());

                                    for (Totem t : listaTotens) {
                                        System.out.println(t);
                                    }
                                }
                                case 2 -> {
                                    System.out.println(totemCadastrado);
                                }

                                case 3 -> {
                                    List<Dados> dadosTotem = dadosDao.getUltimos5RegistrosDoTotemPorId(totemCadastrado.getId());

                                    for (Dados d : dadosTotem){
                                        System.out.println(d);
                                    }

                                }
                                case 0 -> {
                                    System.out.println("Até mais :)");
                                }
                                default -> {
                                    System.out.println("Opção inválida. Tente novamente.");
                                }
                            }
                        } while(opcaoDigitada != 0 );
                    }
                }
                case 0 -> {
                    menu.exibirTitulo("Até mais :)");
                }

                default -> {System.out.println("Opção inválida, tente novamente.");}
            }
        } while(opcaoDigitada != 0);

    }
}
