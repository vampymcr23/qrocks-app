use qrocks;
create table negocio(
    id                  bigint primary key auto_increment not null,
    nombre              varchar(100) not null,
    usuario             varchar(100) not null,
    fecha               timestamp default current_timestamp,
    licencia            varchar(10),
    serial              varchar(50),
    activo              integer,
    online              integer
)engine=InnoDB;


create table mesa(
	id				bigint primary key not null auto_increment,
	idNegocio		bigint not null,
	claveMesa		varchar(15)
)engine=InnoDB;

create table pedidoMesa(
	id				bigint not null primary key auto_increment,
	idMesa			bigint not null,
	fecha			timestamp default current_timestamp,
	activo			integer not null default 1
)engine=InnoDB;

create table cliente(
	id 				bigint primary key not null auto_increment,
	alias			varchar(25),
	idPedidoMesa	bigint not null
)engine=InnoDB;

create table pedidoIndividual(
	id				bigint primary key not null auto_increment,
	idProducto		bigint not null,
	idCliente		bigint not null,
	pagado			integer default 0
)engine=InnoDB;

create table cuenta(
	id				bigint primary key not null auto_increment,
	idPedidoMesa	bigint not null,
	pagado			integer default 0,
	montoParcial	decimal(10,2) default 0,
	montoTotal		decimal(10,2) default 0,
	montoPropina	decimal(10,2) default 0,
	fecha			timestamp default current_timestamp
)engine=InnoDB;

create table producto(
	id				bigint primary key not null auto_increment,
	nombre			varchar(100) not null,
	costo			decimal(10,2) not null default 0,
	descripcion		varchar(200),
	activo			integer default 1,
	idNegocio 		bigint not null
)engine=InnoDB;

alter table producto add imagen longblob;

alter table negocio add imagen longblob;

alter table pedidoMesa add llamarMesero integer default 0;

alter table pedidoMesa add solicitarCuenta integer default 0;

alter table pedidoMesa add solicitarNuevoPedido integer default 0;

alter table pedidoIndividual add atendido integer default 0;

alter table negocio add playlistId varchar(150);

alter table negocio add playlistName varchar(150);

alter table negocio add usersPlaylistId varchar(100);

alter table negocio add usersPlaylistName varchar(100);

create table youtubePlaylist(
	id bigint not null primary key auto_increment,
	nombre	varchar(150) not null,
	idNegocio bigint not null
)Engine=InnoDB;

create table youtubeVideo(
	id bigint not null primary key auto_increment,
	idVideo varchar(50) not null,
	titulo varchar(150) not null,
	descripcion varchar(600),
	urlImagenDefault varchar(300),
	urlImagenMediana varchar(300),
	urlImagenGrande varchar(300),
	idPlaylist bigint not null
)Engine=InnoDB;

alter table youtubeVideo add constraint fk_youtubeVideo_playlist foreign key(idplaylist) references qrocks.youtubeplaylist(id);

alter table youtubePlaylist add constraint fk_playlist_negocio foreign key(idNegocio) references qrocks.negocio(id);

create table youtubeUserPlaylist(
	id bigint not null primary key auto_increment,
	nombre	varchar(150) not null,
	idNegocio bigint not null
)Engine=InnoDB;

create table youtubeUserVideo(
	id bigint not null primary key auto_increment,
	idYoutubeVideo bigint not null,
	idYoutubeUserPlaylist bigint not null
)Engine=InnoDB;

alter table youtubeUserVideo add constraint fk_youtubeUsrVideo_playlist 
foreign key(idYoutubeUserPlaylist) references youtubeUserPlaylist(id);

alter table youtubeUserVideo add constraint fk_youtubeUsrVideo_video 
foreign key(idYoutubeVideo) references youtubeVideo(id);

alter table qrocks.youtubeUserPlaylist add constraint fk_userplaylist_negocio foreign key(idNegocio) references qrocks.negocio(id);

create table gcmUsr(
	id varchar(200) not null primary key,
	gcmId	varchar(200),
	nombre varchar(100) not null,
	email varchar(150) not null,
	fechaCreacion timestamp not null default CURRENT_TIMESTAMP
)Engine=InnoDB;

