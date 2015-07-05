package com.quuiko.services.impl;
import static com.quuiko.util.Utileria.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.daos.ProductoDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ProductoService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProductoServiceImpl implements ProductoService{
	private static Logger log=Logger.getLogger(ProductoServiceImpl.class);
	@Autowired
	private ProductoDAO productoDao;
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void registrarProducto(Producto producto) throws QRocksException{
		File archivo=producto.getArchivoImagen();
		if(isNotNull(archivo)){
			log.warn("Procesando Imagen...");
//			byte[] data=obtenerImagenPorArchivo(producto.getArchivoImagen());
			byte[] data=Utileria.getResizeImage(producto.getArchivoImagen());
			log.warn("Resize de imagen terminado...");
			producto.setImagen(data);
		}
		log.warn("Iniciando a guardar el producto...");
		productoDao.registrarProducto(producto);
		log.warn("Producto registrado!");
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarProducto(Long idProducto) throws QRocksException{
		productoDao.eliminarProducto(idProducto);
	}
	
	public Producto consultarProductoPorId(Long idProducto) throws QRocksException{
		return productoDao.consultarProductoPorId(idProducto);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void inhabilitarProducto(Long idProducto) throws QRocksException{
		productoDao.inhabilitarProducto(idProducto);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void actualizarProducto(Producto producto) throws QRocksException{
		File archivo=producto.getArchivoImagen();
		if(isNotNull(archivo)){
//			byte[] data=obtenerImagenPorArchivo(producto.getArchivoImagen());
			byte[] data=Utileria.getResizeImage(producto.getArchivoImagen());
			producto.setImagen(data);
		}else{
			Producto anterior=consultarProductoPorId(producto.getId());
			byte[] data=anterior.getImagen();
			producto.setImagen(data);
		}
		productoDao.actualizarProducto(producto);
	}
	
	public List<Producto> consultarTodosLosProductos(){
		return productoDao.consultarTodos();
	}
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Long  idNegocio) throws QRocksException{
		return productoDao.consultarTodosLosProductosPorNegocio(idNegocio);
	}
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Negocio negocio) throws QRocksException{
		return productoDao.consultarTodosLosProductosPorNegocio(negocio);
	}
	
	public InputStream obtenerImagenProducto(Long idProducto) throws QRocksException{
		if(isNull(idProducto)){
			//FIXME
			onError("");
		}
		Producto producto=consultarProductoPorId(idProducto);
		InputStream stream=null;
		byte[] data=producto.getImagen();
		if(data!=null){
			try {
				File tmp=File.createTempFile("_"+System.currentTimeMillis(), ".png");
				FileUtils.writeByteArrayToFile(tmp, data);
				stream=new FileInputStream(tmp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stream;
	}
	
	public InputStream obtenerImagenProducto(Producto producto) throws QRocksException{
		if(isNull(producto)){
			onError("");
		}
		Long idProducto=producto.getId();
		return obtenerImagenProducto(idProducto);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void subirImagenProducto(File imagen,Long idProducto) throws QRocksException{
		if(isNull(imagen)){
			onError("");
		}
		
		if(isNull(idProducto)){
			onError("");
		}
		try {
			byte[] data=FileUtils.readFileToByteArray(imagen);
			if(data==null){
				onError("");
			}
			Producto producto=consultarProductoPorId(idProducto);
			producto.setImagen(data);
			actualizarProducto(producto);
		} catch (IOException e) {
			onError("");
		}
		
	}
	
	private byte[] obtenerImagenPorArchivo(File archivo) throws QRocksException{
		if(isNull(archivo)){
			onError("");
		}
		byte[] data=null;
		try {
			data=FileUtils.readFileToByteArray(archivo);
			if(data==null){
				onError("");
			}
		}catch(IOException e){
			onError("");
		}
		return data;
	}

	/**
	 * =============================================
	 * 	Sets and Gets
	 * =============================================
	 */
	public void setProductoDao(ProductoDAO productoDao) {
		this.productoDao = productoDao;
	}
}
