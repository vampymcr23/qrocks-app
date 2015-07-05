package com.quuiko.dtos;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 * Clase para la consulta de restaurantes cuando entra el usuario con su celular para saber si esta suscrito o no
 * @author victorherrera
 *
 */
@SqlResultSetMapping(name="restaurantes.usuarioRegistrado",entities={
	@EntityResult(entityClass=RestauranteUsuarioDTO.class,fields={
		@FieldResult(name="id", column="id"),
		@FieldResult(name="nombre", column="nombre"),
		@FieldResult(name="descripcion", column="descripcion"),
		@FieldResult(name="direccion", column="direccion"),
		@FieldResult(name="ligaUbicacion", column="ligaUbicacion"),
		@FieldResult(name="usuario", column="usuario"),
		@FieldResult(name="fecha", column="fecha"),
		@FieldResult(name="licencia", column="licencia"),
		@FieldResult(name="serial", column="serial"),
		@FieldResult(name="activo", column="activo"),
		@FieldResult(name="online", column="online"),
		@FieldResult(name="imagen", column="imagen"),
		@FieldResult(name="telefono", column="telefono"),
		@FieldResult(name="soportaPedidos", column="soportaPedidos"),
		@FieldResult(name="usuarioYaRegistrado", column="usuarioYaRegistrado"),
		@FieldResult(name="tipoComida", column="tipoComida")
	})
})
@NamedNativeQueries({
	@NamedNativeQuery(name="consultarRestaurantesUsuarioRegistrado",query="select n.id, n.nombre, n.descripcion,case when un.id is null then 0 when un.id is not null and un.recibirNotificacion=1 then 1 else 0 end as usuarioSuscrito,n.tipoComida as tipoComida,n.direccion,n.direccion as direccion from negocio n left join (select un.* from gcmUsrNegocio un where gcmId=?1) un on un.idNegocio=n.id where n.activo=1 and (?2 is null or UPPER(n.nombre) like UPPER(?2) ) and (?3 is null or n.tipoComida =?3 ) order by n.nombre asc",resultSetMapping="restaurantes.usuarioRegistrado")
})
@Entity
public class RestauranteUsuarioDTO {
	@Id
	private Long id;
	private String nombre;
	private String descripcion;
	private Integer usuarioSuscrito;
	private String tipoComida;
	private String direccion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getUsuarioSuscrito() {
		return usuarioSuscrito;
	}
	public void setUsuarioSuscrito(Integer usuarioSuscrito) {
		this.usuarioSuscrito = usuarioSuscrito;
	}
	public String getTipoComida() {
		return tipoComida;
	}
	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
