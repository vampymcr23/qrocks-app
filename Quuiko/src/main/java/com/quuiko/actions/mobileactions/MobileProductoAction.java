package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.isEmptyCollection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Producto;
import com.quuiko.dtos.MenuProducto;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.ProductosDTO;
import com.quuiko.services.ProductoService;
import com.quuiko.util.Utileria;

@Namespace("/m/droid/productos")
@ParentPackage("qrocks-default")
public class MobileProductoAction extends QRocksAction{
	/**
	 * 
	 */
	private static Logger log=Logger.getLogger(MobileProductoAction.class.getCanonicalName());
	private static final long serialVersionUID = 1L;
	public final static String JSON_ACTION_CATALOGO_PRODUCTOS="getAllProducts";
	public final static String JSON_ACTION_IMAGEN_PRODUCTO="getImg";
	private ProductosDTO productosDTO;
	private String contentType;
    private String filename;
    private InputStream inputStream;
	private String contentDisposition;
	private Long idProducto;
	private Long idNegocio;
	@Autowired
	private ProductoService productoService;
	
	@Action(value=JSON_ACTION_CATALOGO_PRODUCTOS,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","productosDTO"})
	})
	public String obtenerCatalogoProductos(){
		List<Producto> productos;
		List<MenuProducto> menuProductos=new ArrayList<MenuProducto>();
		try {
			productos = productoService.consultarTodosLosProductosPorNegocio(idNegocio);
			agruparMenu(menuProductos, productos);
			productosDTO=new ProductosDTO();
			if(!Utileria.isEmptyCollection(menuProductos)){
				for(MenuProducto m:menuProductos){
					List<Producto> listaProductos=m.getProductos();
					if(!Utileria.isEmptyCollection(listaProductos)){
						for(Producto p:listaProductos){
							p.setImagen(null);
							if(p.getNegocio()!=null){
								p.getNegocio().setImagen(null);
							}
						}
					}
				}
			}
//			productosDTO.setProductos(productos);
			productosDTO.setMenuProductos(menuProductos);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, productosDTO);
		}
		return result;
	}
	
	private void agruparMenu(List<MenuProducto> menuProductos,List<Producto> productos){
		if(!isEmptyCollection(productos)){
			for(Producto p:productos){
				String tipoProducto=p.getTipoProducto();
				//Si la lista de menu esta vacia, entonces se agrega la primera categoria
				if(menuProductos!=null && isEmptyCollection(menuProductos)){
					MenuProducto categoria=new MenuProducto();
					categoria.setCategoria(tipoProducto);
					List<Producto> productosCategoria=categoria.getProductos();
					if(productosCategoria==null){
						productosCategoria=new ArrayList<Producto>();
					}
					productosCategoria.add(p);
					categoria.setProductos(productosCategoria);
					menuProductos.add(categoria);
				}else if(menuProductos!=null && !isEmptyCollection(menuProductos)){
					boolean existeCategoria=false;
					//Se busca si ya existe la categoria, si si, entonces se agrega el producto a la lista de esta categoria
					for(MenuProducto m:menuProductos){
						String categoria=m.getCategoria();
						if(categoria.equalsIgnoreCase(tipoProducto)){
							List<Producto> productosCategoria=m.getProductos();
							if(productosCategoria==null){
								productosCategoria=new ArrayList<Producto>();
							}
							m.getProductos().add(p);
							existeCategoria=true;
							break;
						}
					}
					if(!existeCategoria){
						MenuProducto categoria=new MenuProducto();
						categoria.setCategoria(tipoProducto);
						List<Producto> productosCategoria=categoria.getProductos();
						if(productosCategoria==null){
							productosCategoria=new ArrayList<Producto>();
						}
						productosCategoria.add(p);
						categoria.setProductos(productosCategoria);
						menuProductos.add(categoria);
					}
				}
				
			}
		}
	}
	
	
	@Action(value=JSON_ACTION_IMAGEN_PRODUCTO,results={
			@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","inputStream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerImagenProducto(){
		try {
			inputStream=productoService.obtenerImagenProducto(idProducto);
			String nombre="productImage.png";
			System.out.println("Imagen producto:"+idProducto);
			System.out.println("Stream:"+inputStream);
			contentDisposition="attachment;filename="+nombre;
		} catch (QRocksException e) {
			log.log(Level.WARNING,"No se logro cargar la imagen del producto:"+idProducto);
		}
		return result;
	}
	
	/**
	 * ==================================================
	 * Sets and Gets 
	 * ==================================================
	 */

	public ProductosDTO getProductosDTO() {
		return productosDTO;
	}

	public void setProductosDTO(ProductosDTO productosDTO) {
		this.productosDTO = productosDTO;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}
}
