package com.quuiko.daos;
import static com.quuiko.util.Utileria.agregarCondicion;

import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.dtos.MesaControl;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.MesaService;
import com.quuiko.services.PedidoIndividualService;
import com.quuiko.util.Utileria.CondicionLogica;
@Component
public class PedidoMesaDAO extends DAO<PedidoMesa, Long>{
	@Autowired
	private MesaService mesaService;
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	
	public PedidoMesa registrarPedido(Long idMesa) throws QRocksException{
		if(isNull(idMesa)){
			//FIXME Mensaje
			onError("");
		}
		Mesa mesa=mesaService.obtenerMesaPorId(idMesa);
		if(isNull(mesa)){
			//FIXME Mensaje
			onError("");
		}
		//Se obtienen los pedidos que hay activos de esa mesa y que deben de cancelarse para poder registrar este nuevo pedido.
		List<PedidoMesa> pedidosActivosPorCancelar=obtenerPedidosActivosPendientesPorCancelarEnMesa(idMesa);
		if(!isEmptyCollection(pedidosActivosPorCancelar)){
			for(PedidoMesa p:pedidosActivosPorCancelar){
				p.setActivo(0);
				actualizar(p);
			}
		}
		PedidoMesa pedido=new PedidoMesa();
		pedido.setMesa(mesa);
		pedido.setFecha(new Date());
		pedido.setActivo(1);
		crear(pedido);
		return pedido;
	}
	
	public PedidoMesa registrarPedido(String claveMesa) throws QRocksException{
		if(isNull(claveMesa)){
			//FIXME Mensaje
			onError("");
		}
		Mesa mesa=mesaService.obtenerMesaPorClave(claveMesa);
		if(isNull(mesa)){
			//FIXME Mensaje
			onError("");
		}
		//Se obtienen los pedidos que hay activos de esa mesa y que deben de cancelarse para poder registrar este nuevo pedido.
		List<PedidoMesa> pedidosActivosPorCancelar=obtenerPedidosActivosPendientesPorCancelarEnMesa(mesa.getId());
		if(!isEmptyCollection(pedidosActivosPorCancelar)){
			for(PedidoMesa p:pedidosActivosPorCancelar){
				p.setActivo(0);
				actualizar(p);
			}
		}
		PedidoMesa pedido=new PedidoMesa();
		pedido.setMesa(mesa);
		pedido.setFecha(new Date());
		pedido.setActivo(1);
		crear(pedido);
		return pedido;
	}
	
	public void cancelarPedido(Long idPedido)throws QRocksException{
		if(isNull(idPedido)){
			//FIXME Mensaje
			onError("");
		}
		PedidoMesa pedido=consultarPorId(PedidoMesa.class, idPedido);
		if(isNull(pedido)){
			//FIXME mensaje
			onError("");
		}
		pedido.setActivo(0);
		actualizar(pedido);
	}
	
