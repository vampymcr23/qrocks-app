package com.quuiko.services;

import com.quuiko.beans.Reservacion;
import com.quuiko.exception.QRocksException;

public interface AdministradorReservacionProgramadaService {
	
	/**
	 * Metodo que agrega una tarea programada para enviar la notificacion de confirmar su asistencia.
	 * @param reservacion
	 */
	public void agregarTareaReservacionPorConfirmar(Reservacion reservacion) throws QRocksException;
	
	/**
	 * Busca la tarea programada y la cancela para no enviar la notificacion
	 * @param idReservacion
	 */
	public void cancelarTareaProgramadaPorReservacion(Long idReservacion) throws QRocksException;
	
	/**
	 * Metodo que obtiene todas las reservaciones pendientes por confirmar asistencia y que una no estan vencidas para programar su notificacion.
	 */
	public void inicializarReservacionesPendientesPorConfirmar();
}
