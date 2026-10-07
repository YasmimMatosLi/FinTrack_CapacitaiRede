package fintrack.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//classe pra conectar o banco de dados
public class ConexaoFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/fintrack";
    private static final String USUARIO = "root";
    private static final String SENHA = "book007";

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
