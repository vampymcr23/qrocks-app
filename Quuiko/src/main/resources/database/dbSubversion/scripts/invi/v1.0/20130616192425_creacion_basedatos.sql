/*TODO
@name:		20130616192425_creacion_basedatos.sql
@release:	
@date:		20130616192425
@author:	vicHerrera
@summary:	
@objects:	
@END*/
create table if not exists usuario(
id int auto_increment primary key,
nombre varchar(50) not null,
clave varchar(20) not null,
idRol int not null,
estatus int not null
)engine=innodb;

create table if not exists evento(
id int auto_increment primary key,
tipo int not null,
nombre varchar(100) not null,
fecha date not null,
lugar varchar(100) not null,
hora varchar(20) not null,
direccion varchar(100),
idUsuario int not null,
coordX decimal,
coordY decimal,
fotoCover varchar(100),
descripcion varchar(500))engine=innodb;

create table if not exists invitado(
id int auto_increment primary key,
nombreFamilia varchar(50) not null,
numPersonas int not null,
estatus int not null,
idevento int not null)engine=innodb;

create table if not exists rol(
id int auto_increment primary key,
nombreRol varchar(20) not null)engine=innodb;

create table if not exists permiso(
id int auto_increment primary key,
idRol int not null,
nombrePermiso varchar(20) not null)engine=innodb;

create table if not exists catalogo(
id int auto_increment primary key,
nomCatalogo varchar(20) not null)engine=innodb;

create table if not exists elementoCatalogo(
id int auto_increment primary key,
idCatalogo int not null,
nombreElemento varchar(20) not null,
valorDefault varchar(20))engine=innodb;


alter table invi.elementocatalogo 
add constraint fk_elementocat_catalogo
foreign key(idCatalogo) references invi.catalogo(id);

alter table invi.invitado
add constraint fk_invitado_evento
foreign key(idEvento) references invi.evento(id);

alter table invi.invitado 
add constraint fk_elementocat_estatus 
foreign key(estatus) references invi.elementoCatalogo(id);

alter table invi.usuario
add constraint fk_usuario_rol
foreign key(idRol) references invi.rol(id);

alter table invi.permiso
add constraint fk_rol_permiso
foreign key(idRol) references invi.rol(id);

alter table invi.evento
add constraint fk_evento_usuario
foreign key(idUsuario) references invi.usuario(id);

alter table invi.evento 
add constraint fk_evento_tipo
foreign key(tipo) references invi.elementoCatalogo(id);

create table if not exists datosusuario(
id int auto_increment primary key,
idUsuario int,
nombre varchar(50),
apellidop varchar(50),
appelidom varchar(50),
email varchar(80),
telefono varchar(20))engine=innodb;

alter table invi.datosusuario
add constraint fk_datosusuario_usuario
foreign key(idUsuario) references invi.usuario(id);

alter table invi.usuario
add constraint fk_usuario_estatus 
foreign key(estatus) references invi.elementoCatalogo(id);
--@Undo
/*
@name:		20130616192425_creacion_basedatos.sql
@release:	
@date:		20130616192425
@author:	vicHerrera
@summary:	
@objects:	
@END*/
drop table if exists elementocatalogo;
drop table if exists catalogo;
drop table if exists permiso;
drop table if exists invitado;
drop table if exists evento;
drop table if exists datosusuario;
drop table if exists usuario;
drop table if exists rol;
