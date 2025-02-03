DROP DATABASE IF EXISTS canastaFeliz;
CREATE DATABASE IF NOT EXISTS canastaFeliz;
USE canastaFeliz;

-- CREACIÓN DE LAS TABLAS

DROP TABLE IF EXISTS proveedores;
CREATE TABLE IF NOT EXISTS proveedores(
id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
rif VARCHAR(10) NOT NULL,
nombre VARCHAR(50),
apellido VARCHAR(50),
telefono VARCHAR(11),
correo VARCHAR(30),
direccion VARCHAR(350),
UNIQUE(rif)
)engine=innoDB;

DROP TABLE IF EXISTS productos;
CREATE TABLE IF NOT EXISTS productos (
id_producto INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(30),
precio DECIMAL(10,2),
merma_promedio DECIMAL (5,2),
fk_id_proveedor INT(8),
FOREIGN KEY (fk_id_proveedor) REFERENCES proveedores(id_proveedor)
)engine=innoDB;

DROP TABLE IF EXISTS cortes;

DROP TABLE IF EXISTS inventario;
CREATE TABLE IF NOT EXISTS inventario(
id_inventario INT AUTO_INCREMENT PRIMARY KEY,
fk_id_producto INT(8),
peso_inicial DECIMAL(8,2),
peso_actual DECIMAL(8,2),
fecha_entrada DATETIME,
FOREIGN KEY (fk_id_producto) REFERENCES productos(id_producto)
)engine=innoDB;

DROP TABLE IF EXISTS movimientos_inventario;
CREATE TABLE IF NOT EXISTS movimientos_inventario(
id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
movimiento VARCHAR(7),
cantidad DECIMAL(8,2),
fecha DATETIME,
fk_id_producto INT(8),
fk_id_inventario INT(8),
FOREIGN KEY (fk_id_producto) REFERENCES inventario(fk_id_producto),
FOREIGN KEY (fk_id_inventario) REFERENCES inventario(id_inventario)
)engine=innoDB;

DROP TABLE IF EXISTS movimientos_cocina;
CREATE TABLE IF NOT EXISTS movimientos_cocina(
id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
cantidad DECIMAL(8,2),
merma_real DECIMAL(5,2),
merma_promedio DECIMAL (5,2),
peso_merma_real DECIMAL (8,2),
peso_merma_promedio DECIMAL (8,2),
fecha DATETIME,
fk_id_producto INT(8),
FOREIGN KEY (fk_id_producto) REFERENCES inventario(fk_id_producto)
)engine=innoDB;

DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS usuarios(
id_usuario INT AUTO_INCREMENT PRIMARY KEY,
cedula VARCHAR(10),
nombre VARCHAR(50),
apellido VARCHAR(50),
rol VARCHAR(12),
usuario VARCHAR(15),
clave VARCHAR(30),
UNIQUE(cedula),
UNIQUE(usuario)
)engine=innoDB;

-- TRIGGERS

CREATE TRIGGER newInvMov
AFTER INSERT ON inventario
FOR EACH ROW
	INSERT INTO movimientos_inventario (movimiento, cantidad, fecha, fk_id_producto, fk_id_inventario)
	VALUES ("Entrada", NEW.peso_inicial, NEW.fecha_entrada, NEW.fk_id_producto, NEW.id_inventario);
    

CREATE TRIGGER newInvEx
AFTER UPDATE ON inventario
FOR EACH ROW
	INSERT INTO movimientos_inventario (movimiento, cantidad, fecha, fk_id_producto, fk_id_inventario)
	VALUES ("Salida", OLD.peso_actual - NEW.peso_actual, CURRENT_TIMESTAMP, NEW.fk_id_producto, NEW.id_inventario);

-- CREACIÓN DE PROVEEDORES PARA USAR COMO EJEMPLO
INSERT INTO proveedores (rif, nombre, apellido, telefono, correo, direccion)
VALUES ('30468971-9', 'Pedro', 'Pérez', '4123456789', 'pedroperez@gmail.com', 'Avenida Libertador, Edificio Sol Naciente, Apartamento 5B, Caracas, Venezuela');
INSERT INTO proveedores (rif, nombre, apellido, telefono, correo, direccion)
VALUES ('12345678-9', 'María', 'Serrano', '4149876543', 'mariaserrano@gmail.com', 'Calle Principal, Edificio Innovación, Oficina 302, Maracaibo, Venezuela');

INSERT INTO productos (nombre, precio, merma_promedio, fk_id_proveedor) VALUES ('Salmón limpio', 300, 15, 1);
INSERT INTO inventario (fk_id_producto, peso_inicial, peso_actual, fecha_entrada) VALUES (1, 30, 30, CURRENT_TIMESTAMP());
INSERT INTO usuarios (cedula, nombre, apellido, rol, usuario, clave) VALUES ('V12345678', 'Tecnico', 'Canasta Feliz', 1, 'tecnicocf', '12345678');
INSERT INTO usuarios (cedula, nombre, apellido, rol, usuario, clave) VALUES ('V12345679', 'Supervisor', 'Canasta Feliz', 2, 'supervisorcf', '123456789');
INSERT INTO usuarios (cedula, nombre, apellido, rol, usuario, clave) VALUES ('V12345670', 'Cocinero', 'Canasta Feliz', 3, 'cocinerocf', '1234567890');

SELECT * FROM movimientos_inventario;
select productos.nombre, productos.precio, proveedores.nombre, proveedores.apellido from productos inner join proveedores on productos.id_producto=proveedores.id_proveedor;





