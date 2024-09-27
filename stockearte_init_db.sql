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
CREATE TABLE IF NOT EXISTS `stockearte`.`productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `codigo` VARCHAR(10) NULL,
  `talle` VARCHAR(5) NULL,
  `foto` VARCHAR(300) NULL,
  `color` VARCHAR(50) NULL,
  `habilitado` BOOLEAN NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`codigo` ASC) VISIBLE
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
INSERT INTO `productos` (`nombre`, `codigo`, `talle`, `foto`, `color`, `habilitado`)
VALUES 
('Camiseta', 'P001', 'M', "https://www.shutterstock.com/image-vector/3d-realistic-soccer-jersey-argentina-600nw-2446075731.jpg", 'Blanco', TRUE),
('Pantalón', 'P002', 'L', "https://acdn.mitiendanube.com/stores/002/023/047/products/marino-pantalon-stock-con-bolsillos11-c2e595f8e8f57a760616835590772521-1024-1024.png", 'Negro', TRUE),
('Zapatillas', 'P003', '42', "https://www.stockcenter.com.ar/on/demandware.static/-/Sites-365-dabra-catalog/default/dw5ea76a27/products/NI_BQ3207-002/NI_BQ3207-002-6.JPG", 'Rojo', TRUE),
('Chaqueta', 'P004', 'XL', "https://www.stockcenter.com.ar/on/demandware.static/-/Sites-365-dabra-catalog/default/dw8c3e9aaf/products/KA31153FWA07/KA31153FWA07-1.JPG", 'Azul', TRUE),
('Bufanda', 'P005', 'Único', "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ20UdFSOcKUCSAOj-DUpmT0nNNsIxrGD-_yQ&s", 'Verde', TRUE),
('Gorro de Lana', 'P006', 'Único', "https://http2.mlstatic.com/D_NQ_NP_839450-MLA70350283266_072023-O.webp", 'Negro', TRUE),
('Chaleco', 'P007', 'L', "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6TjQkwgsGfhwqad7pG68w4LkzwDRgHmm5AQ&s", 'Naranja', TRUE),
('Guantes', 'P008', 'M', "https://seguridadglobal.com.ar/wp-content/uploads/2023/05/guantes-vaqueta-pulgar-volcado.jpg", 'Oro', TRUE),
('Cinturón', 'P009', 'Único', "https://m.media-amazon.com/images/I/81qOglWUQAL._AC_SL1500_.jpg", 'Negro', TRUE),
('Pantalón Corto', 'P010', 'L', NULL, 'Gris', TRUE),
('Buzo', 'P011', 'L', NULL, 'Verde', TRUE),
('Sandalias', 'P012', '41', NULL, 'Marrón', TRUE),
('Cartera', 'P013', 'Único', NULL, 'Rojo', TRUE),
('Chaqueta Ligera', 'P014', 'M', NULL, 'Azul', TRUE),
('Pijama', 'P015', 'L', NULL, 'Rosa', TRUE);

-- -----------------------------------------------------
-- Insertar stock
-- -----------------------------------------------------
INSERT INTO `stock` (`fk_tienda_id`, `fk_producto_id`, `stock`)
VALUES 
((SELECT id FROM `tiendas` WHERE codigo = 'T001'), (SELECT id FROM `productos` WHERE codigo = 'P001'), 100),
((SELECT id FROM `tiendas` WHERE codigo = 'T001'), (SELECT id FROM `productos` WHERE codigo = 'P002'), 50),
((SELECT id FROM `tiendas` WHERE codigo = 'T001'), (SELECT id FROM `productos` WHERE codigo = 'P003'), 75),
((SELECT id FROM `tiendas` WHERE codigo = 'T001'), (SELECT id FROM `productos` WHERE codigo = 'P004'), 30),
((SELECT id FROM `tiendas` WHERE codigo = 'T002'), (SELECT id FROM `productos` WHERE codigo = 'P005'), 80),
((SELECT id FROM `tiendas` WHERE codigo = 'T002'), (SELECT id FROM `productos` WHERE codigo = 'P006'), 60),
((SELECT id FROM `tiendas` WHERE codigo = 'T002'), (SELECT id FROM `productos` WHERE codigo = 'P007'), 45),
((SELECT id FROM `tiendas` WHERE codigo = 'T003'), (SELECT id FROM `productos` WHERE codigo = 'P008'), 100),
((SELECT id FROM `tiendas` WHERE codigo = 'T003'), (SELECT id FROM `productos` WHERE codigo = 'P009'), 90),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P010'), 70),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P011'), 50),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P012'), 40),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P013'), 80),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P014'), 30),
((SELECT id FROM `tiendas` WHERE codigo = 'T004'), (SELECT id FROM `productos` WHERE codigo = 'P015'), 20);
