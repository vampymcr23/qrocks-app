package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.onError;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.beans.YoutubeUserVideo;
import com.quuiko.beans.YoutubeVideo;
import com.quuiko.daos.YoutubeUserVideoDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.YoutubeUserPlaylistService;
import com.quuiko.services.YoutubeUserVideoService;
import com.quuiko.services.YoutubeVideoService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class YoutubeUserVideoServiceImpl implements YoutubeUserVideoService{
	@Autowired
	private YoutubeUserVideoDAO youtubeUserVideoDao;
	@Autowired
	private YoutubeUserPlaylistService youtubeUserPlaylistService;
	@Autowired
	private YoutubeVideoService youtubeVideoService;
	/**
	 * Agrega un nuevo video a la playlist del usuario
	 * @param video
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void agregarVideo(YoutubeUserVideo video) throws QRocksException{
		youtubeUserVideoDao.agregarVideo(video);
	}
	/**
	 * Agrega un video a la playlist de los usuarios mediante el id de la playlist de usuarios y el id del video de youtube
	 * @param idUserplaylist
	 * @param idVideo
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void agregarVideo(Long idUserplaylist,String idVideo) throws QRocksException{
		if(isNull(idUserplaylist)){
			onError("Favor de proporcionar el playlist de usuarios");
		}
		if(!isValid(idVideo)){
			onError("Favor de proporcionar el video que desea agregar");
		}
		YoutubeUserPlaylist userPlaylist=youtubeUserPlaylistService.consultarPorId(idUserplaylist);
		YoutubeUserVideo video=new YoutubeUserVideo();
		video.setUserPlaylist(userPlaylist);
		YoutubeVideo v=youtubeVideoService.consultarVideoPorIdVideo(idVideo);
		if(v==null ){
			onError("No se encontro el video con el id:"+idVideo);
		}
		video.setVideo(v);
		agregarVideo(video);
	}
	
	/**
	 * Consulta el video por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserVideo consultarPorId(Long id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar el video a consultar");
		}
		YoutubeUserVideo video=null;
		video=youtubeUserVideoDao.consultarPorId(YoutubeUserVideo.class,id);
		return video;
	}
	
	/**
	 * Marca el video con el id proporcionado como reproducido (1)
	 * @param id
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void marcarVideoComoReproducido(Long id) throws QRocksException{
		YoutubeUserVideo video=consultarPorId(id);
		if(isNull(video)){
			onError("El video no existe!");
		}
		video.setReproducido(1);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarVideo(Long id) throws QRocksException{
		youtubeUserVideoDao.eliminarVideo(id);
	}
	
	/**
	 * Barre la lista de videos de la playlist de usuarios y elimina todos los videos que se habian seleccionado para reproducir
	 * @param idUserPlaylist
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void limpiarPlaylistUsuarios(Long idUserPlaylist) throws QRocksException{
		youtubeUserVideoDao.limpiarPlaylistUsuarios(idUserPlaylist);
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylistUsuario(Long idUserPlaylist){
		return youtubeUserVideoDao.consultarVideosPorPlaylistUsuario(idUserPlaylist);
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id, pero trae todos inclusive los que ya se reproducieron
	 * @param idUserPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarTodosVideosPorPlaylistUsuario(Long idUserPlaylist){
		return youtubeUserVideoDao.consultarTodosVideosPorPlaylistUsuario(idUserPlaylist);
	}
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylist(Long idBusinessPlaylist) throws QRocksException{
		return youtubeUserVideoDao.consultarVideosPorPlaylist(idBusinessPlaylist);
	}

	public void setYoutubeUserVideoDao(YoutubeUserVideoDAO youtubeUserVideoDao) {
		this.youtubeUserVideoDao = youtubeUserVideoDao;
	}
}
