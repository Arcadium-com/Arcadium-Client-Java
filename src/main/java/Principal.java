import database.Conexao;
import models.SistemaOperacional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner leitorInteiros = new Scanner(System.in);
        Menu menu = new Menu();
        Scanner leitorComLinha = new Scanner(System.in);
        Integer opcaoDigitada;

        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();
        con.update("INSERT INTO sistemaOperacional(distribuicao, versionamento) VALUES(?, ?)", "Linux", "Arch Linux");
        List<SistemaOperacional> sistemasOperacionais = con.query("SELECT * FROM sistemaOperacional", new BeanPropertyRowMapper<>(SistemaOperacional.class));
        System.out.println(sistemasOperacionais);

        do{
            menu.exibirMenu();
            opcaoDigitada = leitorInteiros.nextInt();

            switch (opcaoDigitada){
                case 1 -> {
                    menu.exibirTitulo("Cadastre-se");
                }
                case 2 -> {
                    menu.exibirTitulo("Entrar");
                }
                case 0 -> {
                    menu.exibirTitulo("Até mais :)");
                }

                default -> {System.out.println("Opção inválida, tente novamente.");}
            }
        } while(opcaoDigitada != 0);

    }
}
