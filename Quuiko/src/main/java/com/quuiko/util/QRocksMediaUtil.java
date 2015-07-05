package com.quuiko.util;

import static com.quuiko.util.Utileria.isEmptyArray;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.quuiko.dtos.QRocksCancion;
import com.quuiko.dtos.QRocksCategoria;
import com.quuiko.exception.QRocksException;

@Component
public class QRocksMediaUtil {
	//Variable que tendra la ruta donde se encuentran las carpetas de categorias de musica
	private static String mediaFolder;
	private static File directorioMediaFolder;
	
	public static File getDirectorioMediaFolder() throws QRocksException{
		if(directorioMediaFolder==null){
			directorioMediaFolder=new File(mediaFolder);
		}
		if(!directorioMediaFolder.exists()){
			onError("");
		}
		return directorioMediaFolder;
	}
	
	public List<QRocksCategoria> obtenerCategorias() throws QRocksException{
		List<QRocksCategoria> categorias=new ArrayList<QRocksCategoria>();
		File directorio=getDirectorioMediaFolder();
		File[] archivos=directorio.listFiles();
		if(!isEmptyArray(archivos)){
			int tamanio=archivos.length-1;
			for(int i=tamanio;i>=0;i--){
				File archivo=archivos[i];
				if(archivo!=null && archivo.isDirectory()){
					QRocksCategoria categoria=new QRocksCategoria(archivo);
					if(isNotNull(categoria)){
						categorias.add(categoria);
					}
				}
			}
		}
		return categorias;
	}
	
	public List<QRocksCancion> obtenerCancionesPorCategoria(QRocksCategoria categoria) throws QRocksException{
		if(isNull(categoria)){
			//FIXME Mensaje
			onError("");
		}
		String nombreCategoria=categoria.getNombre();
		List<QRocksCancion> canciones=new ArrayList<QRocksCancion>();
		File directorio=getDirectorioMediaFolder();
		String pathCategoria=directorio.getPath()+File.separator+nombreCategoria;
		File directorioCategoria=new File(pathCategoria);
		if(!directorioCategoria.exists() || !directorioCategoria.isDirectory()){
			//FIXME Mensaje
			onError("");
		}
		File[] archivos=directorioCategoria.listFiles();
		if(!isEmptyArray(archivos)){
			int tamanio=archivos.length-1;
			for(int i=tamanio;i>=0;i--){
				File archivo=archivos[i];
				QRocksCancion cancion=new QRocksCancion(archivo);
				if(isNotNull(cancion)){
					canciones.add(cancion);
				}
			}
		}
		return canciones;
	}
	
	public static void main(String... x){
		QRocksMediaUtil utileria=new QRocksMediaUtil();
		QRocksMediaUtil.mediaFolder="/Users/victorherrera/Documents/personal/qrocksMediaFolder";
		try {
			List<QRocksCategoria> categorias=utileria.obtenerCategorias();
			if(!isEmptyCollection(categorias)){
				for(QRocksCategoria categoria:categorias){
					System.out.println("====================");
					System.out.println("Categoria:"+categoria.getNombre());
					System.out.println("====================");
					List<QRocksCancion> canciones=utileria.obtenerCancionesPorCategoria(categoria);
					if(!isEmptyCollection(canciones)){
						for(QRocksCancion cancion:canciones){
							System.out.println("-"+cancion.getNombreCancion()+"/Ext:"+cancion.getFormato());
						}
					}
				}
			}
		} catch (QRocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setMediaFolder(String mediaFolder) {
		QRocksMediaUtil.mediaFolder = mediaFolder;
	}
}
