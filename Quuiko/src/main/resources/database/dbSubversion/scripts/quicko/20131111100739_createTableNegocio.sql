/*TODO
@name:		20131111100739_createTableNegocio.sql
@release:	
@date:		20131111100739
@author:	vampy
@summary:	
@objects:	
@END*/
create table koala_314_quicko.negocio(
    id                  bigint primary key auto_increment not null,
    nombre              varchar(100) not null,
    usuario             varchar(100) not null,
    fecha               timestamp default current_timestamp,
    licencia            varchar(10),
    serial              varchar(50),
    activo              integer,
    online              integer
)engine=InnoDB;
--@Undo
/*
@name:		20131111100739_createTableNegocio.sql
@release:	
@date:		20131111100739
@author:	vampy
@summary:	
@objects:	
@END*/
drop table quicko_db.negocio;