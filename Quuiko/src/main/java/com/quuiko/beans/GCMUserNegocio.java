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

@Entity(name="gcmUserNegocio")
@Table(name="gcmUsrNegocio")
public class GCMUserNegocio implements Serializable{
	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=GCMUser.class)
	@JoinColumn(name="gcmId")
	private GCMUser gcmUser;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=AppUser.class)
	@JoinColumn(name="idAppUser")
	private AppUser appUser;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@Column(name="email")
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ultimaVisita")
	private Date ultimaVisita;
	
	@Column(name="recibirNotificacion")
	private Integer recibirNotificacion;
	
	@Column(name="numVisitas")
	private Integer numVisitas;//Numero de veces que ha ido al restaurante
	
	@Column(name="numPuntos")
	private Integer numPuntos;//Num puntos acomulados Este se debe de ir sumando por cada visita en base a un porcentaje.

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getUltimaVisita() {
		return ultimaVisita;
	}

	public void setUltimaVisita(Date ultimaVisita) {
		this.ultimaVisita = ultimaVisita;
	}

	public GCMUser getGcmUser() {
		return gcmUser;
	}

	public void setGcmUser(GCMUser gcmUser) {
		this.gcmUser = gcmUser;
	}

	public Integer getRecibirNotificacion() {
		return recibirNotificacion;
	}

	public void setRecibirNotificacion(Integer recibirNotificacion) {
		this.recibirNotificacion = recibirNotificacion;
	}

	public Integer getNumVisitas() {
		return numVisitas;
	}

	public void setNumVisitas(Integer numVisitas) {
		this.numVisitas = numVisitas;
	}

	public Integer getNumPuntos() {
		return numPuntos;
	}

	public void setNumPuntos(Integer numPuntos) {
		this.numPuntos = numPuntos;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
}
