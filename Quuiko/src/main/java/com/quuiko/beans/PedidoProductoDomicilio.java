package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="pedidoProductoDomicilio")
@Table(name="pedidoProductoDomicilio")
public class PedidoProductoDomicilio implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Producto.class)
	@JoinColumn(name="idProducto")
	private Producto producto;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=PedidoDomicilio.class)
	@JoinColumn(name="idPedido")
	private PedidoDomicilio pedido;
	
	@Column(name="costo")
	private Double costo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public PedidoDomicilio getPedido() {
		return pedido;
	}
	public void setPedido(PedidoDomicilio pedido) {
		this.pedido = pedido;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
}
