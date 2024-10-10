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

-- -----------------------------------------------------
-- Insertar producto
-- -----------------------------------------------------
INSERT INTO `proveedorsys`.`producto`
(`nombre`, `codigo`, `talle`, `foto`, `color`, `habilitado`, `cantidad`)
VALUES
('Camiseta Deportiva', 'CMD016', 'L', 'https://sublimesport.com.ar/wp-content/uploads/2017/09/011-01.png', 'Azul', true, 50),
('Remera básica', 'RMB001', 'M', 'https://acdn.mitiendanube.com/stores/943/997/products/basica-negra1-9ede7dca77d434160616676898161690-1024-1024.jpg', 'Negro', true, 50),
('Pantalón jeans', 'PTJ002', 'L', 'https://www.grupooxicas.com/3082-thickbox_default/pantalon-jean-far-west-indigo-14-onzas-talles-del-38-al-60.jpg', 'Azul', true, 35),
('Campera abrigo', 'CMP003', 'XL', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/c/a/campera-de-abrigo-con-capucha-under-armour-storm-armour-mujer-gris-700021372648001-1.jpg', 'Negro', true, 20),
('Camisa formal', 'CMS004', 'M', 'https://http2.mlstatic.com/D_660286-MLA53603382519_022023-O.jpg', 'Blanco', true, 15),
('Vestido casual', 'VST005', 'S', 'https://i.pinimg.com/736x/2d/9a/6a/2d9a6a4ef44d6b078a92fec52119538d.jpg', 'Verde', true, 40),
('Zapatillas deportivas', 'ZPD006', '42', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/z/a/zapatillas-running-adidas-runfalcon-3-0-negra-90497022-100010hq3790001-1.jpg', 'Negro', true, 25),
('Gorra de algodón', 'GRR007', 'Único', 'https://nuevogema-cdn.b-cdn.net/Content/UploadDirectory/Products/1452/image_144495c2-5e15-4533-8f98-1fc770e4915b.jpg', 'Azul', true, 60),
('Buzo con capucha', 'BZO008', 'L', 'https://media2.solodeportes.com.ar/media/catalog/product/cache/7c4f9b393f0b8cb75f2b74fe5e9e52aa/b/u/buzo-con-capucha-hang-loose-picher-208020008029002-1.jpg', 'Gris', true, 30),
('Cinturón cuero', 'CTN009', 'L', 'https://d368r8jqz0fwvm.cloudfront.net/7038-product_lg/cinturon-de-cuero-de-hombre-chane.jpg', 'Marrón', true, 80),
('Sombrero de playa', 'SMB010', 'Único', 'https://acdn.mitiendanube.com/stores/003/993/269/products/whatsapp-image-2024-07-05-at-15-59-59-2-ee57fbd46044c6b33e17206467966326-1024-1024.jpeg', 'Beige', true, 45),
('Bufanda lana', 'BFD011', 'Único', 'https://ar.isadoraonline.com/media/catalog/product/4/3/43451101_0_1_20240412120951.jpg', 'Rojo', true, 25),
('Guantes cuero', 'GVN012', 'M', 'https://arandu.com.ar/media/2020/06/07490N-1.jpg', 'Negro', true, 10),
('Chaqueta de cuero', 'CHQ013', 'L', 'https://dorianargentina.com/wp-content/uploads/2021/01/Montreal-Marro%CC%81n-A.jpg', 'Marrón', true, 5),
('Falda corta', 'FLD014', 'S', 'https://acdn.mitiendanube.com/stores/096/689/products/54-6c5ff8e2a85e469e8617271883599591-1024-1024.png', 'Rojo', true, 30),
('Pantalones cargo', 'PTC015', 'M', 'https://acdn.mitiendanube.com/stores/339/112/products/sin-titulo-5kkk-60-c620fe2964d543272a52b54f45bba2a-1024-1024.png', 'Verde', true, 20),
('Chaleco de abrigo', 'CHV016', 'M', 'https://cdn.shopify.com/s/files/1/0637/4801/products/F79705_01.jpg?v=1676931640', 'Gris', true, 15),
('Chaleco acolchado', 'CHL017', 'XL', 'https://tequierofashion.com/cdn/shop/products/S4661db27284344abb417a10634348cafN_1024x1024.jpg', 'Negro', true, 60),
('Zapatos de cuero', 'ZPC018', '42', 'https://www.rallys.com.ar/wp-content/uploads/2021/04/24575-4.jpg', 'Negro', true, 75),
('Polo de algodón', 'PLA019', 'M', 'https://plazavea.vteximg.com.br/arquivos/ids/1924772-512-512/image-98ea0e8908874d2789428b8e38d55bbe.jpg', 'Celeste', false, 80),
('Mochila escolar', 'MCH020', 'Único', 'https://samsonite.com.ar/cdn/shop/files/46a249b3520e294154a612a2e2d3113fea4569c210ba5e04185c209dd2a1695d.jpg', 'Rosa', true, 90);
