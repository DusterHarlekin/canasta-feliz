DROP DATABASE IF EXISTS canastaFeliz;
CREATE DATABASE IF NOT EXISTS canastaFeliz;
USE canastaFeliz;

-- CREACIÓN DE LAS TABLAS

DROP TABLE IF EXISTS proveedores;
CREATE TABLE IF NOT EXISTS proveedores(
id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
rif VARCHAR(10),
nombre VARCHAR(50),
apellido VARCHAR(50),
telefono VARCHAR(11),
correo VARCHAR(30),
direccion VARCHAR(350)
)engine=innoDB;

DROP TABLE IF EXISTS productos;
CREATE TABLE IF NOT EXISTS productos (
id_producto INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(30),
precio DECIMAL(10,2),
fk_id_proveedor INT(8),
FOREIGN KEY (fk_id_proveedor) REFERENCES proveedores(id_proveedor)
)engine=innoDB;

DROP TABLE IF EXISTS cortes;
CREATE TABLE IF NOT EXISTS cortes(
id_corte INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(30),
merma_promedio DECIMAL(5,2),
fk_id_producto INT(8),
FOREIGN KEY (fk_id_producto) REFERENCES productos(id_producto)
)engine=innoDB;

DROP TABLE IF EXISTS inventario;
CREATE TABLE IF NOT EXISTS inventario(
id_producto INT AUTO_INCREMENT PRIMARY KEY,
peso_inicial DECIMAL(8,2),
peso_actual DECIMAL(8,2),
merma_total DECIMAL (5,2),
lote VARCHAR (6),
fecha_entrada DATE,
FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
)engine=innoDB;

DROP TABLE IF EXISTS movimientos_inventario;
CREATE TABLE IF NOT EXISTS movimientos_inventario(
id_movimientos INT AUTO_INCREMENT PRIMARY KEY,
movimiento VARCHAR(7),
cantidad DECIMAL(8,2),
merma DECIMAL(8,2),
fecha DECIMAL(8,2),
fk_id_producto INT(8),
FOREIGN KEY (fk_id_producto) REFERENCES inventario(id_producto)
)engine=innoDB;

DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS usuarios(
id_usuario INT AUTO_INCREMENT PRIMARY KEY,
cedula VARCHAR(10),
nombre VARCHAR(50),
apellido VARCHAR(50),
rol VARCHAR(12),
usuario VARCHAR(15),
clave VARCHAR(30)
)engine=innoDB;

-- CREACIÓN DE PROVEEDORES PARA USAR COMO EJEMPLO
INSERT INTO proveedores (rif, nombre, apellido, telefono, correo, direccion)
VALUES ('30468971-9', 'Pedro', 'Pérez', '4123456789', 'pedroperez@gmail.com', 'Avenida Libertador, Edificio Sol Naciente, Apartamento 5B, Caracas, Venezuela');
INSERT INTO proveedores (rif, nombre, apellido, telefono, correo, direccion)
VALUES ('12345678-9', 'María', 'Serrano', '4149876543', 'mariaserrano@gmail.com', 'Calle Principal, Edificio Innovación, Oficina 302, Maracaibo, Venezuela');

INSERT INTO productos (nombre, precio, fk_id_proveedor) VALUES ('Salmón', 300, 1);
INSERT INTO cortes (nombre, merma_promedio, fk_id_producto) VALUES ('Lomo', 25, 1);
INSERT INTO cortes (nombre, merma_promedio, fk_id_producto) VALUES ('Filete', 20, 1);

select productos.nombre, productos.precio, proveedores.nombre, proveedores.apellido from productos inner join proveedores on productos.id_producto=proveedores.id_proveedor;
select cortes.nombre, cortes.merma_promedio, productos.nombre from cortes inner join productos on cortes.id_corte=productos.id_producto;
select * from cortes;






