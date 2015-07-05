/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.gcmUsrNegocio(
	id bigint not null primary key auto_increment,
	gcmId	varchar(200) not null,
	idNegocio bigint not null,
	email varchar(150) ,
	fechaCreacion timestamp not null default CURRENT_TIMESTAMP,
	ultimaVisita date,
	recibirNotificacion integer not null default 1
)Engine=InnoDB;

alter table qrocks.gcmUsrNegocio add constraint fk_gcmUN_negocio foreign key (idNegocio) references qrocks.negocio(id);
alter table qrocks.gcmUsrNegocio add constraint fk_gcmUN_gcmId foreign key (gcmId) references qrocks.gcmUsr(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.gcmUsrNegocio;