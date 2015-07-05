package com.quuiko.services;

import com.quuiko.dtos.Contacto;
import com.quuiko.exception.QRocksException;

public interface ContactoService {
	/**
	 * Envia correo de contacto a nuestra cuenta de gmail
	 * @param contacto
	 * @throws QRocksException
	 */
	public void enviarCorreoDeContacto(Contacto contacto) throws QRocksException;
}
