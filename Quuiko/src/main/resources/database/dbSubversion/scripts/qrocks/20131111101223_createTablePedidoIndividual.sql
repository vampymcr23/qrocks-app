/*TODO
@name:		20131111101223_createTablePedidoIndividual.sql
@release:	
@date:		20131111101223
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.pedidoIndividual(
	id				bigint primary key not null auto_increment,
	idProducto		bigint not null,
	idCliente		bigint not null,
	pagado			integer default 0
)engine=InnoDB;
--@Undo
/*
@name:		20131111101223_createTablePedidoIndividual.sql
@release:	
@date:		20131111101223
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.pedidoIndividual;