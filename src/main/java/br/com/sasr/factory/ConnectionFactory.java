package br.com.sasr.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:oracle:thin:@oracle.'trecho-do-endereco-omitido':1521:orcl";

    private static final String USUARIO = "usuario-omitido";

    private static final String SENHA = "senha-omitida";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
