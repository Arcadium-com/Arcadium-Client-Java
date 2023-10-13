import java.util.Scanner;

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
                    menu.exibirTitulo("Cadastre-se");

                    System.out.print("Digite seu nome completo: ");
                    String nomeCompleto = leitorComLinha.nextLine();

                    System.out.print("Digite o nome da empresa que você trabalha: ");
                    String nomeEmpresa = leitorComLinha.nextLine();

                    System.out.print("Digite se trabalha no suporte [S] [N]: ");
                    // Transformando o que o usuário digitou em caixa alta
                    String tipoDigitadoCaixaAlta = leitorComLinha.nextLine().toUpperCase();
                    // Se o que usuário digitou é "S" retorna "Suporte", senão "Usuário"
                    String tipoUsuario = tipoDigitadoCaixaAlta.equals("S") ? "Suporte" : "Usuário";

                    System.out.print("Digite seu email: ");
                    String email = leitorComLinha.nextLine();

                    System.out.print("Digite sua senha: ");
                    String senha = leitorComLinha.nextLine();

                    menu.cadastrarUsuario(nomeCompleto, nomeEmpresa, tipoUsuario, email, senha);
                }
                case 2 -> {
                    menu.exibirTitulo("Entrar");
                    System.out.print("Digite seu email: ");
                    String email = leitorComLinha.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = leitorComLinha.nextLine();
                    Boolean entrou = menu.entrar(email, senha);

                    if(entrou){
                        return;
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
