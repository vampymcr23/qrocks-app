/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.bitacoraVisita(
	id bigint not null primary key auto_increment,
	gcmId	varchar(200) not null,
	idNegocio bigint not null,
	fechaVisita     timestamp default current_timestamp
)engine=InnoDB;
alter table quicko_db.bitacoraVisita add constraint fk_bitacoraVisita_usr foreign key(gcmId) references quicko_db.gcmUsr(id); 
alter table quicko_db.bitacoraVisita add constraint fk_bitacoraVisita_neg foreign key(idNegocio) references quicko_db.negocio(id); 
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table quicko_db.bitacoraVisita;