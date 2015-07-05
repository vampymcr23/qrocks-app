/*TODO
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.youtubePlaylist(
	id varchar(50) not null primary key,
	nombre	varchar(150) not null
)Engine=InnoDB;

create table quicko_db.youtubeVideo(
	id bigint not null primary key auto_increment,
	idVideo varchar(50) not null,
	titulo varchar(150) not null,
	descripcion varchar(600),
	urlImagenDefault varchar(300),
	urlImagenMediana varchar(300),
	urlImagenGrande varchar(300),
	idPlaylist varchar(50) not null
)Engine=InnoDB;

alter table quicko_db.youtubeVideo add constraint fk_youtubeVideo_playlist foreign key(idplaylist) references quicko_db.youtubeplaylist(id);

alter table quicko_db.negocio add constraint fk_negocio_playlist foreign key(playlistid) references quicko_db.youtubeplaylist(id);
--@Undo
/*
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
drop table quicko_db.youtubeVideo;

drop table quicko_db.youtubePlaylist;