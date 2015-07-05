package com.quuiko.services;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.util.List;

import com.quuiko.beans.PedidoIndividual;
import com.quuiko.exception.QRocksException;

public interface PedidoIndividualService {
	/**
	 * Agrega un producto a la cuenta del cliente
	 * @param pedido
	 * @throws QRocksException
	 */
	public void agregarPedido(PedidoIndividual pedido) throws QRocksException;
	
	/**
	 * Agrega la lista de pedidos de un cliente
	 * @param pedidos
	 * @throws QRocksException
	 */
	public void agregarPedidos(List<PedidoIndividual> pedidos) throws QRocksException;
	
	/**
	 * Marca todos los pedidos de un cliente como pagado
	 * @param idCliente
	 * @param claveMesa Clave de la mesa a la cual pertenece el cliente (se utiliza como medio de validacion para que no cambien el num. cliente y puedan acceder a la cuenta)
	 * @throws QRocksException
	 */
	public void pagarCuentaCliente(Long idCliente,String claveMesa) throws QRocksException;
	/**
	 * Actualiza el estatus de un pedido 
	 * @param pedido
	 * @throws QRocksException
	 */
	public void actualizarPedido(PedidoIndividual pedido) throws QRocksException;
	
	/**
	 * Obtiene los productos que solicito un cliente que no hayan sido pagados
	 * @param idCliente
	 * @param claveMesa Clave de la mesa a la cual pertenece el cliente (se utiliza como medio de validacion para que no cambien el num. cliente y puedan acceder a la cuenta)
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaCliente(Long idCliente,String claveMesa) throws QRocksException;
	/**
	 * Aplica filtros de busqueda sobre los pedidos en base a cualquier criterio de busqueda
	 * @param filtro
	 * @return
	 */
	public List<PedidoIndividual> filtrar(PedidoIndividual filtro);
	/**
	 * obtiene la lista de todos los pedidos que hicieron en una mesa (PedidoMesa)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaPorPedidoMesa(Long idPedido) throws QRocksException;
	/**
	 * Marca como atendido un pedido
	 * @param pedido
	 * @throws QRocksException Lanza excepcion si el pedido es nulo o no existe
	 */
	public void atenderPedidoDelCliente(PedidoIndividual pedido) throws QRocksException;
	
	/**
	 * Marca como atendido un pedido
	 * @param idPedido
	 * @throws QRocksException Lanza excepcion si el pedido es nulo o no existe
	 */
	public void atenderPedidoDelCliente(Long idPedido) throws QRocksException;
	
	/**
	 * obtiene la lista de los nuevos pedidos que hicieron en una mesa (PedidoMesa) y que no han sido atendidos
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerNuevosPedidosPorPedidoMesa(Long idPedido) throws QRocksException;
}
