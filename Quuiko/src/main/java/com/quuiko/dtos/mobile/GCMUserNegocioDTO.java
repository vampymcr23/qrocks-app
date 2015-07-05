package com.quuiko.dtos.mobile;

import com.quuiko.beans.GCMUserNegocio;

public class GCMUserNegocioDTO extends JSONDTO{
	private GCMUserNegocio usuarioNegocio;

	public GCMUserNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(GCMUserNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}
}
