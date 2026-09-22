package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class App {

    private static final ResourceBundle config = ResourceBundle.getBundle("config");

    private static final String DB_URL = config.getString("db.url");
    private static final String DB_USER = config.getString("db.user");
    private static final String DB_PASSWORD = config.getString("db.password");

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

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la BD: " + e.getMessage());
        }
    }

    public static boolean autenticarUsuario(String usuario, String contrasena) {
        String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return false;
        }
    }
}