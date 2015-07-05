/*====================================================*/
create table if not exists dbSubversion.changelog(id int not null auto_increment primary key,name varchar(200) not null,registerDate timestamp default now(),version bigint not null,username varchar(100))Engine=InnoDB
create table if not exists dbSubversion.changelogProject(id integer not null auto_increment primary key,projectName varchar(50))Engine=InnoDB
create table if not exists dbSubversion.changelogRelease(id integer not null auto_increment primary key,releaseName varchar(50),idProject integer not null)Engine=InnoDB
alter table dbSubversion.changelog add idRelease integer;
alter table dbSubversion.changelog add constraint fk_changelogRelease foreign key(idRelease) references dbSubversion.changelogRelease(id);

insert into dbSubversion.changelogProject(projectName) select 'qrocks' from dual;
insert into dbSubversion.changelogRelease (releaseName,idProject)  select 'v1.0' as releaseName,p.id as idProject from dbSubversion.changelogProject p where p.projectName like 'qrocks';
/*====================================================*/
/*====================================================
 0.-20131111155334_addConstraints.sql  
====================================================*/

/*
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.mesa drop foreign key fk_MesaNegocio;

alter table qrocks.mesa drop index uq_mesa;

alter table qrocks.pedidoMesa drop foreign key fk_pedidoMesa_mesa;

alter table qrocks.cliente drop foreign key fk_Cliente_pedidoMesa;

alter table qrocks.pedidoIndividual drop foreign key fk_PedidoInv_Prod;

alter table qrocks.pedidoIndividual drop foreign key fk_PedidoInv_Cliente;

alter table qrocks.cuenta drop foreign key fk_cuenta_PedMesa;
null
/*====================================================
 1.-20131111101252_createTableProducto.sql  
====================================================*/

/*
@name:		20131111101252_createTableProducto.sql
@release:	
@date:		20131111101252
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.producto;
null
/*====================================================
 2.-20131111101229_createTableCuenta.sql  
====================================================*/

/*
@name:		20131111101229_createTableCuenta.sql
@release:	
@date:		20131111101229
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.cuenta;
null
/*====================================================
 3.-20131111101223_createTablePedidoIndividual.sql  
====================================================*/

/*
@name:		20131111101223_createTablePedidoIndividual.sql
@release:	
@date:		20131111101223
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.pedidoIndividual;
null
/*====================================================
 4.-20131111101210_createTableCliente.sql  
====================================================*/

/*
@name:		20131111101210_createTableCliente.sql
@release:	
@date:		20131111101210
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.cliente;
null
/*====================================================
 5.-20131111101200_createTablePedidoMesa.sql  
====================================================*/

/*
@name:		20131111101200_createTablePedidoMesa.sql
@release:	
@date:		20131111101200
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.pedidoMesa;
null
/*====================================================
 6.-20131111101149_createTableMesa.sql  
====================================================*/

/*
@name:		20131111101149_createTableMesa.sql
@release:	
@date:		20131111101149
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.mesa;
null
/*====================================================
 7.-20131111100739_createTableNegocio.sql  
====================================================*/

/*
@name:		20131111100739_createTableNegocio.sql
@release:	
@date:		20131111100739
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.negocio;
null
