/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio add descripcion varchar(200);
alter table quicko_db.negocio add direccion varchar(120);
alter table quicko_db.negocio add ligaUbicacion varchar(400);
alter table quicko_db.negocio add telefono varchar(30);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db._negPromo drop column telefono;
alter table quicko_db._negPromo drop column descripcion;
alter table quicko_db._negPromo drop column direccion;
alter table quicko_db._negPromo drop column ligaUbicacion;
