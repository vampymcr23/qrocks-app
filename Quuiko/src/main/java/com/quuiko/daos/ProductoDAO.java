package com.quuiko.daos;
import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.beans.Promocion;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class ProductoDAO extends DAO<Producto,Long>{
	public void registrarProducto(Producto producto) throws QRocksException{
		if(isNull(producto)){
			onError("Favor de proporcionar los datos del nuevo producto");
		}
		validarProducto(producto);
		producto.setActivo(1);
		crear(producto);
	}
	
	public void eliminarProducto(Long idProducto) throws QRocksException{
		if(isNull(idProducto)){
			//FIXME Mensaje
			onError("Favor de proporcionar el producto a eliminar");
		}
		eliminarPorId(Producto.class, idProducto);
	}
	
	public Producto consultarProductoPorId(Long idProducto) throws QRocksException{
		if(isNull(idProducto)){
			//FIXME Mensaje
			onError("Favor de proporcionar el producto a consultar");
		}
		Producto producto=consultarPorId(Producto.class, idProducto);
		if(isNull(producto)){
			//FIXME Mensaje
			onError("No se encontro el producto con esta clave");
		}
		return producto;
	}
	
	public void inhabilitarProducto(Long idProducto) throws QRocksException{
		if(isNull(idProducto)){
			//FIXME Mensaje
			onError("Favor de proporcionar el producto a eliminar");
		}
		Producto producto=consultarProductoPorId(idProducto);
		producto.setActivo(0);
		actualizar(producto);
	}
	
	public void actualizarProducto(Producto producto) throws QRocksException{
		if(isNull(producto)){
			onError("Favor de proporcionar los datos del producto a actualizar");
		}
		validarProducto(producto);
		actualizar(producto);
	}
	
	private void validarProducto(Producto producto) throws QRocksException{
		if(isNull(producto)){
			onError("Favor de proporcionar los datos del producto a actualizar");
		}
		if(isNull(producto.getCosto())){
			onError("Favor de proporcionar el costo del producto");
		}
		if(!isValid(producto.getNombre())){
			onError("Favor de proporcionar el nombre del producto");
		}
		if(!isValid(producto.getDescripcion())){
			onError("Favor de proporcionar una breve descripcion del producto");
		}
	}
	
	public List<Producto> consultarTodos(){
		StringBuilder str=new StringBuilder("select p from producto p ");
		Query q=em.createQuery(str.toString(),Producto.class);
		List<Producto> productos=q.getResultList();
		return productos;
	}
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Long  idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el negocio");
		}
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		return consultarTodosLosProductosPorNegocio(negocio);
	}
	
	public List<Producto> consultarTodosLosProductosPorNegocio(Negocio negocio) throws QRocksException{
		List<Producto> lista=new ArrayList<Producto>();
		if(isNull(negocio)){
			onError("Favor de proporcionar el negocio");
		}
		Producto filtro=new Producto();
		filtro.setNegocio(negocio);
		filtro.setActivo(1);
		lista=filtrar(filtro);
		return lista;
	}
	
	public List<Producto> filtrar(Producto filtro){
		List<Producto> lista=new ArrayList<Producto>();
		StringBuilder str=new StringBuilder("select p from producto p ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by p.id desc ";
		lista=(List<Producto>)consultarPorQuery(query, Producto.class,parametros);
		return lista;
	}
	
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Producto filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Integer enabled=filtro.getActivo();
			Negocio negocio =filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			String nombre=filtro.getNombre();
			if(isNotNull(id)){
				agregarCondicion(str, " p.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(enabled)){
					agregarCondicion(str, " p.activo=:enabled ", CondicionLogica.AND);
					parametros.put("enabled",enabled);
				}
				if(isValid(nombre)){
					agregarCondicion(str, " UPPER(p.nombre)=:nombre ", CondicionLogica.AND);
					parametros.put("nombre",nombre.toUpperCase());
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " p.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
			}
		}
	}
}
