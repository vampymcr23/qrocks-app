/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.cliente add gcmId varchar(200);

alter table quicko_db.cliente add constraint fk_cliente_gcmUsr foreign key (gcmId) references qrocks.gcmUsr(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.cliente drop foreign key fk_cliente_gcmUsr;

alter table qrocks.cliente drop column gcmId;