/*TODO
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table mesa add constraint fk_MesaNegocio foreign key(idNegocio) references negocio(id);

alter table mesa add constraint uq_mesa unique(idNegocio,claveMesa);

alter table pedidoMesa add constraint fk_pedidoMesa_mesa foreign key(idMesa) references mesa(id);

alter table cliente add constraint fk_Cliente_pedidoMesa foreign key(idPedidoMesa) references pedidoMesa(id);

alter table pedidoIndividual add constraint fk_PedidoInv_Prod foreign key(idProducto) references producto(id);

alter table pedidoIndividual add constraint fk_PedidoInv_Cliente foreign key(idCliente) references cliente(id);

alter table cuenta add constraint fk_cuenta_PedMesa foreign key(idPedidoMesa) references pedidoMesa(id);

alter table producto add constraint fk_producto_neg foreign key(idNegocio) references negocio(id);
--@Undo
/*
@name:		20131111155334_addConstraints.sql
@release:	
@date:		20131111155334
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.mesa drop foreign key fk_MesaNegocio;

alter table quicko_db.mesa drop index uq_mesa;

alter table quicko_db.pedidoMesa drop foreign key fk_pedidoMesa_mesa;

alter table quicko_db.cliente drop foreign key fk_Cliente_pedidoMesa;

alter table quicko_db.pedidoIndividual drop foreign key fk_PedidoInv_Prod;

alter table quicko_db.pedidoIndividual drop foreign key fk_PedidoInv_Cliente;

alter table quicko_db.cuenta drop foreign key fk_cuenta_PedMesa;