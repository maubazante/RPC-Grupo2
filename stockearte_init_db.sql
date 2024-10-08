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
('luisgomez', 'password3', 'STOREMANAGER', TRUE, 'Luis', 'Gomez', (SELECT id FROM `tiendas` WHERE codigo = 'T003')),
('analuque', 'password4', 'STOREMANAGER', TRUE, 'Ana', 'Luque', (SELECT id FROM `tiendas` WHERE codigo = 'T004'));

-- -----------------------------------------------------
-- Insertar productos
-- -----------------------------------------------------
INSERT INTO `stockearte`.`productos`
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

INSERT INTO producto (nombre, codigo, talle, foto, color, cantidad, habilitado) VALUES
('Camiseta Deportiva', 'CAM123', 'M', 'imagenes/camiseta_deportiva_m.jpg', 'Azul', 50, true),
('Pantalón Jeans', 'PAN456', 'L', 'imagenes/pantalon_jeans_l.jpg', 'Negro', 30, true);
