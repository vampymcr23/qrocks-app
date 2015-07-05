package com.quuiko.beans;

import static com.quuiko.util.Utileria.castToDate;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerSoloFecha;
import static com.quuiko.util.Utileria.parseDateToString;

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
import javax.persistence.Transient;

import com.quuiko.daos.ReservacionDAO.EstatusReservacion;

@Entity(name="reservacion")
@Table(name="reservacion")
public class Reservacion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=GCMUser.class)
	@JoinColumn(name="idUsuario")
	private GCMUser gcmUser;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaRegistro")
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaReservacion")
	private Date fechaReservacion;
	
	@Transient
	private Date fechaReservacionFin;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="autorizacionEnviada")
	private Integer autorizacionEnviada;
	
	@Column(name="confirmacionRecibida")
	private Integer confirmacionRecibida;
	
	@Column(name="numPersonas")
	private Integer numPersonas;
	
	@Transient
	private String estatusStr;
	
	@Transient
	private String fechaReservacionStr;
	
	@Transient
	private String fechaReservacionFinStr;
	
	@Transient
	private EstatusReservacion[] listaEstatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GCMUser getGcmUser() {
		return gcmUser;
	}

	public void setGcmUser(GCMUser gcmUser) {
		this.gcmUser = gcmUser;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaReservacion() {
		return fechaReservacion;
	}

	public void setFechaReservacion(Date fechaReservacion) {
		this.fechaReservacion = fechaReservacion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFechaReservacionFin() {
		return fechaReservacionFin;
	}

	public void setFechaReservacionFin(Date fechaReservacionFin) {
		this.fechaReservacionFin = fechaReservacionFin;
	}
	
	public String getFechaReservacionStr() {
		if(isNotNull(fechaReservacion)){
			fechaReservacionStr=parseDateToString(fechaReservacion,"dd/MM/yyyy HH:mm");
		}
		return fechaReservacionStr;
	}
	
	public void setFechaReservacionStr(String fechaReservacionStr) {
		this.fechaReservacionStr = fechaReservacionStr;
		if(isValid(fechaReservacionStr)){
			Date fecha=castToDate(fechaReservacionStr,"dd/MM/yyyy HH:mm");
			fechaReservacion=fecha;
		}
	}
	
	public String getFechaReservacionFinStr() {
		if(isNotNull(fechaReservacionFin)){
			fechaReservacionFinStr=obtenerSoloFecha(fechaReservacionFin);
		}
		return fechaReservacionFinStr;
	}
	
	public void setFechaReservacionFinStr(String fechaReservacionFinStr) {
		this.fechaReservacionFinStr = fechaReservacionFinStr;
		if(isValid(fechaReservacionFinStr)){
			Date fecha=castToDate(fechaReservacionFinStr);
			fechaReservacionFin=fecha;
		}
	}

	public EstatusReservacion[] getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(EstatusReservacion[] listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public String getEstatusStr() {
		EstatusReservacion e=EstatusReservacion.obtenerEstatusReservacion(estatus);
		estatusStr=e.name();
		return estatusStr;
	}

	public void setEstatusStr(String estatusStr) {
		this.estatusStr = estatusStr;
	}

	public Integer getAutorizacionEnviada() {
		return autorizacionEnviada;
	}

	public void setAutorizacionEnviada(Integer autorizacionEnviada) {
		this.autorizacionEnviada = autorizacionEnviada;
	}

	public Integer getConfirmacionRecibida() {
		return confirmacionRecibida;
	}

	public void setConfirmacionRecibida(Integer confirmacionRecibida) {
		this.confirmacionRecibida = confirmacionRecibida;
	}

	public Integer getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(Integer numPersonas) {
		this.numPersonas = numPersonas;
	}
}