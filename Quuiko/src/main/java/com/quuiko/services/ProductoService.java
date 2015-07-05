package com.quuiko.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.exception.QRocksException;

public interface ProductoService {
	public void registrarProducto(Producto producto) throws QRocksException;
	
	public void eliminarProducto(Long idProducto) throws QRocksException;
	
	public Producto consultarProductoPorId(Long idProducto) throws QRocksException;
	
	public void inhabilitarProducto(Long idProducto) throws QRocksException;
	
	public void actualizarProducto(Producto producto) throws QRocksException;
	
	public List<Producto> consultarTodosLosProductos();
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Long  idNegocio) throws QRocksException;
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Negocio negocio) throws QRocksException;
	
	public InputStream obtenerImagenProducto(Long idProducto) throws QRocksException;
	
	public InputStream obtenerImagenProducto(Producto producto) throws QRocksException;
	
	public void subirImagenProducto(File imagen,Long idProducto) throws QRocksException;
}
