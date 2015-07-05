package com.quuiko.dtos.mobile;

import java.util.List;

import com.quuiko.beans.Negocio;
import com.quuiko.dtos.RestauranteUsuarioDTO;

public class NegociosDTO extends JSONDTO{
	private List<Negocio> negocios;
	private List<RestauranteUsuarioDTO> restaurantes;

	public List<Negocio> getNegocios() {
		return negocios;
	}

	public void setNegocios(List<Negocio> negocios) {
		this.negocios = negocios;
	}

	public List<RestauranteUsuarioDTO> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(List<RestauranteUsuarioDTO> restaurantes) {
		this.restaurantes = restaurantes;
	}
}
