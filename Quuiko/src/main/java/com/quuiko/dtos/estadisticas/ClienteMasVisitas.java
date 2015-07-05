package com.quuiko.dtos.estadisticas;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClienteMasVisitas implements Serializable{
	@Id
	private String clave;
	private String cliente;
	private Integer numVisitas;
	private Integer numEstrellas;
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Integer getNumVisitas() {
		return numVisitas;
	}
	public void setNumVisitas(Integer numVisitas) {
		this.numVisitas = numVisitas;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Integer getNumEstrellas() {
		return numEstrellas;
	}
	public void setNumEstrellas(Integer numEstrellas) {
		this.numEstrellas = numEstrellas;
	}
}
