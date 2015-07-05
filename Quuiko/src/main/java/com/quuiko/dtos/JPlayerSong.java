package com.quuiko.dtos;

import java.io.Serializable;

import com.quuiko.actions.MediaPlayerAction;
/**
 * Utilizado para manejar objetos JSON
 * @author victorherrera
 *
 */
public class JPlayerSong implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5141515178120505029L;
	private String title;
	private String artist;
	private String mp3;
	private String poster;
	
	public JPlayerSong(){}
	
	public JPlayerSong(Cancion cancion){
		if(cancion!=null){
			this.title=cancion.getNombre();
			this.artist=cancion.getArtista();
			this.mp3="/QRocks/"+MediaPlayerAction.CURRENT_NAMESPACE+"/"+MediaPlayerAction.ACTION_AGREGAR_CANCION+".php?cancionAgregar.nombre="+title+"&cancionAgregar.artista="+artist+"&cancionAgregar.genero="+cancion.getGenero();
			this.poster="/QRocks/"+MediaPlayerAction.CURRENT_NAMESPACE+"/"+MediaPlayerAction.ACTION_OBTENER_IMAGEN_ALBUM+".php?cancionAgregar.nombre="+title+"&cancionAgregar.artista="+artist+"&cancionAgregar.genero="+cancion.getGenero();
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getMp3() {
		return mp3;
	}
	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
}
