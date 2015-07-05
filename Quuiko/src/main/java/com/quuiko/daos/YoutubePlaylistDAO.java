package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class YoutubePlaylistDAO extends DAO<YoutubePlaylist, Long>{
	
	public void guardarPlaylist(YoutubePlaylist playlist) throws QRocksException{
		if(playlist==null || !isValid(playlist.getNombre()) ){
			onError("Favor de proporcionar la playlist");
		}
		crear(playlist);
	}
	
	public List<YoutubePlaylist> filtrar(YoutubePlaylist filtro){
		List<YoutubePlaylist> lista=new ArrayList<YoutubePlaylist>();
		StringBuilder str=new StringBuilder("select p from youtubePlaylist p ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by p.id desc ";
		lista=(List<YoutubePlaylist>)consultarPorQuery(query, YoutubePlaylist.class,parametros);
		return lista;
	}
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubePlaylist consultarPorNegocio(Long idNegocio){
		YoutubePlaylist filtro=new YoutubePlaylist();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		List<YoutubePlaylist> lista=filtrar(filtro);
		filtro=(lista!=null && !lista.isEmpty())?lista.get(0):null;
		return filtro;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,YoutubePlaylist filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Negocio negocio=filtro.getNegocio();
			Long idNeg=(negocio!=null)?negocio.getId():null;
			String nombre=filtro.getNombre();
			if(isNotNull(id)){
				agregarCondicion(str, " p.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(nombre)){
					agregarCondicion(str, " p.nombre = :nombre ", CondicionLogica.AND);
					parametros.put("nombre",nombre);
				}
				if(isNotNull(idNeg)){
					agregarCondicion(str, " p.negocio.id = :idNeg ", CondicionLogica.AND);
					parametros.put("idNeg",idNeg);
				}
			}
		}
	}
	
}
