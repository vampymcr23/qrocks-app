package com.quuiko.dtos;

import java.util.List;
/**
 * 
 * @author victorherrera
 *
 */
public class Genero {
	private Integer idGenero;
	private String nombre;
	private List<Cancion> canciones;
	public Integer getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Cancion> getCanciones() {
		return canciones;
	}
	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}
}
