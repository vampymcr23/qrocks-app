package com.quuiko.daos;
import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;
import static com.quuiko.util.Utileria.isValid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.beans.Producto;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ClienteService;
import com.quuiko.services.MesaService;
import com.quuiko.services.PedidoMesaService;
import com.quuiko.util.Utileria.CondicionLogica;
@Component
public class PedidoIndividualDAO extends DAO<PedidoIndividual,Long>{
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private MesaService mesaService;
	@Autowired
	private PedidoMesaService pedidoMesaService;
	/**
	 * Agrega un producto a la cuenta del cliente
	 * @param pedido
	 * @throws QRocksException
	 */
	public void agregarPedido(PedidoIndividual pedido) throws QRocksException{
		if(isNull(pedido)){
			onError("Favor de proporcionar el pedido");
		}
		if(isNull(pedido.getCliente())){
			onError("Favor de proporcionar el cliente");
		}
		if(isNull(pedido.getProducto())){
			onError("Favor de proporcionar el producto");
		}
		//Si ya tiene un id, no se inserta, se ignora
		if(pedido.getId()==null){
			pedido.setPagado(0);
			pedido.setAtendido(0);
			crear(pedido);
		}
	}
	
	/**
	 * Agrega la lista de pedidos de un cliente
	 * @param pedidos
	 * @throws QRocksException
	 */
	public void agregarPedidos(List<PedidoIndividual> pedidos) throws QRocksException{
		if(isEmptyCollection(pedidos)){
			onError("");
		}
		PedidoIndividual p=pedidos.get(0);
		Long idCliente=(p.getCliente()!= null)?p.getCliente().getId():null;
		Cliente cliente=clienteService.consultarCliente(idCliente);
		PedidoMesa pedidoMesa=cliente.getPedidoMesa();
		for(PedidoIndividual pedido:pedidos){
			agregarPedido(pedido);
		}
		if(pedidoMesa!=null){
			pedidoMesaService.solicitarNuevoPedido(pedidoMesa.getId());
		}
	}
	
	public void pagarCuentaCliente(Long idCliente,String claveMesa) throws QRocksException{
		if(isNull(idCliente)){
			onError("");
		}
		List<PedidoIndividual> pedidos=obtenerCuentaCliente(idCliente,claveMesa);
		if(isEmptyCollection(pedidos)){
			//FIXME Mensaje
			onError("");
		}
		int tamanio=pedidos.size()-1;
		for(int i=tamanio;i>=0;i--){
			PedidoIndividual pedido=pedidos.get(i);
			pedido.setPagado(1);
			actualizarPedido(pedido);
		}
	}
	
	public void actualizarPedido(PedidoIndividual pedido) throws QRocksException{
		if(isNull(pedido) || isNull(pedido.getId())){
			onError("");
		}
		actualizar(pedido);
	}
	
