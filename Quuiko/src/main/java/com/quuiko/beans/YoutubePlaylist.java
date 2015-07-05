package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="youtubePlaylist")
@Table(name="youtubePlaylist")
public class YoutubePlaylist implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2129227053938393360L;
	@Id
	private Long id;
	@Column(name="nombre")
	private String nombre;
	
	@ManyToOne(targetEntity=Negocio.class,fetch=FetchType.LAZY)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	public Negocio getNegocio() {
		return negocio;
	}
	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
