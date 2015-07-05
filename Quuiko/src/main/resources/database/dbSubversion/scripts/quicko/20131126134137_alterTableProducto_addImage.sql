/*TODO
@name:		20131126134137_alterTableProducto_addImage.sql
@release:	
@date:		20131126134137
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.producto add imagen longblob;
--@Undo
/*
@name:		20131126134137_alterTableProducto_addImage.sql
@release:	
@date:		20131126134137
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.producto drop column imagen;