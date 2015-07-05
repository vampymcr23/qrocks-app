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

import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.beans.YoutubeVideo;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class YoutubeVideoDAO extends DAO<YoutubeVideo,Long>{
	public void agregarVideo(YoutubeVideo video) throws QRocksException{
		if(video==null || !isNull(video.getId())){
			onError("Favor de proporcionar los datos del video");
		}
		if(video.getPlaylist()==null || video.getPlaylist().getId()==null){
			onError("Favor de proporcionar la playlist a la cual se agregara el video.");
		}
		crear(video);
	}
	
	public void eliminarVideo(Long id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar el video a eliminar");
		}
		eliminarPorId(YoutubeVideo.class, id);
	}
	
	public List<YoutubeVideo> consultarVideosPorPlaylist(Long idPlaylist){
		YoutubeVideo filtro=new YoutubeVideo();
		YoutubePlaylist playlist=new YoutubePlaylist();
		playlist.setId(idPlaylist);
		filtro.setPlaylist(playlist);
		List<YoutubeVideo> list=filtrar(filtro);
		return list;
	}
	
	public List<YoutubeVideo> filtrar(YoutubeVideo filtro){
		List<YoutubeVideo> lista=new ArrayList<YoutubeVideo>();
		StringBuilder str=new StringBuilder("select v from youtubeVideo v ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by v.id desc ";
		lista=(List<YoutubeVideo>)consultarPorQuery(query, YoutubeVideo.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,YoutubeVideo filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String idVideo=filtro.getIdVideo();
			YoutubePlaylist playlist=filtro.getPlaylist();
			Long idPlaylist=(playlist!=null)?playlist.getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " v.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(idVideo)){
					agregarCondicion(str, " v.idVideo = :idVideo ", CondicionLogica.AND);
					parametros.put("idVideo",idVideo);
				}
				if(isNotNull(idPlaylist)){
					agregarCondicion(str, " v.playlist.id= :idPlaylist ", CondicionLogica.AND);
					parametros.put("idPlaylist",idPlaylist);
				}
			}
		}
	}
	//FIXME Agregar otra tabla para registrar la playlist de los usuarios del restaurante
}
