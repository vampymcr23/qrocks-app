package com.quuiko.services.impl;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.PedidoIndividual;
import com.quuiko.daos.PedidoIndividualDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoIndividualService;

@Service
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS,value="singleton")
public class PedidoIndividualServiceImpl implements PedidoIndividualService{
	@Autowired
	private PedidoIndividualDAO pedidoIndividualDao;
	/**
	 * Agrega un producto a la cuenta del cliente
	 * @param pedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void agregarPedido(PedidoIndividual pedido) throws QRocksException{
		pedidoIndividualDao.agregarPedido(pedido);
	}
	
	/**
	 * Agrega la lista de pedidos de un cliente
	 * @param pedidos
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void agregarPedidos(List<PedidoIndividual> pedidos) throws QRocksException{
		pedidoIndividualDao.agregarPedidos(pedidos);
	}
	
	/**
	 * Marca todos los pedidos de un cliente como pagado
	 * @param idCliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void pagarCuentaCliente(Long idCliente,String claveMesa) throws QRocksException{
		pedidoIndividualDao.pagarCuentaCliente(idCliente,claveMesa);
	}
	/**
	 * Actualiza el estatus de un pedido 
	 * @param pedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void actualizarPedido(PedidoIndividual pedido) throws QRocksException{
		pedidoIndividualDao.actualizarPedido(pedido);
	}
	
	/**
	 * Obtiene los productos que solicito un cliente que no hayan sido pagados
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaCliente(Long idCliente,String claveMesa) throws QRocksException{
		return pedidoIndividualDao.obtenerCuentaCliente(idCliente,claveMesa);
	}
	/**
	 * Aplica filtros de busqueda sobre los pedidos en base a cualquier criterio de busqueda
	 * @param filtro
	 * @return
	 */
	public List<PedidoIndividual> filtrar(PedidoIndividual filtro){
		return pedidoIndividualDao.filtrar(filtro);
	}
	
	/**
	 * obtiene la lista de todos los pedidos que hicieron en una mesa (PedidoMesa)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaPorPedidoMesa(Long idPedido) throws QRocksException{
		return pedidoIndividualDao.obtenerCuentaPorPedidoMesa(idPedido);
	}
	
	/**
	 * Marca como atendido un pedido
	 * @param pedido
	 * @throws QRocksException Lanza excepcion si el pedido es nulo o no existe
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void atenderPedidoDelCliente(PedidoIndividual pedido) throws QRocksException{
		if(isNull(pedido)){
			onError("");
		}
		Long id=pedido.getId();
		atenderPedidoDelCliente(id);
	}
	
	/**
	 * Marca como atendido un pedido
	 * @param idPedido
	 * @throws QRocksException Lanza excepcion si el pedido es nulo o no existe
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void atenderPedidoDelCliente(Long idPedido) throws QRocksException{
		pedidoIndividualDao.atenderPedidoDelCliente(idPedido);
	}
	
	/**
	 * obtiene la lista de los nuevos pedidos que hicieron en una mesa (PedidoMesa) y que no han sido atendidos
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerNuevosPedidosPorPedidoMesa(Long idPedido) throws QRocksException{
		return pedidoIndividualDao.obtenerNuevosPedidosPorPedidoMesa(idPedido);
	}
	
	/**
	 * =============================================
	 * 	Sets and Gets
	 * =============================================
	 */
	public void setPedidoIndividualDao(PedidoIndividualDAO pedidoIndividualDao) {
		this.pedidoIndividualDao = pedidoIndividualDao;
	}
}
