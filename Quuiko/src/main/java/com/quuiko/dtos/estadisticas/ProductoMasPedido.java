package com.quuiko.dtos.estadisticas;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductoMasPedido {
	
	@Id
	private Long clave;
	private String producto;
	private Integer numVecesPedida;
	private Integer numEstrellas;
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Integer getNumVecesPedida() {
		return numVecesPedida;
	}
	public void setNumVecesPedida(Integer numVecesPedida) {
		this.numVecesPedida = numVecesPedida;
	}
	public Long getClave() {
		return clave;
	}
	public void setClave(Long clave) {
		this.clave = clave;
	}
	public Integer getNumEstrellas() {
		return numEstrellas;
	}
	public void setNumEstrellas(Integer numEstrellas) {
		this.numEstrellas = numEstrellas;
	}
}
