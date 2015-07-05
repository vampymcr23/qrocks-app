package com.quuiko.actions.mobileactions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.PedidoDomicilioAction;
import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoDomicilioService;

@Namespace("/m/pedidoDomicilio")
@ParentPackage("qrocks-default")
public class MobilePedidoDomicilioAction extends QRocksAction{
	private static Logger logger=Logger.getLogger(PedidoDomicilioAction.class);
	private static final String ACTION_AGREGAR_PEDIDO="save";
	@Autowired
	private PedidoDomicilioService pedidoDomicilioService;
	private List<PedidoDomicilio> pedidos=new ArrayList<PedidoDomicilio>();
	private PedidoDomicilio pedidoDomicilio;
	
	/**
	 * Accion para registrar un nuevo Pedido
	 * @return
	 */
	@Action(value=ACTION_AGREGAR_PEDIDO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","pedidoDomicilio\\..*","excludeProperties","pedidoDomicilio\\.listaProductos\\[\\d+\\]\\.producto\\.imagen,pedidoDomicilio\\.listaProductos\\[\\d+\\]\\.producto\\.negocio"})
	})
	public String nuevoPedido(){
		try {
			pedidoDomicilioService.guardarPedido(pedidoDomicilio);
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
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

	public void setPedidoDomicilioService(
			PedidoDomicilioService pedidoDomicilioService) {
		this.pedidoDomicilioService = pedidoDomicilioService;
	}
}
