/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.gcmUsr(
	id varchar(200) not null primary key,
	gcmId	varchar(200),
	nombre varchar(100) not null,
	email varchar(150) not null,
	fechaCreacion timestamp not null default CURRENT_TIMESTAMP
)Engine=InnoDB;
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.gcmUsr;