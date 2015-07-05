/*TODO
@name:		20131111101210_createTableCliente.sql
@release:	
@date:		20131111101210
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.cliente(
	id 				bigint primary key not null auto_increment,
	alias			varchar(25),
	idPedidoMesa	bigint not null
)engine=InnoDB;
--@Undo
/*
@name:		20131111101210_createTableCliente.sql
@release:	
@date:		20131111101210
@author:	vampy
@summary:	
@objects:	
@END*/
drop table quicko_db.cliente;