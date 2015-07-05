package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.PedidoProductoDomicilio;
import com.quuiko.exception.QRocksException;

public interface PedidoProductoDomicilioService {
	/**
	 * Guarda la info de un producto-pedido
	 * @param pedido
	 * @throws QRocksException
	 */
	public void guardar(PedidoProductoDomicilio pedido) throws QRocksException;
	
	/**
	 * Elimina un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void eliminarPedido(Long idPedido) throws QRocksException;
	
	/**
	 * Consulta la informacion de un solo pedido
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public PedidoProductoDomicilio consultarPedido(Long idPedido) throws QRocksException;
	
	/**
	 * Obtiene la lista de todos los productos solicitados de este pedido
	 * @param idPedidoDomicilio
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoProductoDomicilio> consultarProductosPorPedido(Long idPedidoDomicilio) throws QRocksException;
	
}