	public void actualizarPedido(PedidoMesa pedido) throws QRocksException{
		if(isNull(pedido)){
			//FIXME
			onError("");
		}
		actualizar(pedido);
	}
	/**
	 * Metodo para eliminar un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void eliminarPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			//FIXME Mensaje
			onError("");
		}
		PedidoMesa pedido=consultarPorId(PedidoMesa.class, idPedido);
		if(isNull(pedido)){
			//FIXME mensaje
			onError("");
		}
		eliminar(pedido);
	}
	
	public List<PedidoMesa> filtrar(PedidoMesa filtro){
		List<PedidoMesa> lista=new ArrayList<PedidoMesa>();
		StringBuilder str=new StringBuilder("select pm from pedidoMesa pm ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pm.id desc ";
		lista=(List<PedidoMesa>)consultarPorQuery(query, PedidoMesa.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,PedidoMesa filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Integer activo=filtro.getActivo();
			Date fecha=filtro.getFecha();
			Mesa mesa=filtro.getMesa();
			Long idMesa=(mesa!=null)?mesa.getId():null;
			String claveMesa=(mesa!=null && isValid(mesa.getClaveMesa()))?mesa.getClaveMesa():null;
			if(isNotNull(id)){
				agregarCondicion(str, " pm.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(activo)){
					agregarCondicion(str, " pm.activo=:activo ", CondicionLogica.AND);
					parametros.put("activo",activo);
				}
				if(isNotNull(fecha)){
					agregarCondicion(str, " pm.fecha=:fecha ", CondicionLogica.AND);
					parametros.put("fecha",fecha);
				}
				if(isNotNull(idMesa)){
					agregarCondicion(str, " pm.mesa.id =:idMesa ", CondicionLogica.AND);
					parametros.put("idMesa",idMesa);
				}
				if(isNotNull(claveMesa)){
					agregarCondicion(str, " pm.mesa.claveMesa =:claveMesa ", CondicionLogica.AND);
					parametros.put("claveMesa",claveMesa);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(PedidoMesa filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(pm) from pedidoMesa pm ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pm.id desc ";
		conteo=obtenerConteoPorQuery(query, PedidoMesa.class, parametros);
		return conteo;
	}
	
	public List<PedidoMesa> filtrarPaginado(PedidoMesa filtro, int inicio,int numRegistros){
		List<PedidoMesa> lista=new ArrayList<PedidoMesa>();
		StringBuilder str=new StringBuilder("select n from Negocio n ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pm.id desc ";
		lista=consultarPorQueryPaginado(query,PedidoMesa.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param idMesa numero de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(Long idMesa) throws QRocksException{
		PedidoMesa pedido=null;
		if(isNull(idMesa)){
			onError("");
		}
		PedidoMesa filtro=new PedidoMesa();
		Mesa mesa=new Mesa();
		mesa.setId(idMesa);
		filtro.setMesa(mesa);
		filtro.setActivo(1);
		List<PedidoMesa> pedidos=filtrar(filtro);
		if(!isEmptyCollection(pedidos)){
			pedido=pedidos.get(0);
		}
		return pedido;
	}
	
	/**
	 * Obtiene los pedidos activos que en teoria deben de cancelarse por una mesa ya que este metodo solo se manda  a llamar por el registro de un nuevo pedido,
	 * antes de registrarse, se deben de cancelar los actualmente activos.
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	private List<PedidoMesa> obtenerPedidosActivosPendientesPorCancelarEnMesa(Long idMesa) throws QRocksException{
		if(isNull(idMesa)){
			onError("");
		}
		PedidoMesa filtro=new PedidoMesa();
		Mesa mesa=new Mesa();
		mesa.setId(idMesa);
		filtro.setMesa(mesa);
		filtro.setActivo(1);
		List<PedidoMesa> pedidos=filtrar(filtro);
		return pedidos;
	}
	
	/**
	 * Obtiene el ultimo pedido que se encuentra activo por una mesa, es utilizado cada vez que se 
	 * @param claveMesa clave de la mesa
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa obtenerPedidoActivoPorMesa(String claveMesa) throws QRocksException{
		PedidoMesa pedido=null;
		if(!isValid(claveMesa)){
			onError("");
		}
		PedidoMesa filtro=new PedidoMesa();
		Mesa mesa=new Mesa();
		mesa.setClaveMesa(claveMesa);
		filtro.setMesa(mesa);
		filtro.setActivo(1);
		List<PedidoMesa> pedidos=filtrar(filtro);
		if(!isEmptyCollection(pedidos)){
			pedido=pedidos.get(0);
			//Si hay mas de un pedido activo de la misma mesa, entonces los demas pedidos se desactivan automaticamente
			if(pedidos.size()>1){
				for(int i=1;i<pedidos.size();i++){
					PedidoMesa p=pedidos.get(i);
					if(p!=null){
						p.setActivo(0);
						actualizar(p);
					}
				}
			}
		}
		return pedido;
	}
	
	/**
	 * Consulta por id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public PedidoMesa consultarPorId(Long id) throws QRocksException{
		//FIXME Mensaje
		if(isNull(id)){
			onError("");
		}
		PedidoMesa pedido=super.consultarPorId(PedidoMesa.class,id);
		if(isNull(pedido)){
			onError("");
		}
		return pedido;
	}
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MesaControl> verMesaControl(Negocio negocio) throws QRocksException{
		List<MesaControl> pedidos=new ArrayList<MesaControl>();
		if(isNull(negocio) || isNull(negocio.getId())){
			onError("Favor de proporcionar el negocio");
		}
		Query q=em.createNamedQuery("verMesaControlPorNegocio");
		q.setParameter(1, negocio.getId());
		pedidos=q.getResultList();
		return pedidos;
	}
	
	/**
	 * Obtiene la mesa de control de los pedidos actuales POR MESERO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MesaControl> verMesaControlPorMesero(Negocio negocio,Long idMesero) throws QRocksException{
		List<MesaControl> pedidos=new ArrayList<MesaControl>();
		if(isNull(negocio) || isNull(negocio.getId())){
			onError("Favor de proporcionar el negocio");
		}
		Query q=em.createNamedQuery("verMesaControlPorNegocioMesero");
		q.setParameter(1, negocio.getId());
		q.setParameter(2, idMesero);
		pedidos=q.getResultList();
		return pedidos;
	}
	
	/**
	 * El cliente hace el llamado a un mesero
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void llamarMesero(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setLlamarMesero(1);
		actualizarPedido(pedido);
	}
	/**
	 * Atiende el llamado del cliente para que un mesero lo atienda
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderLlamadoMesero(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setLlamarMesero(0);
		actualizarPedido(pedido);
	}
	/**
	 * Indica que nuestro cliente solicito la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void solicitarCuentaMesero(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setSolicitarCuenta(1);
		actualizarPedido(pedido);
	}
	/**
	 * Atiende el llamado del cliente para solicitar la cuenta
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderCuentaMesero(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setSolicitarCuenta(0);
		actualizarPedido(pedido);
	}
	/**
	 * Se utiliza cada vez que un cliente pide un nuevo pedido, o agrega un nuevo producto a su cuenta, esto permite que la mesa de control vea
	 * cuando una persona de una mesa acaba de pedir algun nuevo producto.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void solicitarNuevoPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setSolicitarNuevoPedido(1);
		actualizarPedido(pedido);
	}
	/**
	 * La mesa de control al dar click sobre la alerta del nuevo pedido se marca como atendido para que la cocina
	 * pueda continuar con ese nuevo pedido que le solicitaron.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void atenderNuevoPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setSolicitarNuevoPedido(0);
		actualizarPedido(pedido);
	}
	/**
	 * Metodo que marca como pagado una cuenta de una mesa, barre todos los pedidos de un cliente y las marca como pagadas tambien.
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void pagarCuentaMesa(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("");
		}
		PedidoMesa pedido=consultarPorId(idPedido);
		pedido.setActivo(0);
		actualizarPedido(pedido);
		String claveMesa=pedido.getMesa().getClaveMesa();
		List<PedidoIndividual> listaPedidosPorMesa=pedidoIndividualService.obtenerCuentaPorPedidoMesa(idPedido);
		if(!isEmptyCollection(listaPedidosPorMesa)){
			for(PedidoIndividual pedidoIndividual:listaPedidosPorMesa){
				try{
					Cliente cliente=pedidoIndividual.getCliente();
					Long idCliente=cliente.getId();
					pedidoIndividualService.pagarCuentaCliente(idCliente, claveMesa);
				}catch(Exception e){
					//NO hacer nada cuando un cliente no tenga pedidos.
				}
			}
		}
		registrarPedido(claveMesa);//Despues de que se paga un pedido de esa mesa, se crea un nuevo pedido vacio.
	}
	/**
	 * ============================================================
	 * 	Sets and gets
	 * ============================================================
	 * 
	 */
	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}
}
