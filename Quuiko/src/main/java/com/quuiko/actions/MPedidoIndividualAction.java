package com.quuiko.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.Producto;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoIndividualService;
import com.quuiko.services.ProductoService;

@Namespace("/m/pedidos/cliente")
@ParentPackage("qrocks-default")
public class MPedidoIndividualAction extends QRocksAction{
	private final static String ACTION_VER_PEDIDO_CLIENTE="miCuenta";
	private final static String ACTION_GUARDAR_CUENTA="guardarCuenta";
	private final static String ACTION_OBTENER_MI_CUENTA="misPedidos";
	private final static String PAGINA_PEDIDOS_CLIENTE="/mobil/pedidos/cliente/cuentaCliente.jsp";
	private final static String CURRENT_NAMESPACE="/m/pedidos/cliente";
	private Long qrCID;
	private String qrMKEY;//clave de la mesa
	private List<PedidoIndividual> pedidos=new ArrayList<PedidoIndividual>();
	private List<Producto> listaProductos=new ArrayList<Producto>();
	
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	
	@Autowired
	private ProductoService productoService;
	
	@Action(value=ACTION_VER_PEDIDO_CLIENTE,results=@Result(name=SUCCESS,location=PAGINA_PEDIDOS_CLIENTE))
	public String verMiCuenta(){
		try {
			pedidos=pedidoIndividualService.obtenerCuentaCliente(qrCID,qrMKEY);
			listaProductos=productoService.consultarTodosLosProductos();
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Action(value=ACTION_GUARDAR_CUENTA,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_VER_PEDIDO_CLIENTE,"namespace",CURRENT_NAMESPACE,"qrCID","${qrCID}","qrMKEY","${qrMKEY}"}),
		@Result(name=INPUT,location=PAGINA_PEDIDOS_CLIENTE)
	})
	public String guardarCuentaCliente(){
		try {
			pedidoIndividualService.agregarPedidos(pedidos);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_OBTENER_MI_CUENTA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","pedidos"})
	})
	public String obtenerMisCuentas(){
		try {
			pedidos=pedidoIndividualService.obtenerCuentaCliente(qrCID,qrMKEY);
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Long getQrCID() {
		return qrCID;
	}

	public void setQrCID(Long qrCID) {
		this.qrCID = qrCID;
	}

	public String getQrMKEY() {
		return qrMKEY;
	}

	public void setQrMKEY(String qrMKEY) {
		this.qrMKEY = qrMKEY;
	}

	public List<PedidoIndividual> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoIndividual> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public void setPedidoIndividualService(
			PedidoIndividualService pedidoIndividualService) {
		this.pedidoIndividualService = pedidoIndividualService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}
}
