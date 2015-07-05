/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table quicko_db.youtubeUserPlaylist(
	id varchar(50) not null primary key,
	nombre	varchar(150) not null,
	idYoutubePlaylist varchar(50) not null
)Engine=InnoDB;

create table quicko_db.youtubeUserVideo(
	id bigint not null primary key auto_increment,
	idYoutubeVideo bigint not null,
	idYoutubeUserPlaylist varchar(50) not null
)Engine=InnoDB;

alter table quicko_db.youtubeUserVideo add constraint fk_youtubeUsrVideo_playlist 
foreign key(idYoutubeUserPlaylist) references quicko_db.youtubeUserPlaylist(id);

alter table quicko_db.youtubeUserVideo add constraint fk_youtubeUsrVideo_video 
foreign key(idYoutubeVideo) references quicko_db.youtubeVideo(id);

alter table quicko_db.negocio add constraint fk_negocio_userplaylist foreign key(usersPlaylistId) references quicko_db.youtubeUserPlaylist(id);

alter table quicko_db.youtubeUserPlaylist add constraint fk_youtubeUsrPlaylist_play 
foreign key(idYoutubePlaylist) references quicko_db.youtubePlaylist(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table quicko_db.youtubeUserVideo;

drop table quicko_db.youtubeUserPlaylist;

alter table quicko_db.negocio drop constraint fk_negocio_userplaylist;