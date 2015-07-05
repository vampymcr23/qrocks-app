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
 0.-20131111100739_createTableNegocio.sql  
====================================================*/
/*TODO
@name:		20131111100739_createTableNegocio.sql
@release:	
@date:		20131111100739
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.negocio(
    id                  integer primary key auto_increment not null,
    nombre              varchar(100) not null,
    usuario             varchar(100) not null,
    fecha               timestamp default current_timestamp,
    licencia            varchar(10),
    serial              varchar(50),
    activo              integer,
    online              integer
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111100739_createTableNegocio.sql','20131111100739','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 1.-20131111101149_createTableMesa.sql  
====================================================*/
/*TODO
@name:		20131111101149_createTableMesa.sql
@release:	
@date:		20131111101149
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.mesa(
	id				integer primary key not null auto_increment,
	idNegocio		integer not null,
	claveMesa		varchar(15)
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101149_createTableMesa.sql','20131111101149','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 2.-20131111101200_createTablePedidoMesa.sql  
====================================================*/
/*TODO
@name:		20131111101200_createTablePedidoMesa.sql
@release:	
@date:		20131111101200
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.pedidoMesa(
	id				integer not null primary key auto_increment,
	idMesa			integer not null,
	fecha			timestamp default current_timestamp,
	activo			integer not null default 1
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101200_createTablePedidoMesa.sql','20131111101200','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 3.-20131111101210_createTableCliente.sql  
====================================================*/
/*TODO
@name:		20131111101210_createTableCliente.sql
@release:	
@date:		20131111101210
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.cliente(
	id 				integer primary key not null auto_increment,
	alias			varchar(25),
	idPedidoMesa	integer not null
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101210_createTableCliente.sql','20131111101210','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 4.-20131111101223_createTablePedidoIndividual.sql  
====================================================*/
/*TODO
@name:		20131111101223_createTablePedidoIndividual.sql
@release:	
@date:		20131111101223
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.pedidoIndividual(
	id				integer primary key not null auto_increment,
	idProducto		integer not null,
	idCliente		integer not null,
	pagado			integer default 0
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101223_createTablePedidoIndividual.sql','20131111101223','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 5.-20131111101229_createTableCuenta.sql  
====================================================*/
/*TODO
@name:		20131111101229_createTableCuenta.sql
@release:	
@date:		20131111101229
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.cuenta(
	id				integer primary key not null auto_increment,
	idPedidoMesa	integer not null,
	pagado			integer default 0,
	montoParcial	decimal(10,2) default 0,
	montoTotal		decimal(10,2) default 0,
	montoPropina	decimal(10,2) default 0,
	fecha			timestamp default current_timestamp
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101229_createTableCuenta.sql','20131111101229','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 6.-20131111101252_createTableProducto.sql  
====================================================*/
/*TODO
@name:		20131111101252_createTableProducto.sql
@release:	
@date:		20131111101252
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.producto(
	id				integer primary key not null auto_increment,
	nombre			varchar(100) not null,
	costo			decimal(10,2) not null default 0,
	descripcion		varchar(200),
	activo			integer default 1
)engine=InnoDB;

insert into changelog(name,version,userName,idRelease) select '20131111101252_createTableProducto.sql','20131111101252','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
/*====================================================
 7.-20131111155334_addConstraints.sql  
====================================================*/
/*TODO
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.mesa add constraint fk_MesaNegocio foreign key(idNegocio) references qrocks.negocio(id);

alter table qrocks.mesa add constraint uq_mesa unique(idNegocio,claveMesa);

alter table qrocks.pedidoMesa add constraint fk_pedidoMesa_mesa foreign key(idMesa) references qrocks.mesa(id);

alter table qrocks.cliente add constraint fk_Cliente_pedidoMesa foreign key(idPedidoMesa) references qrocks.pedidoMesa(id);

alter table qrocks.pedidoIndividual add constraint fk_PedidoInv_Prod foreign key(idProducto) references qrocks.producto(id);

alter table qrocks.pedidoIndividual add constraint fk_PedidoInv_Cliente foreign key(idCliente) references qrocks.cliente(id);

alter table qrocks.cuenta add constraint fk_cuenta_PedMesa foreign key(idPedidoMesa) references qrocks.pedidoMesa(id);

insert into changelog(name,version,userName,idRelease) select '20131111155334_addConstraints.sql','20131111155334','vampy',rel.id  from dbSubversion.changelogRelease rel inner join dbSubversion.changelogProject project on project.id=rel.idProject where project.projectName='qrocks' and rel.releaseName='v1.0'
