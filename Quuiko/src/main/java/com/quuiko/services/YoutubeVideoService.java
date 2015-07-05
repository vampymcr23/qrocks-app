package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.YoutubeVideo;
import com.quuiko.exception.QRocksException;

public interface YoutubeVideoService {
	/**
	 * Agrega un nuevo video a una playlist
	 * @param video
	 * @throws QRocksException
	 */
	public void agregarVideo(YoutubeVideo video) throws QRocksException;
	
	public YoutubeVideo consultarVideoPorId(Long id) throws QRocksException;
	
	/**
	 * Consulta un video en base al id que asigna youtube al video
	 * @param idYoutube id de youtube del video
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeVideo consultarVideoPorIdVideo(String idYoutube) throws QRocksException;
	
	/**
	 * Elimina un video de la playlist, se debe de proporcionar el id del video a eliminar
	 * @param idVideo
	 * @throws QRocksException
	 */
	public void eliminarVideo(Long id) throws QRocksException;
	
	/**
	 * Consulta de videos por playlist
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeVideo> consultarVideosPorPlaylist(Long idPlaylist);
}
