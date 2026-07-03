package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String USUARIO = "root";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USUARIO,SENHA);
    }
}
