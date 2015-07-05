/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.reservacion(
	id	bigint not null primary key auto_increment,
	idNegocio bigint not null,
	idUsuario varchar(200) not null,
	fechaRegistro timestamp default current_timestamp,
	fechaReservacion timestamp not null,
	estatus		varchar(10) not null default 'PEND'
)engine=InnoDB;
/* ESTATUS SOLO DE PEND - CAN - AUT - N/A - OK */
alter table qrocks.reservacion add constraint fk_reservacion_usr foreign key(idUsuario) references qrocks.gcmUsr(id);
alter table qrocks.reservacion add constraint fk_reservacion_neg foreign key(idNegocio) references qrocks.negocio(id);

create index idx_reservacion_neg on qrocks.reservacion(idNegocio);

create index idx_reservacion_negUsr on qrocks.reservacion(idNegocio,idUsuario);

create index idx_reservacion_negFechaRes on qrocks.reservacion(idNegocio,fechaReservacion);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.reservacion drop foreign key fk_reservacion_neg;
alter table qrocks.reservacion drop foreign key fk_reservacion_usr;
drop table qrocks.reservacion;