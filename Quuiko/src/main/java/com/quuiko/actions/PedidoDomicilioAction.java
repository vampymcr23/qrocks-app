package com.quuiko.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.PedidoDomicilio.EstatusPedidoDomicilio;
import com.quuiko.beans.PedidoProductoDomicilio;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.PedidoDomicilioDTO;
import com.quuiko.services.PedidoDomicilioService;
import com.quuiko.services.PedidoProductoDomicilioService;

@Namespace("/pedidoDomicilio")
@ParentPackage("qrocks-default")
public class PedidoDomicilioAction extends QRocksAction{
	private static Logger logger=Logger.getLogger(PedidoDomicilioAction.class);
	private static final String ACTION_VER_PEDIDOS="open";
	private static final String ACTION_VER_HISTORIAL_PEDIDOS="history";
	private static final String ACTION_FILTRAR="search";
	private static final String ACTION_FILTRAR_PENDIENTES="searchPnd";
	private static final String ACTION_VER_DETALLE_PEDIDO="details";
	private static final String ACTION_VER_DETALLE_PEDIDO_JSON="gtDetails";
	private static final String PAGINA_VER_PEDIDOS="/pedidoDomicilio/pedidosDomicilio.jsp";
	private static final String PAGINA_VER_HISTORIAL_PEDIDOS="/pedidoDomicilio/historialPedidosDomicilio.jsp";
	private static final String PAGINA_VER_DETALLE_PEDIDO="verPedido";
	private static final String ACTION_AUTORIZAR_PEDIDO="auth";
	private static final String ACTION_RECHAZAR_PEDIDO="reject";
	private static final String ACTION_CANCELAR_PEDIDO="cancel";
	private static final String ACTION_MARCAR_PEDIDO_ATENDIDO="attend";
	private static final String ACTION_MARCAR_PEDIDO_LISTO="ready";
	private static final String ACTION_MARCAR_PEDIDO_CANCELADO="markAsCancel";
	private static final String ACTION_AGREGAR_PEDIDO="save";
	
	private PedidoDomicilioDTO pedidoDomicilioDto;
	@Autowired
	private PedidoDomicilioService pedidoDomicilioService;
	@Autowired
	private PedidoProductoDomicilioService pedidoProductoDomicilioService;
	private List<PedidoDomicilio> pedidos=new ArrayList<PedidoDomicilio>();
	private PedidoDomicilio pedidoDomicilio;
	private PedidoDomicilio filtro;
	
