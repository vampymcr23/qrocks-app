/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create index idx_gcmUsrNeg_negGcmId on quicko_db.gcmUsrNegocio(idNegocio,gcmId);
create index idx_mesa_idNeg on quicko_db.mesa(idnegocio);
create index idx_mesa_clave on quicko_db.mesa(claveMesa);
create index idx_mesero_idNeg on quicko_db.mesero(idNegocio);
create index idx_pedidoMesa_idMesa on quicko_db.pedidoMesa(idMesa);
create index idx_pedidoInd_idCliente on quicko_db.pedidoIndividual(idCliente);
create index idx_cliente_gcmId on quicko_db.cliente(gcmId);
create index idx_cliente_idPedidoMesa on quicko_db.cliente(idPedidoMesa);
create index idx_cuenta_idPedidoMesa on quicko_db.cuenta(idPedidoMesa);
create index idx_producto_negocio on quicko_db.producto(idNegocio);
create index idx_youtubePlaylist_neg on quicko_db.youtubePlaylist(idNegocio);
create index idx_youtubeUserPlaylist_neg on quicko_db.youtubeUserPlaylist(idNegocio);
create index idx_youtubeUsrVideo_userPlaylist on quicko_db.youtubeUserVideo(idYoutubeUserPlaylist);
create index idx_youtubeVideo_playlist on quicko_db.youtubeVideo(idPlaylist);
create index idx_bitacoraVisita_gcmId on quicko_db.bitacoraVisita(gcmId);
create index idx_bitacoraVisita_neg on quicko_db.bitacoraVisita(idNegocio);
create index idx_negPromo_idNeg on quicko_db._negPromo(idNeg);
create index idx_negPromo_idNegFechaExp on quicko_db._negPromo(idNeg,expirationDate);
create index idx_businessUser on quicko_db._businessUser(username,enabled);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
