/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create index idx_gcmUsrNeg_negGcmId on qrocks.gcmUsrNegocio(idNegocio,gcmId);
create index idx_mesa_idNeg on qrocks.mesa(idnegocio);
create index idx_mesa_clave on qrocks.mesa(claveMesa);
create index idx_mesero_idNeg on qrocks.mesero(idNegocio);
create index idx_pedidoMesa_idMesa on qrocks.pedidoMesa(idMesa);
create index idx_pedidoInd_idCliente on qrocks.pedidoIndividual(idCliente);
create index idx_cliente_gcmId on qrocks.cliente(gcmId);
create index idx_cliente_idPedidoMesa on qrocks.cliente(idPedidoMesa);
create index idx_cuenta_idPedidoMesa on qrocks.cuenta(idPedidoMesa);
create index idx_producto_negocio on qrocks.producto(idNegocio);
create index idx_youtubePlaylist_neg on qrocks.youtubePlaylist(idNegocio);
create index idx_youtubeUserPlaylist_neg on qrocks.youtubeUserPlaylist(idNegocio);
create index idx_youtubeUsrVideo_userPlaylist on qrocks.youtubeUserVideo(idYoutubeUserPlaylist);
create index idx_youtubeVideo_playlist on qrocks.youtubeVideo(idPlaylist);
create index idx_bitacoraVisita_gcmId on qrocks.bitacoraVisita(gcmId);
create index idx_bitacoraVisita_neg on qrocks.bitacoraVisita(idNegocio);
create index idx_negPromo_idNeg on qrocks._negPromo(idNeg);
create index idx_negPromo_idNegFechaExp on qrocks._negPromo(idNeg,expirationDate);
create index idx_businessUser on qrocks._businessUser(username,enabled);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
