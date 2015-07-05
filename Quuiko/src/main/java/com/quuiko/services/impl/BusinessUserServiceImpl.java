package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.BusinessUser;
import com.quuiko.daos.BusinessUserDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.gcm.service.BusinessUserService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BusinessUserServiceImpl implements BusinessUserService{
	@Autowired
	private BusinessUserDAO businessUserDao;
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void guardar(BusinessUser user) throws QRocksException {
		businessUserDao.guardar(user);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void eliminarUsuario(String username) throws QRocksException {
		businessUserDao.eliminarUsuario(username);
	}

	/**
	 * Activa/Desactiva a un usuario
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void desactivarUsuario(BusinessUser user) throws QRocksException {
		businessUserDao.desactivarUsuario(user);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void desactivarUsuario(String username) throws QRocksException {
		businessUserDao.desactivarUsuario(username);
	}

	public List<BusinessUser> listaUsuarios() {
		return businessUserDao.listaUsuarios();
	}

	public BusinessUser consultarUsuario(String username)throws QRocksException {
		return businessUserDao.consultarUsuario(username);
	}

	public List<BusinessUser> filtrar(BusinessUser filtro) {
		return businessUserDao.filtrar(filtro);
	}
	
	public List<BusinessUser> filtrarPaginado(BusinessUser filtro,int inicio,int numRegistros){
		return businessUserDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	
	public int conteoDetallePorBusqueda(BusinessUser filtro){
		return businessUserDao.conteoDetallePorBusqueda(filtro);
	}
	
	public BusinessUser consultarUsuario(Long id) throws QRocksException{
		return businessUserDao.consultarUsuario(id);
	}

	public void setBusinessUserDao(BusinessUserDAO businessUserDao) {
		this.businessUserDao = businessUserDao;
	}
}
