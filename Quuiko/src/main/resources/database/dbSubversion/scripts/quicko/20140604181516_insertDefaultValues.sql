/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
--Se inserta usuario administrador
insert into quicko_db._businessUser(username,name,businessKey,enabled,creationDate,usrType)
values('vampy','Administrador','gyv712',1,current_date,'GV');

--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
delete from quicko_db._businessUser;