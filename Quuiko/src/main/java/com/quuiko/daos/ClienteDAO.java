package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.GCMService;
import com.quuiko.services.PedidoMesaService;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class ClienteDAO extends DAO<Cliente,Long>{
	@Autowired
	private PedidoMesaService pedidoMesaService;
	@Autowired
	private GCMService gcmService;
	
	public List<Cliente> filtrar(Cliente filtro){
		List<Cliente> lista=new ArrayList<Cliente>();
		StringBuilder str=new StringBuilder("select c from cliente c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		lista=(List<Cliente>)consultarPorQuery(query, Cliente.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Cliente filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String alias=filtro.getAlias();
			PedidoMesa pedido=filtro.getPedidoMesa();
			Long idPedido=(pedido!=null)?pedido.getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " c.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(alias)){
					agregarCondicion(str, " c.alias=:alias ", CondicionLogica.AND);
					parametros.put("alias","%"+alias+"%");
				}
				if(isNotNull(idPedido)){
					agregarCondicion(str, " c.pedidoMesa.id=:idPedido ", CondicionLogica.AND);
					parametros.put("idPedido",idPedido);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Cliente filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(c) from cliente c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		conteo=obtenerConteoPorQuery(query, Cliente.class, parametros);
		return conteo;
	}
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Cliente> filtrarPaginado(Cliente filtro, int inicio,int numRegistros){
		List<Cliente> lista=new ArrayList<Cliente>();
		StringBuilder str=new StringBuilder("select c from cliente c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		lista=consultarPorQueryPaginado(query,Cliente.class,parametros,inicio, numRegistros);
		return lista;
	}
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	public Cliente agregarCliente(Cliente cliente,PedidoMesa pedido) throws QRocksException{
		//FIXME Mensaje
		if(isNull(cliente) || !isValid(cliente.getAlias())){
			onError("Favor de proporcionar el nombre del invitado");
		}
		if(isNull(pedido) || isNull(pedido.getId())){
			onError("");
		}
		cliente.setPedidoMesa(pedido);
		try{
			GCMUser gcmUser=gcmService.consultarPorGCMId(cliente.getGcmId());
			if(gcmUser!=null && gcmUser.getAppUser()!=null){
				cliente.setIdAppUser(gcmUser.getAppUser().getId());
			}
		}catch(Exception e){
		}
		crear(cliente);
		return cliente;
	}
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	public void actualizarCliente(Cliente cliente) throws QRocksException{
		//FIXME Mensaje
		if(isNull(cliente)){
			onError("Favor de proporcionar el nombre del invitado");
		}
		actualizar(cliente);
	}
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	public void eliminarCliente(Cliente cliente) throws QRocksException{
		//FIXME Mensaje
		if(isNull(cliente) || isNull(cliente.getId())){
			onError("Favor de proporcionar el nombre del invitado a eliminar");
		}
		eliminarCliente(cliente.getId());
	}
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	public void eliminarCliente(Long idCliente) throws QRocksException{
		//FIXME Mensaje
		if(isNull(idCliente)){
			onError("");
		}
		eliminarPorId(Cliente.class,idCliente);
	}
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Cliente consultarCliente(Long idCliente) throws QRocksException{
		//FIXME Mensaje
		if(isNull(idCliente)){
			onError("");
		}
		Cliente cliente=consultarPorId(Cliente.class, idCliente);
		//FIXME Mensaje
		if(isNull(cliente)){
			onError("");
		}
		return cliente;
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(pedido)
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorPedido(Long idPedido) throws QRocksException{
		//FIXME Pedido
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=pedidoMesaService.consultarPorId(idPedido);
		Cliente filtro=new Cliente();
		filtro.setPedidoMesa(pedido);
		List<Cliente> personasXMesa=filtrar(filtro);
		return personasXMesa;
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Long idMesa) throws QRocksException{
		PedidoMesa pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(idMesa);
		//FIXME Mensaje
		if(isNull(pedido)){
			return null;
		}
		Cliente filtro=new Cliente();
		filtro.setPedidoMesa(pedido);
		List<Cliente> personasXMesa=filtrar(filtro);
		return personasXMesa;
	}
	/**
	 * Consulta a las personas que estan ordenando en una mesa(utiliza el idmesa para consultar el ultimo pedido activo)
	 * @param mesa
	 * @return
	 * @throws QRocksException
	 */
	public List<Cliente> consultarClientesPorMesaActiva(Mesa mesa) throws QRocksException{
		//FIXME Mensaje
		if(isNull(mesa) || isNull(mesa.getId())){
			onError("");
		}
		Long idMesa=mesa.getId();
		return consultarClientesPorMesaActiva(idMesa);
	}
	/**
	 * ==================================================
	 * 	Sets y Gets
	 * ==================================================
	 */
	public void setPedidoMesaService(PedidoMesaService pedidoMesaService) {
		this.pedidoMesaService = pedidoMesaService;
	}
}
