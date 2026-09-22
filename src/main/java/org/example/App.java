package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class App {

    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "SuperSecretPassword123!";

    public static void main(String[] args) {
        System.out.println("=== Iniciando Aplicación ===");

        try {
            inicializarBaseDeDatos();

            boolean loginExitoso = autenticarUsuario("admin", "1234");
            System.out.println("Resultado del login: " + (loginExitoso ? "ÉXITO" : "FALLO"));

        } catch (Exception e) {
            System.err.println("Error crítico en la aplicación: " + e.getMessage());
        }
    }

    public static void inicializarBaseDeDatos() {
        String sqlCrearTabla = "CREATE TABLE usuarios (id INT PRIMARY KEY, username VARCHAR(50) password VARCHAR(50))";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlCrearTabla);
            stmt.execute("INSERT INTO usuarios VALUES (1, 'admin', '1234')");
            System.out.println("Base de datos H2 inicializada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la BD: " + e.getMessage());
        }
    }

    public static boolean autenticarUsuario(String usuario, String contrasena) {
        String query = "SELECT * FROM usuarios WHERE username = '" + usuario + "' AND password = '" + contrasena + "'";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return false;
        }
    }
}