/*TODO
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio add playlistId varchar(150);
alter table quicko_db.negocio add playlistName varchar(150);
alter table quicko_db.negocio add usersPlaylistId varchar(100);
alter table quicko_db.negocio add usersPlaylistName varchar(100);
--@Undo
/*
@name:		20131230121516_altertTablePedidoIndividual_atendido.sql
@release:	
@date:		20131230121516
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio drop column playlistId;
alter table quicko_db.negocio drop column playlistName;
alter table quicko_db.negocio drop column usersPlaylistId;
alter table quicko_db.negocio drop column usersPlaylistName;