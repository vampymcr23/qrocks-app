package com.quuiko.actions;

import static com.quuiko.util.Utileria.isEmptyCollection;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.dtos.MenuProducto;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ProductoService;

@Namespace("/productos")
@ParentPackage("qrocks-default")
public class ProductoAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MesaControlAction.class);
	private final static String ACTION_MOSTRAR_CATALOGO="catalogo";
	private final static String ACTION_OBTENER_IMAGEN_PRODUCTO="imagenProducto";
	private final static String ACTION_NUEVO_PRODUCTO="producto";
	private final static String ACTION_GUARDAR_PRODUCTO="guardarProducto";
	private final static String ACTION_ELIMINAR_PRODUCTO="eliminarProducto";
	private final static String PAGINA_NUEVO_PRODUCTO="/productos/producto.jsp";
	private final static String PAGINA_CATALOGO_PRODUCTOS="/productos/catalogoProductos.jsp";
	private final static String CURRENT_NAMESPACE="/productos";
	private List<Producto> productos=new ArrayList<Producto>();
	private List<MenuProducto> menuProductos=new ArrayList<MenuProducto>();
	private InputStream stream;
	private String contentDisposition;
	private Producto producto;
	private Long idProducto;
	private File fileUploaded;
	private String contentType;
    private String filename;
	
	@Autowired
	private ProductoService productoService;
	
	@Action(value=ACTION_MOSTRAR_CATALOGO,results=@Result(name=SUCCESS,location=PAGINA_CATALOGO_PRODUCTOS))
	public String mostrarCatalogo(){
		log.warn("Cargando mis productos...");
		consultarProductos();
		log.warn("Productos cargados...respondiendo al cliente");
		return result;
	}
	
	private void consultarProductos(){
		Negocio negocioLogeado=getNegocioLogeado();
		try {
			productos=productoService.consultarTodosLosProductosPorNegocio(negocioLogeado);
			agruparMenu();
		} catch (QRocksException e) {
			log.warn(e);
			e.printStackTrace();
		}
	}
	
	private void agruparMenu(){
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
	
	@Action(value=ACTION_OBTENER_IMAGEN_PRODUCTO,
		results={
		@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerImagenProducto(){
		try {
			stream=productoService.obtenerImagenProducto(idProducto);
			String nombre="productImage.png";
			contentDisposition="attachment;filename="+nombre;
		} catch (QRocksException e) {
			log.info(e);
			System.out.println("Imposible cargar la imagen del producto.");
		}
		return result;
	}
	
	@Action(value=ACTION_NUEVO_PRODUCTO,results=@Result(name=SUCCESS,location=PAGINA_NUEVO_PRODUCTO))
	public String mostrarProducto(){
		if(producto!=null && producto.getId()!=null){
			try {
				producto=productoService.consultarProductoPorId(producto.getId());
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	public void validateGuardarProducto(){
		log.warn("Validando producto...");
		validar(producto);
		log.warn("Producto validado!");
	}
	
	@Action(value=ACTION_GUARDAR_PRODUCTO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MOSTRAR_CATALOGO,"namespace",CURRENT_NAMESPACE,"messageResult","${messageResult}","messageCode","${messageCode}"}),
		@Result(name=INPUT,location=PAGINA_NUEVO_PRODUCTO)
	})
	public String guardarProducto(){
		log.warn("Guardando producto...");
		try{
			if(producto!=null){
				if(producto.getDescripcion()!=null){
					producto.setDescripcion(producto.getDescripcion().trim());
				}
				producto.setNegocio(getNegocioLogeado());
				if(producto!=null && producto.getId()!=null){
					productoService.actualizarProducto(producto);
				}else{
					productoService.registrarProducto(producto);
				}
				log.warn("Redireccionando a mis productos...");
				onSuccessMessage("Se ha agregado \""+producto.getNombre()+"\" a su menu!");
			}
		}catch(QRocksException e){
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_ELIMINAR_PRODUCTO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MOSTRAR_CATALOGO,"namespace",CURRENT_NAMESPACE,"messageResult","${messageResult}","messageCode","${messageCode}"}),
		@Result(name=INPUT,location=ACTION_ELIMINAR_PRODUCTO)
	})
	public String inhabilitarProducto(){
		try {
			productoService.inhabilitarProducto(idProducto);
			onSuccessMessage("Su producto se ha eliminado exitosamente!");
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
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

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public File getFileUploaded() {
		return fileUploaded;
	}

	public void setFileUploaded(File fileUploaded) {
		this.fileUploaded = fileUploaded;
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

	public List<MenuProducto> getMenuProductos() {
		return menuProductos;
	}

	public void setMenuProductos(List<MenuProducto> menuProductos) {
		this.menuProductos = menuProductos;
	}
}
