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

import com.quuiko.beans.Mesero;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class MeseroDAO extends DAO<Mesero, Long>{
	
	public List<Mesero> filtrar(Mesero filtro){
		List<Mesero> lista=new ArrayList<Mesero>();
		StringBuilder str=new StringBuilder("select m from mesero m ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.nombre asc ";
		lista=(List<Mesero>)consultarPorQuery(query, Mesero.class,parametros);
		return lista;
	}
	
	public List<Mesero> listarMeserosPorNegocio(Long idNegocio) throws QRocksException{
		List<Mesero> lista=new ArrayList<Mesero>();
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el negocio");
		}
		Mesero filtro=new Mesero();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		lista=filtrar(filtro);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Mesero filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String nombre=filtro.getNombre();
			Negocio negocio=filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " m.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(nombre)){
					agregarCondicion(str, " m.nombre like :nombre ", CondicionLogica.AND);
					parametros.put("nombre","%"+nombre+"%");
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " m.negocio.id=:idNegocio", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesero filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(m) from mesero m ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.nombre asc ";
		conteo=obtenerConteoPorQuery(query, Mesero.class, parametros);
		return conteo;
	}
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesero> filtrarPaginado(Mesero filtro, int inicio,int numRegistros){
		List<Mesero> lista=new ArrayList<Mesero>();
		StringBuilder str=new StringBuilder("select m from mesero c ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by m.nombre asc ";
		lista=consultarPorQueryPaginado(query,Mesero.class,parametros,inicio, numRegistros);
		return lista;
	}
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	public Mesero agregarMesero(Mesero mesero) throws QRocksException{
		//FIXME Mensaje
		if(isNull(mesero) || !isValid(mesero.getNombre())){
			onError("Favor de proporcionar el nombre del invitado");
		}
		if(isNull(mesero.getNegocio()) || isNull(mesero.getNegocio().getId())){
			onError("Favor de proporcionar el negocio al que pertenece el mesero");
		}
		crear(mesero);
		return mesero;
	}
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	public void actualizarMesero(Mesero mesero) throws QRocksException{
		//FIXME Mensaje
		if(isNull(mesero)){
			onError("Favor de proporcionar el nombre del invitado");
		}
		actualizar(mesero);
	}
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	public void eliminarMesero(Mesero mesero) throws QRocksException{
		//FIXME Mensaje
		if(isNull(mesero) || isNull(mesero.getId())){
			onError("Favor de proporcionar el mesero a eliminar");
		}
		eliminarMesero(mesero.getId());
	}
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	public void eliminarMesero(Long idMesero) throws QRocksException{
		//FIXME Mensaje
		if(isNull(idMesero)){
			onError("");
		}
		eliminarPorId(Mesero.class,idMesero);
	}
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Mesero consultarMesero(Long idMesero) throws QRocksException{
		//FIXME Mensaje
		if(isNull(idMesero)){
			onError("Favor de proporcionar el mesero");
		}
		Mesero mesero=consultarPorId(Mesero.class, idMesero);
		//FIXME Mensaje
		if(isNull(mesero)){
			onError("No se encontro el mesero con la clave:"+idMesero);
		}
		return mesero;
	}
}
