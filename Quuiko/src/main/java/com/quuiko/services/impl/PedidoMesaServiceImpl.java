package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.daos.PedidoMesaDAO;
import com.quuiko.dtos.MesaControl;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoMesaService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PedidoMesaServiceImpl implements PedidoMesaService{
	@Autowired
	private PedidoMesaDAO pedidoMesaDao;
	/**
	 * Da de alta un nuevo pedido por mesa
	 * @param idMesa
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public PedidoMesa registrarPedido(Long idMesa) throws QRocksException{
		return pedidoMesaDao.registrarPedido(idMesa);
	}
	/**
	 * Da de alta un nuevo pedido por la clave de la mesa
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public PedidoMesa registrarPedido(String claveMesa) throws QRocksException{
		return pedidoMesaDao.registrarPedido(claveMesa);
	}
	/**
	 * Cancela un pedido 
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void cancelarPedido(Long idPedido)throws QRocksException{
		pedidoMesaDao.cancelarPedido(idPedido);
	}
	/**
	 * Actualiza los datos de un pedido
	 * @param pedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void actualizarPedido(PedidoMesa pedido) throws QRocksException{
		pedidoMesaDao.actualizar(pedido);
	}
	/**
	 * Metodo para eliminar un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarPedido(Long idPedido) throws QRocksException{
		pedidoMesaDao.eliminarPedido(idPedido);
	}
	/**
	 * Consulta sobre los pedidos bajo ciertos criterios basandose en el filtro.
	 * @param filtro
	 * @return
	 */
	public List<PedidoMesa> filtrar(PedidoMesa filtro){
		return pedidoMesaDao.filtrar(filtro);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(PedidoMesa filtro){
		return pedidoMesaDao.conteoDetallePorBusqueda(filtro);
	}
	/**
	 * Filtra los registros para paginacion
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<PedidoMesa> filtrarPaginado(PedidoMesa filtro, int inicio,int numRegistros){
		return pedidoMesaDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param idMesa numero de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(Long idMesa) throws QRocksException{
		return pedidoMesaDao.obtenerPedidoActivoPorMesa(idMesa);
	}
	/**
	 * Consulta por id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa consultarPorId(Long id) throws QRocksException{
		return pedidoMesaDao.consultarPorId(id);
	}
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param claveMesa clave de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(String claveMesa) throws QRocksException{
		return pedidoMesaDao.obtenerPedidoActivoPorMesa(claveMesa);
	}
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales
	 * @return
	 * @throws QRocksException 
	 */
	@SuppressWarnings("unchecked")
	public List<MesaControl> verMesaControl(Negocio negocio) throws QRocksException{
		return pedidoMesaDao.verMesaControl(negocio);
	}
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales POR MESERO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MesaControl> verMesaControlPorMesero(Negocio negocio,Long idMesero) throws QRocksException{
		return pedidoMesaDao.verMesaControlPorMesero(negocio, idMesero);
	}
	/**
	 * El cliente hace el llamado a un mesero
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void llamarMesero(Long idPedido) throws QRocksException{
		pedidoMesaDao.llamarMesero(idPedido);
	}
	/**
	 * Atiende el llamado del cliente para que un mesero lo atienda
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void atenderLlamadoMesero(Long idPedido) throws QRocksException{
		pedidoMesaDao.atenderLlamadoMesero(idPedido);
	}
	/**
	 * Indica que nuestro cliente solicito la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void solicitarCuentaMesero(Long idPedido) throws QRocksException{
		pedidoMesaDao.solicitarCuentaMesero(idPedido);
	}
	/**
	 * Atiende el llamado del cliente para solicitar la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void atenderCuentaMesero(Long idPedido) throws QRocksException{
		pedidoMesaDao.atenderCuentaMesero(idPedido);
	}
	/**
	 * Se utiliza cada vez que un cliente pide un nuevo pedido, o agrega un nuevo producto a su cuenta, esto permite que la mesa de control vea
	 * cuando una persona de una mesa acaba de pedir algun nuevo producto.
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void solicitarNuevoPedido(Long idPedido) throws QRocksException{
		pedidoMesaDao.solicitarNuevoPedido(idPedido);
	}
	/**
	 * La mesa de control al dar click sobre la alerta del nuevo pedido se marca como atendido para que la cocina
	 * pueda continuar con ese nuevo pedido que le solicitaron.
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void atenderNuevoPedido(Long idPedido) throws QRocksException{
		pedidoMesaDao.atenderNuevoPedido(idPedido);
	}
	
	/**
	 * Metodo que marca como pagado una cuenta de una mesa, barre todos los pedidos de un cliente y las marca como pagadas tambien.
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void pagarCuentaMesa(Long idPedido) throws QRocksException{
		pedidoMesaDao.pagarCuentaMesa(idPedido);
	}
	/**
	 * ============================================================
	 * 	Sets and gets
	 * ============================================================
	 * 
	 */
	public void setPedidoMesaDao(PedidoMesaDAO pedidoMesaDao) {
		this.pedidoMesaDao = pedidoMesaDao;
	}
}
