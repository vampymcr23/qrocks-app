package com.quuiko.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SqlResultSetMapping(name="ContratoView.miVista",entities={
	@EntityResult(entityClass=ContratoView.class,fields={
		@FieldResult(name="idContrato",column="idContrato"),
		@FieldResult(name="referencia",column="referencia"),
		@FieldResult(name="fecha",column="fechaContrato"),
		@FieldResult(name="cashback",column="cashback"),
		@FieldResult(name="direccion",column="direccion"),
		@FieldResult(name="sucursal",column="sucursal"),
		@FieldResult(name="nomina",column="nomina")
	})
})
@Entity
public class ContratoView implements Serializable{
	@Id
	private Integer idContrato;
	private String referencia;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private String cashback;
	private String direccion;
	private String sucursal;
	private String nomina;
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCashback() {
		return cashback;
	}
	public void setCashback(String cashback) {
		this.cashback = cashback;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getNomina() {
		return nomina;
	}
	public void setNomina(String nomina) {
		this.nomina = nomina;
	}
}
