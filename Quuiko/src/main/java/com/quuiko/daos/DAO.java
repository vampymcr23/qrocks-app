package com.quuiko.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria;

/**
 * Clase general para manejo de persistencia
 * @author victorherrera
 *
 * @param <T>	Tipo de dato de la Entidad, debe de heredar de Entidad
 * @param <K>	Tipo de dato de la llave primaria de la entidad
 */
@Component
public class DAO<T,K> {
	protected static final String PU = "QRocksPU";
	
	@PersistenceContext(unitName=PU)
	protected EntityManager em;
	/**
	 * Inserta y registra la entidad en el contexto de persistencia
	 * @param objeto
	 * @throws QRocksException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=SQLException.class)
	public void crear(T objeto) throws QRocksException{
		try{
			em.persist(objeto);
		}catch(Exception e){
			e.printStackTrace();
			throw new QRocksException("pu.entitymanager.crear");
		}
	}
	/**
	 * Metodo que libera el objeto, lo quita de contexto de persistencia y lo limpia para liberar memoria
	 * @param objeto
	 */
	public void liberarObjeto(T objeto){
		em.detach(objeto);
		objeto=null;
	}
	/**
	 * Actualiza una entidad
	 * @param objeto
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=SQLException.class)
	public T actualizar(T objeto) throws QRocksException{
		if(!em.contains(objeto)){
			objeto=em.merge(objeto);
		}
		return objeto;
	}
	/**
	 * Elimina una entidad dado un objeto con el id seteado.
	 * @param objeto
	 * @throws QRocksException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=SQLException.class)
	public void eliminar(T objeto) throws QRocksException{
		try{
			em.remove(objeto);
		}catch(Exception e){
			e.printStackTrace();
			throw new QRocksException("pu.entitymanager.eliminar");
		}
	}
	/**
	 * Elimina una entidad por su id
	 * @param clase
	 * @param llave
	 * @throws QRocksException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=SQLException.class)
	public void eliminarPorId(Class<T> clase,K llave) throws QRocksException{
		try{
			T objeto=consultarPorId(clase, llave);
			eliminar(objeto);
		}catch(Exception e){
			e.printStackTrace();
			throw new QRocksException("pu.entitymanager.eliminarPorId");
		}
	}
	/**
	 * Consulta una entidad por Id
	 * @param clase Entidad que deseamos consultar
	 * @param llave Id de la entidad que deseamos consultar
	 * @return Entidad que coincide con el id proporcionado
	 * @throws QRocksException
	 */
	public T consultarPorId(Class<T> clase,K llave) throws QRocksException{
		T objeto=null;
		try{
			objeto=(T)em.find(clase,llave);
		}catch(Exception e){
			e.printStackTrace();
			throw new QRocksException("pu.entitymanager.consultarPorId");
		}
		return objeto;
	}
	/**
	 * Obtiene el listado de todos los registros de esta entidad, ordenados por su id descendentemente
	 * @param clase
	 * @return
	 */
	public List<T> consultarTodos(Class<T> clase){
		List<T> lista=null;
		EntityType<T> t=em.getMetamodel().entity(clase);
		CriteriaBuilder criteria=em.getCriteriaBuilder();
		CriteriaQuery<T> query=criteria.createQuery(clase);
		Root<T> root=query.from(clase);
		query.select(root);
		query.orderBy(criteria.desc(root.get(t.getId(clase))));
		TypedQuery<T> q=em.createQuery(query);
		q.setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE);//habilitar el cache
		lista=q.getResultList();
		return lista;
	}
	
	/**
	 * Consulta por medio de un query proporcionado, y si tiene parametros, se filtra por los parametros proporcionados
	 * @param query	Query a consultar
	 * @param clase	Clase a la que se casteara el resultado.
	 * @param parameters Parametros que se mandan en el query.
	 * @return Lista del tipo de @clase que se mando como parametro
	 */
	@SuppressWarnings("unchecked")
	public List<T> consultarPorQuery(String query,Class<T> clase,Map<String,Object> parameters){
		List<T> lista=new ArrayList<T>();
		if(Utileria.isValid(query) && Utileria.isNotNull(clase)){
			Query q=em.createQuery(query,clase);
			if(!Utileria.isEmptyMap(parameters)){
				for(String key:parameters.keySet()){
					Object value=parameters.get(key);
					if(value instanceof Date){
						Date d=(Date)value;
						q.setParameter(key, d,TemporalType.DATE);
					}else if(value instanceof Calendar){
						Calendar c=(Calendar)value;
						q.setParameter(key,c,TemporalType.DATE);	
					}else{
						q.setParameter(key, value);
					}
				}
			}
			q.setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE);//habilitar el cache
			lista=q.getResultList();
		}
		return lista;
	}
	/**
	 * Metodo que le debes de pasar un query con sus parametros y este te da un solo resultado y lo convierte en entero, este deberia de ser el num. de registros,
	 * para esto, tu query debe de tener un select count(entidad) para que funcione correctamente
	 * @param query
	 * @param clase
	 * @param parameters
	 * @return
	 */
	public int obtenerConteoPorQuery(String query,Class<T> clase,Map<String,Object> parameters){
		int conteo=0;
		if(Utileria.isValid(query) && Utileria.isNotNull(clase)){
			Query q=em.createQuery(query,clase);
			if(!Utileria.isEmptyMap(parameters)){
				for(String key:parameters.keySet()){
					Object value=parameters.get(key);
					if(value instanceof Date){
						Date d=(Date)value;
						q.setParameter(key, d,TemporalType.DATE);
					}else if(value instanceof Calendar){
						Calendar c=(Calendar)value;
						q.setParameter(key,c,TemporalType.DATE);	
					}else{
						q.setParameter(key, value);
					}
				}
			}
			q.setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE);//habilitar el cache
			Long records=(Long)q.getSingleResult();
			conteo=(records!=null)?records.intValue():0;
		}
		return conteo;
	}
	
	/**
	 * Consulta por medio de un query proporcionado, y si tiene parametros, se filtra por los parametros proporcionados
	 * @param query	Query a consultar
	 * @param clase	Clase a la que se casteara el resultado.
	 * @param parameters Parametros que se mandan en el query.
	 * @param inicio numero de registro en el cual va a iniciar
	 * @param numRegistros Cantidad de registros que se van a mostrar
	 * @return Lista del tipo de @clase que se mando como parametro
	 */
	@SuppressWarnings("unchecked")
	public List<T> consultarPorQueryPaginado(String query,Class<T> clase,Map<String,Object> parameters,int inicio,int numRegistros){
		List<T> lista=new ArrayList<T>();
		if(Utileria.isValid(query) && Utileria.isNotNull(clase)){
			Query q=em.createQuery(query,clase);
			if(!Utileria.isEmptyMap(parameters)){
				for(String key:parameters.keySet()){
					Object value=parameters.get(key);
					q.setParameter(key, value);
				}
			}
			q.setFirstResult(inicio);
			q.setMaxResults(numRegistros);
			q.setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE);//habilitar el cache
			lista=q.getResultList();
		}
		return lista;
	}
	
	public void flushCache(){
		em.getEntityManagerFactory().getCache().evictAll();
	}
	
	public void refresh(T objeto){
		em.refresh(objeto);
	}
	
	public T merge(T objeto){
		return em.merge(objeto);
	}
}
