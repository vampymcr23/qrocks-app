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

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.springframework.stereotype.Component;

import com.quuiko.beans.BusinessUser;
import com.quuiko.beans.ContratoDTO;
import com.quuiko.beans.ContratoView;
import com.quuiko.beans.Negocio;
import com.quuiko.dtos.MesaControl;
import com.quuiko.dtos.RestauranteUsuarioDTO;
import com.quuiko.util.Utileria.CondicionLogica;
/**
 * Clase dao para accesar a los registros de Lote
 * @author victorherrera
 *
 */
@Component
public class NegocioDAO extends DAO<Negocio,Long>{
	/**
	 * Permite filtrar los registros de la tabla de lote de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<Negocio> filtrar(Negocio filtro){
		List<Negocio> lista=new ArrayList<Negocio>();
		StringBuilder str=new StringBuilder("select n from Negocio n ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by n.nombre asc ";
		lista=(List<Negocio>)consultarPorQuery(query, Negocio.class,parametros);
		return lista;
	}
	
	public List<ContratoDTO> listar(){
		StringBuilder str=new StringBuilder("");
		str.append("select c.id as contrato,e.nombre as empleado, a.valor as cashback from contract c "); 
		str.append("inner join employee e on e.userid=c.userid ");
		str.append("inner join attribute a on a.contractid=c.id and a.version=:idVersion ");
		str.append("inner join product p on p.id=c.productId ");
		str.append("where a.nombre='cashback' ");
		Query query=em.createNativeQuery(str.toString(),"ContractDTO.consultaReporteExcel");
		List<ContratoDTO> lista=query.getResultList();
		List<String> excel=new ArrayList<String>();
		for(ContratoDTO c:lista){
			String empleado=c.getEmpleado();
			String fecha=c.getFecha();
			excel.add(empleado);
			excel.add(fecha);
		}
//		Query query=em.createNativeQuery(str.toString(),ContratoDTO.class);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Negocio filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Integer activo=filtro.getActivo();
			Integer online=filtro.getOnline();
			String usuario=filtro.getUsuario();
			String tipoComida=filtro.getTipoComida();
			if(isNotNull(id)){
				agregarCondicion(str, " n.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(activo)){
					agregarCondicion(str, " n.activo=:activo ", CondicionLogica.AND);
					parametros.put("activo",activo);
				}
				if(isNotNull(online)){
					agregarCondicion(str, " n.online=:online ", CondicionLogica.AND);
					parametros.put("online",online);
				}
				if(isValid(usuario)){
					agregarCondicion(str, " n.usuario=:usuario ", CondicionLogica.AND);
					parametros.put("usuario",usuario);
				}
				if(isValid(tipoComida)){
					agregarCondicion(str, " n.tipoComida=:tipoComida ", CondicionLogica.AND);
					parametros.put("tipoComida",tipoComida);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Negocio filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(n) from Negocio n ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by n.id desc ";
		conteo=obtenerConteoPorQuery(query, Negocio.class, parametros);
		return conteo;
	}
	
	public List<Negocio> filtrarPaginado(Negocio filtro, int inicio,int numRegistros){
		List<Negocio> lista=new ArrayList<Negocio>();
		StringBuilder str=new StringBuilder("select n from Negocio n ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by n.id desc ";
		lista=consultarPorQueryPaginado(query,Negocio.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	/**
	 * Obtiene la lista de restaurantes considerando si el usuario que lo consulta esta o no registrado.
	 * @param gcmId
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<RestauranteUsuarioDTO> consultarRestaurantesUsuario(String gcmId,String nombreRestaurante,String tipoComida,int inicio,int numRegistros){
		nombreRestaurante=(isValid(nombreRestaurante))?nombreRestaurante:null;
		List<RestauranteUsuarioDTO> negocios=new ArrayList<RestauranteUsuarioDTO>();
		Query q=em.createNamedQuery("consultarRestaurantesUsuarioRegistrado");
		q.setParameter(1, gcmId);
		if(nombreRestaurante==null){
			q.setParameter(2,null);
		}else{
			q.setParameter(2,"%"+ nombreRestaurante+"%");
		}
		if(tipoComida==null || tipoComida.isEmpty()){
			q.setParameter(3,null);
		}else{
			q.setParameter(3,tipoComida);
		}
		q.setFirstResult(inicio);
		q.setMaxResults(numRegistros);
		q.setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE);//habilitar el cache
		negocios=q.getResultList();
		return negocios;
	}
	
	public List<ContratoView> consultarVista(){
		StringBuilder str=new StringBuilder("");
		str.append("select c.id as idContrato, ");
		str.append("c.referencia as referencia, ");
		str.append("c.fecha as fechaContrato,  ");
		str.append("caCashback.valor as cashback, ");
		str.append("caDireccion.valor as direccion, ");
		str.append("caSucursal.valor as sucursal, ");
		str.append("caNomina.valor as nomina    ");
		str.append("from contrato c ");
		str.append("inner join contratoAtributo caCashback on (caCashback.idContrato=c.id) ");
		str.append("inner join atributo aCashback on (caCashback.idAtributo=aCashback.id and aCashback.nombre='cashback') ");
		str.append("inner join contratoAtributo caDireccion on (caDireccion.idContrato=c.id) ");
		str.append("inner join atributo aDireccion on (caDireccion.idAtributo=aDireccion.id and aDireccion.nombre='direccion') ");
		str.append("inner join contratoAtributo caSucursal on (caSucursal.idContrato=c.id) ");
		str.append("inner join atributo aSucursal on (caSucursal.idAtributo=aSucursal.id and aSucursal.nombre='sucursal') ");
		str.append("inner join contratoAtributo caNomina on (caNomina.idContrato=c.id) ");
		str.append("inner join atributo aNomina on (caNomina.idAtributo=aNomina.id and aNomina.nombre='nomina') ");
		String consulta=str.toString();
		Query query=em.createNativeQuery(consulta, "ContratoView.miVista");
		List<ContratoView> lista=new ArrayList<ContratoView>();
		lista=(List<ContratoView>)query.getResultList();
		return lista;
	}
	
	/**
	 * Actualiza el contexto de persistencia
	 */
	public void flushCache(){
		super.flushCache();
	}
}
