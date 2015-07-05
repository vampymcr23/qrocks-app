package com.quuiko.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.daos.YoutubePlaylistDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.YoutubePlaylistService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class YoutubePlaylistServiceImpl implements YoutubePlaylistService{
	@Autowired
	private YoutubePlaylistDAO youtubePlaylistDao;
	/**
	 * Metodo que guarda una playlist de youtube dado el id de la playlist y el nombre de la playlist, se guarda en la base de datos
	 * para evitar ir muchas veces a youtube.
	 * @param playlist
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardarPlaylist(YoutubePlaylist playlist) throws QRocksException{
		youtubePlaylistDao.guardarPlaylist(playlist);
	}
	
	/**
	 * Obtiene el playlist por medio del id
	 * @param playlistId
	 * @return
	 * @throws QRocksException
	 */
	public YoutubePlaylist obtenerPlaylistPorIdYoutube(Long playlistId) throws QRocksException{
		YoutubePlaylist pl=youtubePlaylistDao.consultarPorId(YoutubePlaylist.class, playlistId);
		if(pl==null){
			Utileria.onError("No existe la playlist '"+playlistId+"'");
		}
		return pl;
	}
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubePlaylist obtenerPlaylistPorNegocio(Long idNegocio){
		YoutubePlaylist pl=youtubePlaylistDao.consultarPorNegocio(idNegocio);
		return pl;
	}
	
	public void setYoutubePlaylistDao(YoutubePlaylistDAO youtubePlaylistDao) {
		this.youtubePlaylistDao = youtubePlaylistDao;
	}
}
