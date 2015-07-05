package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.PedidoDomicilio.EstatusPedidoDomicilio;
import com.quuiko.exception.QRocksException;

public interface PedidoDomicilioService {
	/**
	 * Guarda / Actualiza pedido domicilio
	 * @param pedido
	 * @throws QRocksException
	 */
	public void guardarPedido(PedidoDomicilio pedido) throws QRocksException;
	
	/**
	 * Obtiene todos los peedidos del negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoDomicilio> consultarPedidosPorNegocio(Long idNegocio) throws QRocksException;
	
	public List<PedidoDomicilio> consultarPedidosPendientesPorNegocio(Long idNegocio) throws QRocksException;
	
	public PedidoDomicilio consultarPedido(Long idPedido) throws QRocksException;
	
	/**
	 * Actualiza el estatus de un pedido
	 * @param idPedido
	 * @param estatus
	 * @throws QRocksException
	 */
	public void actualizarEstatus(Long idPedido, EstatusPedidoDomicilio estatus) throws QRocksException;
	/**
	 * Cambia el estatus de un pedido a autorizado, 
	 * Regla: Solo los PENDIENTES pueden autorizarse
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarAutorizado(Long idPedido) throws QRocksException;
	
	/**
	 * Cambia el estatus de un pedido
	 * Regla: Solo los Pendientes pueden rechazarse 
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarRechazado(Long idPedido) throws QRocksException;
	
	/**
	 * Cambia el estatus de un pedido a "Atendiendo"
	 * Regla: Solo los Autorizados pueden marcarse como "Atendiendo"
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarAtentido(Long idPedido) throws QRocksException;
	
	/**
	 * Cambia el estatus de un pedido a Listo para entregarse
	 * Regla: Solo los atendidos o autorizados pueden cambiarse a Listo para entregar
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarListoParaEntregar(Long idPedido) throws QRocksException;
	
	/**
	 * Cambia el estatus de un pedido a cancelado
	 * Regla: Cualquier estatus menos Cancelado puede cancelarse
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarCancelado(Long idPedido) throws QRocksException;
	
	/**
	 * Filtra los resultados bajo ciertos criterios de busqueda.
	 * @param filtro
	 * @return
	 */
	public List<PedidoDomicilio> filtrar(PedidoDomicilio filtro);
}
