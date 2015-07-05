/*TODO
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
alter table pedidoIndividual add atendido integer default 0;
--@Undo
/*
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
alter table pedidoIndividual drop column atendido;