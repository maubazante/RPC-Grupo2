-- MySQL Script generated by MySQL Workbench
-- Sun Sep 15 14:01:04 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema stockearte
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema stockearte
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `stockearte` DEFAULT CHARACTER SET utf8MB4 ;
USE `stockearte` ;

-- -----------------------------------------------------
-- Table `stockearte`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stockearte`.`usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `rol` ENUM("ADMIN", "STOREMANAGER") NOT NULL,
  `habilitado` BOOLEAN NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(100) NOT NULL,
  `fk_tienda_id` bigint NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_usuario_tienda`
    FOREIGN KEY (`fk_tienda_id`)
    REFERENCES `stockearte`.`tiendas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `stockearte`.`filtro_de_ordenes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NULL,
  `filtro_codigo_producto` TINYINT NULL,
  `filtro_rango_de_fechas` TINYINT NULL,
  `filtro_estado` TINYINT NULL,
  `filtro_tienda` TINYINT NULL,
  `fk_usuarios_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `fk_usuarios_id`),
  INDEX `fk_filtro_de_ordenes_usuarios_idx` (`fk_usuarios_id` ASC) VISIBLE,
  CONSTRAINT `fk_filtro_de_ordenes_usuarios`
    FOREIGN KEY (`fk_usuarios_id`)
    REFERENCES `stockearte`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stockearte`.`tiendas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stockearte`.`tiendas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(20) NOT NULL UNIQUE,
  `direccion` VARCHAR(255) NOT NULL,
  `ciudad` VARCHAR(100) NOT NULL,
  `provincia` VARCHAR(100) NOT NULL,
  `habilitada` BOOLEAN NOT NULL,
  `es_casa_central` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stockearte`.`productos`
-- -----------------------------------------------------
CREATE TABLE productos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  codigo VARCHAR(10) NOT NULL UNIQUE,
  talle VARCHAR(5) NOT NULL,
  foto VARCHAR(300) NOT NULL,
  color VARCHAR(50) NOT NULL,
  cantidad INT NOT NULL, -- Cambiar "stock" a "cantidad"
  habilitado BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `stockearte`.`stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stockearte`.`stock` (
  `fk_tienda_id` bigint NOT NULL,
  `fk_producto_id` bigint NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`fk_tienda_id`, `fk_producto_id`),
  INDEX `fk_store_has_product_product1_idx` (`fk_producto_id` ASC) VISIBLE,
  INDEX `fk_store_has_product_store1_idx` (`fk_tienda_id` ASC) VISIBLE,
  CONSTRAINT `fk_store_has_product_store1`
    FOREIGN KEY (`fk_tienda_id`)
    REFERENCES `stockearte`.`tiendas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_store_has_product_product1`
    FOREIGN KEY (`fk_producto_id`)
    REFERENCES `stockearte`.`productos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;



-- Crear la tabla de Orden de Compra
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
  tiendas_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_tiendas FOREIGN KEY (tiendas_id) REFERENCES tiendas(id) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE novedades (
  id BIGINT NOT NULL AUTO_INCREMENT,
  codigo_producto VARCHAR(10) NOT NULL,
  talle VARCHAR(5) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  color VARCHAR(30) NOT NULL,
  url_foto VARCHAR(300),
  habilitado BOOLEAN NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Índices adicionales para optimización de búsqueda
CREATE INDEX idx_estado ON orden_de_compra (estado);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- Insertar datos iniciales para la tienda central

-- La tienda será marcada como casa central
INSERT INTO `stockearte`.`tiendas` (`codigo`, `direccion`, `ciudad`, `provincia`, `habilitada`, `es_casa_central`)
VALUES ('T001', 'Av. 29 de Septiembre 3901', 'Lanús', 'Buenos Aires', TRUE, TRUE);

SET @tienda_id = LAST_INSERT_ID();

-- Insertar un usuario admin asociado a la tienda central
INSERT INTO `stockearte`.`usuarios` (`username`, `password`, `rol`, `habilitado`, `nombre`, `apellido`, `fk_tienda_id`)
VALUES ('admin', 'admin', 'ADMIN', TRUE, 'Admin', 'Tienda', @tienda_id);

-- -----------------------------------------------------
-- Insertar tiendas
-- -----------------------------------------------------
INSERT INTO `tiendas` (`codigo`, `direccion`, `ciudad`, `provincia`, `habilitada`, `es_casa_central`)
VALUES 
('T002', 'Alsina 100', 'CABA', 'Buenos Aires', TRUE, FALSE),
('T003', '16 de Julio', 'Rosario', 'Santa Fe', TRUE, FALSE),
('T004', 'Mercedes 742', 'Córdoba', 'Córdoba', TRUE, FALSE);

-- -----------------------------------------------------
-- Insertar usuarios
-- -----------------------------------------------------
INSERT INTO `usuarios` (`username`, `password`, `rol`, `habilitado`, `nombre`, `apellido`, `fk_tienda_id`)
VALUES 
('juanperez', 'password1', 'STOREMANAGER', TRUE, 'Juan', 'Perez', (SELECT id FROM `tiendas` WHERE codigo = 'T001')),
('mariasanchez', 'password2', 'STOREMANAGER', TRUE, 'Maria', 'Sanchez', (SELECT id FROM `tiendas` WHERE codigo = 'T002')),
('luisgomez', 'password3', 'STOREMANAGER', FALSE, 'Luis', 'Gomez', (SELECT id FROM `tiendas` WHERE codigo = 'T003')),
('analuque', 'password4', 'STOREMANAGER', TRUE, 'Ana', 'Luque', (SELECT id FROM `tiendas` WHERE codigo = 'T004'));

-- -----------------------------------------------------
-- Insertar productos
-- -----------------------------------------------------
-- Camiseta Deportiva
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Camiseta Deportiva', 'CMD016', 'L', 'https://sublimesport.com.ar/wp-content/uploads/2017/09/011-01.png', 'Azul', 50, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 50);

-- Remera básica
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Remera básica', 'RMB001', 'M', 'https://acdn.mitiendanube.com/stores/943/997/products/basica-negra1-9ede7dca77d434160616676898161690-1024-1024.jpg', 'Negro', 50, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 50);

-- Pantalón jeans
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Pantalón jeans', 'PTJ002', 'L', 'https://www.grupooxicas.com/3082-thickbox_default/pantalon-jean-far-west-indigo-14-onzas-talles-del-38-al-60.jpg', 'Azul', 35, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 35);

-- Campera abrigo
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Campera abrigo', 'CMP003', 'XL', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/c/a/campera-de-abrigo-con-capucha-under-armour-storm-armour-mujer-gris-700021372648001-1.jpg', 'Negro', 20, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 20);