create table _businessUser(
	id bigint not null primary key auto_increment,
	username varchar(40) not null,
	name varchar(100),
	businessKey varchar(20),
	enabled	integer default 1,
	creationDate timestamp not null default CURRENT_TIMESTAMP,
	expirationDate date ,
	usrType varchar(5) not null default 'USR'
)Engine=InnoDB;

alter table _businessUser add constraint uq_username unique (username);

create table _negPromo(
	id bigint not null primary key auto_increment,
	idNeg bigint not null,
	nombrePromo varchar(100),
	descripcionPromo varchar(400),
	notificacionEnviada integer not null default 0,
	enabled	integer default 1,
	creationDate timestamp not null default CURRENT_TIMESTAMP,
	expirationDate date 
)Engine=InnoDB;

alter table _negPromo add constraint fk_negPromo_neg foreign key(idNeg) references qrocks.negocio(id);

create table gcmUsrNegocio(
	id bigint not null primary key auto_increment,
	gcmId	varchar(200) not null,
	idNegocio bigint not null,
	email varchar(150) ,
	fechaCreacion timestamp not null default CURRENT_TIMESTAMP,
	ultimaVisita date,
	recibirNotificacion integer not null default 1
)Engine=InnoDB;

alter table gcmUsrNegocio add constraint fk_gcmUN_negocio foreign key (idNegocio) references qrocks.negocio(id);
alter table gcmUsrNegocio add constraint fk_gcmUN_gcmId foreign key (gcmId) references qrocks.gcmUsr(id);

insert into _businessUser(username,name,businessKey,enabled,creationDate,usrType)
values('vampy','Administrador','gyv712',1,current_date,'GV');


alter table _negPromo add imagen longblob;

alter table negocio add descripcion varchar(200);
alter table negocio add direccion varchar(120);
alter table negocio add ligaUbicacion varchar(400);
alter table negocio add telefono varchar(30);

create table mesero(
	id bigint not null primary key auto_increment,
	idNegocio bigint not null,
	nombre varchar(20) not null 
)Engine=InnoDB;

alter table mesero add constraint fk_mesero_negocio foreign key (idNegocio) references negocio(id);

alter table mesa add idMesero bigint;

alter table mesa add constraint fk_mesa_mesero foreign key (idMesero) references mesero(id);

alter table negocio add soportaPedidos integer(2) default 1;

/*ADDED*/
alter table qrocks.gcmUsr add fechaNacimiento date;

alter table qrocks.gcmUsr add telefono  varchar(25);

alter table qrocks.gcmUsrNegocio add numVisitas bigint default 0;

alter table qrocks.gcmUsrNegocio add numPuntos bigint default 0;

alter table qrocks.gcmUsr add alias  varchar(15);

create table qrocks.bitacoraVisita(
	id bigint not null primary key auto_increment,
	gcmId	varchar(200) not null,
	idNegocio bigint not null,
	fechaVisita     timestamp default current_timestamp
)engine=InnoDB;
alter table qrocks.bitacoraVisita add constraint fk_bitacoraVisita_usr foreign key(gcmId) references qrocks.gcmUsr(id); 
alter table qrocks.bitacoraVisita add constraint fk_bitacoraVisita_neg foreign key(idNegocio) references qrocks.negocio(id);

alter table qrocks.producto add tipoProducto varchar(12); 

alter table qrocks.cliente add gcmId varchar(200);

alter table qrocks.cliente add constraint fk_cliente_gcmUsr foreign key (gcmId) references qrocks.gcmUsr(id);

create table qrocks.reservacion(
	id	bigint not null primary key auto_increment,
	idNegocio bigint not null,
	idUsuario varchar(200) not null,
	fechaRegistro timestamp default current_timestamp,
	fechaReservacion timestamp not null,
	estatus		varchar(10) not null default 'PEND'
)engine=InnoDB;
/* ESTATUS SOLO DE PEND - CAN - AUT - N/A - OK */
alter table qrocks.reservacion add constraint fk_reservacion_usr foreign key(idUsuario) references qrocks.gcmUsr(id);
alter table qrocks.reservacion add constraint fk_reservacion_neg foreign key(idNegocio) references qrocks.negocio(id);

create index idx_reservacion_neg on qrocks.reservacion(idNegocio);

create index idx_reservacion_negUsr on qrocks.reservacion(idNegocio,idUsuario);

