package arcadium.com.conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    private JdbcTemplate conexaoDoBancoMySql;
    private JdbcTemplate conexaoDoBancoSqlServer;

    public Conexao() {
        BasicDataSource dataSourceMySql = new BasicDataSource();
        BasicDataSource dataSourceSqlServer = new BasicDataSource();

        try{
            dataSourceSqlServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dataSourceSqlServer.setUrl("jdbc:sqlserver://ec2-54-88-22-216.compute-1.amazonaws.com:1433;databaseName=Arcadium;encrypt=true;trustServerCertificate=true");
            dataSourceSqlServer.setUsername("sa");
            dataSourceSqlServer.setPassword("#arcadium123");
            conexaoDoBancoSqlServer = new JdbcTemplate(dataSourceSqlServer);
        } catch(Exception e ){
            throw new Error(e);
        }

        try{
            dataSourceMySql.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceMySql.setUrl("jdbc:mysql://localhost:3306/Arcadium");
            dataSourceMySql.setUsername("root");
            dataSourceMySql.setPassword("#Arcadium");

            conexaoDoBancoMySql = new JdbcTemplate(dataSourceMySql);
        } catch (Exception e){
            throw new Error(e);
        }

    }

    public JdbcTemplate getConexaoDoBancoMySql() {
        return conexaoDoBancoMySql;
    }

    public JdbcTemplate getConexaoDoBancoSqlServer() {
        return conexaoDoBancoSqlServer;
    }
}
