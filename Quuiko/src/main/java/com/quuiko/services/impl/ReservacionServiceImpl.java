package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.Reservacion;
import com.quuiko.daos.ReservacionDAO;
import com.quuiko.daos.ReservacionDAO.EstatusReservacion;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ReservacionService;
import com.quuiko.util.Utileria;

@Service
public class ReservacionServiceImpl implements ReservacionService{
	@Autowired
	private ReservacionDAO reservacionDao;
	/**
	 * Guarda / actualiza una reservacion
	 * @param reservacion
	 * @return
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public Reservacion guardar(Reservacion reservacion) throws QRocksException{
		if(reservacion!=null && reservacion.getId()!=null){
			Reservacion r=consultarPorId(reservacion.getId());
			if(r!=null){
				Date fechaReservacionActual=r.getFechaReservacion();
				Date fechaReservacionNueva=reservacion.getFechaReservacion();
				if(fechaReservacionNueva==null){
					reservacion.setFechaReservacion(fechaReservacionActual);
				}else{
					Date fechaActual=new Date();
					int diferenciaDias=(Utileria.compararFechas(fechaReservacionActual, fechaActual, true));
					if(diferenciaDias<=0){
						onError("Su reservacion debe registrarse al menos un día antes de su fecha de reservación.");
					}
				}
			}
		}
		reservacionDao.guardar(reservacion);
		return reservacion;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void actualizar(Reservacion reservacion) throws QRocksException{
		if(isNull(reservacion) || isNull(reservacion.getId())){
			onError("Favor de proporcionar la reservacion");
		}
		reservacionDao.actualizar(reservacion);
	}
	
	/**
	 * Busca el estatus de reservacion en el enum por la clave de estatus
	 * @param clave
	 * @return
	 */
	private EstatusReservacion obtenerEstatusReservacion(String clave){
		EstatusReservacion estatus=null;
		for(EstatusReservacion r:EstatusReservacion.values()){
			String e=r.clave;
			if(e.equalsIgnoreCase(clave)){
				estatus=r;
				break;
			}
		}
		return estatus;
	}
	
	/**
	 * Obtiene la informacion de la reservacion mediante el folio
	 * @param idReservacion
	 * @return
	 * @throws QRocksException
	 */
	public Reservacion consultarPorId(Long idReservacion) throws QRocksException{
		return reservacionDao.consultarPorId(idReservacion);
	}
	
