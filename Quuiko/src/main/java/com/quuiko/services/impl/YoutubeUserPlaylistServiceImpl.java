package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isNull;
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
import com.quuiko.daos.YoutubeUserPlaylistDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.YoutubeUserPlaylistService;
import com.quuiko.services.YoutubeUserVideoService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class YoutubeUserPlaylistServiceImpl implements YoutubeUserPlaylistService{
	@Autowired
	private YoutubeUserPlaylistDAO youtubeUserPlaylistDao;
	@Autowired
	private YoutubeUserVideoService youtubeUserVideoService;
	/**
	 * Guarda los datos de la playlist del usuario
	 * @param playlist
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardarPlaylist(YoutubeUserPlaylist playlist) throws QRocksException{
		youtubeUserPlaylistDao.guardarPlaylist(playlist);
	}
	
	/**
	 * Consulta la playlist del usuario mediante el id de la playlist del negocio
	 * @param idPlaylist id de la playlist de youtube
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist consultarPlaylistDelUsuarioPorPlaylistDelNegocio(Long idPlaylist) throws QRocksException{
		return youtubeUserPlaylistDao.consultarPlaylistDelUsuarioPorPlaylistDelNegocio(idPlaylist);
	}
	
	public YoutubeUserPlaylist consultarPorId(Long userPlaylistId) throws QRocksException{
		if(isNull(userPlaylistId)){
			onError("Favor de proporcionar el id de la playlist");
		}
		YoutubeUserPlaylist playlist=youtubeUserPlaylistDao.consultarPorId(YoutubeUserPlaylist.class, userPlaylistId);
		if(isNull(playlist)){
			onError("No existe una playlist de usuarios con este id:"+userPlaylistId);
		}
		return youtubeUserPlaylistDao.consultarPorId(YoutubeUserPlaylist.class, userPlaylistId);
	}
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist obtenerPlaylistPorNegocio(Long idNegocio){
		return youtubeUserPlaylistDao.consultarPorNegocio(idNegocio);
	}
	
	/**
	 * Elimina los videos elegidos por los usuarios y elimina la playlist
	 * @param idPlaylist
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void eliminarPlaylistUsuario(Long idPlaylist) throws QRocksException{
		YoutubeUserPlaylist userPlaylist=youtubeUserPlaylistDao.consultarPorId(YoutubeUserPlaylist.class, idPlaylist);
		if(userPlaylist!=null){
			List<YoutubeUserVideo> videos=youtubeUserVideoService.consultarTodosVideosPorPlaylistUsuario(userPlaylist.getId());
			//Se elimina video por video de la playlist del usuario
			if(!Utileria.isEmptyCollection(videos)){
				for(YoutubeUserVideo v:videos){
					youtubeUserVideoService.eliminarVideo(v.getId());
				}
			}
			youtubeUserPlaylistDao.eliminar(userPlaylist);
		}
	}

	public void setYoutubeUserPlaylistDao(
			YoutubeUserPlaylistDAO youtubeUserPlaylistDao) {
		this.youtubeUserPlaylistDao = youtubeUserPlaylistDao;
	}
}
