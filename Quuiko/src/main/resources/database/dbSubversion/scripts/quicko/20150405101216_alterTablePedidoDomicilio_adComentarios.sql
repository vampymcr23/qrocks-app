/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.pedidoDomicilio add comentarios varchar(200);
alter table qrocks.pedidoDomicilio add formaPago varchar(2);
alter table qrocks.pedidoDomicilio add cambio decimal(10,2) not null default 0;
alter table qrocks.pedidoDomicilio add feria decimal(10,2) not null default 0;
alter table qrocks.pedidoDomicilio add comentariosRestaurante varchar(200);

alter table qrocks.pedidoProductoDomicilio drop column estatus;
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