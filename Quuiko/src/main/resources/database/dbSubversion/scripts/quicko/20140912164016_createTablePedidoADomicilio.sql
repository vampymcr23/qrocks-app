/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.pedidoDomicilio(
	id bigint not null primary key auto_increment,
	idNegocio	bigint not null,
	gcmId		varchar(200) not null,
	estatus		varchar(12) not null,
	fechaRegistro	timestamp not null default current_timestamp,
	fechaEntrega	timestamp not null,
	total			decimal(10,2) not null default 0,
	tipoEntrega		varchar(10),
	notificacionEnviadaAut integer,
	notificacionEnviadaCompleto	integer,
	tiempoEntrega	varchar(25)
)engine=InnoDB;

alter table quicko_db.pedidoDomicilio add constraint fk_pedidoDomicilio_idNeg foreign key(idNegocio)  references quicko_db.negocio(id);
alter table quicko_db.pedidoDomicilio add constraint fk_pedidoDomicilio_usr foreign key(gcmId) references quicko_db.gcmUsr(id);

create table quicko_db.pedidoProductoDomicilio(
	id bigint not null primary key auto_increment,
	idProducto bigint not null,
	idPedido bigint not null,
	costo	decimal(10,2) not null default 0,
	estatus	varchar(12) not null default 'PEND'
)engine=InnoDB;

alter table quicko_db.pedidoProductoDomicilio add constraint fk_pedidoProdDom_idProducto foreign key(idProducto) references quicko_db.producto(id);
alter table quicko_db.pedidoProductoDomicilio add constraint fk_pedidoProdDom_idPedido foreign key(idPedido) references quicko_db.pedidoDomicilio(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.pedidoProductoDomicilio drop foreign key fk_pedidoProdDom_idProducto;
alter table quicko_db.pedidoProductoDomicilio drop foreign key fk_pedidoProdDom_idPedido;
drop table quicko_db.pedidoProductoDomicilio;

alter table quicko_db.pedidoDomicilio drop foreign key fk_pedidoDomicilio_usr;
alter table quicko_db.pedidoDomicilio drop foreign key fk_pedidoDomicilio_idNeg;
drop table quicko_db.pedidoDomicilio;