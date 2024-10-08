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

-- Crear la tabla de Orden de Compra
CREATE TABLE orden_de_compra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tienda_id BIGINT NOT NULL,
    estado ENUM('SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'RECIBIDA') NOT NULL,
    observaciones TEXT,
    id_orden_despacho BIGINT,
    fecha_solicitud DATETIME NOT NULL,
    fecha_recepcion DATETIME,
    FOREIGN KEY (tienda_id) REFERENCES tienda(id)
);

CREATE TABLE orden_de_despacho (
  id BIGINT NOT NULL AUTO_INCREMENT,
  id_orden_compra BIGINT,
  fecha_de_envio DATETIME,
  PRIMARY KEY (id),
  CONSTRAINT fk_orden_compra FOREIGN KEY (id_orden_compra) REFERENCES orden_de_compra(id) ON DELETE CASCADE
) ENGINE = InnoDB;

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
