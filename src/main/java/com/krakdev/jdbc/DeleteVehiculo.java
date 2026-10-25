package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteVehiculo {

    private static final Logger log = LoggerFactory.getLogger(DeleteVehiculo.class);

    private static final String SQL = "DELETE FROM vehiculos WHERE placa = ?";

    /**
     * Elimina el vehiculo con la placa indicada y devuelve las filas eliminadas.
     */
    public static int eliminar(String placa) {
        Connection con = null;
        PreparedStatement ps = null;
        int filas = 0;

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setString(1, placa);

            filas = ps.executeUpdate();

            if (filas > 0) {
                log.info("Vehículo eliminado: {}", placa);
                System.out.println("Vehículo eliminado. Filas afectadas: " + filas);
            } else {
                log.warn("No se eliminó nada: no existe vehículo con placa {}", placa);
                System.out.println("No existe un vehículo con la placa " + placa + ". Filas afectadas: 0");
            }

        } catch (SQLException e) {
            log.error("Error al eliminar vehículo {}: {}", placa, e.getMessage(), e);
            System.out.println("Error al eliminar: " + e.getMessage());
        } finally {
            Conexion.cerrar(ps);
            Conexion.cerrar(con);
            log.debug("Recursos cerrados (DeleteVehiculo)");
        }
        return filas;
    }

    public static void main(String[] args) {
        eliminar("GAB-9012");
    }
}
