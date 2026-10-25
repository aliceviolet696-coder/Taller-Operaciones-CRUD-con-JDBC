-- 1) Ejecutar conectado a la base "postgres":
--    CREATE DATABASE tallerjdbc;
--
-- 2) Conectarse a "tallerjdbc" y ejecutar lo siguiente.
--
-- NOTA: PostgreSQL NO tiene el tipo DOUBLE; el equivalente es DOUBLE PRECISION.

CREATE TABLE vehiculos(
    placa       VARCHAR(10) PRIMARY KEY,
    marca       VARCHAR(50) NOT NULL,
    modelo      VARCHAR(50) NOT NULL,
    anio        INT NOT NULL,
    precio      DOUBLE PRECISION NOT NULL,
    color       VARCHAR(30),
    disponible  BOOLEAN NOT NULL
);
