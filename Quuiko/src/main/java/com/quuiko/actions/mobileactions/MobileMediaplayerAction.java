package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Thumbnail;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoMesa;
import com.quuiko.beans.YoutubePlaylist;
import com.quuiko.beans.YoutubeUserPlaylist;
import com.quuiko.beans.YoutubeUserVideo;
import com.quuiko.beans.YoutubeVideo;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.PlaylistVideoDTO;
import com.quuiko.dtos.mobile.VideoDTO;
import com.quuiko.services.NegocioService;
import com.quuiko.services.PedidoMesaService;
import com.quuiko.services.YoutubePlaylistService;
import com.quuiko.services.YoutubeUserPlaylistService;
import com.quuiko.services.YoutubeUserVideoService;
import com.quuiko.services.YoutubeVideoService;
import com.quuiko.youtube.util.YoutubeUtil;

@Namespace("/m/droid/mediaplayer")
@ParentPackage("qrocks-default")
public class MobileMediaplayerAction extends QRocksAction{
	public final static String JSON_ACTION_LISTA_VIDEOS="getPlaylist";
	public final static String JSON_ACTION_ADD_TO_PLAYLIST="addVidToPlay";
	public final static int NUM_MIN_ENTRE_VIDEOS=5;
	public final static String JSON_ACTION_YOUTUBE_BUSINESS_PLAYLIST="getBusinessPlaylist";
	public final static String JSON_ACTION_YOUTUBE_ADD_VID_TO_USRPLAYLIST="addVidToUserPlay";
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private YoutubePlaylistService youtubePlaylistService;
	@Autowired
	private YoutubeUserPlaylistService youtubeUserPlaylistService;
	@Autowired
	private YoutubeVideoService youtubeVideoService;
	@Autowired
	private YoutubeUserVideoService youtubeUserVideoService;
	
	@Autowired
	private PedidoMesaService pedidoMesaService;
	
	public static final long NUM_SEGUNDOS_CONFIGURACION=60;
	
	private Long bussinessID;//Id del negocio
	private String videoId;
	private Negocio negocio;
	private PlaylistVideoDTO playlistDTO;
	
	private String qrMkey;
	
	private static final int NUM_VIDEOS_ANTERIORES_PARA_REPETIR=10;//Numero de videos que se validan para volver a repetir el mismo video
	
