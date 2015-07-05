/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/

alter table qrocks.pedidoDomicilio drop foreign key fk_pedidoDomicilio_usr;

alter table qrocks.pedidodomicilio drop column gcmId;

alter table qrocks.pedidoDomicilio add idAppUser bigint not null;

alter table qrocks.pedidoDomicilio add constraint fk_pedidoDomicilio_usr foreign key(idAppUser) references qrocks.appUsers(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.pedidoDomicilio drop column comentarios;
alter table qrocks.pedidoDomicilio drop column formaPago;
alter table qrocks.pedidoDomicilio drop column cambio;
alter table qrocks.pedidoDomicilio drop column feria;
alter table qrocks.pedidoDomicilio drop column comentariosRestaurante;