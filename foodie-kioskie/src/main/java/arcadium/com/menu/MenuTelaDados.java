package arcadium.com.menu;

public class MenuTelaDados extends Menu {
    @Override
    public void exibirOpcoes(){
        System.out.println("""
            Escolha uma opção correspondente:
            
            1 - Visualizar todos os totens cadastrados
            2 - Visualizar informações deste totem
            3 - Visualizar últimos 5 registros deste totem
            4 - Visualizar indicador para geração de alerta da empresa
            5 - Criar ou Atualizar todos os indicadores para geração de alertas
            6 - Atualizar um indicador para geração de alertas
            0 - Sair
            """);
    }

    @Override
    public void exibirMenu(){
        exibirLogo();
        System.out.println("Bem-Vindo de volta! \n");
        exibirOpcoes();
    }

    public void exibirOpcoesAtualizarIndicador(){
        System.out.println("""
            Escolha um dos indicadores para atualizar
            
            1 - Indicador para Limite de CPU
            2 - Indicador para Limite da Memória RAM
            3 - Indicador para Limite do Disco
            4 - Indicador para Quantidade de USBs
            0 - Retornar para início
            """);
    }
}
