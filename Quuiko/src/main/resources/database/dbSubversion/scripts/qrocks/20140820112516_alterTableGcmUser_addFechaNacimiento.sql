/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.gcmUsr add fechaNacimiento date;

alter table qrocks.gcmUsr add telefono  varchar(25);

alter table qrocks.gcmUsrNegocio add numVisitas bigint default 0;

alter table qrocks.gcmUsrNegocio add numPuntos bigint default 0;

alter table qrocks.gcmUsr add alias  varchar(15);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.gcmUsr drop column fechaNacimiento;

alter table qrocks.gcmUsr drop column telefono;

alter table qrocks.gcmUsrNegocio drop column numVisitas;

alter table qrocks.gcmUsrNegocio drop column numPuntos;

alter table qrocks.gcmUsr drop column alias;