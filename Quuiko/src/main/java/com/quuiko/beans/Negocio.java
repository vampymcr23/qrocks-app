package com.quuiko.beans;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;

@Entity(name="Negocio")
@Table(name="negocio")
@Cache(
	type=CacheType.SOFT,//se genera cache hasta que el limite de la memoria sea baja
	isolation=CacheIsolationType.PROTECTED,//Protegida para que las relaciones que no sean Cacheables puedan establecerse correctamente
	expiry=QRocksCache.PER_DAY,
	size=10//num de objetos a almacenar en cache
)
public class Negocio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8965512883658581606L;

	@Id
	private Long id;
	
	@Column(name="nombre")
	@Size(min=1,max=40,message="Favor de proporcionar el nombre del negocio. Maximo 40 caracteres")
	@NotNull(message="Favor de proporcionar el nombre del negocio")
	private String nombre;
	
	@Column(name="descripcion")
	@NotNull(message="Favor de proporcionar una breve descripcion del negocio")
	@Size(min=1,max=200,message="Solo se permite una descripcion maxima de 120 caracteres")
	private String descripcion;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="ligaUbicacion")
	private String ligaUbicacion;
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name="licencia")
	private String licencia;
	
	@Column(name="serial")
	private String serial;
	
	@Column(name="activo")
	private Integer activo;
	
	@Column(name="online")
	private Integer online;
	
	@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name="imagen",length=65535)
	private byte[] imagen;
	
	@Transient
	private File archivoImagen;
	
	@Transient
	private String archivoImagenFileName;

	@Transient
	private String archivoImagenContentType;
	
	@Column(name="telefono")
	private String telefono;
	
	@Column(name="soportaPedidos")
	private Integer soportaPedidos;
	
	@Transient
	private Integer usuarioYaRegistrado;
	
	@Column(name="soportaReservaciones")
	private Integer soportaReservaciones;
	
	@Column(name="soportaPlaylist")
	private Integer soportaPlaylist;
	
	@Column(name="tipoComida")
	private String tipoComida;
	
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getLicencia() {
		return licencia;
	}
	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public File getArchivoImagen() {
		return archivoImagen;
	}
	public void setArchivoImagen(File archivoImagen) {
		this.archivoImagen = archivoImagen;
	}
	public String getArchivoImagenFileName() {
		return archivoImagenFileName;
	}
	public void setArchivoImagenFileName(String archivoImagenFileName) {
		this.archivoImagenFileName = archivoImagenFileName;
	}
	public String getArchivoImagenContentType() {
		return archivoImagenContentType;
	}
	public void setArchivoImagenContentType(String archivoImagenContentType) {
		this.archivoImagenContentType = archivoImagenContentType;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLigaUbicacion() {
		return ligaUbicacion;
	}
	public void setLigaUbicacion(String ligaUbicacion) {
		this.ligaUbicacion = ligaUbicacion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Integer getSoportaPedidos() {
		return soportaPedidos;
	}
	public void setSoportaPedidos(Integer soportaPedidos) {
		this.soportaPedidos = soportaPedidos;
	}
	public Integer getUsuarioYaRegistrado() {
		return usuarioYaRegistrado;
	}
	public void setUsuarioYaRegistrado(Integer usuarioYaRegistrado) {
		this.usuarioYaRegistrado = usuarioYaRegistrado;
	}
	public Integer getSoportaReservaciones() {
		return soportaReservaciones;
	}
	public void setSoportaReservaciones(Integer soportaReservaciones) {
		this.soportaReservaciones = soportaReservaciones;
	}
	public Integer getSoportaPlaylist() {
		return soportaPlaylist;
	}
	public void setSoportaPlaylist(Integer soportaPlaylist) {
		this.soportaPlaylist = soportaPlaylist;
	}
	public String getTipoComida() {
		return tipoComida;
	}
	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}
}