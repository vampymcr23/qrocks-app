package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.PedidoProductoDomicilio;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class PedidoProductoDomicilioDAO extends DAO<PedidoProductoDomicilio,Long>{
	
	public void guardar(PedidoProductoDomicilio pedido) throws QRocksException{
		if(isNull(pedido)){
			onError("Favor de proporcionar su pedido");
		}
		if(pedido.getId()==null){
			crear(pedido);
		}else{
			actualizar(pedido);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("Favor de proporcionar el producto a eliminar");
		}
		eliminarPorId(PedidoProductoDomicilio.class, idPedido);
	}
	
	public PedidoProductoDomicilio consultarPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("Favor de proporcionar el producto a eliminar");
		}
		PedidoProductoDomicilio pedido=consultarPorId(PedidoProductoDomicilio.class, idPedido);
		return pedido;
	}
	
	public List<PedidoProductoDomicilio> filtrar(PedidoProductoDomicilio filtro){
		List<PedidoProductoDomicilio> lista=new ArrayList<PedidoProductoDomicilio>();
		StringBuilder str=new StringBuilder("select pp from pedidoProductoDomicilio pp join fetch pp.pedido ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pp.producto.nombre asc ";
		lista=(List<PedidoProductoDomicilio>)consultarPorQuery(query, PedidoProductoDomicilio.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,PedidoProductoDomicilio filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			PedidoDomicilio pedido=filtro.getPedido();
			Long idPedido=(pedido!=null)?pedido.getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " pp.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(idPedido)){
					agregarCondicion(str, " pp.pedido.id=:idPedido ", CondicionLogica.AND);
					parametros.put("idPedido",idPedido);
				}
			}
		}
	}
}