	/**
	 * Accion para obtener todos los pedidos pendientes por autorizar
	 * @return
	 */
	@Action(value=ACTION_VER_PEDIDOS,results={
		@Result(name=SUCCESS,location=PAGINA_VER_PEDIDOS)
	})
	public String verPedidos(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	
	/**
	 * Accion para consultar todos los pedidos que se han recibido, atendido, cancelado, autorizado o terminado
	 * @return
	 */
	@Action(value=ACTION_VER_HISTORIAL_PEDIDOS,results={
		@Result(name=SUCCESS,location=PAGINA_VER_HISTORIAL_PEDIDOS)
	})
	public String verHistorialPedidos(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidos=pedidoDomicilioService.consultarPedidosPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	/**
	 * Accion para filtrar o hacer busquedas avanzadas
	 * @return
	 */
	@Action(value=ACTION_FILTRAR,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String filtrar(){
		pedidos=pedidoDomicilioService.filtrar(filtro);
		return result;
	}
	
	/**
	 * Accion para filtrar o hacer busquedas avanzadas
	 * @return
	 */
	@Action(value=ACTION_FILTRAR_PENDIENTES,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String filtrarPendientes(){
		if(filtro==null){
			filtro=new PedidoDomicilio();
		}
		filtro.setEstatus(EstatusPedidoDomicilio.PENDIENTE.clave);
		pedidos=pedidoDomicilioService.filtrar(filtro);
		return result;
	}
	
	/**
	 * Metodo para validar el nuevo pedido y verificar si se tiene toda la informacion necesaria para solicitar el pedido
	 */
	public void validateNuevoPedido(){
		validar(pedidoDomicilio);
	}
	
	/**
	 * Accion para registrar un nuevo Pedido
	 * TODO Excluir la imagen del producto
	 * @return
	 */
	@Action(value=ACTION_AGREGAR_PEDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidoDomicilioDto"})
	})
	public String nuevoPedido(){
		try {
			pedidoDomicilioDto=new PedidoDomicilioDTO();
			pedidoDomicilioService.guardarPedido(pedidoDomicilio);
			pedidoDomicilioDto.setPedidoDomicilio(pedidoDomicilio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorAndroidMessage(e, pedidoDomicilioDto);
		}
		return result;
	}
	
	@Action(value=ACTION_AUTORIZAR_PEDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String autorizarPedido(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidoDomicilioService.marcarAutorizado(pedidoDomicilio.getId());
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_RECHAZAR_PEDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String rechazarPedido(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidoDomicilioService.marcarRechazado(pedidoDomicilio.getId());
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_MARCAR_PEDIDO_ATENDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String atenderPedido(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidoDomicilioService.marcarAtentido(pedidoDomicilio.getId());
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_MARCAR_PEDIDO_LISTO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String pedidoListoParaEntrega(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidoDomicilioService.marcarListoParaEntregar(pedidoDomicilio.getId());
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_MARCAR_PEDIDO_CANCELADO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String cancelarPedido(){
		Negocio negocioLogeado=getNegocioLogeado();
		Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
		try {
			pedidoDomicilioService.marcarCancelado(pedidoDomicilio.getId());
			pedidos=pedidoDomicilioService.consultarPedidosPendientesPorNegocio(idNegocio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_VER_DETALLE_PEDIDO,results={
		@Result(name=SUCCESS,location=PAGINA_VER_DETALLE_PEDIDO)
	})
	public String verDetallePedido(){
		try {
			pedidoDomicilio=pedidoDomicilioService.consultarPedido(pedidoDomicilio.getId());
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_VER_DETALLE_PEDIDO_JSON,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","pedidoDomicilioDto\\..*","excludeProperties","pedidoDomicilioDto\\.pedidoDomicilio\\.listaProductos\\[\\d+\\]\\.producto\\.imagen,pedidoDomicilioDto\\.pedidoDomicilio\\.listaProductos\\[\\d+\\]\\.producto\\.negocio,pedidoDomicilioDto\\.pedidoDomicilio\\.negocio"})
	})
	public String verDetallePedidoJson(){
		pedidoDomicilioDto=new PedidoDomicilioDTO();
		try {
			pedidoDomicilio=pedidoDomicilioService.consultarPedido(pedidoDomicilio.getId());
			List<PedidoProductoDomicilio> productos=pedidoProductoDomicilioService.consultarProductosPorPedido(pedidoDomicilio.getId());
			pedidoDomicilio.setListaProductos(productos);
			pedidoDomicilioDto.setPedidoDomicilio(pedidoDomicilio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorAndroidMessage(e, pedidoDomicilioDto);
		}
		return result;
	}

	public PedidoDomicilioDTO getPedidoDomicilioDto() {
		return pedidoDomicilioDto;
	}

	public void setPedidoDomicilioDto(PedidoDomicilioDTO pedidoDomicilioDto) {
		this.pedidoDomicilioDto = pedidoDomicilioDto;
	}

	public List<PedidoDomicilio> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoDomicilio> pedidos) {
		this.pedidos = pedidos;
	}

	public PedidoDomicilio getPedidoDomicilio() {
		return pedidoDomicilio;
	}

	public void setPedidoDomicilio(PedidoDomicilio pedidoDomicilio) {
		this.pedidoDomicilio = pedidoDomicilio;
	}

	public PedidoDomicilio getFiltro() {
		return filtro;
	}

	public void setFiltro(PedidoDomicilio filtro) {
		this.filtro = filtro;
	}
}
