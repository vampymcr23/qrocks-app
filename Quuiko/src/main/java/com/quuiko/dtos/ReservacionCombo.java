package com.quuiko.dtos;

import java.io.Serializable;

public class ReservacionCombo implements Serializable{
	private String etiqueta;
	private String valor;
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
