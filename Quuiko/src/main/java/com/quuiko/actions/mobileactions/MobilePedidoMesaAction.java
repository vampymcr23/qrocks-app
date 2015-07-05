package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Cliente;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.PedidoMesaCuentaDTO;
import com.quuiko.services.ClienteService;
import com.quuiko.services.PedidoIndividualService;
import com.quuiko.services.PedidoMesaService;

@Namespace("/m/droid/pedidos")
@ParentPackage("qrocks-default")
public class MobilePedidoMesaAction extends QRocksAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854444152211871800L;
	/**
	 * =============================================
	 * 	CONSTANTES,VARIABLES GLOBALES
	 * =============================================
	 */
	public final static String JSON_ACTION_INFO_PEDIDO_MESA="getInfoPedidoMesa";
	public final static String JSON_ACTION_INFO_CUENTA="getInfoCuenta";
	public final static String JSON_ACTION_INFO_GRUPO="getInfoGrupo";
	public final static String JSON_ACTION_ADD_PAL="addPal";
	public final static String JSON_ACTION_REMOVE_PAL="removePal";
	public final static String JSON_ACTION_CALL_WITRESS="callWit";
	public final static String JSON_ACTION_GET_ACCOUNT="gtAccount";
	public final static String JSON_ACTION_SHOW_ACCOUNT="showAccount";
	private final static String CURRENT_NAMESPACE="/m/droid/pedidos";
	private String qrMKEY;
	private PedidoMesa pedido;
	private List<PedidoIndividual> cuenta=new ArrayList<PedidoIndividual>();
	private Double totalCuenta;
	private PedidoMesaCuentaDTO pedidoMesaCuentaDTO;
	private List<Cliente> grupo=new ArrayList<Cliente>();
	private Cliente amigo;
	private static Logger LOG=Logger.getLogger(MobilePedidoMesaAction.class.getName());
	
	@Autowired
	private PedidoMesaService pedidoMesaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	/**
	 * =======================================================
	 * 	Actions para la parte de ANDROID
	 * =======================================================
	 */
	@Action(value=JSON_ACTION_INFO_PEDIDO_MESA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoMesaCuentaDTO"})
	})
	public String obtenerInfoMesaPedidoActual(){
		try {
			cargarInformacionPedidoMesa();
//			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
//			if(isNull(pedido)){
//				//Se regresa al input para que le de Commit al pedido 
//				pedido=pedidoMesaService.registrarPedido(qrMKEY);
//				return INPUT;
//			}
//			Date fechaPedido=pedido.getFecha();
//			String fecha=pedido.getFechaStr();
//			pedido.setFecha(null);
//			pedido.setFechaStr(fecha);
//			pedido.getMesa().getNegocio().setFecha(null);
//			pedido.getMesa().getNegocio().setImagen(null);
//			grupo=clienteService.consultarClientesPorMesaActiva(pedido.getMesa().getId());
//			if(!isEmptyCollection(grupo)){
//				for(Cliente c:grupo){
//					c.setPedidoMesa(pedido);
//				}
//				cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
//				if(!isEmptyCollection(cuenta)){
//					for(PedidoIndividual p:cuenta){
//						p.getCliente().setPedidoMesa(pedido);
//						p.getProducto().setImagen(null);
//					}
//					totalCuenta=calcularCuenta(cuenta);
//				}else{
//					totalCuenta=0d;
//				}
//			}
//			pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
//			pedidoMesaCuentaDTO.setPedidoMesa(pedido);
//			pedidoMesaCuentaDTO.setCuenta(cuenta);
//			pedidoMesaCuentaDTO.setTotalCuenta(totalCuenta);
//			pedidoMesaCuentaDTO.setGrupo(grupo);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_SHOW_ACCOUNT,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoMesaCuentaDTO"})
		})
		public String obtenerTodaLaCuentaActual(){
			try {
				cargarInformacionPedido();
			} catch (QRocksException e) {
				onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
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
	
	/**
	 * Obtiene la info de la mesa solo la info general de los usuarios que estan en esa mesa
	 * @throws QRocksException
	 */
	private void cargarInformacionPedidoMesa() throws QRocksException{
		pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
		if(isNull(pedido)){
			//Se regresa al input para que le de Commit al pedido 
			pedido=pedidoMesaService.registrarPedido(qrMKEY);
		}
		Date fechaPedido=pedido.getFecha();
		String fecha=pedido.getFechaStr();
		pedido.setFecha(null);
		pedido.setFechaStr(fecha);
//		pedido.getMesa().getNegocio().setFecha(null);
//		pedido.getMesa().getNegocio().setImagen(null);
		Negocio n=new Negocio();
		n.setId(pedido.getMesa().getNegocio().getId());
		pedido.getMesa().setNegocio(n);
		pedido.getMesa().setMesero(null);
		grupo=clienteService.consultarClientesPorMesaActiva(pedido.getMesa().getId());
		if(!isEmptyCollection(grupo)){
			for(Cliente c:grupo){
				c.setPedidoMesa(pedido);
			}
			cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
			if(!isEmptyCollection(cuenta)){
				for(PedidoIndividual p:cuenta){
					p.getCliente().setPedidoMesa(pedido);
					p.getProducto().setImagen(null);
					if(p.getProducto()!=null && p.getProducto().getNegocio()!=null){
						p.getProducto().getNegocio().setImagen(null);
						p.getProducto().setNegocio(null);
					}
				}
				totalCuenta=calcularCuenta(cuenta);
			}else{
				totalCuenta=0d;
			}
		}
		Date fechaSeleccionVideo=pedido.getFechaSeleccionVideo();
		Integer puedeSeleccionarVideo=null;
		Integer tiempoEspera=null;
		if(fechaSeleccionVideo==null){
			puedeSeleccionarVideo=1;
		}else{
			Calendar c=Calendar.getInstance();
			c.setTime(fechaSeleccionVideo);
			Date fechaActual=new Date();
			long miliSecondsFechaSeleccion=c.getTimeInMillis();
			long miliSecondsFechaActual=fechaActual.getTime();
			long diferencia=miliSecondsFechaActual-miliSecondsFechaSeleccion;
			long numSegundosDiferencia=(diferencia/1000L);
			//Si el num. de segundos es mayor a los segundos de la configuracion, entonces ya se puede agregar.
			if(numSegundosDiferencia>=MobileMediaplayerAction.NUM_SEGUNDOS_CONFIGURACION){
				puedeSeleccionarVideo=1;
				tiempoEspera=0;
			}else{
				puedeSeleccionarVideo=0;
				tiempoEspera=Integer.parseInt(String.valueOf(MobileMediaplayerAction.NUM_SEGUNDOS_CONFIGURACION-numSegundosDiferencia));
			}
			
		}
		pedido.setPuedeSeleccionarVideo(puedeSeleccionarVideo);
		pedido.setTiempoEspera(tiempoEspera);
		pedido.setFechaSeleccionVideo(fechaSeleccionVideo);
		pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
		pedidoMesaCuentaDTO.setPedidoMesa(pedido);
		pedidoMesaCuentaDTO.setCuenta(null);
		pedidoMesaCuentaDTO.setTotalCuenta(totalCuenta);
		pedidoMesaCuentaDTO.setGrupo(grupo);
	}
	
	/**
	 * Obtiene la info para la cuenta por la mesa (todos los pedidos de todos los clientes)
	 * @throws QRocksException
	 */
	private void cargarInformacionPedido() throws QRocksException{
		pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
		if(isNull(pedido)){
			//Se regresa al input para que le de Commit al pedido 
			pedido=pedidoMesaService.registrarPedido(qrMKEY);
		}
		Date fechaPedido=pedido.getFecha();
		String fecha=pedido.getFechaStr();
		pedido.setFecha(null);
		pedido.setFechaStr(fecha);
//		pedido.getMesa().getNegocio().setFecha(null);
//		pedido.getMesa().getNegocio().setImagen(null);
		pedido.getMesa().setNegocio(null);
		pedido.getMesa().setMesero(null);
		grupo=clienteService.consultarClientesPorMesaActiva(pedido.getMesa().getId());
		if(!isEmptyCollection(grupo)){
			for(Cliente c:grupo){
				c.setPedidoMesa(pedido);
			}
			cuenta=pedidoIndividualService.obtenerCuentaPorPedidoMesa(pedido.getId());
			if(!isEmptyCollection(cuenta)){
				for(PedidoIndividual p:cuenta){
					p.getCliente().setPedidoMesa(pedido);
					p.getProducto().setImagen(null);
					if(p.getProducto()!=null && p.getProducto().getNegocio()!=null){
						p.getProducto().getNegocio().setImagen(null);
						p.getProducto().setNegocio(null);
					}
				}
				totalCuenta=calcularCuenta(cuenta);
			}else{
				totalCuenta=0d;
			}
		}
		pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
		pedidoMesaCuentaDTO.setPedidoMesa(pedido);
		pedidoMesaCuentaDTO.setCuenta(cuenta);
		pedidoMesaCuentaDTO.setTotalCuenta(totalCuenta);
		pedidoMesaCuentaDTO.setGrupo(grupo);
	}
	
	/**
	 * Action que agrega un amigo a la mesa
	 * @return
	 */
	@Action(value=JSON_ACTION_ADD_PAL,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoMesaCuentaDTO"})
	})
	public String agregarAmigo(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(!isNull(pedido)){
				LOG.log(Level.INFO,"Agregando amigo:"+amigo.getAlias());
				clienteService.agregarCliente(amigo, pedido);
				LOG.log(Level.INFO,"Amigo agregado!");
			}
			cargarInformacionPedidoMesa();
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
		}
		return result;
	}
	
	/**
	 * Action que agrega un amigo a la mesa
	 * @return
	 */
	@Action(value=JSON_ACTION_REMOVE_PAL,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoMesaCuentaDTO"})
	})
	public String eliminarAmigo(){
		try {
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(!isNull(pedido)){
				LOG.log(Level.INFO,"Eliminando amigo:"+amigo.getAlias());
				clienteService.eliminarCliente(amigo);
				LOG.log(Level.INFO,"Amigo:"+amigo.getAlias()+" eliminado!");
			}
			cargarInformacionPedidoMesa();
		} catch (QRocksException e) {
			pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
			onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
			System.out.println("PedidoMesaCuentaDTO- Code:"+pedidoMesaCuentaDTO.getRequestCode()+"|Message:"+pedidoMesaCuentaDTO.getErrorMessage());
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_CALL_WITRESS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoMesaCuentaDTO"})
	})
	public String llamarMesero(){
		try{
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNotNull(pedido) && isNotNull(pedido.getId())){
				pedidoMesaService.llamarMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
				cargarInformacionPedidoMesa();
			}
		}catch (QRocksException e) {
			pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
			onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
			System.out.println("PedidoMesaCuentaDTO- Code:"+pedidoMesaCuentaDTO.getRequestCode()+"|Message:"+pedidoMesaCuentaDTO.getErrorMessage());
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_GET_ACCOUNT,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String pedirCuenta(){
		try{
			pedido=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMKEY);
			if(isNotNull(pedido) && isNotNull(pedido.getId())){
				pedidoMesaService.solicitarCuentaMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
				cargarInformacionPedidoMesa();
			}
		}catch (QRocksException e) {
			pedidoMesaCuentaDTO=new PedidoMesaCuentaDTO();
			onErrorAndroidMessage(e,pedidoMesaCuentaDTO);
			System.out.println("PedidoMesaCuentaDTO- Code:"+pedidoMesaCuentaDTO.getRequestCode()+"|Message:"+pedidoMesaCuentaDTO.getErrorMessage());
		}
		return result;
	}
	/**
	 * =============================================
	 * 	Sets & Gets
	 * =============================================
	 */
	public String getQrMKEY() {
		return qrMKEY;
	}

	public void setQrMKEY(String qrMKEY) {
		this.qrMKEY = qrMKEY;
	}

	public PedidoMesa getPedido() {
		return pedido;
	}

	public void setPedido(PedidoMesa pedido) {
		this.pedido = pedido;
	}

	public List<PedidoIndividual> getCuenta() {
		return cuenta;
	}

	public void setCuenta(List<PedidoIndividual> cuenta) {
		this.cuenta = cuenta;
	}

	public Double getTotalCuenta() {
		return totalCuenta;
	}

	public void setTotalCuenta(Double totalCuenta) {
		this.totalCuenta = totalCuenta;
	}

	public PedidoMesaCuentaDTO getPedidoMesaCuentaDTO() {
		return pedidoMesaCuentaDTO;
	}

	public void setPedidoMesaCuentaDTO(PedidoMesaCuentaDTO pedidoMesaCuentaDTO) {
		this.pedidoMesaCuentaDTO = pedidoMesaCuentaDTO;
	}

	public List<Cliente> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Cliente> grupo) {
		this.grupo = grupo;
	}

	public void setPedidoMesaService(PedidoMesaService pedidoMesaService) {
		this.pedidoMesaService = pedidoMesaService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setPedidoIndividualService(
			PedidoIndividualService pedidoIndividualService) {
		this.pedidoIndividualService = pedidoIndividualService;
	}

	public Cliente getAmigo() {
		return amigo;
	}

	public void setAmigo(Cliente amigo) {
		this.amigo = amigo;
	}
}
