package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.entidades.Vehiculo;

public class UpdateVehiculo {

    private static final Logger log = LoggerFactory.getLogger(UpdateVehiculo.class);

    private static final String SQL =
            "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ?, precio = ?, " +
            "color = ?, disponible = ?, kilometraje = ? WHERE placa = ?";

    /**
     * Actualiza el vehiculo cuya placa coincide y devuelve las filas afectadas.
     */
    public static int actualizar(Vehiculo v) {
        Connection con = null;
        PreparedStatement ps = null;
        int filas = 0;

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getAnio());
            ps.setDouble(4, v.getPrecio());
            ps.setString(5, v.getColor());
            ps.setBoolean(6, v.isDisponible());
            ps.setInt(7, v.getKilometraje());
            ps.setString(8, v.getPlaca()); // WHERE

            filas = ps.executeUpdate();

            if (filas > 0) {
                log.info("Vehículo actualizado: {} (filas afectadas: {})", v.getPlaca(), filas);
                System.out.println("Vehículo actualizado. Filas afectadas: " + filas);
            } else {
                log.warn("No se actualizó nada: no existe vehículo con placa {}", v.getPlaca());
                System.out.println("No existe un vehículo con la placa " + v.getPlaca() + ". Filas afectadas: 0");
            }

        } catch (SQLException e) {
            log.error("Error al actualizar vehículo {}: {}", v.getPlaca(), e.getMessage(), e);
            System.out.println("Error al actualizar: " + e.getMessage());
        } finally {
            Conexion.cerrar(ps);
            Conexion.cerrar(con);
            log.debug("Recursos cerrados (UpdateVehiculo)");
        }
        return filas;
    }

    public static void main(String[] args) {
        actualizar(new Vehiculo("PBA-1234", "Toyota", "Corolla", 2022, 21900.00, "Blanco", false, 18000));
    }
}
