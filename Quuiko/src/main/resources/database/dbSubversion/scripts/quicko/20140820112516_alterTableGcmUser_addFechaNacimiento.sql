/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.gcmUsr add fechaNacimiento date;

alter table quicko_db.gcmUsr add telefono  varchar(25);

alter table quicko_db.gcmUsrNegocio add numVisitas bigint default 0;

alter table quicko_db.gcmUsrNegocio add numPuntos bigint default 0;

alter table quicko_db.gcmUsr add alias  varchar(15);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.gcmUsr drop column fechaNacimiento;

alter table quicko_db.gcmUsr drop column telefono;

alter table quicko_db.gcmUsrNegocio drop column numVisitas;

alter table quicko_db.gcmUsrNegocio drop column numPuntos;

alter table quicko_db.gcmUsr drop column alias;