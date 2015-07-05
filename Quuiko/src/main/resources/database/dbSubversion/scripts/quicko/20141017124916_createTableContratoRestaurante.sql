/*TODO
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
create table qrocks.contratoNegocio(
	id	bigint not null primary key auto_increment,
	nombreNegocio		varchar(150) not null,
	nombreContacto		varchar(150) not null,
	telefonoContacto	varchar(50) not null,
	emailContacto		varchar(50) not null,
	rfc					varchar(30) not null,
	periodoContrato		varchar(20) not null,
	fechaRegistro	    timestamp default current_timestamp,
	fechaPrimerCargo	date,
	fechaVigencia		date,
	numTarjetaCargo		varchar(18) not null,
	fechaExpiracion		varchar(5) not null,
	activo				integer default 1
)Engine=InnoDB;
--Posibles valores (iOS , android)
--@Undo
/*
@name:		20140415112816_createTableYoutubeUserPlaylist_YoutubeUserVideo.sql
@release:	
@date:		20140415112816
@author:	vampy
@summary:	
@objects:	
@END*/
drop table qrocks.contratoNegocio;