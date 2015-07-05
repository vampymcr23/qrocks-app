package com.quuiko.services;

import com.quuiko.beans.GCMUser;
import com.quuiko.exception.QRocksException;

public interface GCMService {
	/**
	 * Metodo para registrar un nuevo usuario
	 * @param usuario
	 * @throws QRocksException
	 */
	public GCMUser registrarUsuario(GCMUser usuario) throws QRocksException;
	/**
	 * Consutla el usuario por el id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarUsuario(String id) throws QRocksException;
	
	/**
	 * Consulta el usuario GCM mediante el id de google play
	 * @param gcmId
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarPorGCMId(String gcmId) throws QRocksException;
	
	/**
	 * Elimina un usuario registrado en la base de datos
	 * @param gcmId
	 * @throws QRocksException
	 */
	public void eliminarRegistroUsuario(String gcmId) throws QRocksException;
	
	public long consultarCuantosUsanQuicko();
}
