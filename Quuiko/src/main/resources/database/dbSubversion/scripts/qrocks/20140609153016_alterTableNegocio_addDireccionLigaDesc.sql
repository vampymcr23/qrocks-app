/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.negocio add descripcion varchar(200);
alter table qrocks.negocio add direccion varchar(120);
alter table qrocks.negocio add ligaUbicacion varchar(400);
alter table qrocks.negocio add telefono varchar(30);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks._negPromo drop column telefono;
alter table qrocks._negPromo drop column descripcion;
alter table qrocks._negPromo drop column direccion;
alter table qrocks._negPromo drop column ligaUbicacion;
