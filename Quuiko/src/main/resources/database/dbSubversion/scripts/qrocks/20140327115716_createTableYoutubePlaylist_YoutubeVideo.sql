/*TODO
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.youtubePlaylist(
	id bigint not null primary key auto_increment,
	nombre	varchar(150) not null,
	idNegocio bigint not null
)Engine=InnoDB;

create table qrocks.youtubeVideo(
	id bigint not null primary key auto_increment,
	idVideo varchar(50) not null,
	titulo varchar(150) not null,
	descripcion varchar(600),
	urlImagenDefault varchar(300),
	urlImagenMediana varchar(300),
	urlImagenGrande varchar(300),
	idPlaylist bigint not null
)Engine=InnoDB;

alter table qrocks.youtubeVideo add constraint fk_youtubeVideo_playlist foreign key(idplaylist) references qrocks.youtubeplaylist(id);

alter table qrocks.youtubePlaylist add constraint fk_playlist_negocio foreign key(idNegocio) references qrocks.negocio(id);

--@Undo
/*
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.youtubeVideo;

drop table qrocks.youtubePlaylist;