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

import com.quuiko.beans.ContratoNegocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class ContratoNegocioDAO extends DAO<ContratoNegocio,Long>{
	
	/**
	 * GUarda o actualiza un contrato
	 * @param contrato
	 * @throws QRocksException
	 */
	public void guardarContrato(ContratoNegocio contrato) throws QRocksException{
		if(isNull(contrato)){
			onError("Favor de proporcionar el contrato a guardar");
		}
		Long id=contrato.getId();
		if(isNull(id)){
			contrato.setActivo(1);
			contrato.setFechaRegistro(new Date());
			crear(contrato);
		}else{
			actualizar(contrato);
		}
	}
	
	/**
	 * Consulta un contrato por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public ContratoNegocio consultarContrato(Long id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar el contrato a consultar");
		}
		ContratoNegocio contrato=consultarPorId(ContratoNegocio.class, id);
		return contrato;
	}
	
	/**
	 * Habilita - deshabilita un contrato
	 * @param id
	 * @throws QRocksException
	 */
	public void habilitarDeshabilitarContrato(Long id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar el contrato a deshabilitar");
		}
		ContratoNegocio contrato=consultarContrato(id);
		if(isNull(contrato)){
			onError("El contrato con el folio "+id+" no existe");
		}
		Integer activo=contrato.getActivo();
		if(activo!=null && activo.intValue()==1){
			contrato.setActivo(0);
		}else if(activo!=null && activo.intValue()==0){
			contrato.setActivo(1);
		}
		actualizar(contrato);
	}
	
	/**
	 * Permite filtrar los registros de la tabla de lote de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<ContratoNegocio> filtrar(ContratoNegocio filtro){
		List<ContratoNegocio> lista=new ArrayList<ContratoNegocio>();
		StringBuilder str=new StringBuilder("select c from contratoNegocio c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		lista=(List<ContratoNegocio>)consultarPorQuery(query, ContratoNegocio.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,ContratoNegocio filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Integer activo=filtro.getActivo();
			String nombreNegocio=filtro.getNombreNegocio();
			String nombreContacto=filtro.getNombreContacto();
			String numTarjeta=filtro.getNumTarjetaCargo();
			if(isNotNull(id)){
				agregarCondicion(str, " c.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(activo)){
					agregarCondicion(str, " c.activo=:activo ", CondicionLogica.AND);
					parametros.put("activo",activo);
				}
				if(isValid(nombreNegocio)){
					agregarCondicion(str, " UPPER(c.nombreNegocio) like :nombreNegocio ", CondicionLogica.AND);
					parametros.put("nombreNegocio","%"+nombreNegocio.toUpperCase()+"%");
				}
				if(isValid(nombreContacto)){
					agregarCondicion(str, " UPPER(c.nombreContacto) like :nombreContacto ", CondicionLogica.AND);
					parametros.put("nombreContacto","%"+nombreContacto.toUpperCase()+"%");
				}
				if(isValid(numTarjeta)){
					agregarCondicion(str, " c.numTarjeta like :numTarjeta ", CondicionLogica.AND);
					parametros.put("numTarjeta","%"+numTarjeta+"%");
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(ContratoNegocio filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(c) from contratoNegocio c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		conteo=obtenerConteoPorQuery(query, ContratoNegocio.class, parametros);
		return conteo;
	}
	
	/**
	 * Filtra los resultados de forma paginada
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<ContratoNegocio> filtrarPaginado(ContratoNegocio filtro, int inicio,int numRegistros){
		List<ContratoNegocio> lista=new ArrayList<ContratoNegocio>();
		StringBuilder str=new StringBuilder("select c from contratoNegocio c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by c.id desc ";
		lista=consultarPorQueryPaginado(query,ContratoNegocio.class,parametros,inicio, numRegistros);
		return lista;
	}
}
