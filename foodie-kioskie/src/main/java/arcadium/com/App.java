package arcadium.com;

import arcadium.com.dao.DadosDao;
import arcadium.com.menu.Menu;
import arcadium.com.menu.MenuTelaDados;
import arcadium.com.models.Log;
import arcadium.com.models.Shutdown;
import arcadium.com.models.Totem;
import arcadium.com.models.Usuario;
import arcadium.com.service.FoodieKioskie;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    public App() {
    }

    public static void main(String[] args) {
        Scanner leitorInteiros = new Scanner(System.in);
        Scanner leitorComLinha = new Scanner(System.in);
        Menu menu = new Menu();
        Integer opcaoDigitada;
        Log logger = new Log();

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

                try {
                    FoodieKioskie foodieKioskie = new FoodieKioskie();
                    Usuario usuario = foodieKioskie.entrarNaConta(email, senha);

                    if (usuario != null) {
                        foodieKioskie.inserirTotemNoBanco(usuario.getFkEmpresa());
                        Totem totemCadastrado = foodieKioskie.obterTotemPorEnderecoMac();
                        Timer timer = new Timer();
                        DadosDao dadosDao = new DadosDao();

                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                foodieKioskie.inserirDadosObtidosDoTotemNoBanco(totemCadastrado);
                            }
                        }, 0, 10000);

                        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown(totemCadastrado.getId(), dadosDao, logger)));


                        MenuTelaDados menuTelaDados = new MenuTelaDados();

                        do {
                            menuTelaDados.exibirMenu();
                            opcaoDigitada = leitorInteiros.nextInt();

                            switch (opcaoDigitada) {
                                case 1 -> {
                                    foodieKioskie.exibirTodosOsTotens(usuario.getFkEmpresa());
                                }
                                case 2 -> {
                                    System.out.println(totemCadastrado);
                                }

                                case 3 -> {
                                    foodieKioskie.exibirUltimos5RegistrosDoTotem(totemCadastrado.getId());

                                }
                                case 0 -> {
                                    timer.cancel();
                                    menuTelaDados.exibirTitulo("Até mais :)");
                                    logger.log("Usuário '" + email + "' fez Logout.");
                                    System.exit(0);
                                }
                                default -> {
                                    System.out.println("Opção inválida. Tente novamente.");
                                }
                            }
                        } while (opcaoDigitada != 0);
                    }
                }catch (Exception e){
                    logger.log("Erro na conexão do Banco de Dados, verifique se o serviço esta ativo");
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
