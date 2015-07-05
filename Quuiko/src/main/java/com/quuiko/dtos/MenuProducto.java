package com.quuiko.dtos;

import java.io.Serializable;
import java.util.List;

import com.quuiko.beans.Producto;

/**
 * DTO Para mostrar los productos agrupados por categoria
 * @author victorherrera
 *
 */
public class MenuProducto implements Serializable{
	private List<Producto> productos;
	private String categoria;
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
