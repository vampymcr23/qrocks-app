package com.quuiko.actions;

import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Negocio;
import com.quuiko.dtos.estadisticas.ClienteFestejado;
import com.quuiko.dtos.estadisticas.ClienteMasVisitas;
import com.quuiko.dtos.estadisticas.EstadisticaProducto;
import com.quuiko.dtos.estadisticas.ProductoMasPedido;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.BitacoraVisitaService;
import com.quuiko.services.EstadisticasService;
import com.quuiko.services.GCMService;
import com.quuiko.util.Utileria;

@Namespace("/analytics")
@ParentPackage("qrocks-default")
public class EstadisticasAction extends QRocksAction{
	private static Logger log=Logger.getLogger(EstadisticasAction.class);
	private static final String ACTION_VER_ESTADISTICAS="show";
	private static final String ACTION_HAPPY_BIRTHDAYS="gtHappyBirths";
	private static final String ACTION_CONSULTAR_PRODUCTOS_VENDIDOS_NEGOCIO="gtTopProducts";
	private static final String PAGE_VER_ESTADISTICAS="/estadisticas/misEstadisticas.jsp";
	private static final String ACTION_CONSULTAR_ESTADISTICAS_PRODUCTOS="showProductsAnalysis";
	private static final String PAGE_VER_ESTADISTICAS_PRODUCTOS="/estadisticas/estadisticasProductos.jsp";
	private static final String ACTION_CONSULTAR_ESTADISTICAS_PRODUCTOS_JSON="showProductsAnalysisJson";
	
	private Long idNegocio;
	private Negocio negocio;
	private String fechaInicioStr;
	private String fechaFinStr;
	private List<ProductoMasPedido> productos=new ArrayList<ProductoMasPedido>();
	private List<ClienteMasVisitas> clientes=new ArrayList<ClienteMasVisitas>();
	private Long numClientesPorNegocio;
	private Long numUsuariosQuicko;
	private List<ClienteFestejado> clientesFestejados=new ArrayList<ClienteFestejado>();
	
	private Map<String,Integer> meses;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer mesInicio;
	private Integer mesFin;
	private List<EstadisticaProducto> listaProductos=new ArrayList<EstadisticaProducto>();
	
	@Autowired
	private GCMService gcmService;
	@Autowired
	private BitacoraVisitaService bitacoraVisitaService;
	@Autowired
	private EstadisticasService estadisticasService;
	
