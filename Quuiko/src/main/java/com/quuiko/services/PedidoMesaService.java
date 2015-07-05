package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.dtos.MesaControl;
import com.quuiko.exception.QRocksException;

public interface PedidoMesaService {
	/**
	 * Da de alta un nuevo pedido por mesa
	 * @param idMesa
	 * @throws QRocksException
	 */
	public PedidoMesa registrarPedido(Long idMesa) throws QRocksException;
	/**
	 * Registra un nuevo pedido por clave de la mesa
	 * @param claveMesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa registrarPedido(String claveMesa) throws QRocksException;
	/**
	 * Cancela un pedido 
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void cancelarPedido(Long idPedido)throws QRocksException;
	/**
	 * Actualiza los datos de un pedido
	 * @param pedido
	 * @throws QRocksException
	 */
	public void actualizarPedido(PedidoMesa pedido) throws QRocksException;
	/**
	 * Metodo para eliminar un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void eliminarPedido(Long idPedido) throws QRocksException;
	/**
	 * Consulta sobre los pedidos bajo ciertos criterios basandose en el filtro.
	 * @param filtro
	 * @return
	 */
	public List<PedidoMesa> filtrar(PedidoMesa filtro);
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(PedidoMesa filtro);
	/**
	 * Filtra los registros para paginacion
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<PedidoMesa> filtrarPaginado(PedidoMesa filtro, int inicio,int numRegistros);
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param idMesa numero de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(Long idMesa) throws QRocksException;
	/**
	 * Consulta por id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa consultarPorId(Long id) throws QRocksException;
	
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param claveMesa clave de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(String claveMesa) throws QRocksException;
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales
	 * @return
	 */
	public List<MesaControl> verMesaControl(Negocio negocio) throws QRocksException;
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales POR MESERO
	 * @return
	 */
	public List<MesaControl> verMesaControlPorMesero(Negocio negocio,Long idMesero) throws QRocksException;
	/**
	 * El cliente hace el llamado a un mesero
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void llamarMesero(Long idPedido) throws QRocksException;
	/**
	 * Atiende el llamado del cliente para que un mesero lo atienda
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderLlamadoMesero(Long idPedido) throws QRocksException;
	/**
	 * Indica que nuestro cliente solicito la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void solicitarCuentaMesero(Long idPedido) throws QRocksException;
	/**
	 * Atiende el llamado del cliente para solicitar la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderCuentaMesero(Long idPedido) throws QRocksException;
	/**
	 * Se utiliza cada vez que un cliente pide un nuevo pedido, o agrega un nuevo producto a su cuenta, esto permite que la mesa de control vea
	 * cuando una persona de una mesa acaba de pedir algun nuevo producto.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void solicitarNuevoPedido(Long idPedido) throws QRocksException;
	/**
	 * La mesa de control al dar click sobre la alerta del nuevo pedido se marca como atendido para que la cocina
	 * pueda continuar con ese nuevo pedido que le solicitaron.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderNuevoPedido(Long idPedido) throws QRocksException;
	
	/**
	 * Metodo que marca como pagado una cuenta de una mesa, barre todos los pedidos de un cliente y las marca como pagadas tambien.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void pagarCuentaMesa(Long idPedido) throws QRocksException;
}
