package com.quuiko.dtos.mobile;

import com.quuiko.beans.GCMUser;

public class GCMUserDTO extends JSONDTO{
	private GCMUser usuario;
	private Integer requiereRegistro;

	public GCMUser getUsuario() {
		return usuario;
	}

	public void setUsuario(GCMUser usuario) {
		this.usuario = usuario;
	}

	public Integer getRequiereRegistro() {
		return requiereRegistro;
	}

	public void setRequiereRegistro(Integer requiereRegistro) {
		this.requiereRegistro = requiereRegistro;
	}
}
