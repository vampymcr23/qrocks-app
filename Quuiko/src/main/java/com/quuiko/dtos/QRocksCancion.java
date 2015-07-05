package com.quuiko.dtos;

import java.io.File;

import com.quuiko.util.Utileria;
/**
 * Clase para manejo de canciones
 * @author victorherrera
 *
 */
public class QRocksCancion {
	private String nombreCancion;
	private File archivo;
	private QRocksCategoria categoria;
	private String formato;
	
	public QRocksCancion(){}
	
	public QRocksCancion(File cancion){
		if(cancion!=null && cancion.exists()){
			this.archivo=cancion;
			this.formato=Utileria.obtenerExtensionArchivo(cancion.getName());
			this.nombreCancion=Utileria.obtenerNombreArchivo(cancion);
		}
	}
	
	public String getNombreCancion() {
		return nombreCancion;
	}
	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}
	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	public QRocksCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(QRocksCategoria categoria) {
		this.categoria = categoria;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
}
