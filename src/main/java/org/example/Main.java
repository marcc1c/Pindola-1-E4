package org.example;
public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SUITE DE PRUEBAS LOCALES - DESPLIEGUE H2      ");
        System.out.println("==================================================\n");

        System.out.println("[PRUEBA 1] Inicializando Base de Datos H2...");
        try {
            App.inicializarBaseDeDatos();
            System.out.println("-> RESULTADO: OK (Tabla creada e inserción realizada)");
        } catch (Exception e) {
            System.out.println("-> RESULTADO: ERROR (" + e.getMessage() + ")");
        }

        System.out.println("\n[PRUEBA 2] Login con credenciales válidas (admin / 1234)...");
        boolean loginOk = App.autenticarUsuario("admin", "1234");
        if (loginOk) {
            System.out.println("-> RESULTADO: OK (Autenticación exitosa)");
        } else {
            System.out.println("-> RESULTADO: FALLO (No se pudo autenticar usuario válido)");
        }

        System.out.println("\n[PRUEBA 3] Login con contraseña errónea (admin / badpass)...");
        boolean loginFail = App.autenticarUsuario("admin", "badpass");
        if (!loginFail) {
            System.out.println("-> RESULTADO: OK (Acceso denegado correctamente)");
        } else {
            System.out.println("-> RESULTADO: FALLO (El sistema permitió una contraseña errónea)");
        }

        System.out.println("\n[PRUEBA 4] Simulación de Ataque de Inyección SQL...");
        String usuarioAtaque = "' OR '1'='1";
        String passAtaque = "' OR '1'='1";

        boolean ataqueExitoso = App.autenticarUsuario(usuarioAtaque, passAtaque);
        if (!ataqueExitoso) {
            System.out.println("-> RESULTADO: OK (Ataque bloqueado. El código es seguro)");
        } else {
            System.out.println("-> RESULTADO: ALERTA DE SEGURIDAD (Vulnerable a Inyección SQL)");
        }

        System.out.println("\n==================================================");
        System.out.println("     FIN DE LA EJECUCIÓN DE PRUEBAS EN LOCAL     ");
        System.out.println("==================================================");
    }
}