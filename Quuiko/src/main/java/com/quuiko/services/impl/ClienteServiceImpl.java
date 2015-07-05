package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.daos.ClienteDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ClienteService;
import com.quuiko.services.PedidoIndividualService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ClienteServiceImpl implements ClienteService{
	@Autowired
	private ClienteDAO clienteDao;
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	
	public List<Cliente> filtrar(Cliente filtro){
		return clienteDao.filtrar(filtro);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Cliente filtro){
		return clienteDao.conteoDetallePorBusqueda(filtro);
	}
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Cliente> filtrarPaginado(Cliente filtro, int inicio,int numRegistros){
		return clienteDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public Cliente agregarCliente(Cliente cliente,PedidoMesa pedido) throws QRocksException{
		return clienteDao.agregarCliente(cliente, pedido);
	}
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void actualizarCliente(Cliente cliente) throws QRocksException{
		clienteDao.actualizarCliente(cliente);
	}
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarCliente(Cliente cliente) throws QRocksException{
		if(cliente==null || cliente.getId()==null){
			Utileria.onError("Favor de proporcionar el amigo a eliminar");
		}
		eliminarCliente(cliente.getId());
	}
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarCliente(Long idCliente) throws QRocksException{
		if(idCliente!=null){
			Cliente cliente=consultarCliente(idCliente);
			Mesa mesa=cliente.getPedidoMesa().getMesa();
			String claveMesa=mesa.getClaveMesa();
			List<PedidoIndividual> pedidosDelCliente=pedidoIndividualService.obtenerCuentaCliente(cliente.getId(), claveMesa);
			if(!Utileria.isEmptyCollection(pedidosDelCliente)){
				System.out.println("No se puede eliminar al invitado ya que tienen pedidos pendientes.");
				Utileria.onError("No se puede eliminar al invitado ya que tienen pedidos pendientes.");
			}
		}
		clienteDao.eliminarCliente(idCliente);
	}
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Cliente consultarCliente(Long idCliente) throws QRocksException{
		return clienteDao.consultarCliente(idCliente);
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(pedido)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorPedido(Long idPedido) throws QRocksException{
		return clienteDao.consultarClientesPorPedido(idPedido);
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Long idMesa) throws QRocksException{
		return clienteDao.consultarClientesPorMesaActiva(idMesa);
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param mesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Mesa mesa) throws QRocksException{
		return clienteDao.consultarClientesPorMesaActiva(mesa);
	}
	/**
	 * ==================================================
	 * 	Sets y Gets
	 * ==================================================
	 */

	public void setClienteDao(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}
}
