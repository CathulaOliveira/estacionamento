package br.edu.utfpr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDataBase {

    private static final
    String DATABASE_URL = "jdbc:postgresql://localhost/estacionamento";

    public static Connection createConnections() {
        Properties properties = new Properties();

        properties.setProperty("user", "postgres");
        properties.setProperty("password", "postgres");

        Connection conexao = null;

        try {
            conexao = DriverManager
                    .getConnection(DATABASE_URL, properties);
        } catch (SQLException ex) {
            System.out.println("ERRO AO CRIAR CONEXÃO");
            System.out.println(ex.getMessage());
        }

        return conexao;
    }
}
