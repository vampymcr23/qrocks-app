package com.quuiko.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity(name="contratoNegocio")
@Table(name="contratoNegocio")
public class ContratoNegocio {
	@Id
	private Long id;
	
	@Column(name="nombreNegocio")
	@Size(min=1,max=150,message="Favor de proporcionar el nombre del negocio. Maximo 150 caracteres")
	@NotNull(message="Favor de proporcionar el nombre del negocio")
	private String nombreNegocio;
	
	@Column(name="nombreContacto")
	@Size(min=1,max=100,message="Favor de proporcionar el nombre del contacto. Maximo 100 caracteres")
	@NotNull(message="Favor de proporcionar el nombre del contacto")
	private String nombreContacto;

	@Column(name="telefonoContacto")
	@Size(min=1,max=30,message="Favor de proporcionar el telefono de contacto. Maximo 30 caracteres")
	@NotNull(message="Favor de proporcionar el telefono de contacto")
	private String telefonoContacto;

	@Column(name="emailContacto")
	@Size(min=1,max=50,message="Favor de proporcionar el email. Maximo 50 caracteres")
	@NotNull(message="Favor de proporcionar el email de contacto")
	private String emailContacto;
	
	@Column(name="rfc")
	@Size(min=1,max=30,message="Favor de proporcionar el rfc fiscal. Maximo 30 caracteres")
	@NotNull(message="Favor de proporcionar el rfc fiscal")
	private String rfc;

	@Column(name="periodoContrato")
	@Size(min=1,max=20,message="Favor de proporcionar el periodo de su contratacion.")
	@NotNull(message="Favor de proporcionar el periodo de su contratacion")
	private String periodoContrato;

	@Column(name="fechaRegistro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;

	@Column(name="fechaPrimerRegistro")
	@Temporal(TemporalType.DATE)
	private Date fechaPrimerRegistro;

	@Column(name="fechaVigencia")
	@Temporal(TemporalType.DATE)
	private Date fechaVigencia;

	@Column(name="numTarjetaCargo")
	@Size(min=16,max=18,message="Favor de proporcionar el numero de su tarjeta a la cual se hara el cargo. De 16 a 18 caracteres")
	@NotNull(message="Favor de proporcionar el numero de la tarjeta. De 16 a 18 caracteres")
	private String numTarjetaCargo;

	@Column(name="fechaExpiracion")
	@Size(min=5,max=5,message="Favor de proporcionar la fecha en que expira su tarjeta (mm/aa , ejemplo: 09/17)")
	@NotNull(message="Favor de proporcionar la fecha en que expira su tarjeta (mm/aa , ejemplo: 09/17)")
	private String fechaExpiracion;

	@Column(name="activo")	
	private Integer activo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreNegocio() {
		return nombreNegocio;
	}

	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getPeriodoContrato() {
		return periodoContrato;
	}

	public void setPeriodoContrato(String periodoContrato) {
		this.periodoContrato = periodoContrato;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaPrimerRegistro() {
		return fechaPrimerRegistro;
	}

	public void setFechaPrimerRegistro(Date fechaPrimerRegistro) {
		this.fechaPrimerRegistro = fechaPrimerRegistro;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getNumTarjetaCargo() {
		return numTarjetaCargo;
	}

	public void setNumTarjetaCargo(String numTarjetaCargo) {
		this.numTarjetaCargo = numTarjetaCargo;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}
}
