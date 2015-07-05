package com.quuiko.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.Reservacion;
import com.quuiko.daos.ReservacionDAO.EstatusReservacion;
import com.quuiko.dtos.NotificationMessage;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AdministradorReservacionProgramadaService;
import com.quuiko.services.GCMNotificationService;
import com.quuiko.services.ReservacionService;
import com.quuiko.util.Utileria;

@Namespace("/reservaciones")
@ParentPackage("qrocks-default")
public class ReservacionAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MesaControlAction.class);
	private static final String ACTION_VER_RESERVACIONES_HOY="showAll";
	private static final String ACTION_VER_RESERVACIONES_HOY_JSON="searchReservationsToday";
	private static final String ACTION_VER_MAS_DETALLE_RESERVACIONES="showDetails";
	private static final String ACTION_VER_MAS_DETALLE_RESERVACIONES_PAG="showDetailsPag";
	private static final String ACTION_FILTRAR="search";
	private static final String ACTION_VER_RESERVACIONES_POR_AUTORIZAR="showPendingReservations";
	private static final String ACTION_VER_RESERVACIONES_POR_AUTORIZAR_PAG="showPendingReservationsPag";
	private static final String ACTION_EDITAR_RESERVACION="saveReservation";
	private static final String ACTION_AUTORIZAR_RESERVACION="authReservation";
	private static final String ACTION_RECHAZAR_RESERVACION="rejectReservation";
	private static final String ACTION_CANCELAR_RESERVACION="cancelReservation";
	private static final String ACTION_ENVIAR_NOTIFICACION_CONFIRMACION="sendConfirmReservation";
	private static final String ACTION_ENVIAR_NOTIFICACION_AUTORIZACION="sendAuthReservation";
	private static final String ACTION_MARCAR_ASISTENCIA_RESERVACION="okReservation";
	private static final String ACTION_MARCAR_FALTA_RESERVACION="naReservation";
	private static final String PAGE_RESERVACIONES="/reservaciones/misReservaciones.jsp";
	private static final String PAGE_DETALLES_RESERVACIONES="/reservaciones/todasReservaciones.jsp";
	private static final String PAGE_RESERVACIONES_PENDIENTES="/reservaciones/reservacionesPendientes.jsp";
	private Long idNegocio;
	private Long idReservacion;
	private Negocio negocio;
	private List<Reservacion> reservaciones=new ArrayList<Reservacion>();
	private Reservacion filtroReservacion;
	private static final Integer numRegistrosPaginados=10;
	private int paginaActual=0;
	private int conteo=0;
	private int numPaginas=0;
	@Autowired
	private ReservacionService reservacionService;
	@Autowired
	private GCMNotificationService gcmNotificationService;
	@Autowired
	private AdministradorReservacionProgramadaService administradorReservacionProgramadaService;
	
	@Action(value=ACTION_VER_RESERVACIONES_HOY,results={
		@Result(name=SUCCESS,location=PAGE_RESERVACIONES)
	})
	public String verReservacionesDeHoy(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		try {
			reservaciones=reservacionService.verTodasLasReservacionesAutorizadasParaHoy(null,idNegocio);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_VER_RESERVACIONES_HOY_JSON,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String verReservacionesDeHoyJson(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		try {
			reservaciones=reservacionService.verTodasLasReservacionesAutorizadasParaHoy(filtroReservacion,idNegocio);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_FILTRAR,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String buscar(){
		consultarReservaciones();
		return result;
	}
	
	@Action(value=ACTION_MARCAR_ASISTENCIA_RESERVACION,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String marcarAsistencia(){
		try {
			reservacionService.marcarAsistencia(idReservacion);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		consultarReservaciones();
		return result;
	}
	
	@Action(value=ACTION_MARCAR_FALTA_RESERVACION,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String marcarFalta(){
		try {
			reservacionService.marcarFalta(idReservacion);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		consultarReservaciones();
		return result;
	}
	
	@Action(value=ACTION_VER_MAS_DETALLE_RESERVACIONES,results={
		@Result(name=SUCCESS,location=PAGE_DETALLES_RESERVACIONES)
	})
	public String verDetallesDeReservaciones(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		conteo=reservacionService.conteoDetallePorBusqueda(filtroReservacion);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		reservaciones=reservacionService.filtrarPaginado(filtroReservacion, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_VER_MAS_DETALLE_RESERVACIONES_PAG,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","numPaginas,conteo,reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String verDetallesDeReservacionesPaginado(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		conteo=reservacionService.conteoDetallePorBusqueda(filtroReservacion);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		reservaciones=reservacionService.filtrarPaginado(filtroReservacion, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_VER_RESERVACIONES_POR_AUTORIZAR,results={
			@Result(name=SUCCESS,location=PAGE_RESERVACIONES_PENDIENTES)
	})
	public String verReservacionesPendientesPorAutorizar(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		if(filtroReservacion==null){
			filtroReservacion=new Reservacion();
		}
		filtroReservacion.setEstatus(EstatusReservacion.PENDIENTE.clave);
		conteo=reservacionService.conteoDetallePorBusqueda(filtroReservacion);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		reservaciones=reservacionService.filtrarPaginado(filtroReservacion, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_VER_RESERVACIONES_POR_AUTORIZAR_PAG,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","numPaginas,conteo,reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String verReservacionesPendientesPorAutorizarPaginado(){
		negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		if(filtroReservacion==null){
			filtroReservacion=new Reservacion();
		}
		filtroReservacion.setEstatus(EstatusReservacion.PENDIENTE.clave);
		conteo=reservacionService.conteoDetallePorBusqueda(filtroReservacion);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		reservaciones=reservacionService.filtrarPaginado(filtroReservacion, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_AUTORIZAR_RESERVACION,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageResult,messageCode,reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String autorizarReservacion(){
		try {
			reservacionService.autorizarReservacion(idReservacion);
			enviarNotificacionConfirmacionReservacion();
			Reservacion r=reservacionService.consultarPorId(idReservacion);
			administradorReservacionProgramadaService.agregarTareaReservacionPorConfirmar(r);
			onSuccessMessage("Se ha autorizado la reservacion con folio:"+idReservacion);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		verReservacionesPendientesPorAutorizar();
		return result;
	}
	
	@Action(value=ACTION_RECHAZAR_RESERVACION,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageResult,messageCode,reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String rechazarReservacion(){
		try {
			reservacionService.rechazarReservacion(idReservacion);
			onSuccessMessage("Se ha rechazado la reservacion con folio:"+idReservacion);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		verReservacionesPendientesPorAutorizar();
		return result;
	}
	
	private void consultarReservaciones(){
		reservaciones=reservacionService.filtrar(filtroReservacion);
	}
	
	
	/**
	 * Action para enviar autorizacion de la reservacion
	 * @return
	 */
	@Action(value=ACTION_ENVIAR_NOTIFICACION_AUTORIZACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","numPaginas,conteo,reservaciones\\[\\d+\\]\\..*","excludeProperties","reservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String enviarNotificacionConfirmacionReservacion(){
		try{
			negocio=getNegocioLogeado();
			if(idReservacion!=null){
				Reservacion reservacion=reservacionService.consultarPorId(idReservacion);
				if(reservacion==null){
					throw new QRocksException("No se encontró la reservacion con el folio:"+idReservacion);
				}
				if(negocio==null || negocio.getId()==null){
					throw new QRocksException("Favor de autenticarse ya que no se encontro ningun negocio.");
				}
				String tituloNotificacion="Reservación autorizada!";
				String contenidoNotificacion="El folio de su reservación es: "+reservacion.getId()+" para el día "+reservacion.getFechaReservacionStr()+". Se le enviará un recordatorio el mismo día del evento para confirmar su asistencia.";
				NotificationMessage notificacion=new NotificationMessage();
				notificacion.setMessage(contenidoNotificacion);
				notificacion.setTitle(tituloNotificacion);
				Map<String,String> params=new HashMap<String, String>();
				params.put("qrIdReservacion",reservacion.getId().toString());
				params.put("qrBKEY",negocio.getId()+"");
				params.put("qrBName",negocio.getNombre());
				params.put("title",tituloNotificacion);
				notificacion.setParams(params);
				List<String> gcmIds=new ArrayList<String>();
				List<String> tokens=new ArrayList<String>();
				GCMUser usr=reservacion.getGcmUser();
				if(usr!=null){
					String gcmId=usr.getId();
					String tipoDispositivo=usr.getTipoDispositivo();
					if("android".equalsIgnoreCase(tipoDispositivo)){
						gcmIds.add(gcmId);
					}else if("iOS".equalsIgnoreCase(tipoDispositivo)){
						tokens.add(gcmId);
					}
				}
				if(!Utileria.isEmptyCollection(gcmIds)){
					gcmNotificationService.sendNotifications(gcmIds, notificacion);
				}
				if(!Utileria.isEmptyCollection(tokens)){
					gcmNotificationService.sendNotificationsIOS(tokens, notificacion);
				}
				
			}
			verDetallesDeReservacionesPaginado();
		}catch(Exception e){
			log.error(e);
			onErrorMessage(e);
		}
		return result;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public List<Reservacion> getReservaciones() {
		return reservaciones;
	}

	public void setReservaciones(List<Reservacion> reservaciones) {
		this.reservaciones = reservaciones;
	}

	public void setReservacionService(ReservacionService reservacionService) {
		this.reservacionService = reservacionService;
	}

	public Reservacion getFiltroReservacion() {
		return filtroReservacion;
	}

	public void setFiltroReservacion(Reservacion filtroReservacion) {
		this.filtroReservacion = filtroReservacion;
	}
	
	public Long getIdReservacion() {
		return idReservacion;
	}

	public void setIdReservacion(Long idReservacion) {
		this.idReservacion = idReservacion;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}
}
