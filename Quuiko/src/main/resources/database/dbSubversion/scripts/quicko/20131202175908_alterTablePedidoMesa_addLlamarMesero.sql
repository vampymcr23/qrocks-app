/*TODO
@name:		20131202175908_alterTablePedidoMesa_addLlamarMesero.sql
@release:	
@date:		20131202175908
@author:	jorgeruiz
@summary:	
@objects:	
@END*/
alter table quicko_db.pedidoMesa add llamarMesero integer default 0;
alter table quicko_db.pedidoMesa add solicitarCuenta integer default 0;
--@Undo
/*
@name:		20131202175908_alterTablePedidoMesa_addLlamarMesero.sql
@release:	
@date:		20131202175908
@author:	jorgeruiz
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio drop column llamarMesero;
alter table quicko_db.negocio drop column solicitarCuenta;