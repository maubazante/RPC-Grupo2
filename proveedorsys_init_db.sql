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
  habilitado BOOLEAN NULL,
  cantidad int not null,
  PRIMARY KEY (id),
  UNIQUE INDEX code_UNIQUE (codigo ASC)
) ENGINE = InnoDB;

-- Crear la tabla de Orden de Compra
CREATE TABLE orden_de_compra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tienda_id BIGINT NOT NULL,
    estado ENUM('SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'RECIBIDA') NOT NULL,
    observaciones TEXT,
    orden_de_despacho VARCHAR(20),
    fecha_solicitud DATETIME NOT NULL,
    fecha_recepcion DATETIME,
    FOREIGN KEY (tienda_id) REFERENCES tienda(id)
);

-- Crear la tabla de Item de Orden de Compra
CREATE TABLE item_orden (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orden_id BIGINT NOT NULL,
    codigo_articulo VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    talle VARCHAR(10) NOT NULL,
    cantidad_solicitada INT NOT NULL,
    FOREIGN KEY (orden_id) REFERENCES orden_de_compra(id)
);

-- Crear la tabla de Despachos
CREATE TABLE despacho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orden_id BIGINT NOT NULL,
    fecha_estimacion_envio DATETIME NOT NULL,
    fecha_real_envio DATETIME,
    FOREIGN KEY (orden_id) REFERENCES orden_de_compra(id)
);

-- Tabla para las novedades de productos nuevos
CREATE TABLE novedades_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_producto VARCHAR(50) NOT NULL,
    talle VARCHAR(10) NOT NULL,
    color VARCHAR(30) NOT NULL,
    url_foto VARCHAR(255),
    fecha_publicacion DATETIME NOT NULL
);

-- -----------------------------------------------------
-- Insertar producto
-- -----------------------------------------------------
INSERT INTO producto (id, nombre, codigo, talle, foto, color, habilitado, cantidad, stock)
VALUES
(1, 'Camiseta', 'P001', 'M', 'https://www.shutterstock.com/image-vector/3d-realistic-soccer-jersey-argentina-600nw-2446075731.jpg', 'Blanco', 1, 3, 0),
(2, 'Pantalón', 'P002', 'L', 'https://acdn.mitiendanube.com/stores/002/023/047/products/marino-pantalon-stock-con-bolsillos11-c2e595f8e8f57a760616835590772521-1024-1024.png', 'Negro', 1, 0, 0),
(3, 'Zapatillas', 'P003', '42', 'https://www.stockcenter.com.ar/on/demandware.static/-/Sites-365-dabra-catalog/default/dw5ea76a27/products/NI_BQ3207-002/NI_BQ3207-002-6.JPG', 'Rojo', 1, 0, 0),
(4, 'Chaqueta', 'P004', 'XL', 'https://www.stockcenter.com.ar/on/demandware.static/-/Sites-365-dabra-catalog/default/dw8c3e9aaf/products/KA31153FWA07/KA31153FWA07-1.JPG', 'Azul', 1, 0, 0),
(5, 'Bufanda', 'P005', 'Único', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ20UdFSOcKUCSAOj-DUpmT0nNNsIxrGD-_yQ&s', 'Verde', 1, 0, 0),
(6, 'Gorro de Lana', 'P006', 'Único', 'https://http2.mlstatic.com/D_NQ_NP_839450-MLA70350283266_072023-O.webp', 'Negro', 1, 0, 0),
(7, 'Chaleco', 'P007', 'L', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6TjQkwgsGfhwqad7pG68w4LkzwDRgHmm5AQ&s', 'Naranja', 1, 0, 0),
(8, 'Guantes', 'P008', 'M', 'https://seguridadglobal.com.ar/wp-content/uploads/2023/05/guantes-vaqueta-pulgar-volcado.jpg', 'Oro', 1, 0, 0),
(9, 'Cinturón', 'P009', 'Único', 'https://m.media-amazon.com/images/I/81qOglWUQAL._AC_SL1500_.jpg', 'Negro', 1, 0, 0),
(10, 'Pantalón Corto', 'P010', 'L', NULL, 'Gris', 1, 0, 0),
(11, 'Buzo', 'P011', 'L', NULL, 'Verde', 1, 0, 0),
(12, 'Sandalias', 'P012', '41', NULL, 'Marrón', 1, 0, 0),
(13, 'Cartera', 'P013', 'Único', NULL, 'Rojo', 1, 0, 0),
(14, 'Chaqueta Ligera', 'P014', 'M', NULL, 'Azul', 1, 0, 0),
(15, 'Pijama', 'P015', 'L', NULL, 'Rosa', 1, 0, 0);
