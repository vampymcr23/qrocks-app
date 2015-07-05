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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.beans.YoutubeUserVideo;
import com.quuiko.beans.YoutubeVideo;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class YoutubeUserVideoDAO extends DAO<YoutubeUserVideo,Long>{
	@Autowired
	private YoutubeUserPlaylistDAO youtubeUserPlaylistDao; 
	
	public void agregarVideo(YoutubeUserVideo video) throws QRocksException{
		if(video==null || !isNull(video.getId())){
			onError("Favor de proporcionar los datos del video");
		}
		if(video.getUserPlaylist()==null || video.getUserPlaylist().getId()==null){
			onError("Favor de proporcionar la playlist a la cual se agregara el video.");
		}
		video.setReproducido(0);//Nuevo video se marca como no reproducido
		crear(video);
	}
	
	public void eliminarVideo(Long id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar el video a eliminar");
		}
		eliminarPorId(YoutubeUserVideo.class, id);
	}
	
	/**
	 * Barre la lista de videos de la playlist de usuarios y elimina todos los videos que se habian seleccionado para reproducir
	 * @param idUserPlaylist
	 * @throws QRocksException
	 */
	public void limpiarPlaylistUsuarios(Long idUserPlaylist) throws QRocksException{
		if(isNull(idUserPlaylist)){
			onError("Favor de proporcionar la playlist de usuarios");
		}
		List<YoutubeUserVideo> videos=consultarVideosPorPlaylistUsuario(idUserPlaylist);
		if(!isEmptyCollection(videos)){
			for(YoutubeUserVideo video:videos){
				if(video!=null){
					eliminarVideo(video.getId());
				}
			}
		}
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylistUsuario(Long idUserPlaylist){
		YoutubeUserVideo filtro=new YoutubeUserVideo();
		YoutubeUserPlaylist playlist=new YoutubeUserPlaylist();
		playlist.setId(idUserPlaylist);
		filtro.setUserPlaylist(playlist);
		filtro.setReproducido(1); // Que se traiga todos menos los que ya se reproducieron
		List<YoutubeUserVideo> list=filtrar(filtro);
		return list;
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarTodosVideosPorPlaylistUsuario(Long idUserPlaylist){
		YoutubeUserVideo filtro=new YoutubeUserVideo();
		YoutubeUserPlaylist playlist=new YoutubeUserPlaylist();
		playlist.setId(idUserPlaylist);
		filtro.setUserPlaylist(playlist);
		List<YoutubeUserVideo> list=filtrar(filtro);
		return list;
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylist(Long idBusinessPlaylist) throws QRocksException{
		if(isNull(idBusinessPlaylist)){
			onError("Favor de proporcionar la playlist del negocio");
		}
		YoutubeUserPlaylist userPlaylist=youtubeUserPlaylistDao.consultarPlaylistDelUsuarioPorPlaylistDelNegocio(idBusinessPlaylist);
		if(isNull(userPlaylist)){
			onError("No existe la playlist de usuarios");
		}
		YoutubeUserVideo filtro=new YoutubeUserVideo();
		filtro.setUserPlaylist(userPlaylist);
		List<YoutubeUserVideo> list=filtrar(filtro);
		return list;
	}
	
	public List<YoutubeUserVideo> filtrar(YoutubeUserVideo filtro){
		List<YoutubeUserVideo> lista=new ArrayList<YoutubeUserVideo>();
		StringBuilder str=new StringBuilder("select v from youtubeUserVideo v ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by v.id asc ";
		lista=(List<YoutubeUserVideo>)consultarPorQuery(query, YoutubeUserVideo.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,YoutubeUserVideo filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			YoutubeVideo video=filtro.getVideo();
			Long idVideo=(isNotNull(video))?video.getId():null;
			YoutubeUserPlaylist playlist=filtro.getUserPlaylist();
			Long idPlaylist=(playlist!=null)?playlist.getId():null;
			Integer reproducido=filtro.getReproducido();
//			Long youtubeBusinessPlaylist=(playlist!=null && playlist.getPlaylist()!=null)?playlist.getPlaylist().getId():null;
			if(isNotNull(id)){
				agregarCondicion(str, " v.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(idVideo)){
					agregarCondicion(str, " v.video.id = :idVideo ", CondicionLogica.AND);
					parametros.put("idVideo",idVideo);
				}
				if(isNotNull(idPlaylist)){
					agregarCondicion(str, " v.userPlaylist.id= :idPlaylist ", CondicionLogica.AND);
					parametros.put("idPlaylist",idPlaylist);
				}
				if(isNotNull(reproducido)){
					agregarCondicion(str, " v.reproducido != :reproducido ", CondicionLogica.AND);
					parametros.put("reproducido",reproducido);
				}
//				if(isNotNull(youtubeBusinessPlaylist)){
//					agregarCondicion(str, " v.userPlaylist.playlist.id= :youtubeBusinessPlaylist ", CondicionLogica.AND);
//					parametros.put("youtubeBusinessPlaylist",youtubeBusinessPlaylist);
//				}
			}
		}
	}

	public void setYoutubeUserPlaylistDao(
			YoutubeUserPlaylistDAO youtubeUserPlaylistDao) {
		this.youtubeUserPlaylistDao = youtubeUserPlaylistDao;
	}
}