create index idx_reservacion_negFechaRes on qrocks.reservacion(idNegocio,fechaReservacion);

alter table qrocks.reservacion add autorizacionEnviada integer default 0;
alter table qrocks.reservacion add confirmacionRecibida integer default 0;
alter table qrocks.reservacion add numPersonas integer default 0;

alter table qrocks.negocio add soportaReservaciones integer default 0;

create index idx_gcmUsrNeg_negGcmId on qrocks.gcmUsrNegocio(idNegocio,gcmId);
create index idx_mesa_idNeg on qrocks.mesa(idnegocio);
create index idx_mesa_clave on qrocks.mesa(claveMesa);
create index idx_mesero_idNeg on qrocks.mesero(idNegocio);
create index idx_pedidoMesa_idMesa on qrocks.pedidoMesa(idMesa);
create index idx_pedidoInd_idCliente on qrocks.pedidoIndividual(idCliente);
create index idx_cliente_gcmId on qrocks.cliente(gcmId);
create index idx_cliente_idPedidoMesa on qrocks.cliente(idPedidoMesa);
create index idx_cuenta_idPedidoMesa on qrocks.cuenta(idPedidoMesa);
create index idx_producto_negocio on qrocks.producto(idNegocio);
create index idx_youtubePlaylist_neg on qrocks.youtubePlaylist(idNegocio);
create index idx_youtubeUserPlaylist_neg on qrocks.youtubeUserPlaylist(idNegocio);
create index idx_youtubeUsrVideo_userPlaylist on qrocks.youtubeUserVideo(idYoutubeUserPlaylist);
create index idx_youtubeVideo_playlist on qrocks.youtubeVideo(idPlaylist);
create index idx_bitacoraVisita_gcmId on qrocks.bitacoraVisita(gcmId);
create index idx_bitacoraVisita_neg on qrocks.bitacoraVisita(idNegocio);
create index idx_negPromo_idNeg on qrocks._negPromo(idNeg);
create index idx_negPromo_idNegFechaExp on qrocks._negPromo(idNeg,expirationDate);
create index idx_businessUser on qrocks._businessUser(username,enabled);

create table qrocks.pedidoDomicilio(
	id bigint not null primary key auto_increment,
	idNegocio	bigint not null,
	gcmId		varchar(200) not null,
	estatus		varchar(12) not null,
	fechaRegistro	timestamp not null default current_timestamp,
	fechaEntrega	timestamp not null,
	total			decimal(10,2) not null default 0,
	tipoEntrega		varchar(10),
	notificacionEnviadaAut integer,
	notificacionEnviadaCompleto	integer,
	tiempoEntrega	varchar(25)
)engine=InnoDB;

alter table qrocks.pedidoDomicilio add constraint fk_pedidoDomicilio_idNeg foreign key(idNegocio)  references qrocks.negocio(id);
alter table qrocks.pedidoDomicilio add constraint fk_pedidoDomicilio_usr foreign key(gcmId) references qrocks.gcmUsr(id);

create table qrocks.pedidoProductoDomicilio(
	id bigint not null primary key auto_increment,
	idProducto bigint not null,
	idPedido bigint not null,
	costo	decimal(10,2) not null default 0,
	estatus	varchar(12) not null default 'PEND'
)engine=InnoDB;

alter table qrocks.pedidoProductoDomicilio add constraint fk_pedidoProdDom_idProducto foreign key(idProducto) references qrocks.producto(id);
alter table qrocks.pedidoProductoDomicilio add constraint fk_pedidoProdDom_idPedido foreign key(idPedido) references qrocks.pedidoDomicilio(id);

alter table qrocks.gcmUsr add tipoDispositivo varchar(10) default 'android';

alter table qrocks.negocio add soportaPlaylist integer default 0;

alter table qrocks.pedidoMesa add fechaSeleccionVideo timestamp;

alter table qrocks.youtubeUserVideo add reproducido integer default 0;

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

alter table qrocks.negocio add tipoComida varchar(20) default 'OTRO';
create index idx_negocio_tipoComida on qrocks.negocio(tipoComida);

alter table qrocks.pedidoIndividual add fechaRegistro timestamp default current_timestamp;
create index idx_pedidoIndividual_fecha on qrocks.pedidoIndividual(fechaRegistro);
