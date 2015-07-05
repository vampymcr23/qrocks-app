package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="mesero")
@Table(name="mesero")
public class Mesero implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1701140112738177257L;

	@Id
	private Long id;
	
	@ManyToOne(targetEntity=Negocio.class,fetch=FetchType.LAZY)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@Column(name="nombre")
	private String nombre;

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
