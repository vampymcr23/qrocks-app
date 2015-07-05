/*TODO
@name:		20131202121901_alterTableNegocio_addImage.sql
@release:	
@date:		20131202121901
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio add imagen longblob;
--@Undo
/*
@name:		20131202121901_alterTableNegocio_addImage.sql
@release:	
@date:		20131202121901
@author:	vampy
@summary:	
@objects:	
@END*/
alter table quicko_db.negocio drop column imagen;