	/**
	 * Actualiza el estatus de la reservacion - Pendiente a Autorizada
	 * @param idReservacion
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void autorizarReservacion(Long idReservacion) throws QRocksException{
		EstatusReservacion[] estatusOrigenPermitidos={EstatusReservacion.PENDIENTE};
		reservacionDao.actualizarEstatus(idReservacion, EstatusReservacion.AUTORIZADA, estatusOrigenPermitidos);
	}
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void rechazarReservacion(Long idReservacion) throws QRocksException{
		EstatusReservacion[] estatusOrigenPermitidos={EstatusReservacion.PENDIENTE};
		reservacionDao.actualizarEstatus(idReservacion, EstatusReservacion.RECHAZADA, estatusOrigenPermitidos);
	}
	
	/**
	 * Actualiza el estatus de la reservacion (PEND | CONF | AUT ) -> CAN
	 * @param idReservacion
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void cancelarReservacion(Long idReservacion) throws QRocksException{
		EstatusReservacion[] estatusOrigenPermitidos={EstatusReservacion.PENDIENTE,EstatusReservacion.CONFIRMADA,EstatusReservacion.AUTORIZADA};
		reservacionDao.actualizarEstatus(idReservacion, EstatusReservacion.CANCELADA, estatusOrigenPermitidos);
	}
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void marcarAsistencia(Long idReservacion) throws QRocksException{
		EstatusReservacion[] estatusOrigenPermitidos={EstatusReservacion.CONFIRMADA,EstatusReservacion.AUTORIZADA};
		reservacionDao.actualizarEstatus(idReservacion, EstatusReservacion.OK, estatusOrigenPermitidos);
	}
	
	/**
	 * Actualiza el estatus de la reservacion
	 * @param idReservacion
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void marcarFalta(Long idReservacion) throws QRocksException{
		EstatusReservacion[] estatusOrigenPermitidos={EstatusReservacion.CONFIRMADA,EstatusReservacion.AUTORIZADA};
		reservacionDao.actualizarEstatus(idReservacion, EstatusReservacion.SIN_ASISTENCIA, estatusOrigenPermitidos);
	}
	
	/**
	 * Consulta todas las reservaciones que estan autorizadas y que son para hoy.
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Reservacion> verTodasLasReservacionesAutorizadasParaHoy(Reservacion filtro,Long idNegocio) throws QRocksException{
		List<Reservacion> lista=new ArrayList<Reservacion>();
		if(filtro==null){
			filtro=new Reservacion();
		}
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		Date fechaReservacion=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(fechaReservacion);
		c.set(Calendar.HOUR,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		fechaReservacion=c.getTime();
		filtro.setFechaReservacion(fechaReservacion);
		filtro.setFechaReservacionFin(fechaReservacion);
		//Si no eligio estatus entonces se pone ambos estatus
		if(!isValid(filtro.getEstatus())){
			filtro.setListaEstatus(new EstatusReservacion[]{EstatusReservacion.AUTORIZADA,EstatusReservacion.CONFIRMADA});
		}else{
			EstatusReservacion estatus=EstatusReservacion.obtenerEstatusReservacion(filtro.getEstatus());
			filtro.setListaEstatus(new EstatusReservacion[]{estatus});
		}
		lista=filtrar(filtro);
		return lista;
	}
	
	public List<Reservacion> filtrar(Reservacion filtro){
		List<Reservacion> lista=new ArrayList<Reservacion>();
		lista=reservacionDao.filtrar(filtro);
		return lista;
	}
	
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Reservacion> filtrarPaginado(Reservacion filtro,int inicio, int numRegistros){
		return reservacionDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Reservacion> filtrarPaginadoOrderBy(Reservacion filtro,int inicio, int numRegistros,String orderBy){
		return reservacionDao.filtrarPaginado(filtro, inicio, numRegistros,orderBy);
	}
	
	/**
	 * Obtiene las reservaciones de estee usuario con este negocio.
	 * @param idNegocio
	 * @param gcmId
	 * @return
	 * @throws QRocksException
	 */
	public List<Reservacion> obtenerUltimaReservacionPorNegocioUsuario(Long idNegocio,String gcmId) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el restaurante");
		}
		if(!isValid(gcmId)){
			onError("Favor de proporcionar el usuario que desea reservar");
		}
		List<Reservacion> reservaciones=new ArrayList<Reservacion>();
		Reservacion filtro=new Reservacion();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		GCMUser usuario=new GCMUser();
		usuario.setId(gcmId);
		filtro.setGcmUser(usuario);
		filtro.setNegocio(negocio);
		filtro.setListaEstatus(new EstatusReservacion[]{EstatusReservacion.PENDIENTE,EstatusReservacion.AUTORIZADA,EstatusReservacion.CONFIRMADA,EstatusReservacion.OK,EstatusReservacion.SIN_ASISTENCIA});
		reservaciones=reservacionDao.filtrarPaginado(filtro, 0, 10, " order by r.fechaReservacion desc");//Lo ordena por la fecha 
		return reservaciones;
	}
	
	/**
	 * Marca como recibida la notificacion de una reservacion. Esta se invoca al momento que en el mobil, les llega la notificacion de reservacion autorizada. Cuando
	 * le llegue al dispositivo, se actualiza a la aplicacion que ya le llego la autorizacion al celular para evitar que se envie doble.
	 * @param idReservacoin
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void marcarNotificacionAutorizacionRecibida(Long idReservacion) throws QRocksException{
		if(isNull(idReservacion)){
			onError("Favor de proporcionar el folio de la reservacion");
		}
		Reservacion reservacion=consultarPorId(idReservacion);
		if(isNull(reservacion)){
			onError("La reservacion con el folio:"+idReservacion+" no existe!");
		}
		reservacion.setAutorizacionEnviada(1);
		reservacionDao.guardar(reservacion);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Reservacion filtro){
		return reservacionDao.conteoDetallePorBusqueda(filtro);
	}

	public void setReservacionDao(ReservacionDAO reservacionDao) {
		this.reservacionDao = reservacionDao;
	}
}
