package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="pedidoIndividual")
@Table(name="pedidoIndividual")
public class PedidoIndividual implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -256225707528667774L;

	@Id
	private Long id;
	
	@JoinColumn(name="idProducto")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Producto.class)
	private Producto producto;
	
	@JoinColumn(name="idCliente")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Cliente.class)
	private Cliente cliente;
	
	@Column(name="pagado")
	private Integer pagado;
	
	@Column(name="atendido")
	private Integer atendido;

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getPagado() {
		return pagado;
	}

	public void setPagado(Integer pagado) {
		this.pagado = pagado;
	}

	public Integer getAtendido() {
		return atendido;
	}

	public void setAtendido(Integer atendido) {
		this.atendido = atendido;
	}
}