package com.quuiko.services.impl;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static com.quuiko.util.Utileria.*;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.quuiko.beans.Reservacion;
import com.quuiko.daos.ReservacionDAO.EstatusReservacion;
import com.quuiko.exception.QRocksException;
import com.quuiko.quartz.ReservacionTask;
import com.quuiko.services.AdministradorReservacionProgramadaService;
import com.quuiko.services.ReservacionService;
@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AdministradorReservacionProgramadaServiceImpl implements AdministradorReservacionProgramadaService{
	private static final String PARAM_TAREA_PROGRAMADA="notifConfirmacionReservacion";
	private Scheduler scheduler;
	private final static String JOB_GROUPNAME="grupoReservaciones";
	private Integer MINUTES_AFTER_ACTUAL_DATE;
	private static Logger log=Logger.getLogger(AdministradorReservacionProgramadaServiceImpl.class.getCanonicalName());
	
	private ReservacionService reservacionService;
	
	/**
	 * Metodo que agrega una tarea programada para enviar la notificacion de confirmar su asistencia.
	 * @param reservacion
	 */
	public void agregarTareaReservacionPorConfirmar(Reservacion reservacion) throws QRocksException{
		if(isNull(reservacion)){
			onError("Favor de proporcionar los datos de la reservacion");
		}
		Date fechaReservacion=reservacion.getFechaReservacion();
		if(isNull(fechaReservacion)){
			onError("La reservacion no tiene una fecha de reservacion");
		}
		String expresionQuartz="";
		Calendar c=Calendar.getInstance();
		c.setTime(fechaReservacion);
//		c.add(Calendar.HOUR, -3); //3 horas antes para confirmar
		c.add(Calendar.MINUTE,-1);
		Date fechaProgramacion=c.getTime();
		int dia=c.get(Calendar.DATE);
		int mes=c.get(Calendar.MONTH) +1 ;
		int anio=c.get(Calendar.YEAR);
		int hora=c.get(Calendar.HOUR_OF_DAY);
		int minuto=c.get(Calendar.MINUTE);
		int triggerNumber=1;
		expresionQuartz="0 "+minuto+" "+hora+" "+dia+" "+mes+" ? "+anio;
		log.log(Level.INFO,"Programando envio de notificacion para confirmacion de reservacion para el dia:"+fechaProgramacion+" |Expresion:"+expresionQuartz);
		JobDetail job=JobBuilder.newJob(ReservacionTask.class).withIdentity("_"+triggerNumber+"jobTarea#"+reservacion.getId(),JOB_GROUPNAME).build();
		job.getJobDataMap().put(PARAM_TAREA_PROGRAMADA,reservacion);
		String triggerKey="job_"+reservacion.getId()+"-trigger#"+triggerNumber;
		Trigger trigger=newTrigger()
				.withIdentity(triggerKey,JOB_GROUPNAME)
				.withSchedule(cronSchedule(expresionQuartz))
				.startAt(new Date())
				.build();
		try {
			scheduler.scheduleJob(job,trigger);
			triggerNumber++;
		} catch (SchedulerException e) {
			e.printStackTrace();
			onError("error.excepcion",e.getMessage());
		}
	}
	
	public void inicializarScheduler() throws SchedulerException{
		if(scheduler==null){
			scheduler= new StdSchedulerFactory().getScheduler();
		}
		scheduler.start();
	}
	
	/**
	 * Busca la tarea programada y la cancela para no enviar la notificacion
	 * @param idReservacion
	 */
	public void cancelarTareaProgramadaPorReservacion(Long idReservacion) throws QRocksException{
		if(isNull(idReservacion)){
			onError("Favor de proporcionar la reservacion  a cancelar");
		}
		int triggerNumber=1;
		String strJobKey="_"+triggerNumber+"jobTarea#"+idReservacion;
		JobKey jobKey=new JobKey(strJobKey,JOB_GROUPNAME);
		try {
			scheduler.deleteJob(jobKey);
		} catch (UnableToInterruptJobException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que obtiene todas las reservaciones pendientes por confirmar asistencia y que una no estan vencidas para programar su notificacion.
	 */
	public void inicializarReservacionesPendientesPorConfirmar(){
		Reservacion filtro=new Reservacion();
		filtro.setEstatus(EstatusReservacion.AUTORIZADA.clave);
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date fechaActual=c.getTime();
		filtro.setFechaReservacion(fechaActual);
		//Consulta todas las reservaciones que estan autorizadas y no han sido confirmadas, y su fecha de reservacion aun no vence.
		List<Reservacion> reservaciones=reservacionService.filtrar(filtro);
		if(!isEmptyCollection(reservaciones)){
			log.log(Level.INFO,"se van a programar "+reservaciones.size()+" reservaciones...");
			for(Reservacion r:reservaciones){
				try {
					agregarTareaReservacionPorConfirmar(r);
				} catch (QRocksException e) {
					log.log(Level.WARNING,"No se logro programar la reservacion con el folio:"+r.getId()+" , causado por:"+e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Obtiene la lista de jobs del scheduler
	 * @return
	 */
	private List<JobDetail> getJobsFromScheduler(){
		List<JobDetail> jobs=new ArrayList<JobDetail>();
		try {
			for(String groupName:scheduler.getJobGroupNames()){
				for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))){
					JobDetail jobDetail=scheduler.getJobDetail(jobKey);
					jobs.add(jobDetail);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobs;
	}

	public void setReservacionService(ReservacionService reservacionService) {
		this.reservacionService = reservacionService;
	}
	
}
