package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para obtener conexiones a PostgreSQL.
 *
 * Los datos de conexion se pueden cambiar aqui o con variables de entorno:
 * DB_URL, DB_USER y DB_PASSWORD.
 */
public class Conexion {

    private static final Logger log = LoggerFactory.getLogger(Conexion.class);

    private static final String URL = env("DB_URL", "jdbc:postgresql://localhost:5432/tallerjdbc");
    private static final String USUARIO = env("DB_USER", "postgres");
    private static final String PASSWORD = env("DB_PASSWORD", "postgres"); // <-- cambiar por tu clave

    private Conexion() {
    }

    private static String env(String nombre, String porDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }

    /**
     * Abre una conexion nueva. Quien la reciba debe cerrarla (ver {@link #cerrar(AutoCloseable)}).
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            log.info("Conexión realizada con PostgreSQL: {}", URL);
            System.out.println("Conexión exitosa a PostgreSQL");
            return con;
        } catch (SQLException e) {
            log.error("Error de conexión a {}: {}", URL, e.getMessage(), e);
            System.out.println("Error de conexión: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Cierra Connection, PreparedStatement o ResultSet sin lanzar excepcion.
     * Se usa dentro de los bloques finally.
     */
    public static void cerrar(AutoCloseable recurso) {
        if (recurso == null) {
            return;
        }
        try {
            recurso.close();
        } catch (Exception e) {
            log.error("Error al cerrar {}: {}", recurso.getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
