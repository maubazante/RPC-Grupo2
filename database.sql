-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS stockearte;

-- Usar la base de datos
USE stockearte;

-- Crear la tabla de Tiendas
CREATE TABLE IF NOT EXISTS tiendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    habilitada BOOLEAN DEFAULT TRUE,
    UNIQUE(codigo)
);

-- Crear la tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL, 
    tienda_id INT DEFAULT NULL, -- El usuario puede estar asignado a una tienda
    habilitado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_tienda_usuario FOREIGN KEY (tienda_id) REFERENCES tiendas(id) ON DELETE SET NULL,
    UNIQUE(username)
);

-- Crear la tabla de Productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo CHAR(10) NOT NULL, -- Código único generado al azar
    foto BLOB,
    color VARCHAR(50) NOT NULL,
    talle VARCHAR(5) NOT NULL,
    UNIQUE(codigo)
);

-- Crear la tabla de stock por tienda y producto
CREATE TABLE IF NOT EXISTS stock (
    tienda_id INT NOT NULL,
    producto_id INT NOT NULL,
    stock INT DEFAULT 0,
    PRIMARY KEY (tienda_id, producto_id),
    CONSTRAINT fk_tienda_stock FOREIGN KEY (tienda_id) REFERENCES tiendas(id) ON DELETE CASCADE,
    CONSTRAINT fk_producto_stock FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);

-- Crear la tabla para asignar productos a tiendas
CREATE TABLE IF NOT EXISTS tienda_producto (
    tienda_id INT NOT NULL,
    producto_id INT NOT NULL,
    PRIMARY KEY (tienda_id, producto_id),
    CONSTRAINT fk_tienda_tienda_producto FOREIGN KEY (tienda_id) REFERENCES tiendas(id) ON DELETE CASCADE,
    CONSTRAINT fk_producto_tienda_producto FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);

-- Crear la tabla para asignar usuarios a tiendas
CREATE TABLE IF NOT EXISTS usuario_tienda (
    usuario_id INT NOT NULL,
    tienda_id INT NOT NULL,
    PRIMARY KEY (usuario_id, tienda_id),
    CONSTRAINT fk_usuario_usuario_tienda FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_tienda_usuario_tienda FOREIGN KEY (tienda_id) REFERENCES tiendas(id) ON DELETE CASCADE
);
