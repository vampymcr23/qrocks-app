package com.quuiko.beans;

import static com.quuiko.util.Utileria.formatoDecimal;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.redondear;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;

@Entity(name="producto")
@Table(name="producto")
@Cache(
	type=CacheType.SOFT,//se genera cache hasta que el limite de la memoria sea baja
	isolation=CacheIsolationType.PROTECTED,//Protegida para que las relaciones que no sean Cacheables puedan establecerse correctamente
	expiry=QRocksCache.PER_HOUR,
	size=100//num de objetos a almacenar en cache
)
public class Producto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1793318522025941083L;

	@Id
	private Long id;
	
	@Column(name="nombre")
	@NotNull(message="Favor de proporcionar el nombre del producto, maximo 40 caracteres")
	@Size(min=1,max=40,message="Favor de proporcionar el nombre del producto, maximo 40 caracteres")
	private String nombre;
	
	@Column(name="costo")
	@NotNull(message="El costo del producto es obligatorio")
	@Digits(integer=4,fraction=2,message="El costo debe ser decimal y el valor maximo es: 9999.99")
	private Double costo;
	
	@Transient
	private String costoStr;
	
	@Column(name="descripcion")
	@NotNull(message="Favor de proporcionar el nombre del producto, maximo 120 caracteres")
	@Size(min=1,max=120,message="Introduzca una breve descripcion del producto, maximo 120 caracteres")
	private String descripcion;
	
	@Column(name="activo")
	private Integer activo;
	
	@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name="imagen",length=65535)
	private byte[] imagen;
	
	@Transient
	private File archivoImagen;
	
	@Transient
	private String archivoImagenFileName;

	@Transient
	private String archivoImagenContentType;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@Column(name="tipoProducto")
	@NotNull(message="Favor de seleccionar el tipo de producto")
	@Size(min=1,max=40,message="Favor de proporcionar el tipo de producto")
	private String tipoProducto;
	
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

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
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

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public String getCostoStr() {
		if(costo!=null){
			costoStr=formatoDecimal(costo);
		}
		return costoStr;
	}

	public void setCostoStr(String costoStr) {
		this.costoStr = costoStr;
		if(isValid(costoStr)){
			this.costo=redondear(costoStr);
		}
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}