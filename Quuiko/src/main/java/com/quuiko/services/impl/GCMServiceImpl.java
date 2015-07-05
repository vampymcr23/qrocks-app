package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.GCMUser;
import com.quuiko.daos.GCMDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AppUserService;
import com.quuiko.services.GCMService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GCMServiceImpl implements GCMService{
	@Autowired
	private GCMDAO gcmDao;
	@Autowired
	private AppUserService appUserService;
	/**
	 * Metodo para registrar un nuevo usuario
	 * @param usuario
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public GCMUser registrarUsuario(GCMUser usuario) throws QRocksException{
		GCMUser u=gcmDao.registrarUsuario(usuario);
		return u;
	}
	/**
	 * Consutla el usuario por el id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarUsuario(String id) throws QRocksException{
		return gcmDao.consultarUsuario(id);
	}
	
	/**
	 * Consulta cuantos usuarios tiene quicko
	 * @return
	 */
	public long consultarCuantosUsanQuicko(){
		long conteo=gcmDao.conteoFiltrar(null);
		return conteo;
	}
	
	/**
	 * Consulta el usuario GCM mediante el id de google play
	 * @param gcmId
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarPorGCMId(String gcmId) throws QRocksException{
		return gcmDao.consultarPorGCMId(gcmId);
	}
	
	/**
	 * Elimina un usuario registrado en la base de datos
	 * @param gcmId
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarRegistroUsuario(String gcmId) throws QRocksException{
		gcmDao.eliminarRegistroUsuario(gcmId);
	}
	
	
	public void setGcmDao(GCMDAO gcmDao) {
		this.gcmDao = gcmDao;
	}
}
