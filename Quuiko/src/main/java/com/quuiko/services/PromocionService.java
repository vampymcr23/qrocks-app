package com.quuiko.services;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.io.InputStream;
import java.util.List;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Promocion;
import com.quuiko.exception.QRocksException;

public interface PromocionService {
	/**
	 * Registra una nueva promocion o edita una existente
	 * @param promo
	 * @throws QRocksException
	 */
	public void guardar(Promocion promo) throws QRocksException;
	
	/**
	 * COnsulta una promocion por su id
	 * @param promo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Promocion promo) throws QRocksException;
	
	/**
	 * Consulta una promocion de su id
	 * @param idPromo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Long idPromo) throws QRocksException;
	
	/**
	 * Activa y desactiva una promocion
	 * @param idPromo
	 * @throws QRocksException
	 */
	public void activarDesactivarPromo(Long idPromo) throws QRocksException;
	
	/**
	 * Metodo que cambia el estatus de notificacion enviada a la PROMO
	 * @param idPromo
	 * @throws QRocksException
	 */
	public void enviarNotificacionPromocion(Long idPromo) throws QRocksException;
	
	/**
	 * USADO para aplicacion MOBIL, consultar las promociones activas vigentes de cada restaurante
	 * Consulta todas las promociones activas y vigentes
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocioActivas(Long idNegocio) throws QRocksException;
	
	/**
	 * Consulta todas las promociones activas/inactivas de un negocio sin importar si estan vigentes 
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocio(Negocio negocio) throws QRocksException;
	
	/**
	 * Consulta todas las promociones activas/inactivas de un negocio 
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * USADO para version MOBIL para ver todas las promociones activas vigente sde TODOS los negocios
	 * Obtiene la lista de todas las promociones de todos los negocios 
	 * @return
	 */
	public List<Promocion> consultarTodasPromocionesActivas();
	
	
	public List<Promocion> filtrar(Promocion filtro);
	
	/**
	 * Obtiene la imagen de la promocion.
	 * @param idPromo
	 * @return
	 * @throws QRocksException
	 */
	public InputStream obtenerImagenPromo(Long idPromo) throws QRocksException;
	
	/**
	 * Elimina una promocion que no este activa, o si esta activa, que no se haya enviado la notificacion
	 * @param idPromo Id de la promocion a eliminar.
	 * @throws QRocksException
	 */
	public void eliminarPromo(Long idPromo) throws QRocksException;
}
