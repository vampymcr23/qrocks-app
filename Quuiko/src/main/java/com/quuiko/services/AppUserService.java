package com.quuiko.services;

import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quuiko.beans.AppUser;
import com.quuiko.exception.QRocksException;

public interface AppUserService {
	public AppUser guardarUsuario(AppUser user) throws QRocksException;
	
	/**
	 * Consulta un usuario
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public AppUser getUserById(Long id) throws QRocksException;
	
	/**
	 * Consulta un usuario por email
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public AppUser getUserByUserEmail(String userEmail) throws QRocksException;
	
	/**
	 * Actualizar datos del usuario
	 * @param user
	 * @return
	 * @throws QRocksException
	 */
	public AppUser actualizarUsuario(AppUser user) throws QRocksException;
	
	/**
	 * Actualizacion de contrasenia
	 * @param email
	 * @param newPassword
	 * @throws QRocksException
	 */
	public void resetPassword(String email, String newPassword) throws QRocksException;
	
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<AppUser> filtrar(AppUser filtro);
}
