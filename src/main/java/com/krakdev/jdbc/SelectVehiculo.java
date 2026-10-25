package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.entidades.Vehiculo;

public class SelectVehiculo {

    private static final Logger log = LoggerFactory.getLogger(SelectVehiculo.class);

    private static final String SQL =
            "SELECT placa, marca, modelo, anio, precio, color, disponible, kilometraje " +
            "FROM vehiculos ORDER BY placa";

    /**
     * Lista todos los vehiculos: los muestra en consola y los devuelve en una lista.
     */
    public static List<Vehiculo> listar() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Vehiculo> vehiculos = new ArrayList<>();

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setPrecio(rs.getDouble("precio"));
                v.setColor(rs.getString("color"));
                v.setDisponible(rs.getBoolean("disponible"));
                v.setKilometraje(rs.getInt("kilometraje")); // NULL se lee como 0
                vehiculos.add(v);
            }

            log.info("Consulta realizada: {} vehículo(s) encontrados", vehiculos.size());
            System.out.println("----- LISTA DE VEHICULOS (" + vehiculos.size() + ") -----");
            for (Vehiculo v : vehiculos) {
                System.out.println(v);
            }

        } catch (SQLException e) {
            log.error("Error al listar vehículos: {}", e.getMessage(), e);
            System.out.println("Error al listar: " + e.getMessage());
        } finally {
            Conexion.cerrar(rs);
            Conexion.cerrar(ps);
            Conexion.cerrar(con);
            log.debug("Recursos cerrados (SelectVehiculo)");
        }
        return vehiculos;
    }

    public static void main(String[] args) {
        listar();
    }
}
