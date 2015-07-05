package com.quuiko.daos;

import static com.quuiko.util.Utileria.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Mesa;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class MesaDAO extends DAO<Mesa, Long>{
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<Mesa> filtrar(Mesa filtro){
		List<Mesa> lista=new ArrayList<Mesa>();
		StringBuilder str=new StringBuilder("select m from Mesa m ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.id desc ";
		lista=(List<Mesa>)consultarPorQuery(query, Mesa.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Mesa filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String claveMesa=filtro.getClaveMesa();
			Negocio negocio=filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " m.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " m.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
				if(isValid(claveMesa)){
					agregarCondicion(str, " m.claveMesa like :claveMesa ", CondicionLogica.AND);
					parametros.put("claveMesa","%"+claveMesa+"%");
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesa filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(m) from Mesa m ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.id desc ";
		conteo=obtenerConteoPorQuery(query, Mesa.class, parametros);
		return conteo;
	}
	
	/**
	 * Obtiene la lista de mesas de acuerdo a ciertos filtros
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesa> filtrarPaginado(Mesa filtro, int inicio,int numRegistros){
		List<Mesa> lista=new ArrayList<Mesa>();
		StringBuilder str=new StringBuilder("select m from Mesa m ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.id desc ";
		lista=consultarPorQueryPaginado(query,Mesa.class,parametros,inicio, numRegistros);
		return lista;
	}
	/**
	 * Obtiene la lista de mesas por negocio
	 * @param negocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Negocio negocio) throws QRocksException{
		List<Mesa> mesas=new ArrayList<Mesa>();
		if(isNull(negocio) || isNull(negocio.getId())){
			onError("error.negocio.null");
		}
		Mesa filtro=new Mesa();
		filtro.setNegocio(negocio);
		mesas=filtrar(filtro);
		return mesas;
	}
	/**
	 * Obtiene la lista de mesas por negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Long idNegocio) throws QRocksException{
		List<Mesa> mesas=new ArrayList<Mesa>();
		if(isNull(idNegocio)){
			onError("error.negocio.null");
		}
		Mesa filtro=new Mesa();
		Negocio n=new Negocio();
		n.setId(idNegocio);
		filtro.setNegocio(n);
		mesas=obtenerMesasPorNegocio(n);
		return mesas;
	}
	
	/**
	 * GUarda o actualiza los datos de una mesa
	 * @param mesa
	 * @throws QRocksException
	 */
	public void guardarMesa(Mesa mesa) throws QRocksException{
		if(isNull(mesa)){
			onError("error.mesa.null");
		}
		Long idMesa=mesa.getId();
		if(isNull(idMesa)){
			crear(mesa);
		}else{
			actualizar(mesa);
		}
	}
	/**
	 * Elimina una mesa por su id
	 * @param idMesa
	 * @throws QRocksException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=SQLException.class)
	public void eliminarMesa(Long idMesa) throws QRocksException{
		if(isNull(idMesa)){
			onError("error.mesa.null");
		}
		eliminarPorId(Mesa.class, idMesa);
	}
	/**
	 * Elimina una mesa por su id
	 * @param mesa
	 * @throws QRocksException
	 */
	public void eliminarMesa(Mesa mesa) throws QRocksException{
		if(isNull(mesa)){
			onError("error.mesa.null");
		}
		eliminar(mesa);
	}
	/**
	 * Consultar por id
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorId(Long idMesa) throws QRocksException{
		if(isNull(idMesa)){
			onError("error.mesa.null");
		}
		return consultarPorId(Mesa.class,idMesa);
	}
	
	/**
	 * Obtiene la mesa por la clave de la mesa
	 * @param claveMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorClave(String claveMesa) throws QRocksException{
		if(!isValid(claveMesa)){
			onError("Favor de proporcionar la clave de la mesa");
		}
		Mesa filtro=new Mesa();
		filtro.setClaveMesa(claveMesa);
		List<Mesa> mesas=filtrar(filtro);
		Mesa mesa=(!isEmptyCollection(mesas))?mesas.get(0):null;
		if(isNull(mesa)){
			onError("No existe la mesa con la clave "+claveMesa);
		}
		return mesa;
	}
}
