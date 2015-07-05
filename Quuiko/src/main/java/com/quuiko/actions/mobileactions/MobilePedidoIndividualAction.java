package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Cliente;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.PedidosClienteDTO;
import com.quuiko.services.ClienteService;
import com.quuiko.services.PedidoIndividualService;

@Namespace("/m/droid/pedidos/cliente")
@ParentPackage("qrocks-default")
public class MobilePedidoIndividualAction  extends QRocksAction{
	private final static String ACTION_GUARDAR_PEDIDOS="saveAllProd";
	private final static String ACTION_CARGAR_INFO_CLIENTE="loadClient";
	private final static String ACTION_CARGAR_PEDIDOS="loadAllProdByClient";
	private final static String ACTION_ELIMINAR_PEDIDO="deleteProdByClient";
	private Long qrCID;
	private String qrMKEY;//clave de la mesa
	private List<PedidoIndividual> pedidos=new ArrayList<PedidoIndividual>();
	private PedidosClienteDTO dto;
	@Autowired
	private PedidoIndividualService pedidoIndividualService;
	@Autowired
	private ClienteService clienteService;
	private static Logger LOG=Logger.getLogger(MobilePedidoIndividualAction.class.getName());
	
	private void cargarInformacion(){
		try {
			dto=new PedidosClienteDTO();
			pedidos=pedidoIndividualService.obtenerCuentaCliente(qrCID,qrMKEY);
			Cliente cliente=clienteService.consultarCliente(qrCID);
			if(cliente!=null){
				cliente.getPedidoMesa().setFecha(null);
				cliente.getPedidoMesa().getMesa().setNegocio(null);
				cliente.getPedidoMesa().getMesa().setMesero(null);
			}
			if(!isEmptyCollection(pedidos)){
				for(PedidoIndividual pi:pedidos){
					pi.setCliente(cliente);
					pi.getProducto().setImagen(null);
					if(pi.getProducto()!=null && pi.getProducto().getNegocio()!=null){
						pi.getProducto().getNegocio().setImagen(null);
					}
				}
			}
			dto.setCliente(cliente);
			dto.setPedidos(pedidos);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,dto);
		}
	}
	
	@Action(value=ACTION_CARGAR_INFO_CLIENTE,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","dto"})
		})
	public String cargarInfoCliente(){
		System.out.println("Cargando informacion... cliente:"+qrCID+"|qrMkey:"+qrMKEY);
		Cliente cliente=null;
		try {
			cliente = clienteService.consultarCliente(qrCID);
			if(cliente!=null){
				cliente.getPedidoMesa().setFecha(null);
				cliente.getPedidoMesa().getMesa().setNegocio(null);
				cliente.getPedidoMesa().getMesa().setMesero(null);
			}
			dto=new PedidosClienteDTO();
			dto.setCliente(cliente);
			dto.setPedidos(new ArrayList<PedidoIndividual>());
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,dto);
		}
		return result;
	}
	
	@Action(value=ACTION_CARGAR_PEDIDOS,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","dto"})
		})
	public String cargarCuentaCliente(){
		System.out.println("Cargando informacion... cliente:"+qrCID+"|qrMkey:"+qrMKEY);
		cargarInformacion();
		return result;
	}
	
	@Action(value=ACTION_GUARDAR_PEDIDOS,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","dto"})
		})
	public String guardarCuentaCliente(){
		System.out.println("Guardando informacion... cliente:"+qrCID+"|qrMkey:"+qrMKEY+"|Pedidos:"+pedidos.size());
		try {
			pedidoIndividualService.agregarPedidos(pedidos);
			cargarInformacion();
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,dto);
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

	public PedidosClienteDTO getDto() {
		return dto;
	}

	public void setDto(PedidosClienteDTO dto) {
		this.dto = dto;
	}

	public void setPedidoIndividualService(
			PedidoIndividualService pedidoIndividualService) {
		this.pedidoIndividualService = pedidoIndividualService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
}
