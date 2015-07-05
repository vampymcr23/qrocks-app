package com.quuiko.dtos.mobile;

import static com.quuiko.util.Utileria.formatoDecimal;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.redondear;

import java.util.List;

import com.quuiko.beans.Cliente;
import com.quuiko.beans.PedidoIndividual;
import com.quuiko.beans.PedidoMesa;

public class PedidoMesaCuentaDTO extends JSONDTO{
	private PedidoMesa pedidoMesa;
	private List<PedidoIndividual> cuenta;
	private Double totalCuenta;
	private String totalCuentaStr;
	private List<Cliente> grupo;
	
	public PedidoMesa getPedidoMesa() {
		return pedidoMesa;
	}
	public void setPedidoMesa(PedidoMesa pedidoMesa) {
		this.pedidoMesa = pedidoMesa;
	}
	public List<PedidoIndividual> getCuenta() {
		return cuenta;
	}
	public void setCuenta(List<PedidoIndividual> cuenta) {
		this.cuenta = cuenta;
	}
	public Double getTotalCuenta() {
		return totalCuenta;
	}
	public void setTotalCuenta(Double totalCuenta) {
		this.totalCuenta = totalCuenta;
	}
	public List<Cliente> getGrupo() {
		return grupo;
	}
	public void setGrupo(List<Cliente> grupo) {
		this.grupo = grupo;
	}
	public String getTotalCuentaStr() {
		if(totalCuenta!=null){
			totalCuentaStr=formatoDecimal(totalCuenta);
		}
		return totalCuentaStr;
	}
	public void setTotalCuentaStr(String totalCuentaStr) {
		this.totalCuentaStr = totalCuentaStr;
		if(isValid(totalCuentaStr)){
			this.totalCuenta=redondear(totalCuentaStr);
		}
	}
}
