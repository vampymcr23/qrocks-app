package com.quuiko.dtos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.quuiko.util.Utileria;
/**
 * Clase que se utilizara para almacenar la lista de canciones que tiene una categoria
 * @author victorherrera
 *
 */
public class QRocksCategoria {
	private String nombre;
	private File directorio;
	private int numCanciones;
	private List<QRocksCancion> canciones=new ArrayList<QRocksCancion>();
	
	public QRocksCategoria(){}
	
	public QRocksCategoria(File directorio){
		if(directorio!=null && directorio.isDirectory()){
			this.nombre=Utileria.obtenerNombreArchivo(directorio);
			this.directorio=directorio;
			this.numCanciones=0;
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public File getDirectorio() {
		return directorio;
	}
	public void setDirectorio(File directorio) {
		this.directorio = directorio;
	}
	public int getNumCanciones() {
		return numCanciones;
	}
	public void setNumCanciones(int numCanciones) {
		this.numCanciones = numCanciones;
	}

	public List<QRocksCancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<QRocksCancion> canciones) {
		this.canciones = canciones;
	}
}