	@Action(value=ACTION_HAPPY_BIRTHDAYS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","clientesFestejados"})
	})
	public String obtenerCumpleanios(){
		negocio=getNegocioLogeado();
		if(negocio!=null && (idNegocio=negocio.getId())!=null){
			try {
				clientesFestejados=bitacoraVisitaService.obtenerClientesCumpleanieros(idNegocio);
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	/**
	 * Action para ir  a la pantalla de estadisticas
	 * @return
	 */
	@Action(value=ACTION_VER_ESTADISTICAS,results={
		@Result(name=SUCCESS,location=PAGE_VER_ESTADISTICAS)
	})
	public String irEstadisticas(){
		negocio=getNegocioLogeado();
		if(negocio!=null && (idNegocio=negocio.getId())!=null){
			try {
				numUsuariosQuicko=gcmService.consultarCuantosUsanQuicko();
				numClientesPorNegocio=bitacoraVisitaService.consultarNumClientesPorNegocio(idNegocio);
				productos=bitacoraVisitaService.obtenerEstadisticasProductoMasPedido(idNegocio, getFechaInicio(), getFechaFin());
				clientes=bitacoraVisitaService.obtenerListaClientesFrecuentesPaginado(idNegocio, getFechaInicio(), getFechaFin(),10);
				if(!isEmptyCollection(clientes)){
					long numVisitasTotal=0;
					for(ClienteMasVisitas c:clientes){
						int numVisitas=c.getNumVisitas();
						numVisitasTotal+=numVisitas;
					}
					for(ClienteMasVisitas c:clientes){
						int numVisitas=c.getNumVisitas();
						Float fNumVisitas=new Float(numVisitas);
						Float fTotal=new Float(numVisitasTotal);
						Float pcte=(fNumVisitas/fTotal)*100;
						int numEstrellas=(pcte.intValue() %20 > 0)?(pcte.intValue() /20 )+1:pcte.intValue() / 20;
						c.setNumEstrellas(numEstrellas);
					}
				}
				if(!isEmptyCollection(productos)){
					long numVisitasTotal=0;
					for(ProductoMasPedido p:productos){
						Integer numVisitas=(p.getNumVecesPedida()!=null)?p.getNumVecesPedida():0;
						numVisitasTotal+=numVisitas;
					}
					for(ProductoMasPedido p:productos){
						Integer numVisitas=(p.getNumVecesPedida()!=null)?p.getNumVecesPedida():0;
						Float fNumVisitas=new Float(numVisitas);
						Float fTotal=new Float(numVisitasTotal);
						Float pcte=(fNumVisitas/fTotal)*100;
						int numEstrellas=(pcte.intValue() %20 > 0)?(pcte.intValue() /20 )+1:pcte.intValue() / 20;
						p.setNumEstrellas(numEstrellas);
					}
				}
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	/**
	 * Action para ir  a la pantalla de estadisticas
	 * @return
	 */
	@Action(value=ACTION_CONSULTAR_PRODUCTOS_VENDIDOS_NEGOCIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","productos"})
	})
	public String obtenerProductosVendidos(){
		negocio=getNegocioLogeado();
		if(negocio!=null && (idNegocio=negocio.getId())!=null){
			try {
				productos=bitacoraVisitaService.obtenerEstadisticasProductoMasPedido(idNegocio, getFechaInicio(), getFechaFin());
				if(!isEmptyCollection(productos)){
					long numVisitasTotal=0;
					for(ProductoMasPedido p:productos){
						Integer numVisitas=(p.getNumVecesPedida()!=null)?p.getNumVecesPedida():0;
						numVisitasTotal+=numVisitas;
					}
					for(ProductoMasPedido p:productos){
						Integer numVisitas=(p.getNumVecesPedida()!=null)?p.getNumVecesPedida():0;
						Float fNumVisitas=new Float(numVisitas);
						Float fTotal=new Float(numVisitasTotal);
						Float pcte=(fNumVisitas/fTotal)*100;
						int numEstrellas=(pcte.intValue() %20 > 0)?(pcte.intValue() /20 )+1:pcte.intValue() / 20;
						p.setNumEstrellas(numEstrellas);
					}
				}
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_CONSULTAR_ESTADISTICAS_PRODUCTOS,results={
		@Result(name=SUCCESS,location=PAGE_VER_ESTADISTICAS_PRODUCTOS)
	})
	public String consultarEstadisticasProducto(){
		return result;
	}
	
	@Action(value=ACTION_CONSULTAR_ESTADISTICAS_PRODUCTOS_JSON,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","listaProductos"})
	})
	public String filtrarProductos(){
		Negocio negocio=getNegocioLogeado();
		idNegocio=(negocio!=null)?negocio.getId():null;
		try {
			listaProductos=estadisticasService.consultarEstadisticasProducto(idNegocio, fechaInicio, fechaFin, mesInicio, mesFin);
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	/**
	 * 	===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*
	 * 	Sets and gets
	 * ===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*===*
	 */

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public String getFechaInicioStr() {
		return fechaInicioStr;
	}

	public void setFechaInicioStr(String fechaInicioStr) {
		this.fechaInicioStr = fechaInicioStr;
	}

	public String getFechaFinStr() {
		return fechaFinStr;
	}

	public void setFechaFinStr(String fechaFinStr) {
		this.fechaFinStr = fechaFinStr;
	}

	public void setBitacoraVisitaService(BitacoraVisitaService bitacoraVisitaService) {
		this.bitacoraVisitaService = bitacoraVisitaService;
	}
	
	public Date getFechaInicio(){
		Date f=Utileria.castToDate(fechaInicioStr);
		return f;
	}
	
	public Date  getFechaFin(){
		Date f=Utileria.castToDate(fechaFinStr);
		return f;
	}

	public List<ProductoMasPedido> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoMasPedido> productos) {
		this.productos = productos;
	}

	public List<ClienteMasVisitas> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteMasVisitas> clientes) {
		this.clientes = clientes;
	}

	public Long getNumClientesPorNegocio() {
		return numClientesPorNegocio;
	}

	public void setNumClientesPorNegocio(Long numClientesPorNegocio) {
		this.numClientesPorNegocio = numClientesPorNegocio;
	}

	public List<ClienteFestejado> getClientesFestejados() {
		return clientesFestejados;
	}

	public void setClientesFestejados(List<ClienteFestejado> clientesFestejados) {
		this.clientesFestejados = clientesFestejados;
	}

	public Long getNumUsuariosQuicko() {
		return numUsuariosQuicko;
	}

	public void setNumUsuariosQuicko(Long numUsuariosQuicko) {
		this.numUsuariosQuicko = numUsuariosQuicko;
	}

	public Map<String, Integer> getMeses() {
		return meses;
	}

	public void setMeses(Map<String, Integer> meses) {
		this.meses = meses;
	}

	public Integer getMesFin() {
		return mesFin;
	}

	public void setMesFin(Integer mesFin) {
		this.mesFin = mesFin;
	}

	public List<EstadisticaProducto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<EstadisticaProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Integer mesInicio) {
		this.mesInicio = mesInicio;
	}
}
