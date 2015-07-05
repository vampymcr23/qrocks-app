package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.GCMUserNegocio;
import com.quuiko.exception.QRocksException;

public interface GCMUserNegocioService {
	/**
	 * GUarda o actualiza los datos de este usuario en relacion a un negocio/restaurante en base al id del usuario y del negocio
	 * @param usuarioNegocio
	 * @throws QRocksException
	 */
	public GCMUserNegocio registrarVisita(String gcmId,Long idNegocio) throws QRocksException;
	
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
	public void activarDesactivarNotificaciones(String gcmId,Long idNegocio, Integer activar) throws QRocksException;
	/**
	 * GUarda o actualiza los datos de este usuario en relacion a un negocio/restaurante
	 * @param usuarioNegocio
	 * @throws QRocksException
	 */
	public void guardar(GCMUserNegocio usuarioNegocio) throws QRocksException;
	
	/**
	 * Obtiene el registro de la ultima visita de un usuario con un restaurante que esta visitando.
	 * @param gcmId
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public GCMUserNegocio obtenerUsuarioNegocio(GCMUserNegocio user) throws QRocksException;
	
	/**
	 * Obtiene la ultima vez que un usuario visito este negocio. 
	 * @param gcmId
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public GCMUserNegocio obtenerUsuarioNegocio(String gcmId,Long idNegocio) throws QRocksException;
	
	/**
	 * Obtiene la lista de usuarios que han visitado este negocio y han usado la aplicacion
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<GCMUserNegocio> obtenerUsuariosPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * Obtiene la lista de usuarios por negocio que pueden recibir notificaciones.
	 * Este metodo se utilizara al momento de enviar notificaciones.
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificaciones(Long idNegocio) throws QRocksException;
	
	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificacionesPaginado(Long idNegocio, int inicio,int numRegistros) throws QRocksException;
	
	public Integer conteoUsuariosPorNegocioParaNotificaciones(Long idNegocio) throws QRocksException;
	
	public List<GCMUserNegocio> filtrar(GCMUserNegocio filtro);
}
