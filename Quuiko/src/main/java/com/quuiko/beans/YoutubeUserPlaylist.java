package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="youtubeUserPlaylist")
@Table(name="youtubeUserPlaylist")
public class YoutubeUserPlaylist implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5056127874015081079L;

	@Id
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
//	@ManyToOne(fetch=FetchType.EAGER,targetEntity=YoutubePlaylist.class,cascade=CascadeType.PERSIST)
//	@JoinColumn(name="idYoutubePlaylist")
//	private YoutubePlaylist playlist;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Negocio.class)
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
//	public YoutubePlaylist getPlaylist() {
//		return playlist;
//	}
//	public void setPlaylist(YoutubePlaylist playlist) {
//		this.playlist = playlist;
//	}
}
