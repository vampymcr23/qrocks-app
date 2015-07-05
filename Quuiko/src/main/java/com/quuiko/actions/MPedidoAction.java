package com.quuiko.actions;
import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.Mesa;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ClienteService;
import com.quuiko.services.MesaService;
import com.quuiko.services.PedidoIndividualService;
import com.quuiko.services.PedidoMesaService;
import com.quuiko.util.Utileria;

@Namespace("/m/pedidos")
@ParentPackage("qrocks-default")
public class MPedidoAction extends QRocksAction{
	/**
	 * =============================================
	 * 	PAGINAS, CONSTANTES,VARIABLES GLOBALES
	 * =============================================
	 */
	private final static String PAGINA_MENU_CLIENTE="/mobil/pedidos/menu.jsp";
	public final static String ACTION_MOSTRAR_MENU="menu";
	public final static String ACTION_AGREGAR_AMIGO="agregarAmigo";
	public final static String ACTION_ELIMINAR_AMIGO="eliminarAmigo";
	public final static String ACTION_GUARDAR_PEDIDO="guardarPedido";
	public final static String ACTION_LLAMAR_MESERO="llamarMesero";
	public final static String ACTION_INFO_PEDIDO="infoPedido";
	public final static String ACTION_OBTENER_CUENTA="gtAcc";
	public final static String ACTION_OBTENER_NUEVOS_PEDIDOS="gtNew";
	public final static String ACTION_ATENDER_NUEVO_PEDIDO="atnNew";//Action para cambiar el estatus de un pedido que ya este marcado como nuevo pedido.
	public final static String ACTION_SOLICITAR_CUENTA="askAccnt";
	public final static String JSON_ACTION_INFO_PEDIDO_MESA="getInfoPedidoMesa";
	public final static String JSON_ACTION_INFO_CUENTA="getInfoCuenta";
	public final static String JSON_ACTION_INFO_GRUPO="getInfoGrupo";
	public final static String CURRENT_NAMESPACE="/m/pedidos";
	private Long qrMID;
	private String qrMKEY;
	private PedidoMesa pedido;
	private Negocio negocio;
	private Long idPedidoClienteAtender;
	
	private Cliente amigo;
	private Cliente amigoEliminar;
	private List<Cliente> grupo=new ArrayList<Cliente>();
	private List<PedidoIndividual> cuenta=new ArrayList<PedidoIndividual>();
	private Double totalCuenta;
	
