package com.quuiko.services;

import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.exception.QRocksException;
/**
 * Interface para manejo de playlist de usuarios 
 * @author victorherrera
 *
 */
public interface YoutubeUserPlaylistService {
	/**
	 * Guarda los datos de la playlist del usuario
	 * @param playlist
	 * @throws QRocksException
	 */
	public void guardarPlaylist(YoutubeUserPlaylist playlist) throws QRocksException;
	
	public YoutubeUserPlaylist consultarPorId(Long userPlaylistId) throws QRocksException;
	
	/**
	 * Consulta la playlist del usuario mediante el id de la playlist del negocio
	 * @param idPlaylist id de la playlist de youtube
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist consultarPlaylistDelUsuarioPorPlaylistDelNegocio(Long idPlaylist) throws QRocksException;
	
	/**
	 * Obtiene la playlist de un negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserPlaylist obtenerPlaylistPorNegocio(Long idNegocio);
	
	/**
	 * Elimina los videos elegidos por los usuarios y elimina la playlist
	 * @param idPlaylist
	 * @throws QRocksException
	 */
	public void eliminarPlaylistUsuario(Long idPlaylist) throws QRocksException;
}
