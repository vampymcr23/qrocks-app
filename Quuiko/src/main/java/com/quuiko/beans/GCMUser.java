package com.quuiko.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="gcmUser")
@Table(name="gcmUsr")
public class GCMUser implements Serializable{
	@Id
	private String id;
	
	@Column(name="gcmId")
	private String gcmId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
		
	@Column(name="tipoDispositivo")
	private String tipoDispositivo;
	
	@ManyToOne(targetEntity=AppUser.class,fetch=FetchType.EAGER)
	@JoinColumn(name="idAppUser")
	private AppUser appUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(String tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
}