 package com.quuiko.dtos.mobile;

import java.util.List;

import com.quuiko.beans.Promocion;

public class PromocionesDTO extends JSONDTO{
	public List<Promocion> promociones;

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}
}
