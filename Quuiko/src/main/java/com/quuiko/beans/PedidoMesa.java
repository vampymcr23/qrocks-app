package com.quuiko.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;
import com.quuiko.util.Utileria;

@Entity(name="pedidoMesa")
@Table(name="pedidoMesa")
@Cache(
	type=CacheType.SOFT,//se genera cache hasta que el limite de la memoria sea baja
	isolation=CacheIsolationType.PROTECTED,//Protegida para que las relaciones que no sean Cacheables puedan establecerse correctamente
	expiry=QRocksCache.MIDDLE_MINUTE,
	size=5000//num de objetos a almacenar en cache
)
public class PedidoMesa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2998912178241229327L;

	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Mesa.class)
	@JoinColumn(name="idMesa")
	private Mesa mesa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="activo")
	private Integer activo;
	
	@Transient
	private String fechaStr;
	
	@Column(name="llamarMesero")
	private Integer llamarMesero;
	
	@Column(name="solicitarCuenta")
	private Integer solicitarCuenta;
	
	@Column(name="solicitarNuevoPedido")
	private Integer solicitarNuevoPedido;//Este campo siempre sera 1 o 0, cuando el usuario presione enviar pedido, se pondra 1, cuando en la mesa de control vean el pedido, se apagara a 0.
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaSeleccionVideo",nullable=true)
	private Date fechaSeleccionVideo;//Es el campo para determinar si puede elegir o no otro video
	
	@Transient
	private Integer puedeSeleccionarVideo;//bandera que decide si una mesa puede seguir seleccionando video o todavia no.
	
	@Transient
	private Integer tiempoEspera;//Tiempo de espera para elegir un nuevo video
	
	/**
	 * ==================================================
	 * 	Sets y Gets
	 * ==================================================
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Mesa getMesa() {
		return mesa;
	}
	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public String getFechaStr() {
		Date f=(fecha!=null)?fecha:new Date();
		fechaStr=Utileria.parseDateToString(f);
		return fechaStr;
	}
	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
	}
	public Integer getLlamarMesero() {
		return llamarMesero;
	}
	public void setLlamarMesero(Integer llamarMesero) {
		this.llamarMesero = llamarMesero;
	}
	public Integer getSolicitarCuenta() {
		return solicitarCuenta;
	}
	public void setSolicitarCuenta(Integer solicitarCuenta) {
		this.solicitarCuenta = solicitarCuenta;
	}
	public Integer getSolicitarNuevoPedido() {
		return solicitarNuevoPedido;
	}
	public void setSolicitarNuevoPedido(Integer solicitarNuevoPedido) {
		this.solicitarNuevoPedido = solicitarNuevoPedido;
	}
	public Date getFechaSeleccionVideo() {
		return fechaSeleccionVideo;
	}
	public void setFechaSeleccionVideo(Date fechaSeleccionVideo) {
		this.fechaSeleccionVideo = fechaSeleccionVideo;
	}
	public Integer getPuedeSeleccionarVideo() {
//		if(fechaSeleccionVideo==null){
//			puedeSeleccionarVideo=1;
//		}else{
//			Calendar c=Calendar.getInstance();
//			c.setTime(fechaSeleccionVideo);
//			Date fechaActual=new Date();
//			long miliSecondsFechaSeleccion=c.getTimeInMillis();
//			long miliSecondsFechaActual=fechaActual.getTime();
//			long diferencia=miliSecondsFechaActual-miliSecondsFechaSeleccion;
//			long numSegundosDiferencia=(diferencia/1000L);
//			//Si el num. de segundos es mayor a los segundos de la configuracion, entonces ya se puede agregar.
//			if(numSegundosDiferencia>=MobileMediaplayerAction.NUM_SEGUNDOS_CONFIGURACION){
//				puedeSeleccionarVideo=1;
//				tiempoEspera=0;
//			}else{
//				puedeSeleccionarVideo=0;
//				tiempoEspera=Integer.parseInt(String.valueOf(numSegundosDiferencia));
//			}
//			
//		}
		return puedeSeleccionarVideo;
	}
	public void setPuedeSeleccionarVideo(Integer puedeSeleccionarVideo) {
		this.puedeSeleccionarVideo = puedeSeleccionarVideo;
	}
	public Integer getTiempoEspera() {
		return tiempoEspera;
	}
	public void setTiempoEspera(Integer tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
}
