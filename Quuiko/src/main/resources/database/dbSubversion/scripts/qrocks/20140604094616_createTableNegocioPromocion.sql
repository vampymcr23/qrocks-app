/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks._negPromo(
	id bigint not null primary key auto_increment,
	idNeg bigint not null,
	nombrePromo varchar(100),
	descripcionPromo varchar(400),
	notificacionEnviada integer not null default 0,
	enabled	integer default 1,
	creationDate timestamp not null default CURRENT_TIMESTAMP,
	expirationDate date 
)Engine=InnoDB;

alter table qrocks._negPromo add constraint fk_negPromo_neg foreign key(idNeg) references qrocks.negocio(id);
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks._negPromo;