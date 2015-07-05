/*TODO
@name:		20131111101229_createTableCuenta.sql
@release:	
@date:		20131111101229
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.cuenta(
	id				bigint primary key not null auto_increment,
	idPedidoMesa	bigint not null,
	pagado			integer default 0,
	montoParcial	decimal(10,2) default 0,
	montoTotal		decimal(10,2) default 0,
	montoPropina	decimal(10,2) default 0,
	fecha			timestamp default current_timestamp
)engine=InnoDB;
--@Undo
/*
@name:		20131111101229_createTableCuenta.sql
@release:	
@date:		20131111101229
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.cuenta;