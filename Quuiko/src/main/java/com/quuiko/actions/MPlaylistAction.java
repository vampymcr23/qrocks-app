package com.quuiko.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.quuiko.dtos.Cancion;
import com.quuiko.dtos.Genero;
import com.quuiko.dtos.QRocksCategoria;
@Namespace("/m/play")
@ParentPackage("qrocks-default")
public class MPlaylistAction extends QRocksAction{
	/**
	 * =============================================
	 * 	Paginas,Constantes,variables globales
	 * =============================================
	 */
	private static final String ACTION_MOSTRAR_PLAYLIST="qrocksPlayer";
	public static final String ACTION_AGREGAR_CANCION="addSong";
	public static final String ACTION_OBTENER_IMAGEN_ALBUM="getArtWork";
	public static final String ACTION_ENCOLAR_CANCION="toQueue";
	private static final String PAGINA_PLAYLIST="/mobil/multimedia/playlist.jsp";
	public static final String CURRENT_NAMESPACE="/m/play";
	private List<QRocksCategoria> categorias=new ArrayList<QRocksCategoria>();
	private List<Genero> generos=new ArrayList<Genero>();
	private Cancion cancionAgregar;
	private InputStream stream;
	private String contentDisposition;
	private String qrMKEY;
	
	/**
	 * =============================================
	 * 	Actions
	 * =============================================
	 */
//	/**
//	 * Action que muestra la pagina de playlist y carga las categorias con sus canciones
//	 * @return
//	 */
//	@Action(value=ACTION_MOSTRAR_PLAYLIST,results=@Result(name=SUCCESS,location=PAGINA_PLAYLIST))
//	public String inicio(){
//		try {
//			cargarGeneros();
//		} catch (QRocksException e) {
//			onErrorMessage(e);
//		}
//		return result;
//	}
//	
//	private void cargarGeneros() throws QRocksException{
//		generos=playListService.obtenerGeneros();
//	}
	
//	/**
//	 * Action para agregar una cancion en cola
//	 * @return
//	 */
//	@Action(value=ACTION_AGREGAR_CANCION,
//			results={
//			@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"}),
//			@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_MOSTRAR_PLAYLIST,"namespace",CURRENT_NAMESPACE,"mensajeExito","${mensajeExito}","mensajeError","${mensajeError}"})
//		})
//	public String agregarCancion(){
//		try {
//			stream=playListService.obtenerCancion(cancionAgregar);
//			String nombreCancion=cancionAgregar.getNombre();
//			contentDisposition="attachment;filename="+nombreCancion;
//		} catch (QRocksException e) {
//			onErrorMessage(e);
//		}
//		return result;
//	}
//	
//	/**
//	 * Action para agregar una cancion en cola
//	 * @return
//	 */
//	@Action(value=ACTION_ENCOLAR_CANCION,
//			results={
//			@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MOSTRAR_PLAYLIST,"namespace",CURRENT_NAMESPACE,"mensajeExito","${mensajeExito}","mensajeError","${mensajeError}","qrMKEY","${qrMKEY}"})
//		})
//	public String encolarCancion(){
//		try {
//			playListService.agregarAPlaylist(cancionAgregar);
//		} catch (QRocksException e) {
//			onErrorMessage(e);
//		}
//		return result;
//	}
//	
//	@Action(value=ACTION_OBTENER_IMAGEN_ALBUM,
//			results={
//			@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"}),
//			@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_MOSTRAR_PLAYLIST,"namespace",CURRENT_NAMESPACE,"mensajeExito","${mensajeExito}","mensajeError","${mensajeError}"})
//		})
//	public String obtenerImagenAlbum(){
//		try {
//			stream=playListService.obtenerImagenCancion(cancionAgregar);
//			String nombreCancion=cancionAgregar.getNombre();
//			contentDisposition="attachment;filename="+nombreCancion;
//		} catch (QRocksException e) {
//			onErrorMessage(e);
//		}
//		return result;
//	}
	
	public List<QRocksCategoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<QRocksCategoria> categorias) {
		this.categorias = categorias;
	}
	public Cancion getCancionAgregar() {
		return cancionAgregar;
	}
	public void setCancionAgregar(Cancion cancionAgregar) {
		this.cancionAgregar = cancionAgregar;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public List<Genero> getGeneros() {
		return generos;
	}
	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public String getQrMKEY() {
		return qrMKEY;
	}

	public void setQrMKEY(String qrMKEY) {
		this.qrMKEY = qrMKEY;
	}
}
