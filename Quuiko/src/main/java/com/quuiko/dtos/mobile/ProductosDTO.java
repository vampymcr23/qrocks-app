package com.quuiko.dtos.mobile;

import java.util.List;

import com.quuiko.beans.Producto;
import com.quuiko.dtos.MenuProducto;
/**
 * Clase DTO para obtener el catalogo de los productos
 * @author victorherrera
 *
 */
public class ProductosDTO extends JSONDTO{
	private List<Producto> productos;
	
	private List<MenuProducto> menuProductos;

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<MenuProducto> getMenuProductos() {
		return menuProductos;
	}

	public void setMenuProductos(List<MenuProducto> menuProductos) {
		this.menuProductos = menuProductos;
	}
}
