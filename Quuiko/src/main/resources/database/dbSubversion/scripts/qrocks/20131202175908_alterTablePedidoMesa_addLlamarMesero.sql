/*TODO
@name:		20131202175908_alterTablePedidoMesa_addLlamarMesero.sql
@release:	
@date:		20131202175908
@author:	jorgeruiz
@summary:	
@objects:	
@END*/
alter table pedidoMesa add llamarMesero integer default 0;
alter table pedidoMesa add solicitarCuenta integer default 0;
--@Undo
/*
@name:		20131202175908_alterTablePedidoMesa_addLlamarMesero.sql
@release:	
@date:		20131202175908
@author:	jorgeruiz
@summary:	
@objects:	
@END*/
alter table qrocks.negocio drop column llamarMesero;
alter table qrocks.negocio drop column solicitarCuenta;