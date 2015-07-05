package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.YoutubeUserVideo;
import com.quuiko.exception.QRocksException;
/**
 * Interface para el manejo de videos de una playlist del usuario
 * @author victorherrera
 *
 */
public interface YoutubeUserVideoService {
	/**
	 * Agrega un nuevo video a la playlist del usuario
	 * @param video
	 * @throws QRocksException
	 */
	public void agregarVideo(YoutubeUserVideo video) throws QRocksException;
	
	/**
	 * Consulta el video por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public YoutubeUserVideo consultarPorId(Long id) throws QRocksException;
	
	/**
	 * Marca el video con el id proporcionado como reproducido (1)
	 * @param id
	 * @throws QRocksException
	 */
	public void marcarVideoComoReproducido(Long id) throws QRocksException;
	
	public void eliminarVideo(Long id) throws QRocksException;
	
	/**
	 * Barre la lista de videos de la playlist de usuarios y elimina todos los videos que se habian seleccionado para reproducir
	 * @param idUserPlaylist
	 * @throws QRocksException
	 */
	public void limpiarPlaylistUsuarios(Long idUserPlaylist) throws QRocksException;
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id que no se han reproducido
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylistUsuario(Long idUserPlaylist);
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id, pero trae todos inclusive los que ya se reproducieron
	 * @param idUserPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarTodosVideosPorPlaylistUsuario(Long idUserPlaylist);
	
	/**
	 * Consulta los videos del playlist del usuario mediante el id
	 * @param idPlaylist
	 * @return
	 */
	public List<YoutubeUserVideo> consultarVideosPorPlaylist(Long idBusinessPlaylist) throws QRocksException;
}