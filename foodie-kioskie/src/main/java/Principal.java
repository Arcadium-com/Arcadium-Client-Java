import models.Totem;
import models.Usuario;
import service.FoodieKioskie;
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

                    FoodieKioskie foodieKioskie = new FoodieKioskie();
                    Usuario usuario = foodieKioskie.entrarNaConta(email, senha);

                    if(usuario != null){
                        foodieKioskie.inserirTotemNoBanco(usuario.getFkEmpresa());
                        Totem totemCadastrado = foodieKioskie.obterTotemPorEnderecoMac();
                        Timer timer = new Timer();

                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                foodieKioskie.inserirDadosObtidosDoTotemNoBanco(totemCadastrado);
                            }
                        }, 0, 10000);

                        MenuTelaDados menuTelaDados = new MenuTelaDados();

                        do{
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
                                    System.out.println("Até mais :)");
                                    System.exit(0);
                                }
                                default -> {
                                    System.out.println("Opção inválida. Tente novamente.");
                                }
                            }
                        } while(opcaoDigitada != 0);
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
