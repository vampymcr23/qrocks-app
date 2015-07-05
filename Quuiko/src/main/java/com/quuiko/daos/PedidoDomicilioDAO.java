package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.AppUser;
import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.PedidoDomicilio.EstatusPedidoDomicilio;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class PedidoDomicilioDAO extends DAO<PedidoDomicilio,Long>{
	
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void guardar(PedidoDomicilio pedido) throws QRocksException{
		if(isNull(pedido)){
			onError("Favor de proporcionar el pedido a guardar");
		}
		Long id=pedido.getId();
		if(isNull(id)){
			pedido.setEstatus(EstatusPedidoDomicilio.PENDIENTE.clave);
			pedido.setFechaRegistro(new Date());
			pedido.setTotal(0d);
			crear(pedido);
		}else{
			actualizar(pedido);
		}
	}
	
	public PedidoDomicilio consultarPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("Favor de proporcionar el folio del pedido que desea consultar");
		}
		PedidoDomicilio pedido=null;
		pedido=consultarPorId(PedidoDomicilio.class,idPedido);
		return pedido;
	}
	
	/**
	 * Cancela un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void cancelarPedido(Long idPedido) throws QRocksException{
		if(isNull(idPedido)){
			onError("Favor de proporcionar el folio del pedido que desea consultar");
		}
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("El folio que usted proporciono no existe");
		}
		pedido.setEstatus(EstatusPedidoDomicilio.CANCELADO.clave);
		actualizar(pedido);
	}
	
	public List<PedidoDomicilio> filtrar(PedidoDomicilio filtro){
		List<PedidoDomicilio> lista=new ArrayList<PedidoDomicilio>();
		StringBuilder str=new StringBuilder("select pd from pedidoDomicilio pd join fetch pd.negocio ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by pd.id desc ";
		lista=(List<PedidoDomicilio>)consultarPorQuery(query, PedidoDomicilio.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,PedidoDomicilio filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Negocio negocio=filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			AppUser usuario= filtro.getAppUser();
			Long idUsuario=(usuario!=null)?usuario.getId():null;
			String nombreUsuario=(usuario!=null)?usuario.getNombre():null;
			String estatus=filtro.getEstatus();
			if(isNotNull(id)){
				agregarCondicion(str, " pd.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(estatus)){
					agregarCondicion(str, " pd.estatus = :estatus ", CondicionLogica.AND);
					parametros.put("estatus",estatus);
				}
				if(isNotNull(idUsuario)){
					agregarCondicion(str, " pd.appUser.id= :idUsuario ", CondicionLogica.AND);
					parametros.put("idUsuario",idUsuario);
				}
				if(isValid(nombreUsuario)){
					agregarCondicion(str, " pd.appUser.nombre like :nombreUsuario ", CondicionLogica.AND);
					parametros.put("nombreUsuario","%"+nombreUsuario+"%");
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " pd.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
			}
		}
	}
}
