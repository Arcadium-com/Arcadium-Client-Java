import java.util.ArrayList;
import java.util.List;

public class Menu {
    List<Usuario> listaUsuarios = new ArrayList();
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
                
                1 - Criar uma nova conta
                2 - Entrar na minha conta
                0 - Sair
                """);
    }
    public void exibirTitulo(String titulo){
        System.out.println();
        gerarLinha();
        System.out.println(titulo);
        gerarLinha();
    }

    public boolean validarEmail(String email){
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if(listaUsuarios.get(i).getEmail().equals(email)){
                return true;
            }
        }

        return false;
    }

    public boolean validarSenha(String senha){
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getSenha().equals(senha)){
                return true;
            }
        }

        return false;
    }

    public void cadastrarUsuario(String nome, String empresa, String tipoUsuario, String email, String senha){
        if(nome.isEmpty() || empresa.isEmpty() || tipoUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()){
            System.out.println("\nPreencha todos os campos solicitados.");
        } else {
            Boolean emailExiste = validarEmail(email);
            if(emailExiste) {
                System.out.println("Email já cadastrado, tente com outro.");
            } else {
                Usuario usuario = new Usuario(nome, empresa, tipoUsuario, email, senha);
                listaUsuarios.add(usuario);
                System.out.println("Cadastro realizado com sucesso!");
            }
        }
    }

    public boolean entrar(String email, String senha){
        Boolean emailExiste = validarEmail(email);
        Boolean senhaExiste = validarSenha(senha);

        if(email.isEmpty() || senha.isEmpty()){
            System.out.println("\nPreencha todos os campos solicitados.");
            return false;
        }
        else if(emailExiste && senhaExiste) {
            exibirTitulo("Foodie Kioskie");
            System.out.println("Olá, bem-vindo(a) de volta.");
            return true;
        } else {
            System.out.println("Email e/ou senha incorretos.");
            return false;
        }
    }

    public void exibirMenu(){
        exibirLogo();
        System.out.println("Seja muito bem-vindo(a)!\n");
        exibirOpcoes();
    }

}
