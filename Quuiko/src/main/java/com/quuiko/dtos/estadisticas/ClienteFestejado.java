package com.quuiko.dtos.estadisticas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ClienteFestejado implements Serializable{
	@Id
	private String gcmId;
	private String alias;
	private String nombreCliente;
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	private Integer numAnios;
	private String claveMesa;
	public String getGcmId() {
		return gcmId;
	}
	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Integer getNumAnios() {
		return numAnios;
	}
	public void setNumAnios(Integer numAnios) {
		this.numAnios = numAnios;
	}
	public String getClaveMesa() {
		return claveMesa;
	}
	public void setClaveMesa(String claveMesa) {
		this.claveMesa = claveMesa;
	}
}