	/**
	 * Obtiene los productos que solicito un cliente que no hayan sido pagados
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaCliente(Long idCliente,String claveMesa) throws QRocksException{
		if(isNull(idCliente)){
			onError("");
		}
		if(!isValid(claveMesa)){
			onError("");
		}
		Cliente cliente=clienteService.consultarCliente(idCliente);
		if(isNull(cliente)){
			onError("");
		}
		Mesa mesa=cliente.getPedidoMesa().getMesa();
		if(!claveMesa.equals(mesa.getClaveMesa())){
			//FIXME la mesa no coincide con el cliente al que pertenece 
			onError("");
		}
		PedidoIndividual filtro=new PedidoIndividual();
		filtro.setCliente(cliente);
		filtro.setPagado(0);//Que no este pagado
		List<PedidoIndividual> lista=filtrar(filtro);
		return lista;
	}
	/**
	 * Aplica filtros de busqueda sobre los pedidos en base a cualquier criterio de busqueda
	 * @param filtro
	 * @return
	 */
	public List<PedidoIndividual> filtrar(PedidoIndividual filtro){
		List<PedidoIndividual> lista=new ArrayList<PedidoIndividual>();
		StringBuilder str=new StringBuilder("select pi from pedidoIndividual pi ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pi.id desc ";
		lista=(List<PedidoIndividual>)consultarPorQuery(query, PedidoIndividual.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,PedidoIndividual filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Cliente cliente=filtro.getCliente();
			PedidoMesa pedido=(isNotNull(cliente))?cliente.getPedidoMesa():null;
			Long idPedido=(pedido!=null)?pedido.getId():null;
			Integer pagado=filtro.getPagado();
			Long idCliente=(cliente!=null)?cliente.getId():null;
			Producto producto=filtro.getProducto();
			Long idProducto=(producto!=null)?producto.getId():null;
			Integer atendido=(pedido!=null)?filtro.getAtendido():null;
			if(isNotNull(id)){
				agregarCondicion(str, " pi.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(idCliente)){
					agregarCondicion(str, " pi.cliente.id=:idCliente ", CondicionLogica.AND);
					parametros.put("idCliente",idCliente);
				}
				if(isNotNull(idProducto)){
					agregarCondicion(str, " pi.producto.id=:idProducto ", CondicionLogica.AND);
					parametros.put("idProducto",idProducto);
				}
				if(isNotNull(pagado)){
					agregarCondicion(str, " pi.pagado=:pagado ", CondicionLogica.AND);
					parametros.put("pagado",pagado);
				}
				if(isNotNull(idPedido)){
					agregarCondicion(str, " pi.cliente.pedidoMesa.id=:idPedido ", CondicionLogica.AND);
					parametros.put("idPedido",idPedido);
				}
				if(isNotNull(atendido)){
					agregarCondicion(str, " pi.atendido=:atendido ", CondicionLogica.AND);
					parametros.put("atendido",atendido);
				}
			}
		}
	}
	
	public List<PedidoIndividual> filtrarPorOrden(PedidoIndividual filtro,String queryOrden){
		List<PedidoIndividual> lista=new ArrayList<PedidoIndividual>();
		StringBuilder str=new StringBuilder("select pi from pedidoIndividual pi ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" "+queryOrden;
		lista=(List<PedidoIndividual>)consultarPorQuery(query, PedidoIndividual.class,parametros);
		return lista;
	}
	
	/**
	 * obtiene la lista de todos los pedidos que hicieron en una mesa (PedidoMesa)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerCuentaPorPedidoMesa(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		List<PedidoIndividual> pedidos=new ArrayList<PedidoIndividual>();
		PedidoIndividual filtro=new PedidoIndividual();
		Cliente cliente=new Cliente();
		PedidoMesa pedidoMesa=new PedidoMesa();
		pedidoMesa.setId(idPedido);
		cliente.setPedidoMesa(pedidoMesa);
		filtro.setCliente(cliente);
		pedidos=filtrarPorOrden(filtro," order by pi.cliente.alias asc,pi.id desc ");
		return pedidos;
	}
	
	/**
	 * obtiene la lista de los nuevos pedidos que hicieron en una mesa (PedidoMesa) y que no han sido atendidos
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoIndividual> obtenerNuevosPedidosPorPedidoMesa(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		List<PedidoIndividual> pedidos=new ArrayList<PedidoIndividual>();
		PedidoIndividual filtro=new PedidoIndividual();
		Cliente cliente=new Cliente();
		PedidoMesa pedidoMesa=new PedidoMesa();
		pedidoMesa.setId(idPedido);
		cliente.setPedidoMesa(pedidoMesa);
		filtro.setCliente(cliente);
		filtro.setAtendido(0);
		pedidos=filtrarPorOrden(filtro," order by pi.cliente.alias asc,pi.id desc ");
		return pedidos;
	}
	
	/**
	 * Marca como atendido un pedido
	 * @param idPedido
	 * @throws QRocksException Lanza excepcion si el pedido es nulo o no existe
	 */
	public void atenderPedidoDelCliente(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoIndividual pedido=consultarPorId(PedidoIndividual.class, idPedido);
		if(isNull(pedido)){
			onError("");
		}
		pedido.setAtendido(1);
		actualizarPedido(pedido);
		PedidoMesa pedidoMesa=pedido.getCliente().getPedidoMesa();
		List<PedidoIndividual> pedidos=obtenerNuevosPedidosPorPedidoMesa(pedidoMesa.getId());
		//Si ya no hay nuevos pedidos nuevos entonces se marca como atendida la mesa
		if(isEmptyCollection(pedidos)){
			pedidoMesa.setSolicitarNuevoPedido(0);
		}else{
			pedidoMesa.setSolicitarNuevoPedido(1);
		}
		pedidoMesaService.actualizarPedido(pedidoMesa);
	}

	/**
	 * =============================================
	 * 	Sets and Gets
	 * =============================================
	 */
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}

	public void setPedidoMesaService(PedidoMesaService pedidoMesaService) {
		this.pedidoMesaService = pedidoMesaService;
	}
}
