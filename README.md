# Taller — CRUD JDBC con PostgreSQL

Aplicación Java de consola que realiza operaciones CRUD sobre la tabla `vehiculos`
usando JDBC, `PreparedStatement`, `ResultSet`, manejo de excepciones y logs (SLF4J + Logback).

## Estructura

```
src/main/java/com/krakdev/
├── entidades/Vehiculo.java        # Entidad
├── jdbc/Conexion.java             # Conexión a PostgreSQL + cierre de recursos
├── jdbc/InsertVehiculo.java       # Create
├── jdbc/SelectVehiculo.java       # Read
├── jdbc/UpdateVehiculo.java       # Update
├── jdbc/DeleteVehiculo.java       # Delete
└── tallerjdbc/TallerjdbcApplication.java
src/main/resources/logback.xml     # Logs en consola y en logs/tallerjdbc.log
sql/                               # Scripts de base de datos
```

## Cómo ejecutar

1. Crear la base de datos `tallerjdbc` y ejecutar `sql/01_crear_base_y_tabla.sql`.
2. Ejecutar `sql/02_agregar_kilometraje.sql` (Parte 12).
3. Ajustar usuario/clave en `Conexion.java` (o variables de entorno `DB_URL`, `DB_USER`, `DB_PASSWORD`).
4. Ejecutar el `main` de cada clase: `InsertVehiculo`, `SelectVehiculo`, `UpdateVehiculo`, `DeleteVehiculo`.
