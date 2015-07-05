package com.quuiko.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.Producto;

@Namespace("/temp")
@ParentPackage("qrocks-default")
public class SSEventsAction extends QRocksAction{
	private InputStream stream;
	private String idNegocio;
	private static List<PedidoIndividual> listaPedidos=new ArrayList<PedidoIndividual>();
	
	private static Map<String,List<HttpServletResponse>> events=new HashMap<String, List<HttpServletResponse>>();
	
	@Action(value="verPedidos",results={
		@Result(name=SUCCESS,location="/mesa/tempVerPedidos.jsp")
	})
	public String verPedidos(){
		suscribeSession(ServletActionContext.getRequest());
		return result;
	}
	
	private void suscribeSession(HttpServletRequest request){
		String idSession=request.getSession().getId();
		if(!events.containsKey(idNegocio)){
			List<HttpServletResponse> responses=new ArrayList<HttpServletResponse>();
			responses.add(ServletActionContext.getResponse());
			events.put(idNegocio, responses);
			System.out.println("Agregada la sesion:"+idSession);
		}else{
			List<HttpServletResponse> responses=events.get(idNegocio);
			responses.add(ServletActionContext.getResponse());
		}
	}
	
	@Action(value="agregarPedido",results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","idNegocio"}),
			@Result(name="NOTIFY",type="stream",location="/mesa/empty.txt", params={"inputName","stream"})
	})
	public String agregarPedido(){
		
		PedidoIndividual pedido=new PedidoIndividual();
		pedido.setAtendido(0);
		pedido.setPagado(0);
		pedido.setId(System.currentTimeMillis());
		Producto p=new Producto();
		p.setActivo(1);
		p.setNombre("Test");
		pedido.setProducto(p);
		listaPedidos.add(pedido);
		boolean notify=notifyNewOrder(idNegocio,pedido);
//		if(notify){
			return "NOTIFY";
//		}
//		return result;
	}
	
	public boolean  notifyNewOrder(String idNegocio, PedidoIndividual pedido){
		boolean notify=false;
		if(events!=null && !events.isEmpty()){
			List<HttpServletResponse> responses=events.get(idNegocio);
			if(responses!=null && responses.isEmpty()){
				responses.add(ServletActionContext.getResponse());
			}
				for(HttpServletResponse response:responses){
					 response.setContentType("text/event-stream");
				     response.setCharacterEncoding("UTF-8");
				     
				     PrintWriter printWriter;
					try {
//						printWriter = response.getWriter();
						String r="data: "+pedido.getId() ;
//						printWriter.println(r);
						stream=new ByteArrayInputStream(r.getBytes());
//			            printWriter.println(); // note the additional line being written to the stream..
//			            printWriter.flush();
			            notify=true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		return notify;
	}
	
	public String getIdNegocio() {
		return idNegocio;
	}
	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}
	public static Map<String, List<HttpServletResponse>> getEvents() {
		return events;
	}
	public static void setEvents(Map<String, List<HttpServletResponse>> events) {
		SSEventsAction.events = events;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}
}
