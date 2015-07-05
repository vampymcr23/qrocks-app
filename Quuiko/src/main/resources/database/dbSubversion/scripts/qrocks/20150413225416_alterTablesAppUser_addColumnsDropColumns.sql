/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.appUsers add nombre varchar(100) not null;

alter table qrocks.appUsers add fechaCreacion timestamp not null default CURRENT_TIMESTAMP;

alter table qrocks.appUsers add telefono varchar(20);

alter table qrocks.appUsers add fechaNacimiento date;

alter table qrocks.appUsers add alias varchar(20);

alter table qrocks.gcmUsr drop column nombre;

alter table qrocks.gcmUsr drop column email;

alter table qrocks.gcmUsr drop column fechaNacimiento;

alter table qrocks.gcmUsr drop column telefono;

alter table qrocks.gcmUsr drop column alias;
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
