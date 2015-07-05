package com.quuiko.actions;

import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.beans.YoutubeUserVideo;
import com.quuiko.beans.YoutubeVideo;
import com.quuiko.dtos.Cancion;
import com.quuiko.dtos.JPlayerSong;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.NegocioService;
import com.quuiko.services.YoutubePlaylistService;
import com.quuiko.services.YoutubeUserPlaylistService;
import com.quuiko.services.YoutubeUserVideoService;
import com.quuiko.services.YoutubeVideoService;

@Namespace("/mediaplayer")
@ParentPackage("qrocks-default")
public class MediaPlayerAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MediaPlayerAction.class);
	//FIXME CHECAR QUE SIGA FUNCIONANDO LA OPCION DE AGREGAR, ir al jsp de qrocksCreator con el mismo comentario donde se agrego esa parte de una nueva funcion
	//donde se intenta guardar en base de datos la lista y el video.
//	--CHECAR QUE SIGA FUNCIONANDO LA OPCION DE AGREGAR--
	
	public static final String ACTION_AGREGAR_CANCION="addSong";
	private static final String ACTION_OBTENER_CANCIONES="getPlaylist";
	private static final String ACTION_MOSTRAR_PLAYLIST="qrocksPlayer";
	public static final String ACTION_OBTENER_IMAGEN_ALBUM="getArtWork";
	public static final String ACTION_REGISTRAR_CANCION_ACTUAL="storeCurrentSong";
	public static final String ACTION_DB_CREATE_PLAYLIST="regPlaylist";
	public static final String ACTION_YOUTUBE_PLAYLIST_CREATOR="showPlaylist";
	public static final String ACTION_YOUTUBE_CREATE_PLAYLIST="addNewPlaylist";
	public static final String ACTION_YOUTUBE_UPDATE_VIDEO_PLAYED="updPlayedVideo";
	public static final String ACTION_YOUTUBE_OPEN_PLAYLIST="openPlaylist";
	public static final String ACTION_YOUTUBE_CLEAN_USER_PLAYLIST="clearUsrPlaylist";
	public static final String ACTION_YOUTUBE_REGISTRAR_NUEVO_VIDEO_EN_PLAYLIST="addNewVid";
	public static final String ACTION_YOUTUBE_QUITAR_VIDEO_EN_PLAYLIST="removeVid";
	public static final String ACTION_YOUTUBE_REGISTRAR_NUEVO_VIDEO_EN_USER_PLAYLIST="addNewUsrVid";
	public static final String ACTION_YOUTUBE_CONSULTAR_VIDEOS_EN_PLAYLIST="gtVidsByPlyLst";
	public static final String ACTION_YOUTUBE_CONSULTAR_VIDEOS_EN_USER_PLAYLIST="gtVidsByUsrPlyLst";
	private static final String PAGINA_PLAYLIST="/mediaplayer/mediaPlayer.jsp";
	private static final String PAGINA_YOUTUBE_OPEN_PLAYLIST="/mediaplayer/qrocksPlayer.jsp";
	private static final String PAGINA_YOUTUBE_PLAYLIST_CREATOR="/mediaplayer/qrocksPlayerCreator.jsp";
	public static final String CURRENT_NAMESPACE="/mediaplayer";
	private InputStream stream;
	private String contentDisposition;
	private Cancion cancionAgregar;
	private static List<Cancion> playList=new ArrayList<Cancion>();
	private List<JPlayerSong> songs=new ArrayList<JPlayerSong>();
	
	private List<YoutubeVideo> vids=new ArrayList<YoutubeVideo>();
	private List<YoutubeUserVideo> userVideos=new ArrayList<YoutubeUserVideo>();
	
	@Autowired
	private YoutubePlaylistService youtubePlaylistService;
	@Autowired
	private YoutubeUserPlaylistService youtubeUserPlaylistService;
	@Autowired
	private YoutubeVideoService youtubeVideoService;
	@Autowired
	private YoutubeUserVideoService youtubeUserVideoService;
	
	private static Integer cancionActual=new Integer(-1);
	@Autowired
	private NegocioService negocioService;
	private boolean requiereCrearPlaylist;
	private Negocio negocio;
	private YoutubeVideo youtubeVideo;
	private YoutubeUserVideo userVideo;
	private YoutubePlaylist youtubePlaylist;
	private YoutubeUserPlaylist youtubeUserPlaylist;
	private Long currentPl;//Variable que contiene el id de la playlist Actual para guardar el video en esta playlist
	private Integer cIdx;
	private Long idVideo;
	/**
	 * =============================================
	 * 	Actions
	 * =============================================
	 */
	
	@Action(value=ACTION_YOUTUBE_PLAYLIST_CREATOR,results={
		@Result(name=SUCCESS,location=PAGINA_YOUTUBE_PLAYLIST_CREATOR),
		@Result(name=INPUT,location=PAGINA_YOUTUBE_PLAYLIST_CREATOR)})
	public String mostrarPlaylistCreator(){
		negocio=getNegocioLogeado();
		try{
			youtubePlaylist=youtubePlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			youtubeUserPlaylist=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			//Si no tiene un playlistId, si requiere crear una playlist en youtube automaticamente
			requiereCrearPlaylist=(youtubePlaylist==null);
			if(requiereCrearPlaylist){
				log.info("Creando playlist de youtube...");
				try {
					String playlistName="QRksPlaylist-"+negocio.getNombre();
//					negocio.setPlayListId(playlistid);
					youtubePlaylist=new YoutubePlaylist();
//					youtubePlaylist.setId(playlistid);
					youtubePlaylist.setNegocio(negocio);
					youtubePlaylist.setNombre(playlistName);
					System.out.println("Guardando el playlist de youtube en base de datos...");
					youtubePlaylistService.guardarPlaylist(youtubePlaylist);
					System.out.println("Playlist "+youtubePlaylist.getId()+" de youtube guardado exitosamente...");
				} catch (Exception e) {
					messageResult=e.getMessage();
					result=INPUT;
					e.printStackTrace();
					throw new QRocksException(e.getMessage(),true);
				}
			}
			boolean requiereCrearPlaylistUsuarios=(negocio!=null && isNull(youtubeUserPlaylist));
			if(requiereCrearPlaylistUsuarios){
				try{
					String playlistName="QRksPlaylistUsr-"+negocio.getNombre();
					youtubeUserPlaylist=new YoutubeUserPlaylist();
					youtubeUserPlaylist.setNombre(playlistName);
//					youtubeUserPlaylist.setPlaylist(youtubePlaylist);
					youtubeUserPlaylist.setNegocio(negocio);
					youtubeUserPlaylistService.guardarPlaylist(youtubeUserPlaylist);
				} catch (Exception e) {
					messageResult=e.getMessage();
					result=INPUT;
					throw new QRocksException(e.getMessage(),true);
				}
			}
		}catch(QRocksException e){
			messageResult=e.getMessage();
			onErrorMessage(e);
			result=INPUT;
		}
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_CLEAN_USER_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageResult,messageCode"})
	})
	public String limpiarPlaylistUsuarios(){
		negocio=getNegocioLogeado();
		youtubeUserPlaylist=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
		if(youtubeUserPlaylist!=null){
			try {
				youtubeUserPlaylistService.eliminarPlaylistUsuario(youtubeUserPlaylist.getId());
				onSuccessMessage("Se ha limpiado la playlist de usuarios");
			} catch (QRocksException e) {
				e.printStackTrace();
				onErrorMessage("Ocurrio un error al intentar eliminar la playlist de usuarios");
			}
		}
		return result;
	}
	
	@Action(value="x",results={
		@Result(name=SUCCESS,location=PAGINA_YOUTUBE_PLAYLIST_CREATOR),
		@Result(name=INPUT,location=PAGINA_YOUTUBE_PLAYLIST_CREATOR)})
	@Deprecated
	public String mostrarYoutubePlaylistCreator(){
		negocio=getNegocioLogeado();
		try{
			youtubePlaylist=youtubePlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			youtubeUserPlaylist=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			//Si no tiene un playlistId, si requiere crear una playlist en youtube automaticamente
			requiereCrearPlaylist=(youtubePlaylistService==null);
			if(requiereCrearPlaylist){
				log.info("Creando playlist de youtube...");
				try {
					String playlistName="QRksPlaylist-"+negocio.getNombre();
//					String playlistid=YoutubeUtil.createNewPlayList(playlistName,"Playlist del Negocio:"+negocio.getNombre());
//					negocio.setPlayListId(playlistid);
					youtubePlaylist=new YoutubePlaylist();
//					youtubePlaylist.setId(playlistid);
					youtubePlaylist.setNombre(playlistName);
					System.out.println("Guardando el playlist de youtube en base de datos...");
					youtubePlaylistService.guardarPlaylist(youtubePlaylist);
					System.out.println("Playlist "+playlistName+" de youtube guardado exitosamente...");
				} catch (Exception e) {
					messageResult=e.getMessage();
					result=INPUT;
					e.printStackTrace();
					throw new QRocksException(e.getMessage(),true);
				}
			}
			boolean requiereCrearPlaylistUsuarios=(negocio!=null && isNull(youtubeUserPlaylist));
			if(requiereCrearPlaylistUsuarios){
				crearUserPlaylist();
//				try{
//					String playlistName="QRksPlaylistUsr-"+negocio.getNombre();
//					String playlistid=YoutubeUtil.createNewPlayList(playlistName,"Playlist de usuarios para el Negocio:"+negocio.getNombre());
//					youtubeUserPlaylist=new YoutubeUserPlaylist();
//					youtubeUserPlaylist.setNombre(playlistName);
//					youtubeUserPlaylist.setNegocio(negocio);
//					youtubeUserPlaylistService.guardarPlaylist(youtubeUserPlaylist);
//				} catch (Exception e) {
//					messageResult=e.getMessage();
//					result=INPUT;
//					throw new QRocksException(e.getMessage(),true);
//				}
			}
		}catch(QRocksException e){
			messageResult=e.getMessage();
			onErrorMessage(e);
			result=INPUT;
		}
		return result;
	}
	
	/**
	 * Se crea una playlist de usuarios para ese negocio
	 * @throws QRocksException
	 */
	private void crearUserPlaylist() throws QRocksException{
		boolean requiereCrearPlaylistUsuarios=(negocio!=null && isNull(youtubeUserPlaylist));
		if(requiereCrearPlaylistUsuarios){
			try{
				String playlistName="QRksPlaylistUsr-"+negocio.getNombre();
//				String playlistid=YoutubeUtil.createNewPlayList(playlistName,"Playlist de usuarios para el Negocio:"+negocio.getNombre());
				youtubeUserPlaylist=new YoutubeUserPlaylist();
				youtubeUserPlaylist.setNombre(playlistName);
				youtubeUserPlaylist.setNegocio(negocio);
//				youtubeUserPlaylist.setPlaylist(youtubePlaylist);
				youtubeUserPlaylistService.guardarPlaylist(youtubeUserPlaylist);
			} catch (Exception e) {
				messageResult=e.getMessage();
				result=INPUT;
				throw new QRocksException(e.getMessage(),true);
			}
		}
	}
	
	@Action(value=ACTION_YOUTUBE_OPEN_PLAYLIST,results={@Result(name=SUCCESS,location=PAGINA_YOUTUBE_OPEN_PLAYLIST),@Result(name=INPUT,location=PAGINA_YOUTUBE_OPEN_PLAYLIST)})
	public String mostrarYoutubePlaylist(){
		negocio=getNegocioLogeado();
		try{
			youtubePlaylist=youtubePlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			if(isNull(youtubePlaylist)){
				throw new QRocksException("Favor de primero crear la lista de videos que puede reproducir en su restaurante. Ir a Multimedia>Crear Playlist");
			}
			youtubeUserPlaylist=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
			boolean requiereCrearPlaylistUsuarios=(negocio!=null && isNull(youtubeUserPlaylist));
			if(requiereCrearPlaylistUsuarios){
					crearUserPlaylist();
			}
			currentPl=youtubeUserPlaylist.getId();
			userVideos=youtubeUserVideoService.consultarVideosPorPlaylistUsuario(youtubeUserPlaylist.getId());
		} catch (QRocksException e) {
			e.printStackTrace();
			onErrorMessage(e);
		}
//		playList=playListService.getPlayList();
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_CREATE_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negocio"})
	})
	public String actualizarPlaylistNegocio(){
		if(negocio!=null){
//			try {
//				negocioService.actualizarPlayList(negocio);
//			} catch (QRocksException e) {
//				onErrorMessage(e);
//			}
		}
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_UPDATE_VIDEO_PLAYED,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negocio"})
	})
	public String actualizarVideoReproducido(){
		negocio=getNegocioLogeado();
		if(negocio!=null){
			try {
				youtubeUserVideoService.marcarVideoComoReproducido(idVideo);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	/**
	 * Action que se utiliza para agregar un nuevo video a la playlist pero de base de datos, ya que la parte de Youtube lo hace el api de javascript. 
	 */
	@Action(value=ACTION_YOUTUBE_REGISTRAR_NUEVO_VIDEO_EN_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negocio"})
	})
	public String registrarVideoEnPlaylist(){
		if(youtubeVideo!=null && isNotNull(currentPl)){
			try {
				youtubePlaylist=youtubePlaylistService.obtenerPlaylistPorIdYoutube(currentPl);
				youtubeVideo.setPlaylist(youtubePlaylist);
				youtubeVideoService.agregarVideo(youtubeVideo);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	/**
	 * Action que se utiliza para agregar un nuevo video a la playlist pero de base de datos, ya que la parte de Youtube lo hace el api de javascript. 
	 */
	@Action(value=ACTION_YOUTUBE_QUITAR_VIDEO_EN_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negocio"})
	})
	public String quitarVideoDePlaylist(){
		if(youtubeVideo!=null && isNotNull(youtubeVideo.getId())){
			try {
				youtubeVideoService.eliminarVideo(youtubeVideo.getId());
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_REGISTRAR_NUEVO_VIDEO_EN_USER_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userVideos"})
	})
	public String registrarVideoEnPlaylistUsuarios(){
		if(isNotNull(currentPl) && userVideo!=null){
			try {
				youtubeUserVideoService.agregarVideo(userVideo);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_CONSULTAR_VIDEOS_EN_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","vids"})
	})
	public String obtenerVideosDelPlaylist(){
		vids=youtubeVideoService.consultarVideosPorPlaylist(currentPl);
		return result;
	}
	
	@Action(value=ACTION_YOUTUBE_CONSULTAR_VIDEOS_EN_USER_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userVideos"})
	})
	public String obtenerVideosDeUserPlaylist(){
		negocio=getNegocioLogeado();
		userVideos=youtubeUserVideoService.consultarVideosPorPlaylistUsuario(currentPl);
		//Agregado para cuando no haya mas videos por reproducir
		if(cIdx==null){
			cIdx=0;
		}
		if(userVideos!=null){
			//Si la lista actual reproduciendose es igual a la ultima de la lista, entonces se agrega aleatoriamente una cancion a la lista o que este vacia la lista (cIdx=0 y userVideos.size()==0)
			if(cIdx!=null && cIdx.intValue()==userVideos.size()){
				try {
					registrarVideoRandomEnPlaylistUsuarios();
					userVideos=youtubeUserVideoService.consultarVideosPorPlaylistUsuario(currentPl);
				} catch (QRocksException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * Agrega un video random cuando ya no hay mas videos agregados
	 * @throws QRocksException
	 */
	private void registrarVideoRandomEnPlaylistUsuarios() throws QRocksException{
		YoutubePlaylist pl=youtubePlaylistService.obtenerPlaylistPorNegocio(negocio.getId());
		List<YoutubeVideo> videos=youtubeVideoService.consultarVideosPorPlaylist(pl.getId());
		YoutubeUserPlaylist userPlaylist=youtubeUserPlaylistService.consultarPorId(currentPl);
		if(videos!=null && !videos.isEmpty()){
			int numAvailableVideos=videos.size();
			boolean buscarVideoRandom=true;
			do{
				Double random=Math.random()*numAvailableVideos;
				int randomIndex=random.intValue();
				YoutubeVideo randomVideo=videos.get(randomIndex);
				String idVideoRandom=randomVideo.getIdVideo();
				//El primer video que no este contenido en los del usuario, ese se agregara a la lista de videos del usuario.
				if(!userVideos.contains(randomVideo)){
					YoutubeUserVideo rndVideo=new YoutubeUserVideo();
					rndVideo.setUserPlaylist(userPlaylist);
					rndVideo.setVideo(randomVideo);
					youtubeUserVideoService.agregarVideo(rndVideo);
					buscarVideoRandom=false;
				}
			}while(buscarVideoRandom);
		}
	}
	
	
	
	
//	@Action(value=ACTION_MOSTRAR_PLAYLIST,results=@Result(name=SUCCESS,location=PAGINA_PLAYLIST))
//	public String mostrarMediaPlayer(){
//		playList=playListService.getPlayList();
//		return result;
//	}
//	
//	@Action(value=ACTION_OBTENER_CANCIONES,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","songs"})
//	})
//	public String obtenerCanciones(){
//		try{
//		playList=playListService.getPlayList();
//		songs=new ArrayList<JPlayerSong>();
//		for(Cancion c:playList){
//			JPlayerSong song=new JPlayerSong(c);
//			songs.add(song);
//		}
//		}catch(Exception e){
//			System.out.println("Aqui fallo");
//		}
//		return result;
//	}
//	
//	
//	@Action(value=ACTION_REGISTRAR_CANCION_ACTUAL,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","cancionActual"}),
//		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","cancionActual"})
//	})
//	public String registrarCancionActual(){
//		System.out.println("Cancion Actual:"+cancionActual);
//		return result;
//	}
	
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
	
	/**
	 * =============================================
	 * 	Sets and gets
	 * =============================================
	 */

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

	public Cancion getCancionAgregar() {
		return cancionAgregar;
	}

	public void setCancionAgregar(Cancion cancionAgregar) {
		this.cancionAgregar = cancionAgregar;
	}

	public static List<Cancion> getPlayList() {
		return playList;
	}

	public static void setPlayList(List<Cancion> playList) {
		MediaPlayerAction.playList = playList;
	}

	public List<JPlayerSong> getSongs() {
		return songs;
	}

	public void setSongs(List<JPlayerSong> songs) {
		this.songs = songs;
	}

	public Integer getCancionActual() {
		return cancionActual;
	}

	public void setCancionActual(Integer cancionActual) {
		this.cancionActual = cancionActual;
	}

	public boolean isRequiereCrearPlaylist() {
		return requiereCrearPlaylist;
	}

	public void setRequiereCrearPlaylist(boolean requiereCrearPlaylist) {
		this.requiereCrearPlaylist = requiereCrearPlaylist;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public void setNegocioService(NegocioService negocioService) {
		this.negocioService = negocioService;
	}

	public YoutubeVideo getYoutubeVideo() {
		return youtubeVideo;
	}

	public void setYoutubeVideo(YoutubeVideo youtubeVideo) {
		this.youtubeVideo = youtubeVideo;
	}

	public void setYoutubePlaylistService(
			YoutubePlaylistService youtubePlaylistService) {
		this.youtubePlaylistService = youtubePlaylistService;
	}

	public void setYoutubeVideoService(YoutubeVideoService youtubeVideoService) {
		this.youtubeVideoService = youtubeVideoService;
	}

	public Long getCurrentPl() {
		return currentPl;
	}

	public void setCurrentPl(Long currentPl) {
		this.currentPl = currentPl;
	}

	public List<YoutubeVideo> getVids() {
		return vids;
	}

	public void setVids(List<YoutubeVideo> vids) {
		this.vids = vids;
	}

	public List<YoutubeUserVideo> getUserVideos() {
		return userVideos;
	}

	public void setUserVideos(List<YoutubeUserVideo> userVideos) {
		this.userVideos = userVideos;
	}

	public YoutubeUserVideo getUserVideo() {
		return userVideo;
	}

	public void setUserVideo(YoutubeUserVideo userVideo) {
		this.userVideo = userVideo;
	}

	public void setYoutubeUserPlaylistService(
			YoutubeUserPlaylistService youtubeUserPlaylistService) {
		this.youtubeUserPlaylistService = youtubeUserPlaylistService;
	}

	public void setYoutubeUserVideoService(
			YoutubeUserVideoService youtubeUserVideoService) {
		this.youtubeUserVideoService = youtubeUserVideoService;
	}

	public YoutubePlaylist getYoutubePlaylist() {
		return youtubePlaylist;
	}

	public void setYoutubePlaylist(YoutubePlaylist youtubePlaylist) {
		this.youtubePlaylist = youtubePlaylist;
	}

	public YoutubeUserPlaylist getYoutubeUserPlaylist() {
		return youtubeUserPlaylist;
	}

	public void setYoutubeUserPlaylist(YoutubeUserPlaylist youtubeUserPlaylist) {
		this.youtubeUserPlaylist = youtubeUserPlaylist;
	}

	public Integer getcIdx() {
		return cIdx;
	}

	public void setcIdx(Integer cIdx) {
		this.cIdx = cIdx;
	}

	public Long getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(Long idVideo) {
		this.idVideo = idVideo;
	}
}