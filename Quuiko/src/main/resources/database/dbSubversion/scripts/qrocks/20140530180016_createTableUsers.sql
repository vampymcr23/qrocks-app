/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks._businessUser(
	id bigint not null primary key auto_increment,
	username varchar(40) not null,
	name varchar(100),
	businessKey varchar(20),
	enabled	integer default 1,
	creationDate timestamp not null default CURRENT_TIMESTAMP,
	expirationDate date ,
	usrType varchar(5) not null default 'USR'
)Engine=InnoDB;

alter table qrocks._businessUser add constraint uq_username unique (username);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks._businessUser;