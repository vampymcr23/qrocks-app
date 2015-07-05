package com.quuiko.dtos.mobile;

import java.util.List;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.PedidoIndividual;

public class PedidosClienteDTO extends JSONDTO{
	private Cliente cliente;
	private List<PedidoIndividual> pedidos;
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<PedidoIndividual> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<PedidoIndividual> pedidos) {
		this.pedidos = pedidos;
	}
}