	@Action(value=JSON_ACTION_LISTA_VIDEOS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","playlistDTO"})
	})
	public String consultarPlaylistVideos(){
		//FIXME aqui se debe de consultar la playlist del negocio
		playlistDTO=new PlaylistVideoDTO();
		try {
			negocio=negocioService.consultarPorId(bussinessID);
			YoutubePlaylist playlist= youtubePlaylistService.obtenerPlaylistPorNegocio(bussinessID);
//			List<PlaylistItem> videos=YoutubeUtil.getVideosFromPlaylist(playlistId);
			List<YoutubeVideo> videos=youtubeVideoService.consultarVideosPorPlaylist(playlist.getId());
			List<VideoDTO> listVideos=new ArrayList<VideoDTO>();
			if(videos!=null && !videos.isEmpty()){
				for(YoutubeVideo v:videos){
					VideoDTO video=new VideoDTO(v);
					listVideos.add(video);
				}
			}
			playlistDTO.setVideos(listVideos);
			playlistDTO.setPlaylistId(playlist.getId());
			//FIXME Agregar DTO para android que contenga el mensaje y si se guardo bien o no la lista.
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,playlistDTO);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_YOUTUBE_BUSINESS_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","playlistDTO"})
	})
	public String consultarPlaylistVideosDelNegocio(){
		//FIXME aqui se debe de consultar la playlist del negocio
		playlistDTO=new PlaylistVideoDTO();
		try {
			negocio=negocioService.consultarPorId(bussinessID);
			YoutubePlaylist playlist= youtubePlaylistService.obtenerPlaylistPorNegocio(bussinessID);
			List<VideoDTO> listVideos=new ArrayList<VideoDTO>();
			if(isNotNull(playlist)){
				List<YoutubeVideo> videos=youtubeVideoService.consultarVideosPorPlaylist(playlist.getId());//Una vez que se agrega el video a la lista de usuarios, se consulta de nuevo
				if(videos!=null && !videos.isEmpty()){
					YoutubeUserPlaylist playlistEncolada=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(bussinessID);
					//Si aun no hay lista de videos por reproducir, se regresan todos los videos, pero si hay, se verifica que no se muestren los mismos.
					if(playlistEncolada!=null && playlistEncolada.getId()!=null){
						List<YoutubeUserVideo> videosEncolados=youtubeUserVideoService.consultarVideosPorPlaylistUsuario(playlistEncolada.getId());
						if(videosEncolados!=null && !videosEncolados.isEmpty()){
							for(YoutubeVideo v:videos){
								//FIXME REEMPLAZAR ESTO POR LA LOGICA DE BUSCAR UNO POR UNO
								int posicionDondeSeLocalizaVideo=existeVideoRepetidoEnEncolados(videosEncolados, v);
								//Si no se encontro la posicion, entonces no esta en la cola, y se puede agregar sin problema
								if(posicionDondeSeLocalizaVideo==-1){
									//FIXME si NO LO contiene, entonces es valido agregar a la lista de videos que vamos a regresar
									//Asi seguramos que se agregaran los videos
									//FIXME aqui considerar el parametro de numero de videos que puede aver antes de repetir.
									VideoDTO video=new VideoDTO(v);
									listVideos.add(video);
								}else{
									int posicionEncontrada=posicionDondeSeLocalizaVideo;
									int ultimaPosicion=videosEncolados.size();//Es la posicion donde iria el nuevo video a agregar
									//Si la nueva posicion del video - la posicion en que se encuentra el video que ya esta encolado es mayor al numero de posiciones entre video,
									//entonces si se agrega para que se vuelva a reproducir.
									if((ultimaPosicion-posicionEncontrada)>NUM_MIN_ENTRE_VIDEOS){
										VideoDTO video=new VideoDTO(v);
										listVideos.add(video);
									}
								}
							}
						}else{//Si no hay una playlist de usuarios actual, entonces se muestran todos los videos disponibles
							for(YoutubeVideo v:videos){
								VideoDTO video=new VideoDTO(v);
								listVideos.add(video);
							}
						}
					}else{
						for(YoutubeVideo v:videos){
							VideoDTO video=new VideoDTO(v);
							listVideos.add(video);
						}
					}
				}
				playlistDTO.setPlaylistId(playlist.getId());
			}
			playlistDTO.setVideos(listVideos);
			//FIXME Agregar DTO para android que contenga el mensaje y si se guardo bien o no la lista.
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,playlistDTO);
		}
		return result;
	}
	
	/**
	 * Valida si el nuevo video ya esta entre los encolados o no.
	 * @param videosEncolados
	 * @param nuevoVideo
	 * @return posicion en el que se encuentra el video encolado
	 */
	private int existeVideoRepetidoEnEncolados(List<YoutubeUserVideo> videosEncolados, YoutubeVideo nuevoVideo){
		int posicion=-1;
		if(!isEmptyCollection(videosEncolados) && nuevoVideo!=null){
			String idVideoBuscar=nuevoVideo.getIdVideo();
			int indice=0;
			for(YoutubeUserVideo encolado:videosEncolados){
				String idVideoEncolado=(encolado!=null && encolado.getVideo()!=null)?encolado.getVideo().getIdVideo():null;
				if(idVideoEncolado!=null && idVideoBuscar!=null && idVideoBuscar.equals(idVideoEncolado)){
					posicion=indice;
					break;
				}
				indice++;
			}
		}
		return posicion;
	}
	
	//En esta parte se agrega el video (que existe en la playlist del negocio) para agregarla a la playlist de usuarios del negocio
	//Esta playlist de usuarios es la que se va a estar reproduciendo.
	@Action(value=JSON_ACTION_ADD_TO_PLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","playlistDTO"})
	})
	public String agregarVideo(){
		playlistDTO=new PlaylistVideoDTO();
		try {
			negocio=negocioService.consultarPorId(bussinessID);
			YoutubePlaylist playlistOfBusiness= youtubePlaylistService.obtenerPlaylistPorNegocio(bussinessID);
			if(isNull(playlistOfBusiness)){
				String playlistName="QRksPlaylistUsr-"+negocio.getNombre();
//				playlistId=YoutubeUtil.createNewPlayList(playlistName,"Playlist de usuarios para el Negocio:"+negocio.getNombre());
//				negocio.setUserPlaylistId(playlistId);
				playlistOfBusiness.setNombre(playlistName);
				playlistOfBusiness.setNegocio(negocio);
				youtubePlaylistService.guardarPlaylist(playlistOfBusiness);
			}
			//Se agrega el video
			YoutubeVideo vid=new YoutubeVideo();
			vid.setPlaylist(playlistOfBusiness);
			vid.setIdVideo(videoId);
			youtubeVideoService.agregarVideo(vid);
//			YoutubeUtil.addVideoToPlaylist(playlistId, videoId);
			//Una vez que se agrega el video a la lista de usuarios, se vuelve a obtener la lista de videos pero del negocio 
//			List<PlaylistItem> videos=YoutubeUtil.getVideosFromPlaylist(playlistOfBusiness);
			List<VideoDTO> listVideos=new ArrayList<VideoDTO>();
			List<YoutubeVideo> videos=youtubeVideoService.consultarVideosPorPlaylist(playlistOfBusiness.getId());//Una vez que se agrega el video a la lista de usuarios, se consulta de nuevo
			if(videos!=null && !videos.isEmpty()){
				for(YoutubeVideo v:videos){
					VideoDTO video=new VideoDTO(v);
					listVideos.add(video);
				}
			}
			playlistDTO.setVideos(listVideos);
			playlistDTO.setPlaylistId(playlistOfBusiness.getId());
			//FIXME Agregar DTO para android que contenga el mensaje y si se guardo bien o no la lista.
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,playlistDTO);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_YOUTUBE_ADD_VID_TO_USRPLAYLIST,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","playlistDTO"})
	})
	public String agregarVideoAUserPlaylist(){
		playlistDTO=new PlaylistVideoDTO();
		try {
			negocio=negocioService.consultarPorId(bussinessID);
			YoutubeUserPlaylist playlist=youtubeUserPlaylistService.obtenerPlaylistPorNegocio(bussinessID);
			YoutubePlaylist playlistOfBusiness= youtubePlaylistService.obtenerPlaylistPorNegocio(bussinessID);
			if(isNull(playlist)){
				throw new QRocksException("No existe una usr-playlist para este negocio.");
			}
			if(isNull(playlistOfBusiness)){
				throw new QRocksException("No existe una playlist para este negocio.");
			}
			//Se agrega el video
//			YoutubeUtil.addVideoToPlaylist(playlistId, videoId);
			
			YoutubeUserVideo video=new YoutubeUserVideo();
			YoutubeVideo v=youtubeVideoService.consultarVideoPorIdVideo(videoId);
			video.setVideo(v);
			video.setUserPlaylist(playlist);
			PedidoMesa pedidoMesa=pedidoMesaService.obtenerPedidoActivoPorMesa(qrMkey);
			if(pedidoMesa!=null){
				Date fechaSeleccion=pedidoMesa.getFechaSeleccionVideo();
				if(fechaSeleccion==null){
					video.setReproducido(0);
					youtubeUserVideoService.agregarVideo(video);
					fechaSeleccion=new Date();
					pedidoMesa.setFechaSeleccionVideo(fechaSeleccion);
					pedidoMesaService.actualizarPedido(pedidoMesa);
				}else{
					Calendar c=Calendar.getInstance();
					c.setTime(fechaSeleccion);
					Date fechaActual=new Date();
					long miliSecondsFechaSeleccion=c.getTimeInMillis();
					long miliSecondsFechaActual=fechaActual.getTime();
					long diferencia=miliSecondsFechaActual-miliSecondsFechaSeleccion;
					long numSegundosDiferencia=(diferencia/1000L);
					//Si el num. de segundos es mayor a los segundos de la configuracion, entonces ya se puede agregar.
					if(numSegundosDiferencia>=NUM_SEGUNDOS_CONFIGURACION){
						video.setReproducido(0);
						youtubeUserVideoService.agregarVideo(video);
						fechaSeleccion=new Date();
						pedidoMesa.setFechaSeleccionVideo(fechaSeleccion);
						pedidoMesaService.actualizarPedido(pedidoMesa);
					}else{
						pedidoMesa.setTiempoEspera(Integer.parseInt(String.valueOf(MobileMediaplayerAction.NUM_SEGUNDOS_CONFIGURACION-numSegundosDiferencia)));
					}
				}
			}
//			youtubeUserVideoService.agregarVideo(video);
			//Una vez que se agrega el video a la lista de usuarios, se vuelve a obtener la lista de videos pero del negocio 
//			List<PlaylistItem> videos=YoutubeUtil.getVideosFromPlaylist(playlistOfBusiness);
			List<YoutubeVideo> videos=youtubeVideoService.consultarVideosPorPlaylist(playlistOfBusiness.getId());//Una vez que se agrega el video a la lista de usuarios, se consulta de nuevo
			//la lista de videos pero del playlist del negocio.
			List<VideoDTO> listVideos=new ArrayList<VideoDTO>();
			if(videos!=null && !videos.isEmpty()){
				for(YoutubeVideo vid:videos){
					VideoDTO current=new VideoDTO(vid);
					listVideos.add(current);
				}
			}
//			if(videos!=null && !videos.isEmpty()){
//				for(PlaylistItem v:videos){
//					VideoDTO video=new VideoDTO(v);
//					listVideos.add(video);
//				}
//			}
			playlistDTO.setVideos(listVideos);
			playlistDTO.setPlaylistId(playlist.getId());
			//FIXME Agregar DTO para android que contenga el mensaje y si se guardo bien o no la lista.
		} catch (QRocksException e) {
			onErrorAndroidMessage(e,playlistDTO);
		}
		return result;
	}

	public Long getBussinessID() {
		return bussinessID;
	}

	public void setBussinessID(Long bussinessID) {
		this.bussinessID = bussinessID;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public PlaylistVideoDTO getPlaylistDTO() {
		return playlistDTO;
	}

	public void setPlaylistDTO(PlaylistVideoDTO playlistDTO) {
		this.playlistDTO = playlistDTO;
	}

	public void setNegocioService(NegocioService negocioService) {
		this.negocioService = negocioService;
	}

	public void setQrMkey(String qrMkey) {
		this.qrMkey = qrMkey;
	}
	
	
}
