package com.quuiko.daos;
import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyCollection;
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

import com.quuiko.beans.Negocio;
import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class YoutubeUserPlaylistDAO extends DAO<YoutubeUserPlaylist, Long>{
	/**
	 * Guarda los datos de la playlist del usuario
	 * @param playlist
	 * @throws QRocksException
	 */
	public void guardarPlaylist(YoutubeUserPlaylist playlist) throws QRocksException{
		if(playlist==null || !isValid(playlist.getNombre())){
			onError("Favor de proporcionar la playlist");
		}
		crear(playlist);
	}
	
	/**
	 * Consulta la playlist del usuario mediante el id de la playlist del negocio
	 * @param idPlaylist id de la playlist de youtube
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist consultarPlaylistDelUsuarioPorPlaylistDelNegocio(Long idPlaylist) throws QRocksException{
		YoutubeUserPlaylist userPlaylist=null;
		if(isNull(idPlaylist)){
			onError("Favor de proporcionar la playlist");
		}
		YoutubeUserPlaylist filtro=new YoutubeUserPlaylist();
		YoutubePlaylist playlist=new YoutubePlaylist();
		playlist.setId(idPlaylist);
		List<YoutubeUserPlaylist> list=filtrar(filtro);
		if(!isEmptyCollection(list)){
			userPlaylist=list.get(0);
		}
		return userPlaylist;
	}
	
	public List<YoutubeUserPlaylist> filtrar(YoutubeUserPlaylist filtro){
		List<YoutubeUserPlaylist> lista=new ArrayList<YoutubeUserPlaylist>();
		StringBuilder str=new StringBuilder("select p from youtubeUserPlaylist p ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by p.id desc ";
		lista=(List<YoutubeUserPlaylist>)consultarPorQuery(query, YoutubeUserPlaylist.class,parametros);
		return lista;
	}
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist consultarPorNegocio(Long idNegocio){
		YoutubeUserPlaylist filtro=new YoutubeUserPlaylist();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		List<YoutubeUserPlaylist> lista=filtrar(filtro);
		filtro=(lista!=null && !lista.isEmpty())?lista.get(0):null;
		return filtro;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,YoutubeUserPlaylist filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String nombre=filtro.getNombre();
			Long idNegocio=(filtro!=null && filtro.getNegocio()!=null)?filtro.getNegocio().getId():null;
//			Long idYoutubePlaylist=(filtro.getPlaylist()!=null && isNotNull(filtro.getPlaylist().getId()))?filtro.getPlaylist().getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " p.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(nombre)){
					agregarCondicion(str, " p.nombre = :nombre ", CondicionLogica.AND);
					parametros.put("nombre",nombre);
				}
//				if(isNotNull(idYoutubePlaylist)){
//					agregarCondicion(str, " p.playlist.id = :idYoutubePlaylist ", CondicionLogica.AND);
//					parametros.put("idYoutubePlaylist",idYoutubePlaylist);
//				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " p.negocio.id = :idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
			}
		}
	}
	
}