-- Camisa formal
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Camisa formal', 'CMS004', 'M', 'https://http2.mlstatic.com/D_660286-MLA53603382519_022023-O.jpg', 'Blanco', 15, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 15);

-- Vestido casual
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Vestido casual', 'VST005', 'S', 'https://i.pinimg.com/736x/2d/9a/6a/2d9a6a4ef44d6b078a92fec52119538d.jpg', 'Verde', 40, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 40);

-- Zapatillas deportivas
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Zapatillas deportivas', 'ZPD006', '42', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/z/a/zapatillas-running-adidas-runfalcon-3-0-negra-90497022-100010hq3790001-1.jpg', 'Negro', 25, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 25);

-- Gorra de algodón
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Gorra de algodón', 'GRR007', 'Único', 'https://nuevogema-cdn.b-cdn.net/Content/UploadDirectory/Products/1452/image_144495c2-5e15-4533-8f98-1fc770e4915b.jpg', 'Azul', 60, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 60);

-- Buzo con capucha
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Buzo con capucha', 'BZO008', 'L', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/b/u/buzo-con-capucha-hang-loose-picher-208020008029002-1.jpg', 'Gris', 30, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 30);

-- Cinturón cuero
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Cinturón cuero', 'CTN009', 'L', 'https://d368r8jqz0fwvm.cloudfront.net/7038-product_lg/cinturon-de-cuero-de-hombre-chane.jpg', 'Marrón', 80, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 80);

-- Sombrero de playa
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Sombrero de playa', 'SMB010', 'Único', 'https://acdn.mitiendanube.com/stores/003/993/269/products/whatsapp-image-2024-07-05-at-15-59-59-2-ee57fbd46044c6b33e17206467966326-1024-1024.jpeg', 'Beige', 45, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 45);

-- Bufanda lana
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Bufanda lana', 'BFD011', 'Único', 'https://ar.isadoraonline.com/media/catalog/product/4/3/43451101_0_1_20240412120951.jpg', 'Rojo', 25, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 25);

-- Guantes cuero
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Guantes cuero', 'GVN012', 'M', 'https://arandu.com.ar/media/2020/06/07490N-1.jpg', 'Negro', 10, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 10);

-- Chaqueta de cuero
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Chaqueta de cuero', 'CHQ013', 'L', 'https://dorianargentina.com/wp-content/uploads/2021/01/Montreal-Marro%CC%81n-A.jpg', 'Marrón', 5, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 5);

-- Falda corta
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Falda corta', 'FLD014', 'S', 'https://acdn.mitiendanube.com/stores/096/689/products/54-6c5ff8e2a85e469e8617271883599591-1024-1024.png', 'Rojo', 30, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (1, @producto_id, 30);

-- Pantalones cargo
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Pantalones cargo', 'PTC015', 'M', 'https://acdn.mitiendanube.com/stores/339/112/products/sin-titulo-5kkk-60-c620fe2964d543272a52b54f45bba2a-1024-1024.png', 'Verde', 20, false);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (2, @producto_id, 20);

-- Chaleco de abrigo
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad, habilitado) 
VALUES ('Chaleco de abrigo', 'CHV016', 'M', 'https://cdn.shopify.com/s/files/1/0637/4801/products/F79705_01.jpg?v=1676931640', 'Gris', 15, true);
SET @producto_id = LAST_INSERT_ID();
INSERT INTO stock (fk_tienda_id, fk_producto_id, stock) 
VALUES (2, @producto_id, 15);

