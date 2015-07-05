package com.quuiko.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name="pedidoDomicilio")
@Table(name="pedidoDomicilio")
public class PedidoDomicilio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1694167566406028237L;
	
	public enum EstatusPedidoDomicilio{
		PENDIENTE("PEND"),AUTORIZADO("AUT"),RECHAZADO("RECH"),ATENDIENDO("ATN"),LISTO("OK"),CANCELADO("CAN");
		public String clave;
		private EstatusPedidoDomicilio(String clave){
			this.clave=clave;
		}
		/**
		 * Busca el estatus de reservacion en el enum por la clave de estatus
		 * @param clave
		 * @return
		 */
		public static EstatusPedidoDomicilio obtenerEstatus(String clave){
			EstatusPedidoDomicilio estatus=null;
			for(EstatusPedidoDomicilio r:EstatusPedidoDomicilio.values()){
				String e=r.clave;
				if(e.equalsIgnoreCase(clave)){
					estatus=r;
					break;
				}
			}
			return estatus;
		}
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	private Negocio negocio;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=AppUser.class)
	@JoinColumn(name="idAppUser")
	private AppUser appUser;
	
	@Column(name="estatus")
	private String estatus;
	
	@Transient
	private String estatusStr;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaRegistro")
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaEntrega")
	private Date fechaEntrega;
	
	@Column(name="total")
	private Double total;
	
	@Column(name="tipoEntrega")
	private String tipoEntrega;
	
	//Solo puede ser CR, E (credito o efectivo)
	@Column(name="formaPago")
	private String formaPago;
	
	@Column(name="cambio")
	private Double cambio;
	
	@Column(name="feria")
	private Double feria;
	
	@Column(name="notificacionEnviadaAut")
	private Integer notificacionEnviadaAutorizacion;
	
	@Column(name="notificacionEnviadaCompleto")
	private Integer notificacionEnviadaCompleto;
	
	@Column(name="tiempoEntrega")
	private String tiempoEntrega;
	
	@Column(name="comentarios")
	private String comentarios;
	
	@Column(name="comentariosRestaurante")
	private String comentariosRestaurante;
	
	@Transient
	private List<PedidoProductoDomicilio> listaProductos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Negocio getNegocio() {
		return negocio;
	}
	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getEstatusStr() {
		EstatusPedidoDomicilio estatusPedido=EstatusPedidoDomicilio.obtenerEstatus(estatus);
		if(estatusPedido!=null){
			estatusStr=estatusPedido.name();
		}
		return estatusStr;
	}
	public void setEstatusStr(String estatusStr) {
		this.estatusStr = estatusStr;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public Integer getNotificacionEnviadaAutorizacion() {
		return notificacionEnviadaAutorizacion;
	}
	public void setNotificacionEnviadaAutorizacion(
			Integer notificacionEnviadaAutorizacion) {
		this.notificacionEnviadaAutorizacion = notificacionEnviadaAutorizacion;
	}
	public Integer getNotificacionEnviadaCompleto() {
		return notificacionEnviadaCompleto;
	}
	public void setNotificacionEnviadaCompleto(Integer notificacionEnviadaCompleto) {
		this.notificacionEnviadaCompleto = notificacionEnviadaCompleto;
	}
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public Double getCambio() {
		return cambio;
	}
	public void setCambio(Double cambio) {
		this.cambio = cambio;
	}
	public Double getFeria() {
		return feria;
	}
	public void setFeria(Double feria) {
		this.feria = feria;
	}
	public String getComentariosRestaurante() {
		return comentariosRestaurante;
	}
	public void setComentariosRestaurante(String comentariosRestaurante) {
		this.comentariosRestaurante = comentariosRestaurante;
	}
	public void setListaProductos(List<PedidoProductoDomicilio> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public List<PedidoProductoDomicilio> getListaProductos() {
		return listaProductos;
	}
}
