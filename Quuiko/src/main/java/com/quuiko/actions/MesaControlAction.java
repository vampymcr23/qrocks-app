package com.quuiko.actions;

import static com.quuiko.util.Utileria.isNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.dtos.MesaControl;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoMesaService;

@Namespace("/mesaControl")
@ParentPackage("qrocks-default")
public class MesaControlAction extends QRocksAction{
	private static Logger logger=Logger.getLogger(MesaControlAction.class);
	private static final String ACTION_VER_MESA_CONTROL="verMesaControl";
	private static final String ACTION_OBTENER_MESA_CONTROL="gtMC";
	private static final String ACTION_ATENDER_LLAMADO_MESERO="atnWitress";
	private static final String ACTION_ATENDER_PEDIR_CUENTA="bringTheAccount";
	private static final String ACTION_COBRAR_CUENTA="closeAccnt";
	private static final String PAGINA_VER_MESA_CONTROL="/mesa/mesaDeControl.jsp";
	private List<MesaControl> pedidos=new ArrayList<MesaControl>();
	private PedidoMesa pedido;
	@Autowired
	private PedidoMesaService pedidoMesaService;
	
	private Long idMesero;
	
	@Action(value=ACTION_VER_MESA_CONTROL,results=@Result(name=SUCCESS,location=PAGINA_VER_MESA_CONTROL))
	public String verMesaControl(){
		Negocio negocioLoNegocio=getNegocioLogeado();
		try {
			if(idMesero==null){
				pedidos=pedidoMesaService.verMesaControl(negocioLoNegocio);
			}else{
				pedidos=pedidoMesaService.verMesaControlPorMesero(negocioLoNegocio, idMesero);
			}
		} catch (QRocksException e) {
			logger.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_OBTENER_MESA_CONTROL,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String obtenerPedidosMesaControl(){
		Negocio negocioLoNegocio=getNegocioLogeado();
		if(negocioLoNegocio!=null){
			try {
				if(idMesero==null){
					pedidos=pedidoMesaService.verMesaControl(negocioLoNegocio);
				}else{
					pedidos=pedidoMesaService.verMesaControlPorMesero(negocioLoNegocio, idMesero);
				}
			} catch (QRocksException e) {
				logger.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_COBRAR_CUENTA,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String pagarCuenta(){
		Negocio negocioLoNegocio=getNegocioLogeado();
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedidoMesaService.pagarCuentaMesa(pedido.getId());
				pedidos=pedidoMesaService.verMesaControl(negocioLoNegocio);
			} catch (QRocksException e) {
				logger.warn(e);
				e.printStackTrace();
			}
		}
		return result;
	}

	@Action(value=ACTION_ATENDER_LLAMADO_MESERO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String atenderLlamadoMesero(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedidoMesaService.atenderLlamadoMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
			} catch (QRocksException e) {
				logger.warn(e);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Action(value=ACTION_ATENDER_PEDIR_CUENTA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedido"})
	})
	public String atenderPedirCuenta(){
		if(isNotNull(pedido) && isNotNull(pedido.getId())){
			try {
				pedidoMesaService.atenderCuentaMesero(pedido.getId());
				pedido=pedidoMesaService.consultarPorId(pedido.getId());
			} catch (QRocksException e) {
				logger.warn(e);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<MesaControl> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<MesaControl> pedidos) {
		this.pedidos = pedidos;
	}

	public void setPedidoMesaService(PedidoMesaService pedidoMesaService) {
		this.pedidoMesaService = pedidoMesaService;
	}

	public PedidoMesa getPedido() {
		return pedido;
	}

	public void setPedido(PedidoMesa pedido) {
		this.pedido = pedido;
	}

	public Long getIdMesero() {
		return idMesero;
	}

	public void setIdMesero(Long idMesero) {
		this.idMesero = idMesero;
	}
}
