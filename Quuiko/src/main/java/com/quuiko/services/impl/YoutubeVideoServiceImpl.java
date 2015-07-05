package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isEmptyCollection;
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
import com.quuiko.daos.YoutubeVideoDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.YoutubeUserPlaylistService;
import com.quuiko.services.YoutubeUserVideoService;
import com.quuiko.services.YoutubeVideoService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class YoutubeVideoServiceImpl implements YoutubeVideoService{
	@Autowired
	private YoutubeUserPlaylistService youtubeUserPlaylistService;
	@Autowired
	private YoutubeVideoDAO youtubeVideoDao;
	@Autowired
	private YoutubeUserVideoService youtubeUserVideoService;
	/**
	 * Agrega un nuevo video a una playlist
	 * @param video
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void agregarVideo(YoutubeVideo video) throws QRocksException{
		youtubeVideoDao.agregarVideo(video);
	}
	
	public YoutubeVideo consultarVideoPorId(Long id) throws QRocksException{
		if(id==null){
			onError("Favor de proporcionar el id del video a consultar");
		}
		YoutubeVideo video=youtubeVideoDao.consultarPorId(YoutubeVideo.class, id);
		if(isNull(video)){
			onError("No existe el video con el id:"+id);
		}
		return video;
	}
	
	/**
	 * Consulta un video en base al id que asigna youtube al video
	 * @param idYoutube id de youtube del video
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeVideo consultarVideoPorIdVideo(String idYoutube) throws QRocksException{
		YoutubeVideo video=new YoutubeVideo();
		if(!isValid(idYoutube)){
			onError("Favor de proporcionar el id del video a consultar");
		}
		video.setIdVideo(idYoutube);
		List<YoutubeVideo> list=youtubeVideoDao.filtrar(video);
		if(!isEmptyCollection(list)){
			video=list.get(0);
		}else{
			video=null;
		}
		return video;
	}
	
	/**
	 * Elimina un video de la playlist, se debe de proporcionar el id del video a eliminar
	 * @param idVideo
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarVideo(Long id) throws QRocksException{
		
		YoutubeVideo video=consultarVideoPorId(id);
		if(video!=null){
			List<YoutubeUserVideo> videos=youtubeUserVideoService.consultarVideosPorPlaylist(video.getPlaylist().getId());
			if(!isEmptyCollection(videos)){
				for(YoutubeUserVideo v:videos){
					//Si en la playlist del usuario se encuentra que esta esa rola, entonces va a eliminar ese video de la playlist de usuarios
					if(v!=null && v.getVideo().getId().longValue()==id){
						youtubeUserVideoService.eliminarVideo(v.getId());
					}
				}
			}
			youtubeVideoDao.eliminarVideo(id);//finalmente elimina el video del playlist del negocio
		}
	}
	
	/**
	 * Consulta de videos por playlist
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeVideo> consultarVideosPorPlaylist(Long idPlaylist){
		return youtubeVideoDao.consultarVideosPorPlaylist(idPlaylist);
	}

	public void setYoutubeVideoDao(YoutubeVideoDAO youtubeVideoDao) {
		this.youtubeVideoDao = youtubeVideoDao;
	}
}
