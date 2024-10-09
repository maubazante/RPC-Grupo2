CREATE DATABASE proveedorsys;

-- Usar la base de datos recién creada
USE proveedorsys;

-- Crear la tabla de Tienda
CREATE TABLE tienda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    habilitada BOOLEAN NOT NULL DEFAULT TRUE,
    es_casa_central BOOLEAN NOT NULL DEFAULT FALSE
);

-- Crear la tabla de Productos
CREATE TABLE producto (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  codigo VARCHAR(10) NULL,
  talle VARCHAR(5) NULL,
  foto VARCHAR(300) NULL,
  color VARCHAR(50) NULL,
  cantidad int DEFAULT 0,
  habilitado BOOLEAN NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX code_UNIQUE (codigo ASC)
) ENGINE = InnoDB;

-- Crear la tabla de Stock para cada tienda
CREATE TABLE stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tienda_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    FOREIGN KEY (tienda_id) REFERENCES tienda(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

CREATE TABLE orden_de_compra (
  id BIGINT NOT NULL AUTO_INCREMENT,
  estado ENUM('SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'RECIBIDA') NOT NULL,
  observaciones VARCHAR(500),
  id_orden_despacho BIGINT,
  fecha_solicitud DATE NOT NULL,
  fecha_recepcion DATE,
  codigo_articulo VARCHAR(50),
  color VARCHAR(50),
  talle VARCHAR(50),
  cantidad_solicitada int not null,
  tienda_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_tienda FOREIGN KEY (tienda_id) REFERENCES tienda(id) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE orden_de_despacho (
  id BIGINT NOT NULL AUTO_INCREMENT,
  id_orden_compra BIGINT,
  fecha_de_envio DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (id_orden_compra) REFERENCES orden_de_compra(id) ON DELETE CASCADE
) ENGINE = InnoDB;

/*
-- Crear la tabla de Despachos
CREATE TABLE despacho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orden_id BIGINT NOT NULL,
    fecha_estimacion_envio DATETIME NOT NULL,
    fecha_real_envio DATETIME,
    FOREIGN KEY (orden_id) REFERENCES orden_de_compra(id)
);
*/

-- Tabla para las novedades de productos nuevos
CREATE TABLE novedades_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_producto VARCHAR(50) NOT NULL,
    talle VARCHAR(10) NOT NULL,
    color VARCHAR(30) NOT NULL,
    url_foto VARCHAR(255),
    fecha_publicacion DATETIME NOT NULL
);

/* TODO: INSERTS */

INSERT INTO `proveedorsys`.`producto`
(`nombre`, `codigo`, `talle`, `foto`, `color`, `habilitado`, `cantidad`)
VALUES
('Camiseta Deportiva', 'CAM001', 'M', 'https://example.com/foto1.jpg', 'Rojo', true, 50),
('Pantalón Casual', 'PAN002', 'L', 'https://example.com/foto2.jpg', 'Azul', true, 30),
('Sudadera con Capucha', 'SUD003', 'S', 'https://example.com/foto3.jpg', 'Verde', true, 20),
('Zapatos de Cuero', 'ZAP004', '42', 'https://example.com/foto4.jpg', 'Negro', false, 0),
('Camiseta Básica', 'CAM005', 'XL', 'https://example.com/foto5.jpg', 'Blanco', true, 40),
('Chaqueta de Invierno', 'CHA006', 'M', 'https://example.com/foto6.jpg', 'Gris', true, 15),
('Cinturón de Cuero', 'CIN007', 'N/A', 'https://example.com/foto7.jpg', 'Marrón', true, 60),
('Camisa Formal', 'CAM008', 'L', 'https://example.com/foto8.jpg', 'Celeste', true, 25),
('Short Deportivo', 'SHO009', 'M', 'https://example.com/foto9.jpg', 'Negro', false, 0),
('Gorra de Béisbol', 'GOR010', 'N/A', 'https://example.com/foto10.jpg', 'Azul Marino', true, 100),
('Camiseta Estampada', 'CAM011', 'L', 'https://example.com/foto11.jpg', 'Amarillo', true, 45),
('Pantalón de Jogging', 'PAN012', 'M', 'https://example.com/foto12.jpg', 'Gris', true, 50),
('Sombrero de Playa', 'SOM013', 'N/A', 'https://example.com/foto13.jpg', 'Beige', true, 30),
('Chaqueta de Cuero', 'CHA014', 'L', 'https://example.com/foto14.jpg', 'Negro', true, 20),
('Pantalón de Vestir', 'PAN015', 'S', 'https://example.com/foto15.jpg', 'Negro', true, 35),
('Zapatos Deportivos', 'ZAP016', '44', 'https://example.com/foto16.jpg', 'Blanco', true, 10),
('Camiseta sin Mangas', 'CAM017', 'M', 'https://example.com/foto17.jpg', 'Verde', true, 25),
('Bermudas de Playa', 'BER018', 'L', 'https://example.com/foto18.jpg', 'Rojo', true, 40),
('Chaleco de Punto', 'CHA019', 'XL', 'https://example.com/foto19.jpg', 'Azul', true, 15),
('Pantalón Cargo', 'PAN020', 'M', 'https://example.com/foto20.jpg', 'Verde Oliva', true, 50);

INSERT INTO producto (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Camiseta Deportiva', 'CAM123', 'M', 'imagenes/camiseta_deportiva_m.jpg', 'Azul', 50, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (tienda_id, producto_id, cantidad) 
VALUES (1, @producto_id, 50);

INSERT INTO producto (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Pantalón Jeans', 'PAN456', 'L', 'imagenes/pantalon_jeans_l.jpg', 'Negro', 30, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (tienda_id, producto_id, cantidad) 
VALUES (2, @producto_id, 30);
