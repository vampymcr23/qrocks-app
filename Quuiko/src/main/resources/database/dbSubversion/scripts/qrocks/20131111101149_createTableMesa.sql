/*TODO
@name:		20131111101149_createTableMesa.sql
@release:	
@date:		20131111101149
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.mesa(
	id				bigint primary key not null auto_increment,
	idNegocio		bigint not null,
	claveMesa		varchar(15)
)engine=InnoDB;
--@Undo
/*
@name:		20131111101149_createTableMesa.sql
@release:	
@date:		20131111101149
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.mesa;