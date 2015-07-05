/*TODO
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.mesa add constraint fk_MesaNegocio foreign key(idNegocio) references qrocks.negocio(id);

alter table qrocks.mesa add constraint uq_mesa unique(idNegocio,claveMesa);

alter table qrocks.pedidoMesa add constraint fk_pedidoMesa_mesa foreign key(idMesa) references qrocks.mesa(id);

alter table qrocks.cliente add constraint fk_Cliente_pedidoMesa foreign key(idPedidoMesa) references qrocks.pedidoMesa(id);

alter table qrocks.pedidoIndividual add constraint fk_PedidoInv_Prod foreign key(idProducto) references qrocks.producto(id);

alter table qrocks.pedidoIndividual add constraint fk_PedidoInv_Cliente foreign key(idCliente) references qrocks.cliente(id);

alter table qrocks.cuenta add constraint fk_cuenta_PedMesa foreign key(idPedidoMesa) references qrocks.pedidoMesa(id);

alter table qrocks.producto add constraint fk_producto_neg foreign key(idNegocio) references qrocks.negocio(id);
--@Undo
/*
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table qrocks.mesa drop foreign key fk_MesaNegocio;

alter table qrocks.mesa drop index uq_mesa;

alter table qrocks.pedidoMesa drop foreign key fk_pedidoMesa_mesa;

alter table qrocks.cliente drop foreign key fk_Cliente_pedidoMesa;

alter table qrocks.pedidoIndividual drop foreign key fk_PedidoInv_Prod;

alter table qrocks.pedidoIndividual drop foreign key fk_PedidoInv_Cliente;

alter table qrocks.cuenta drop foreign key fk_cuenta_PedMesa;