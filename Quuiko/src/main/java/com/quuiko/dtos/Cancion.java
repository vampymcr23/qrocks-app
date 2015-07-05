package com.quuiko.dtos;

import java.io.File;
/**
 * Utilizado para leer un archivo mp3 y obtener toda la informacion
 * @author victorherrera
 *
 */
public class Cancion {
	private String nombre;
	private String artista;
	private String album;
	private String genero;
	private Integer idGenero;
	private byte[] imagenAlbum;
	private String mimeTypeImagen;
	private File archivo;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Integer getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}
	public byte[] getImagenAlbum() {
		return imagenAlbum;
	}
	public void setImagenAlbum(byte[] imagenAlbum) {
		this.imagenAlbum = imagenAlbum;
	}
	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	public String getMimeTypeImagen() {
		return mimeTypeImagen;
	}
	public void setMimeTypeImagen(String mimeTypeImagen) {
		this.mimeTypeImagen = mimeTypeImagen;
	}
}
