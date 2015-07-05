package com.quuiko.quartz;

import static com.quuiko.util.Utileria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Reservacion;
import com.quuiko.dtos.NotificationMessage;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.GCMNotificationService;
import com.quuiko.services.ReservacionService;
import com.quuiko.util.Utileria;

@Service
public class ReservacionTask  implements Job,InterruptableJob{
	private static final Logger log=Logger.getLogger(ReservacionTask.class.getName());
	private static final String PARAM_TAREA_PROGRAMADA="notifConfirmacionReservacion";
	private Reservacion reservacion;
	
	private static ReservacionService reservacionService;
	
	private static GCMNotificationService gcmNotificationService;
	
	public ReservacionTask(){}
	
	public ReservacionTask(Reservacion reservacion){
		
	}
	
	/**
	 * Tarea a ejecutar del JOB, en este punto se lanza la tarea programada para generar una transaccion de payworks 2
	 */
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		Object t=jobContext.getMergedJobDataMap().get(PARAM_TAREA_PROGRAMADA);
		reservacion=(t!=null)?(Reservacion)t:null;
		inicializar();
		Long idReservacion=reservacion.getId();
		try {
			reservacion=reservacionService.consultarPorId(idReservacion);
		} catch (QRocksException e) {
			log.log(Level.WARNING,"La reservacion no existe con el folio:"+idReservacion);
			e.printStackTrace();
		}
		if(isNull(reservacion)){
			log.log(Level.WARNING,"La reservacion no existe con el folio:"+idReservacion);
			return;
		}
		//Enviar notificacion
		String tituloNotificacion="Confirmacion de Reservación";
		String contenidoNotificacion="Favor de confirmar su asistencia para el día de hoy "+reservacion.getFechaReservacionStr()+".";
		NotificationMessage notificacion=new NotificationMessage();
		notificacion.setMessage(contenidoNotificacion);
		notificacion.setTitle(tituloNotificacion);
		Map<String,String> params=new HashMap<String, String>();
		params.put("qrIdReservacionConfirmacion",reservacion.getId().toString());
		params.put("qrFechaReservacion",reservacion.getFechaReservacionStr());
		params.put("qrBKEY",reservacion.getNegocio().getId()+"");
		params.put("qrBName",reservacion.getNegocio().getNombre());
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
//		gcmIds.add(reservacion.getGcmUser().getId());
		try {
			if(!Utileria.isEmptyCollection(gcmIds)){
				gcmNotificationService.sendNotifications(gcmIds, notificacion);
			}
			if(!Utileria.isEmptyCollection(tokens)){
				gcmNotificationService.sendNotificationsIOS(tokens, notificacion);
			}
			reservacion.setConfirmacionRecibida(1);
			reservacionService.actualizar(reservacion);
			log.log(Level.INFO,"Se ha enviado la notificacion de confirmacion de la reservacion no."+idReservacion);
		} catch (QRocksException e) {
			log.log(Level.WARNING,"Ocurrio un error al enviar la notificacion de la reservacion no."+idReservacion+", causado por:"+e.getMessage());
		}
	}
	
	public void interrupt() throws UnableToInterruptJobException {
		log.log(Level.WARNING,"Tarea interrumpida...");
	}
	
	/**
	 * Se inicializa el servicio y se recupera del contexto de Spring.
	 * @return
	 */
	public void inicializar() {
		if(reservacionService==null){
			ApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
			reservacionService=(ctx!=null)?(ReservacionService)ctx.getBean("reservacionService"):null;
		}
		if(gcmNotificationService==null){
			ApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
			gcmNotificationService=(ctx!=null)?(GCMNotificationService)ctx.getBean("gcmNotificationService"):null;
		}
	}
}
