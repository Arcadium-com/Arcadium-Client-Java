package arcadium.com.menu;

public class Menu {
    public void gerarLinha(){
        System.out.println("*************************************");
    }

    public void exibirLogo(){
        System.out.println("""     
                              
                █▀▀ █▀█ █▀█ █▀▄ █ █▀▀   █▄▀ █ █▀█ █▀ █▄▀ █ █▀▀
                █▀░ █▄█ █▄█ █▄▀ █ ██▄   █░█ █ █▄█ ▄█ █░█ █ ██▄
            """);
    }

    public void exibirOpcoes(){
        System.out.println("""
                Escolha uma opção correspondente:
                
                1 - Entrar na minha conta
                0 - Sair
                """);
    }
    public void exibirTitulo(String titulo){
        System.out.println();
        gerarLinha();
        System.out.println(titulo);
        gerarLinha();
    }

    public void exibirMenu(){
        exibirLogo();
        System.out.println("Seja muito bem-vindo(a)!\n");
        exibirOpcoes();
    }

}
