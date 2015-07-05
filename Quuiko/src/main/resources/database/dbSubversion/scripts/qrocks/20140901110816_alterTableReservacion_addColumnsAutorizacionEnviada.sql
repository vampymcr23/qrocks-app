/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.reservacion add autorizacionEnviada integer default 0;
alter table qrocks.reservacion add confirmacionRecibida integer default 0;
alter table qrocks.reservacion add numPersonas integer default 0;
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.reservacion drop column autorizacionEnviada;
alter table qrocks.reservacion drop column confirmacionRecibida;
alter table qrocks.reservacion drop column numPersonas;