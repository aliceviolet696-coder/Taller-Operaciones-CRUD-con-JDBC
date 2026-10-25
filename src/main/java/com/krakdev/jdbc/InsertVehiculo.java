package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.entidades.Vehiculo;

public class InsertVehiculo {

    private static final Logger log = LoggerFactory.getLogger(InsertVehiculo.class);

    private static final String SQL =
            "INSERT INTO vehiculos (placa, marca, modelo, anio, precio, color, disponible, kilometraje) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Inserta un vehiculo y devuelve la cantidad de filas insertadas (0 si hubo error).
     */
    public static int insertar(Vehiculo v) {
        Connection con = null;
        PreparedStatement ps = null;
        int filas = 0;

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnio());
            ps.setDouble(5, v.getPrecio());
            ps.setString(6, v.getColor());
            ps.setBoolean(7, v.isDisponible());
            ps.setInt(8, v.getKilometraje());

            filas = ps.executeUpdate();
            log.info("Vehículo insertado: {}", v.getPlaca());
            System.out.println("Vehículo insertado correctamente (filas afectadas: " + filas + ")");

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // violacion de PRIMARY KEY
                log.warn("No se insertó: ya existe un vehículo con placa {}", v.getPlaca());
                System.out.println("Error: ya existe un vehículo con la placa " + v.getPlaca());
            } else {
                log.error("Error al insertar vehículo {}: {}", v.getPlaca(), e.getMessage(), e);
                System.out.println("Error al insertar: " + e.getMessage());
            }
        } finally {
            Conexion.cerrar(ps);
            Conexion.cerrar(con);
            log.debug("Recursos cerrados (InsertVehiculo)");
        }
        return filas;
    }

    public static void main(String[] args) {
        insertar(new Vehiculo("PBA-1234", "Toyota", "Corolla", 2022, 23500.00, "Blanco", true, 15000));
        insertar(new Vehiculo("PCD-5678", "Chevrolet", "Spark", 2020, 12800.50, "Rojo", true, 42000));
        insertar(new Vehiculo("GAB-9012", "Kia", "Rio", 2019, 14250.00, "Gris", false, 61000));
    }
}
