/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.youtubeUserPlaylist(
	id bigint not null primary key auto_increment,
	nombre	varchar(150) not null,
	idNegocio bigint not null
)Engine=InnoDB;

create table qrocks.youtubeUserVideo(
	id bigint not null primary key auto_increment,
	idYoutubeVideo bigint not null,
	idYoutubeUserPlaylist bigint not null
)Engine=InnoDB;

alter table qrocks.youtubeUserVideo add constraint fk_youtubeUsrVideo_playlist 
foreign key(idYoutubeUserPlaylist) references qrocks.youtubeUserPlaylist(id);

alter table qrocks.youtubeUserVideo add constraint fk_youtubeUsrVideo_video 
foreign key(idYoutubeVideo) references qrocks.youtubeVideo(id);

alter table qrocks.youtubeUserPlaylist add constraint fk_userplaylist_negocio foreign key(idNegocio) references qrocks.negocio(id);

--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.youtubeUserVideo;

drop table qrocks.youtubeUserPlaylist;

alter table qrocks.negocio drop constraint fk_negocio_userplaylist;