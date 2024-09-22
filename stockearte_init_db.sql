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
  `foto` BLOB NULL,
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
VALUES ('admin', 'admintienda', 'ADMIN', TRUE, 'Admin', 'Tienda', @tienda_id);

