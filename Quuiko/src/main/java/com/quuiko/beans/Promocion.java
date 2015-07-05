package com.quuiko.beans;

import static com.quuiko.util.Utileria.castToDate;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerSoloFecha;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="promocion")
@Table(name="_negPromo")
public class Promocion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2783478678375291113L;

	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Negocio.class)
	@JoinColumn(name="idNeg")
	private Negocio negocio;
	
	@Column(name="nombrePromo")
	@NotNull(message="Introduzca el nombre de la promocion, maximo 100 caracteres")
	@Size(min=1,max=100,message="Introduzca el nombre de la promocion, maximo 100 caracteres")
	private String nombrePromo;
	
	@Column(name="descripcionPromo")
	@NotNull(message="Introduzca una breve descripcion de la promocion, maximo 400 caracteres")
	@Size(min=1,max=100,message="Introduzca una breve descripcion de la promocion, maximo 400 caracteres")
	private String descripcionPromo;
	
	@Column(name="enabled")
	private Integer enabled;
	
	@Column(name="notificacionEnviada")
	private Integer notificacionEnviada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate")
	private Date fechaCreacion;
	
	@Transient
	private String fechaCreacionStr;
	
	@Temporal(TemporalType.DATE)
	@Column(name="expirationDate")
	@NotNull(message="La fecha es un campo obligatorio")
	@Future(message="La fecha de vigencia debe ser mayor a la fecha actual del sistema.")
	private Date fechaExp;
	
	@Transient
	private Date fechaFinExp;
	
	@Transient
	private String fechaExpStr;
	
	@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name="imagen",length=65535)
	private byte[] imagen;
	
	@Transient
	private File archivoImagen;
	
	@Transient
	private String archivoImagenFileName;

	@Transient
	private String archivoImagenContentType;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Negocio getNegocio() {
		return negocio;
	}
	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	public String getNombrePromo() {
		return nombrePromo;
	}
	public void setNombrePromo(String nombrePromo) {
		this.nombrePromo = nombrePromo;
	}
	public String getDescripcionPromo() {
		return descripcionPromo;
	}
	public void setDescripcionPromo(String descripcionPromo) {
		this.descripcionPromo = descripcionPromo;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaExp() {
		return fechaExp;
	}
	public void setFechaExp(Date fechaExp) {
		this.fechaExp = fechaExp;
	}
	public Date getFechaFinExp() {
		return fechaFinExp;
	}
	public void setFechaFinExp(Date fechaFinExp) {
		this.fechaFinExp = fechaFinExp;
	}
	public Integer getNotificacionEnviada() {
		return notificacionEnviada;
	}
	public void setNotificacionEnviada(Integer notificacionEnviada) {
		this.notificacionEnviada = notificacionEnviada;
	}
	public String getFechaCreacionStr() {
		if(isNotNull(fechaCreacion)){
			fechaCreacionStr=obtenerSoloFecha(fechaCreacion);
		}
		return fechaCreacionStr;
	}
	public void setFechaCreacionStr(String fechaCreacionStr) {
		this.fechaCreacionStr = fechaCreacionStr;
		if(isValid(fechaCreacionStr)){
			Date fecha=castToDate(fechaCreacionStr);
			fechaCreacion=fecha;
		}
	}
	public String getFechaExpStr() {
		if(isNotNull(fechaExp)){
			fechaExpStr=obtenerSoloFecha(fechaExp);
		}
		return fechaExpStr;
	}
	public void setFechaExpStr(String fechaExpStr) {
		this.fechaExpStr = fechaExpStr;
		if(isValid(fechaExpStr)){
			Date fecha=castToDate(fechaExpStr);
			fechaExp=fecha;
		}
	}
}
