-- Limpiar tabla si existe
DROP TABLE IF EXISTS usuarios;

-- Crear tabla (con la sintaxis correcta)
CREATE TABLE usuarios (
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Inserción de datos iniciales
INSERT INTO usuarios (id, username, password) VALUES (1, 'admin', '1234');