package com.quuiko.gcm.service;

import java.util.List;

import com.quuiko.beans.BusinessUser;
import com.quuiko.exception.QRocksException;

public interface BusinessUserService {
	public void guardar(BusinessUser user) throws QRocksException;
	
	/**
	 * Elimina de la base de datos al usuario
	 * @param username
	 * @throws QRocksException
	 */
	public void eliminarUsuario(String username) throws QRocksException;
	
	/**
	 * Desactiva un usuario mediante el username
	 * @param username 
	 * @throws QRocksException se lanza la excepcion cuando no se encuentra al usuario 
	 */
	public void desactivarUsuario(BusinessUser user) throws QRocksException;
	
	/**
	 * Desactiva un usuario mediante el username
	 * @param username 
	 * @throws QRocksException se lanza la excepcion cuando no se encuentra al usuario 
	 */
	public void desactivarUsuario(String username) throws QRocksException;
	
	public List<BusinessUser> listaUsuarios();
	
	/**
	 * Consulta los datos del usuario por el nombre del usuario
	 * @param username
	 * @return
	 * @throws QRocksException
	 */
	public BusinessUser consultarUsuario(String username) throws QRocksException;
	
	public List<BusinessUser> filtrar(BusinessUser filtro);
	
	public List<BusinessUser> filtrarPaginado(BusinessUser filtro,int inicio,int numRegistros);
	
	public BusinessUser consultarUsuario(Long id) throws QRocksException;
	
	public int conteoDetallePorBusqueda(BusinessUser filtro);
}