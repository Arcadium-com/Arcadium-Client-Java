package arcadium.com.menu;

public class MenuTelaDados extends Menu {
    @Override
    public void exibirOpcoes(){
        System.out.println("""
            Escolha uma opção correspondente:
            
            1 - Visualizar todos os totens cadastrados
            2 - Visualizar informações deste totem
            3 - Visualizar últimos 5 registros deste totem
            0 - Sair
            """);
    }

    @Override
    public void exibirMenu(){
        exibirLogo();
        System.out.println("Bem-Vindo de volta! \n");
        exibirOpcoes();
    }
}
