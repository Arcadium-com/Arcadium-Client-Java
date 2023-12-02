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
        Scanner leitorNumeros = new Scanner(System.in);
        Scanner leitorComLinha = new Scanner(System.in);
        Menu menu = new Menu();
        Integer opcaoDigitada;
        Log logger = new Log();

        do{
            menu.exibirMenu();
            opcaoDigitada = leitorNumeros.nextInt();

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
                            Totem totemCadastrado = foodieKioskie.obterTotemPorHostname();
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
                                opcaoDigitada = leitorNumeros.nextInt();

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

                                    case 4 -> {
                                        menuTelaDados.exibirTitulo("Indicador para Geração de Alerta da Empresa");
                                        foodieKioskie.exibirIndicadorDaEmpresa(usuario.getFkEmpresa());
                                    }

                                    case 5 -> {
                                        menuTelaDados.exibirTitulo("Atualizando os Indicadores para Geração de Alerta");
                                        System.out.println("Indicador de Limite para a CPU: ");
                                        Double limiteCpu = leitorNumeros.nextDouble();
                                        System.out.println("Indicador de Limite para a RAM: ");
                                        Double limiteRam = leitorNumeros.nextDouble();
                                        System.out.println("Indicador de Limite para o DISCO: ");
                                        Double limiteDisco = leitorNumeros.nextDouble();
                                        System.out.println("Indicador de Quantidade para o USB: ");
                                        Integer indicadorUSB = leitorNumeros.nextInt();
                                        foodieKioskie.atualizarIndicadorDaEmpresa(usuario.getFkEmpresa(), limiteCpu, limiteRam, limiteDisco, indicadorUSB);

                                    }

                                    case 6 -> {
                                        menuTelaDados.exibirTitulo("Atualizando um Indicador para Geração de Alerta");
                                        menuTelaDados.exibirOpcoesAtualizarIndicador();
                                        Integer indicadorEscolhido = leitorNumeros.nextInt();

                                        if(indicadorEscolhido.equals(1)){
                                            System.out.println("Indicador de Limite para a CPU: ");
                                            Double limiteCpu = leitorNumeros.nextDouble();
                                            foodieKioskie.atualizarIndicadorDaCpu(usuario.getFkEmpresa(), limiteCpu);
                                        } else if(indicadorEscolhido.equals(2)){
                                            System.out.println("Indicador de Limite para a RAM: ");
                                            Double limiteRam = leitorNumeros.nextDouble();
                                            foodieKioskie.atualizarIndicadorDaRam(usuario.getFkEmpresa(), limiteRam);
                                        } else if(indicadorEscolhido.equals(3)){
                                            System.out.println("Indicador de Limite para o DISCO: ");
                                            Double limiteDisco = leitorNumeros.nextDouble();
                                            foodieKioskie.atualizarIndicadorDoDisco(usuario.getFkEmpresa(), limiteDisco);
                                        } else if (indicadorEscolhido.equals(4)){
                                            System.out.println("Indicador de Quantidade para o USB: ");
                                            Integer indicadorUSB = leitorNumeros.nextInt();
                                            foodieKioskie.atualizarIndicadorDeUsb(usuario.getFkEmpresa(), indicadorUSB);
                                        } else if (!indicadorEscolhido.equals(0)){
                                            System.out.println("Opção inválida. Informe uma das opções descritas no menu.");
                                        }
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
                        logger.log("Exceção => " + e);
                    }
                }
                case 0 -> {
                    menu.exibirTitulo("Até mais :)");
                }

                default -> {System.out.println("Opção inválida, tente novamente.");
                }
            }
        } while(opcaoDigitada != 0);

    }
}
