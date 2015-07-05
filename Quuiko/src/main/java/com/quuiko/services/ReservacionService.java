package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Reservacion;
import com.quuiko.exception.QRocksException;

public interface ReservacionService {
	
	/**
	 * Guarda / actualiza una reservacion
	 * @param reservacion
	 * @return
	 * @throws QRocksException
	 */
	public Reservacion guardar(Reservacion reservacion) throws QRocksException;
	
	public void actualizar(Reservacion reservacion) throws QRocksException;
	
	/**
	 * Obtiene la informacion de la reservacion mediante el folio
	 * @param idReservacion
	 * @return
	 * @throws QRocksException
	 */
	public Reservacion consultarPorId(Long idReservacion) throws QRocksException;
	
	/**
	 * Marca como recibida la notificacion de una reservacion. Esta se invoca al momento que en el mobil, les llega la notificacion de reservacion autorizada. Cuando
	 * le llegue al dispositivo, se actualiza a la aplicacion que ya le llego la autorizacion al celular para evitar que se envie doble.
	 * @param idReservacoin
	 * @throws QRocksException
	 */
	public void marcarNotificacionAutorizacionRecibida(Long idReservacion) throws QRocksException;
	
	/**
	 * Obtiene la ultima reservacion de ese usuario con este negocio.
	 * @param idNegocio
	 * @param gcmId
	 * @return
	 * @throws QRocksException
	 */
	public List<Reservacion> obtenerUltimaReservacionPorNegocioUsuario(Long idNegocio,String gcmId) throws QRocksException;
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	public void autorizarReservacion(Long idReservacion) throws QRocksException;
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	public void rechazarReservacion(Long idReservacion) throws QRocksException;
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	public void cancelarReservacion(Long idReservacion) throws QRocksException;
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	public void marcarAsistencia(Long idReservacion) throws QRocksException;
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	public void marcarFalta(Long idReservacion) throws QRocksException;
	
	/**
	 * Consulta todas las reservaciones que estan autorizadas y que son para hoy.
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Reservacion> verTodasLasReservacionesAutorizadasParaHoy(Reservacion filtro,Long idNegocio) throws QRocksException;
	
	public List<Reservacion> filtrar(Reservacion filtro);
	
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Reservacion> filtrarPaginado(Reservacion filtro,int inicio, int numRegistros);
	
	public List<Reservacion> filtrarPaginadoOrderBy(Reservacion filtro,int inicio, int numRegistros,String orderBy);
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Reservacion filtro);
}
