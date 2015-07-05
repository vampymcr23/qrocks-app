/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.mesero(
	id bigint not null primary key auto_increment,
	idNegocio bigint not null,
	nombre varchar(20) not null 
)Engine=InnoDB;

alter table quicko_db.mesero add constraint fk_mesero_negocio foreign key (idNegocio) references quicko_db.negocio(id);

alter table quicko_db.mesa add idMesero bigint;

alter table quicko_db.mesa add constraint fk_mesa_mesero foreign key (idMesero) references quicko_db.mesero(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.mesa drop column idMesero;

drop table quicko_db.mesero;