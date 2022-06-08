SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;
CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL, 
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente1','Apellido1');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente2','Apellido2');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente3','Apellido3');

CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL, 
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes)ON DELETE CASCADE
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1231',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC1131',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC1331',NOW());

CREATE TABLE productos(
id_productos					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos				VARCHAR(80) NOT NULL, 
precio_productos				DOUBLE NOT NULL
)DEFAULT CHARACTER SET utf8;

INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto1',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto2',1.12);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto3',23.30);

CREATE TABLE facturas_productos(
id_facturas					INTEGER NOT NULL,
id_productos					INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas) REFERENCES facturas(id_facturas)ON DELETE CASCADE,
FOREIGN KEY(id_productos) REFERENCES productos(id_productos)ON DELETE CASCADE
)DEFAULT CHARACTER SET utf8;

INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,120);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,2,20);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,1,70);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,3,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,1,17);

/*-----Vistas------------*/
create view todosclientes as select * from clientes;

create view todasfacturas as
select b.id_facturas, b.referencia_facturas, b.fecha_facturas,
 a.id_clientes,a.nombre_clientes,a.apellido_clientes from
 clientes a, facturas b where a.id_clientes=b.id_clientes;

create view todosproductos as
select * from ventas.productos;

create view todasfacturasproductos as
SELECT  b.cantidad_facturas_productos, a.id_facturas,
 a.referencia_facturas,a.fecha_facturas, c.id_productos , c.nombre_productos, c.precio_productos
from facturas_productos b, facturas a, productos c
where b.id_facturas = a.id_facturas and c.id_productos = b.id_productos;


DELIMITER $$
create trigger clientes_mayus before insert on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus before insert on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);  
end $$
DELIMITER ;

/* ---------Clientes Queries------------- */
DELIMITER $$
CREATE  PROCEDURE todosClientes()
BEGIN
	select * from todosClientes;
END $$
DELIMITER ;
DELIMITER $$

CREATE  PROCEDURE insertarClientes(in nombre varchar(25),in apellido varchar(25))
BEGIN
	insert into clientes(nombre_clientes,apellido_clientes) values(nombre,apellido);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarClientes(in id int)
BEGIN
	delete from clientes where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarClientes(in id int,in nombre varchar(25),in apellido varchar(25))
BEGIN
	update clientes set nombre_clientes=nombre,apellido_clientes=apellido where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE buscarClientes(in patron varchar(15))
BEGIN
	select * from clientes where nombre_clientes like concat('%',patron,'%');
END $$


/*---------------FACTURAS queries-------------*/


DELIMITER $$
CREATE  PROCEDURE todasFacturas()
BEGIN
	select * from todasfacturas;
END $$
DELIMITER ; 

DELIMITER $$
CREATE  PROCEDURE insertarFacturas(in id_cli integer,in referencia_fact varchar(10),in fecha_fact date)
BEGIN
	insert into facturas(id_clientes,referencia_facturas,fecha_facturas) values(id_cli,referencia_fact,fecha_fact);
END $$
DELIMITER ; 

DELIMITER $$
CREATE  PROCEDURE modificarFacturas(in id_fact int,in id_cli integer,in referencia_fact varchar(40),in fecha_fact date)
BEGIN
	update facturas set id_clientes=id_cli,referencia_facturas=referencia_fact,fecha_facturas=fecha_fact where id_facturas=id_fact;
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFacturas(in id int)
BEGIN
	delete from facturas where id_facturas=id;
END $$

DELIMITER $$
CREATE  PROCEDURE buscarFacturas(IN patron varchar(15))
BEGIN
	SELECT a.id_facturas, a.referencia_facturas, a.fecha_facturas, b.id_clientes, b.nombre_clientes, b.apellido_clientes FROM
    facturas a, clientes b WHERE a.id_clientes=b.id_clientes AND a.referencia_facturas LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;

/*-----------------------Productos--------------------*/

select * from todosproductos;
DELIMITER $$
CREATE  PROCEDURE todosProductos()
BEGIN
	select * from todosproductos;
END $$

DELIMITER ;

call todosProductos();

DELIMITER $$
CREATE  PROCEDURE insertarProductos(in nombre_pro varchar(80),in precio_pro double)
BEGIN
	insert into productos(nombre_productos,precio_productos) values(nombre_pro,precio_pro);
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarProductos(in id int)
BEGIN
	delete from productos where id_productos=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarProductos(in id int,in nombre_pro varchar(80),in precio_pro double)
BEGIN
	update productos set nombre_productos=nombre_pro,precio_productos=precio_pro where id_productos=id;
END $$

DELIMITER $$
CREATE  PROCEDURE buscarProductos(in patron varchar(15))
BEGIN
	select * from productos where nombre_productos like concat('%',patron,'%');
END $$

/*----------------- FAcrutas Productos-------------------------*/

DELIMITER $$
CREATE  PROCEDURE todasFacturasProductos()
BEGIN
	select * from todasfacturasproductos;
END $$


DELIMITER $$
CREATE  PROCEDURE insertarFacturasProductos(in id_fact int,in id_pro int,in cantfact double)
BEGIN
	insert into facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(id_fact,id_pro,cantfact);
END $$
DELIMITER ; 

DELIMITER $$
CREATE  PROCEDURE modificarFacturasProductos(IN idFactura INT, IN idProducto INT, IN cantidad DOUBLE)
BEGIN
	UPDATE facturas_productos SET id_facturas=idFactura, id_productos=idProducto, cantidad_facturas_productos=cantidad 
    WHERE id_facturas = idFactura AND id_productos = idProducto;
END $$

DELIMITER $$
CREATE  PROCEDURE eliminarFacturasProductos(in id_fact int,in id_prod int)
BEGIN
	delete from facturas_productos where id_facturas=id_fact and id_productos=id_prod;
END $$
