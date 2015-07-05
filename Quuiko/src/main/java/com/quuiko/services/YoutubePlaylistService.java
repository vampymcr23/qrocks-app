package com.quuiko.services;

import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.exception.QRocksException;

public interface YoutubePlaylistService {
	/**
	 * Metodo que guarda una playlist de youtube dado el id de la playlist y el nombre de la playlist, se guarda en la base de datos
	 * para evitar ir muchas veces a youtube.
	 * @param playlist
	 * @throws QRocksException
	 */
	public void guardarPlaylist(YoutubePlaylist playlist) throws QRocksException;
	
	/**
	 * Obtiene el playlist por medio del id
	 * @param playlistId
	 * @return
	 * @throws QRocksException
	 */
	public YoutubePlaylist obtenerPlaylistPorIdYoutube(Long playlistId) throws QRocksException;
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubePlaylist obtenerPlaylistPorNegocio(Long idNegocio);
}
