package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.exception.QRocksException;

public interface ClienteService {
	
	public List<Cliente> filtrar(Cliente filtro);
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Cliente filtro);
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Cliente> filtrarPaginado(Cliente filtro, int inicio,int numRegistros);
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	public Cliente agregarCliente(Cliente cliente,PedidoMesa pedido) throws QRocksException;
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	public void actualizarCliente(Cliente cliente) throws QRocksException;
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	public void eliminarCliente(Cliente cliente) throws QRocksException;
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	public void eliminarCliente(Long idCliente) throws QRocksException;
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Cliente consultarCliente(Long idCliente) throws QRocksException;
	/**
	 * Consulta a las personas que estan ordenando en una mesa(pedido)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorPedido(Long idPedido) throws QRocksException;
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Long idMesa) throws QRocksException;
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param mesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Mesa mesa) throws QRocksException;
}
