package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.AppUser;
import com.quuiko.daos.AppUserDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AppUserService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AppUserServiceImpl implements AppUserService{
	@Autowired
	private AppUserDAO appUserDao;

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public AppUser guardarUsuario(AppUser user) throws QRocksException {
		return appUserDao.guardarUsuario(user);
	}

	public AppUser getUserById(Long id) throws QRocksException {
		return appUserDao.getUserById(id);
	}

	public AppUser getUserByUserEmail(String userEmail) throws QRocksException {
		return appUserDao.getUserByUserEmail(userEmail);
	}

	public List<AppUser> filtrar(AppUser filtro) {
		return appUserDao.filtrar(filtro);
	}
	
	/**
	 * Actualizar datos del usuario
	 * @param user
	 * @return
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public AppUser actualizarUsuario(AppUser user) throws QRocksException{
		return appUserDao.actualizarUsuario(user);
	}
	
	/**
	 * Actualizacion de contrasenia
	 * @param email
	 * @param newPassword
	 * @throws QRocksException
	 */
	public void resetPassword(String email, String newPassword) throws QRocksException{
		appUserDao.resetPassword(email, newPassword);
	}

}
