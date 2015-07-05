package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="youtubeVideo")
@Table(name="youtubeVideo")
public class YoutubeVideo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4453266060775470344L;

	@Id
	private Long id;
	
	@Column(name="idVideo")
	private String idVideo;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="urlImagenDefault")
	private String urlImagenDefault;
	
	@Column(name="urlImagenMediana")
	private String urlImagenMediana;
	
	@Column(name="urlImagenGrande")
	private String urlImagenGrande;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=YoutubePlaylist.class)
	@JoinColumn(name="idPlaylist")
	private YoutubePlaylist playlist;
//FIXME CORREGIR LA BASE DE DATOS PARA CAMBIAR EL TIPO DE DATO DEL ID, Y AGREGAR IDVIDEO asi mismo modificar todo lo demas	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdVideo() {
		return idVideo;
	}
	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrlImagenDefault() {
		return urlImagenDefault;
	}
	public void setUrlImagenDefault(String urlImagenDefault) {
		this.urlImagenDefault = urlImagenDefault;
	}
	public String getUrlImagenMediana() {
		return urlImagenMediana;
	}
	public void setUrlImagenMediana(String urlImagenMediana) {
		this.urlImagenMediana = urlImagenMediana;
	}
	public String getUrlImagenGrande() {
		return urlImagenGrande;
	}
	public void setUrlImagenGrande(String urlImagenGrande) {
		this.urlImagenGrande = urlImagenGrande;
	}
	public YoutubePlaylist getPlaylist() {
		return playlist;
	}
	public void setPlaylist(YoutubePlaylist playlist) {
		this.playlist = playlist;
	}
	
//	@Override
//	public int hashCode(){
//		int hashcode=0;
//		hashcode+=(id!=null)?id.hashCode():0;
//		hashcode+=(idVideo!=null)?idVideo.hashCode():0;
//		return hashcode;
//	}
//	
//	@Override
//	public boolean equals(Object o){
//		if(o==null && this==null){
//			return true;
//		}
//		//Si el objeto a comparar no es de estos 2 tipos entonces no es igual
//		if(! (o instanceof YoutubeVideo || o instanceof YoutubeUserVideo)){
//			return false;
//		}
//		if(o instanceof YoutubeVideo){
//			YoutubeVideo obj=(YoutubeVideo)o;
//			String idVideo=obj.getIdVideo();
//			if(this==null && o==null){
//				return true;
//			}else if(this.idVideo!=null && idVideo!=null && (this.idVideo.equals(idVideo))){
//				return true;
//			}
//		}else if(o instanceof YoutubeUserVideo){
//			YoutubeUserVideo obj=(YoutubeUserVideo)o;
//			YoutubeVideo video=(obj!=null)?obj.getVideo():null;
//			String idVideo=(video!=null)?video.getIdVideo():null;
//			String currentIdVideo=(this.idVideo!=null)?this.idVideo:null;
//			if(currentIdVideo==null && idVideo==null){
//				return true;
//			}else if(currentIdVideo!=null && idVideo!=null && (currentIdVideo.equals(idVideo))){
//				return true;
//			}
//		}
//		return false;
//	}
}
