package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;

@Entity(name="Mesa")
@Table(name="mesa")
@Cache(
	type=CacheType.SOFT,//se genera cache hasta que el limite de la memoria sea baja
	isolation=CacheIsolationType.PROTECTED,//Protegida para que las relaciones que no sean Cacheables puedan establecerse correctamente
	expiry=QRocksCache.PER_HOUR,
	size=100//num de objetos a almacenar en cache
)
public class Mesa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3400583571768163671L;

	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@Column(name="claveMesa")
	private String claveMesa;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Mesero.class)
	@JoinColumn(name="idMesero")
	private Mesero mesero;
	
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
	public String getClaveMesa() {
		return claveMesa;
	}
	public void setClaveMesa(String claveMesa) {
		this.claveMesa = claveMesa;
	}
	public Mesero getMesero() {
		return mesero;
	}
	public void setMesero(Mesero mesero) {
		this.mesero = mesero;
	}
}
