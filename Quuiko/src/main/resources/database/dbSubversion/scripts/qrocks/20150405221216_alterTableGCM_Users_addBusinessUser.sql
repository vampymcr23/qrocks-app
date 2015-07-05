/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.appUsers(
	id bigint not null primary key auto_increment,
	userEmail varchar(100) not null,
	pwd	varchar(100) not null,
	estatus integer not null default 1
)Engine=InnoDB;

alter table qrocks.gcmUsr add  idAppUser bigint;

alter table qrocks.gcmUsr add constraint fk_gcmUsers_appUser foreign key(idAppUser) references qrocks.appUsers(id);

--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.pedidoDomicilio drop column comentarios;
alter table qrocks.pedidoDomicilio drop column formaPago;
alter table qrocks.pedidoDomicilio drop column cambio;
alter table qrocks.pedidoDomicilio drop column feria;
alter table qrocks.pedidoDomicilio drop column comentariosRestaurante;