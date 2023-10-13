import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    private JdbcTemplate conexaoDoBanco;

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();
        /*
             Exemplo de driverClassName:
                com.mysql.cj.jdbc.Driver <- EXEMPLO PARA MYSQL
                com.microsoft.sqlserver.jdbc.SQLServerDriver <- EXEMPLO PARA SQL SERVER
        */
//        dataSource.setDriverClassName("org.h2.Driver");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        /*

             Exemplo de string de conexões:
                jdbc:mysql://localhost:3306/mydb <- EXEMPLO PARA MYSQL
                jdbc:sqlserver://localhost:1433;database=mydb <- EXEMPLO PARA SQL SERVER
        */
        dataSource.setUrl("mysql://localhost:3306/testebd");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }

    public void setConexaoDoBanco(JdbcTemplate conexaoDoBanco) {
        this.conexaoDoBanco = conexaoDoBanco;
    }
}
