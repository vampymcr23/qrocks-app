/*TODO
@name:		20131220175718_alterTablePedidoMesa_addSolicitarNuevoPedido.sql
@release:	
@date:		20131220175718
@author:	vampy
@summary:	
@objects:	
@END*/
alter table pedidoMesa add solicitarNuevoPedido integer default 0;
--@Undo
/*
@name:		20131220175718_alterTablePedidoMesa_addSolicitarNuevoPedido.sql
@release:	
@date:		20131220175718
@author:	vampy
@summary:	
@objects:	
@END*/
alter table pedidoMesa drop column solicitarNuevoPedido;