/*TODO
@name:		20131111101252_createTableProducto.sql
@release:	
@date:		20131111101252
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.producto(
	id				bigint primary key not null auto_increment,
	nombre			varchar(100) not null,
	costo			decimal(10,2) not null default 0,
	descripcion		varchar(200),
	activo			integer default 1,
	idNegocio 		bigint not null
)engine=InnoDB;
--@Undo
/*
@name:		20131111101252_createTableProducto.sql
@release:	
@date:		20131111101252
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.producto;