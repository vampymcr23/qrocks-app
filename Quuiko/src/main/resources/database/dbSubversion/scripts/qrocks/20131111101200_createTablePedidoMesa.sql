/*TODO
@name:		20131111101200_createTablePedidoMesa.sql
@release:	
@date:		20131111101200
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.pedidoMesa(
	id				bigint not null primary key auto_increment,
	idMesa			bigint not null,
	fecha			timestamp default current_timestamp,
	activo			integer not null default 1
)engine=InnoDB;
--@Undo
/*
@name:		20131111101200_createTablePedidoMesa.sql
@release:	
@date:		20131111101200
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.pedidoMesa;