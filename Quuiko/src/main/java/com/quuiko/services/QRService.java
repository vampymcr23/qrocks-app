package com.quuiko.services;



import java.io.InputStream;

import com.quuiko.exception.QRocksException;

public interface QRService {
	/**
	 * Genera un archivo QR en una ruta especifica dado un contenido
	 * @param Carpeta donde se guardara el QR en disco
	 * @param QRFileName Nombre del archivo que se va a generar.
	 * @param content Contenido del QR
	 * @return
	 */
	public InputStream generateQR(String carpeta,String QRFileName, StringBuilder content) throws QRocksException;
	
}
