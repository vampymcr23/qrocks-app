/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.mesero(
	id bigint not null primary key auto_increment,
	idNegocio bigint not null,
	nombre varchar(20) not null 
)Engine=InnoDB;

alter table qrocks.mesero add constraint fk_mesero_negocio foreign key (idNegocio) references qrocks.negocio(id);

alter table qrocks.mesa add idMesero bigint;

alter table qrocks.mesa add constraint fk_mesa_mesero foreign key (idMesero) references qrocks.mesero(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.mesa drop column idMesero;

drop table qrocks.mesero;