	@Autowired
	private PedidoMesaService pedidoMesaService;
	@Autowired
	private MesaService mesaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	/**
	 * =============================================
	 * 	Actions
	 * =============================================
	 */
	@Action(value=ACTION_MOSTRAR_MENU,results={
		@Result(name=SUCCESS,location=PAGINA_MENU_CLIENTE),
		@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_MOSTRAR_MENU,"namespace",CURRENT_NAMESPACE,"qrMKEY","${qrMKEY}"})
		
	})
	public String mostrarMenu(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNull(pedido)){
				//Se regresa al input para que le de Commit al pedido 
				pedido=pedidoMesaService.registrarPedido(qrMKEY);
				return INPUT;
			}
			Long idMesa=pedido.getMesa().getId();
			negocio=pedido.getMesa().getNegocio();
			grupo=clienteService.consultarClientesPorMesaActiva(idMesa);
			if(!isEmptyCollection(grupo)){
				cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
				if(!isEmptyCollection(cuenta)){
					totalCuenta=calcularCuenta(cuenta);
				}else{
					totalCuenta=0d;
				}
			}
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	private Double calcularCuenta(List<PedidoIndividual> cuenta){
		Double total=0d;
		int tamanio=cuenta.size()-1;
		for(int z=tamanio;z>=0;z--){
			PedidoIndividual pedido=cuenta.get(z);
			boolean estaPagado=(pedido!=null && pedido.getPagado()==1);
			double costo=(pedido!=null && pedido.getProducto()!=null && pedido.getProducto().getCosto()!=null)?pedido.getProducto().getCosto():0;
			if(estaPagado){
				costo=costo*-1;
			}
			total+=costo;
		}
		return total;
	}
	
	@Action(value=ACTION_AGREGAR_AMIGO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MOSTRAR_MENU,"namespace","/m/pedidos","qrMKEY","${qrMKEY}"})
	})
	public String agregarAmigo(){
		try {
			clienteService.agregarCliente(amigo, pedido);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_ELIMINAR_AMIGO,results={
			@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MOSTRAR_MENU,"namespace","/m/pedidos","qrMKEY","${qrMKEY}"})
		})
	public String eliminarAmigo(){
		try {
			clienteService.eliminarCliente(amigoEliminar);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_LLAMAR_MESERO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String llamarMesero(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedidoMesaService.llamarMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Action(value=ACTION_SOLICITAR_CUENTA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String pedirCuenta(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedidoMesaService.solicitarCuentaMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Action(value=ACTION_INFO_PEDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String obtenerInfoPedido(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Action(value=ACTION_OBTENER_CUENTA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","cuenta\\[\\d+\\]\\..*","excludeProperties","cuenta\\[\\d+\\]\\.cliente\\.pedidoMesa\\..*,cuenta\\[\\d+\\]\\.producto\\.imagen,cuenta\\[\\d+\\]\\.producto\\.negocio\\..*"})
	})
	public String obtenerNuevosPedidos(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Action que obtiene los nuevos pedidos solicitados por una mesa (que no han sido atendidos)
	 * @return
	 */
	@Action(value=ACTION_OBTENER_NUEVOS_PEDIDOS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","cuenta\\[\\d+\\]\\..*","excludeProperties","cuenta\\[\\d+\\]\\.cliente\\.pedidoMesa\\.mesa\\..*,cuenta\\[\\d+\\]\\.producto\\.imagen,cuenta\\[\\d+\\]\\.producto\\.negocio\\..*"})
	})
	public String obtenerCuenta(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				cuenta=pedidoIndividualService.obtenerNuevosPedidosPorPedidoMesa(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Action que se debe de mandar a llamar por la mesa de control, cuando el mesero quiere marcar como atendido un pedido de un cliente de X mesa.
	 * En este metodo se actualiza el estatus de atendido de un pedido, y de la mesa, de tal manera que en la mesa de control ya no se vera de color "amarillo" ya que fue
	 * atendido por algun mesero.
	 * @return
	 */
	@Action(value=ACTION_ATENDER_NUEVO_PEDIDO,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","cuenta"})
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","cuenta\\[\\d+\\]\\..*","excludeProperties","cuenta\\[\\d+\\]\\.cliente\\.pedidoMesa\\.mesa\\..*,cuenta\\[\\d+\\]\\.producto\\.negocio\\..*,cuenta\\[\\d+\\]\\.producto\\.imagen"})
	})
	public String atenderNuevoPedido(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				//Se actualiza el pedido de la mesa para quitar que estaba pendiente un pedido de un cliente de la mesa
				pedidoMesaService.atenderNuevoPedido(pedido.getId());
				if(idPedidoClienteAtender!=null){
					pedidoIndividualService.atenderPedidoDelCliente(idPedidoClienteAtender);
				}
				cuenta=pedidoIndividualService.obtenerNuevosPedidosPorPedidoMesa(pedido.getId());
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * =======================================================
	 * 	Actions para la parte de ANDROID
	 * =======================================================
	 */
	@Action(value=JSON_ACTION_INFO_PEDIDO_MESA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String obtenerInfoMesaPedidoActual(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNull(pedido)){
				//Se regresa al input para que le de Commit al pedido 
				pedido=pedidoMesaService.registrarPedido(qrMKEY);
				return INPUT;
			}
			Date fechaPedido=pedido.getFecha();
			String fecha=pedido.getFechaStr();
			pedido.setFecha(null);
			pedido.setFechaStr(fecha);
			pedido.getMesa().getNegocio().setFecha(null);
			pedido.getMesa().getNegocio().setImagen(null);
//			String f=Utileria.parseDateToString(fechaPedido, "dd/mm/yyyy HH:mm");
//			System.out.println("Fecha:"+f);
//			Calendar c=Calendar.getInstance();
//			c.setTime(fechaPedido);
//			Date otherDate=Utileria.castToDate(g);
//			pedido.setFecha(c.getTime());
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_INFO_CUENTA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","cuenta"})
	})
	public String obtenerInfoCuenta(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNull(pedido)){
				//Se regresa al input para que le de Commit al pedido 
				pedido=pedidoMesaService.registrarPedido(qrMKEY);
				return INPUT;
			}
			if(!isEmptyCollection(grupo)){
				cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
			}
			
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_INFO_GRUPO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","grupo"})
	})
	public String obtenerInfoGrupo(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNull(pedido)){
				//Se regresa al input para que le de Commit al pedido 
				pedido=pedidoMesaService.registrarPedido(qrMKEY);
				return INPUT;
			}
			Long idMesa=pedido.getMesa().getId();
			grupo=clienteService.consultarClientesPorMesaActiva(idMesa);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	/**
	 * =============================================
	 * 	Sets & Gets
	 * =============================================
	 */

	public Long getQrMID() {
		return qrMID;
	}

	public void setQrMID(Long qrMID) {
		this.qrMID = qrMID;
	}

	public PedidoMesa getPedido() {
		return pedido;
	}

	public void setPedido(PedidoMesa pedido) {
		this.pedido = pedido;
	}

	public void setPedidoService(PedidoMesaService pedidoMesaService) {
		this.pedidoMesaService = pedidoMesaService;
	}

	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public Cliente getAmigo() {
		return amigo;
	}

	public void setAmigo(Cliente amigo) {
		this.amigo = amigo;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public List<Cliente> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Cliente> grupo) {
		this.grupo = grupo;
	}

	public Cliente getAmigoEliminar() {
		return amigoEliminar;
	}

	public void setAmigoEliminar(Cliente amigoEliminar) {
		this.amigoEliminar = amigoEliminar;
	}

	public String getQrMKEY() {
		return qrMKEY;
	}

	public void setQrMKEY(String qrMKEY) {
		this.qrMKEY = qrMKEY;
	}

	public List<PedidoIndividual> getCuenta() {
		return cuenta;
	}

	public void setCuenta(List<PedidoIndividual> cuenta) {
		this.cuenta = cuenta;
	}

	public void setPedidoIndividualService(
			PedidoIndividualService pedidoIndividualService) {
		this.pedidoIndividualService = pedidoIndividualService;
	}

	public Double getTotalCuenta() {
		return totalCuenta;
	}

	public void setTotalCuenta(Double totalCuenta) {
		this.totalCuenta = totalCuenta;
	}

	public Long getIdPedidoClienteAtender() {
		return idPedidoClienteAtender;
	}

	public void setIdPedidoClienteAtender(Long idPedidoClienteAtender) {
		this.idPedidoClienteAtender = idPedidoClienteAtender;
	}
}