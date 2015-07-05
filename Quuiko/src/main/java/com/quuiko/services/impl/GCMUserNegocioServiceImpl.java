package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.BitacoraVisita;
import com.quuiko.beans.GCMUser;
import com.quuiko.beans.GCMUserNegocio;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.GCMUserNegocioDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.BitacoraVisitaService;
import com.quuiko.services.GCMUserNegocioService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GCMUserNegocioServiceImpl implements GCMUserNegocioService{
	@Autowired
	private GCMUserNegocioDAO gcmUsuarioNegocioDao;
	/**
	 * GUarda o actualiza los datos de este usuario en relacion a un negocio/restaurante en base al id del usuario y del negocio
	 * @param usuarioNegocio
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public GCMUserNegocio registrarVisita(String gcmId,Long idNegocio) throws QRocksException{
		return gcmUsuarioNegocioDao.registrarVisita(gcmId, idNegocio);
	}
	
	/**
	 * Activa o desactiva el envio de las notificaciones a los dispositivos mediante el codigo del usuario.
	 * Si se proporcionar el idNegocio, solo se activa/desactiva para este negocio (se ignora el parametro "activar"), y 
	 * cambia el valor de recibir notificaciones a la inversa (si es 0, lo cambia a 1, si es 1 lo cambia a 0).
	 * Pero si NO se proporciona el idNegocio entonces se considera el parametro "activar" y todos los registros que coincidan con
	 * este usuario en todos los restaurantes se activara o desactivara dependiendo del valor de activar (0 desactivar| 1 activar)
	 * @param gcmId
	 * @param idNegocio
	 * @param activar
	 * @throws QRocksException Se lanza excepcion si no se encuentra el usuario con este id proporcionado
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void activarDesactivarNotificaciones(String gcmId,Long idNegocio, Integer activar) throws QRocksException{
		gcmUsuarioNegocioDao.activarDesactivarNotificaciones(gcmId, idNegocio, activar);
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void guardar(GCMUserNegocio usuarioNegocio) throws QRocksException {
		gcmUsuarioNegocioDao.guardar(usuarioNegocio);
	}

	public GCMUserNegocio obtenerUsuarioNegocio(GCMUserNegocio user) throws QRocksException {
		return gcmUsuarioNegocioDao.obtenerUsuarioNegocio(user);
	}

	public GCMUserNegocio obtenerUsuarioNegocio(String gcmId, Long idNegocio) throws QRocksException {
		return gcmUsuarioNegocioDao.obtenerUsuarioNegocio(gcmId, idNegocio);
	}

	public List<GCMUserNegocio> obtenerUsuariosPorNegocio(Long idNegocio) throws QRocksException {
		return gcmUsuarioNegocioDao.obtenerUsuariosPorNegocio(idNegocio);
	}

	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificaciones( Long idNegocio) throws QRocksException {
		return gcmUsuarioNegocioDao.obtenerUsuariosPorNegocioParaNotificaciones(idNegocio);
	}

	public List<GCMUserNegocio> filtrar(GCMUserNegocio filtro) {
		return gcmUsuarioNegocioDao.filtrar(filtro);
	}

	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificacionesPaginado(Long idNegocio, int inicio, int numRegistros) throws QRocksException {
		return gcmUsuarioNegocioDao.obtenerUsuariosPorNegocioParaNotificacionesPaginado(idNegocio, inicio, numRegistros);
	}

	public Integer conteoUsuariosPorNegocioParaNotificaciones(Long idNegocio) throws QRocksException {
		return gcmUsuarioNegocioDao.conteoUsuariosPorNegocioParaNotificaciones(idNegocio);
	}
	
	public void setGcmUsuarioNegocioDao(GCMUserNegocioDAO gcmUsuarioNegocioDao) {
		this.gcmUsuarioNegocioDao = gcmUsuarioNegocioDao;
	}
}
