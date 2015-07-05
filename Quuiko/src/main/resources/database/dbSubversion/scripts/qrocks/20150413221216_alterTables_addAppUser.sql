/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/

alter table qrocks.bitacoraVisita add idAppUser bigint;
alter table qrocks.bitacoraVisita add constraint fk_bitacora_AppUser foreign key(idAppUser) references qrocks.appUsers(id);

alter table qrocks.cliente add idAppUser bigint;
alter table qrocks.cliente add constraint fk_cliente_appUser foreign key(idAppUser) references qrocks.appUsers(id);

alter table qrocks.gcmUsrNegocio add idAppUser bigint;
alter table qrocks.gcmUsrNegocio add constraint fk_gcmUsrNeg_appUser foreign key(idAppUser) references qrocks.appUsers(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
