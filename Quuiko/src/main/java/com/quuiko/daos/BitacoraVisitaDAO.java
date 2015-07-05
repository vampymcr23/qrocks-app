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

import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.quuiko.beans.BitacoraVisita;
import com.quuiko.dtos.estadisticas.ClienteFestejado;
import com.quuiko.dtos.estadisticas.ClienteMasVisitas;
import com.quuiko.dtos.estadisticas.ProductoMasPedido;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

public class BitacoraVisitaDAO extends DAO<BitacoraVisita,Long>{
	
	public List<BitacoraVisita> filtrar(BitacoraVisita filtro){
		List<BitacoraVisita> lista=new ArrayList<BitacoraVisita>();
		StringBuilder str=new StringBuilder("select b from bitacoraVisita b join fetch b.user join fetch b.negocio join fetch b.appUser ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by b.id desc ";
		lista=(List<BitacoraVisita>)consultarPorQuery(query, BitacoraVisita.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,BitacoraVisita filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String gcmId=(filtro.getUser()!=null)?filtro.getUser().getGcmId():null;
			String userEmail=(filtro.getAppUser()!=null)?filtro.getAppUser().getUserEmail():null;
			Long idNegocio=(filtro.getNegocio()!=null)?filtro.getNegocio().getId():null;
			Date fechaInicio=filtro.getFechaVisita();
			Date fechaFin=filtro.getFechaVisitaFin();
			if(isNotNull(id)){
				agregarCondicion(str, " b.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(gcmId)){
					agregarCondicion(str, " b.user.gcmId=:gcmId ", CondicionLogica.AND);
					parametros.put("gcmId",gcmId);
				}
				if(isValid(userEmail)){
					agregarCondicion(str, " b.appUser.userEmail=:userEmail ", CondicionLogica.AND);
					parametros.put("userEmail",userEmail);
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " b.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
				if(isNotNull(fechaInicio)){
					agregarCondicion(str, " b.fechaVisita >= :fechaInicio ", CondicionLogica.AND);
					parametros.put("fechaInicio",fechaInicio);
				}
				
				if(isNotNull(fechaFin)){
					agregarCondicion(str, " b.fechaVisita <= :fechaFin ", CondicionLogica.AND);
					parametros.put("fechaFin",fechaFin);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(BitacoraVisita filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(b) from bitacoraVisita b ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by n.id desc ";
		conteo=obtenerConteoPorQuery(query, BitacoraVisita.class, parametros);
		return conteo;
	}
	
	public List<BitacoraVisita> filtrarPaginado(BitacoraVisita filtro, int inicio,int numRegistros){
		List<BitacoraVisita> lista=new ArrayList<BitacoraVisita>();
		StringBuilder str=new StringBuilder("select b from bitacoraVisita b ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by b.id desc ";
		lista=consultarPorQueryPaginado(query,BitacoraVisita.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	/**
	 * Obtiene el numero de clientes que usan la aplicacion y que estan ligados a este restaurante
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public Long consultarNumClientesPorNegocio(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		Long numClientes=null;
		Query q=em.createNamedQuery("numClientesPorNegocio",Long.class);
		q.setParameter(1, idNegocio);
		numClientes=(Long) q.getSingleResult();
		return numClientes;
	}
	
	/**
	 * Obtiene la lista de productos mas pedidos por negocio
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ProductoMasPedido> obtenerEstadisticasProductoMasPedido(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException{
		List<ProductoMasPedido> productos=new ArrayList<ProductoMasPedido>();
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		Query q=em.createNamedQuery("productoMasPedido",ProductoMasPedido.class);
		q.setParameter(1, idNegocio);
		q.setParameter(2, fechaInicio,TemporalType.DATE);
		q.setParameter(3, fechaFin,TemporalType.DATE);
		productos=q.getResultList();
		return productos;
	}
	
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentes(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException{
		List<ClienteMasVisitas> clientes=new ArrayList<ClienteMasVisitas>();
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		Query q=em.createNamedQuery("clienteMasVisitas",ClienteMasVisitas.class);
		q.setParameter(1, idNegocio);
		q.setParameter(2, fechaInicio,TemporalType.DATE);
		q.setParameter(3, fechaFin,TemporalType.DATE);
		clientes=q.getResultList();
		return clientes;
	}
	
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentesPaginado(Long idNegocio, Date fechaInicio, Date fechaFin, int numRegistrosLimite) throws QRocksException{
		List<ClienteMasVisitas> clientes=new ArrayList<ClienteMasVisitas>();
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		Query q=em.createNamedQuery("clienteMasVisitas",ClienteMasVisitas.class);
		q.setParameter(1, idNegocio);
		q.setParameter(2, fechaInicio,TemporalType.DATE);
		q.setParameter(3, fechaFin,TemporalType.DATE);
		q.setMaxResults(numRegistrosLimite);
		clientes=q.getResultList();
		return clientes;
	}
	
	/**
	 * Obtiene la lista de cumpleanieros de la fecha actual
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteFestejado> obtenerClientesCumpleanieros(Long idNegocio) throws QRocksException{
		List<ClienteFestejado> clientesCumpleanieros=new ArrayList<ClienteFestejado>();
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		Query q=em.createNamedQuery("clientesCumplanieros",ClienteFestejado.class);
		q.setParameter(1, idNegocio);
		clientesCumpleanieros=q.getResultList();
		return clientesCumpleanieros;
	}
}
