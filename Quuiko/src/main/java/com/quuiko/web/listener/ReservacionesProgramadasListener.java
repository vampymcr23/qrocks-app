package com.quuiko.web.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.quuiko.services.AdministradorReservacionProgramadaService;


public class ReservacionesProgramadasListener implements ServletContextListener{
	private static final Logger log=Logger.getLogger(ReservacionesProgramadasListener.class.getName());
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		log.log(Level.INFO,"Iniciando la programacion automatica de reservaciones a programar.");
		AdministradorReservacionProgramadaService servicio=getAdministradorReservacionProgramadaService();
		servicio.inicializarReservacionesPendientesPorConfirmar();
		log.log(Level.INFO,"Ha finalizado la programacion automatica.");
	}

	private AdministradorReservacionProgramadaService getAdministradorReservacionProgramadaService(){
		AdministradorReservacionProgramadaService service=null;
		ApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		service=(ctx!=null)?(AdministradorReservacionProgramadaService)ctx.getBean("administradorReservacionProgramadaService"):null;
		return service;
	}
	
}
