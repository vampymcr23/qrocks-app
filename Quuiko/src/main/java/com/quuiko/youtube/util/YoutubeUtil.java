package com.quuiko.youtube.util;

import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.onError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.PlaylistItems;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.common.collect.Lists;
import com.quuiko.exception.QRocksException;

public class YoutubeUtil {
	private static Logger log=Logger.getLogger(YoutubeUtil.class.getName());
	private static final long MAX_RESULTS=3L;
	/**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    
    
    public static YouTube getYoutube() throws QRocksException{
    	log.log(Level.INFO,"INICIALIZANDO YOUTUBE [getYoutube]...");
    	if(youtube==null){
    		System.out.println("=========inicializando youtube...===========");
    		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
            try {
            	log.log(Level.INFO,"Autorizando credenciales...ACTUALIZADO");
    			Credential credenciales=Auth.authorize(scopes, "qrocksList");
//            	Credential credenciales=Auth.authorizeAsService();
    			log.log(Level.INFO,"Credenciales autorizadas...ACTUALIZADO");
    			youtube=new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credenciales)
                .setApplicationName("qrocks-youtube-app")
                .build();
    		} catch (Exception e) {
    			log.log(Level.INFO,"Ocurrio un error en autenticacion de credenciales causado por:"+e.getMessage());
    			throw new QRocksException(e.getMessage(),true);
    		}
    	}
    	return youtube;
    }
    
    public void enlistar() throws QRocksException{
    	log.log(Level.INFO,"INICIALIZANDO LISTA [enlistar]...");
    	// This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        try {
			Credential credenciales=Auth.authorize(scopes, "qrocksList");
			youtube=new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credenciales)
            .setApplicationName("qrocks-youtube-app")
            .build();
			showList();
		} catch (Exception e) {
			throw new QRocksException(e.getMessage(),true);
		}
    }
    
    /**
     * Crea una lista de manera privada
     * @param name	Nombre de la playlist
     * @param description Descripcion de la playlist
     * @return Id de la playlist generada
     * @throws QRocksException 
     */
    public static String createNewPlayList(String name,String description) throws QRocksException{
    	log.log(Level.INFO,"CREANDO LISTA [createNewPlayList]...");
    	Playlist playlistInserted=null;
    	String playlistId=null;
    	PlaylistSnippet snippet=new PlaylistSnippet();
    	snippet.setTitle(name);
    	snippet.setDescription(description);
    	PlaylistStatus privacyStatus=new PlaylistStatus();
    	privacyStatus.setPrivacyStatus("private");
    	Playlist youtubePlaylist=new Playlist();
    	youtubePlaylist.setSnippet(snippet);
    	youtubePlaylist.setStatus(privacyStatus);
    	try {
    		YouTube y=getYoutube();
    		log.log(Level.INFO,"Objeto Youtube:"+y);
			YouTube.Playlists.Insert playlistCommand=y.playlists().insert("snippet,status", youtubePlaylist);
			log.log(Level.INFO,"Objeto playlistCommand:"+playlistCommand);
			playlistInserted=playlistCommand.execute();
			playlistId=playlistInserted.getId();
			System.out.println("New playlist named '"+playlistInserted.getSnippet().getTitle()+"' with id:"+playlistInserted.getId());
		} catch (Exception e) {
			throw new QRocksException(e.getMessage(),true);
		}
    	return playlistId;
    }
    
    /**
     * Agrega un nuevo video a una playlist 
     * @param playlistId Id de la playlist de youtube
     * @param videoId Id del video que se quiere agregar a la playlist
     * @return
     */
    public static PlaylistItem addVideoToPlaylist(String playlistId,String videoId) throws QRocksException{
    	if(!isValid(playlistId)){
    		onError("Ha ocurrido un error al intentar cargar la playlist con identificador vacio");
    	}
    	if(!isValid(videoId)){
    		onError("Ha ocurrido un error al intentar agregar un video vacio a la playlist");
    	}
    	PlaylistItem newVideoInserted=null;
    	try {
    		PlaylistItem newVideo=new PlaylistItem();
    		ResourceId resourceId=new ResourceId();
    		resourceId.setVideoId(videoId);
    		resourceId.setKind("youtube#video");
    		PlaylistItemSnippet snippet=new PlaylistItemSnippet();
    		snippet.setPlaylistId(playlistId);
    		snippet.setResourceId(resourceId);
    		newVideo.setSnippet(snippet);
			PlaylistItems.Insert playlistItemCommand=getYoutube().playlistItems().insert("snippet", newVideo);
			newVideoInserted=playlistItemCommand.execute();
			System.out.println("New video named:"+newVideoInserted.getSnippet().getTitle()+" has been inserted in playlist "+newVideoInserted.getSnippet().getPlaylistId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QRocksException(e.getMessage(),true);
		}
    	return newVideoInserted;
    }
    
    public static List<PlaylistItem> getVideosFromPlaylist(String playlistId) throws QRocksException{
    	List<PlaylistItem> videos=new ArrayList<PlaylistItem>();
    	try {
			YouTube.PlaylistItems.List playlistItemsCommand=getYoutube().playlistItems().list("snippet");
			playlistItemsCommand.setPlaylistId(playlistId);
			playlistItemsCommand.setMaxResults(MAX_RESULTS);
			PlaylistItemListResponse response=playlistItemsCommand.execute();
			if(response!=null){
				List<PlaylistItem> videoList=response.getItems();
				Integer numResultados=response.getPageInfo().getTotalResults();
				String nextToken=response.getNextPageToken();
				System.out.println("Num.videos:"+numResultados);
				System.out.println("===================================");
				for(PlaylistItem v:videoList){
					videos.add(v);
				}
				if(nextToken!=null && !nextToken.isEmpty()){
					while((nextToken=getVideosFromPlaylist(playlistId,nextToken,videos))!=null){
						System.out.println("Fetchin next "+MAX_RESULTS+" videos");
					}
				}
				System.out.println("===================================");
			}
			System.out.println("Playlist command:"+playlistItemsCommand);
		} catch (IOException e) {
			e.printStackTrace();
			throw new QRocksException("Ocurrio un error al intentar cargar la playlist:"+playlistId,true);
		}
    	return videos;
    }
    
    
    /**
     * Obtiene el listado de videos de una playlist por paginacion (token)
     * @param playlistId Id de la playlist 
     * @param token Token de la paginacion que trae los siguientes 50 registros
     * @param list	Lista de tipo PlaylistItem que contiene TODOS los videos de la playlist, como es un metodo recursivo, esta lista se va a ir llenando por cada llamada recursiva.
     * @return nextToken	Es el token de la siguiente pagina de videos, nos sirve para determinar cuando se termina de mandar a llamar recursivamente este metodo
     * @throws QRocksException 
     */
    public static String getVideosFromPlaylist(String playlistId,String token,List<PlaylistItem> list) throws QRocksException{
    	String nextToken=null;
    	try {
			YouTube.PlaylistItems.List playlistItemsCommand=getYoutube().playlistItems().list("snippet");
			playlistItemsCommand.setPlaylistId(playlistId);
			playlistItemsCommand.setPageToken(token);
			playlistItemsCommand.setMaxResults(MAX_RESULTS);
			PlaylistItemListResponse response=playlistItemsCommand.execute();
			if(response!=null){
				nextToken=response.getNextPageToken();
				if(list==null){
					list=new ArrayList<PlaylistItem>();
				}
				List<PlaylistItem> videos=response.getItems();
				list.addAll(videos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new QRocksException(e.getMessage(),true);
		}
    	return nextToken;
    }
    
    public void showList() throws QRocksException{
    	try {
			YouTube.PlaylistItems.List playlistItemsCommand=getYoutube().playlistItems().list("snippet");
			playlistItemsCommand.setPlaylistId("PLsUbc_EMhJe9OEPG4TP00x6Yc7YNIsSO3");
			PlaylistItemListResponse response=playlistItemsCommand.execute();
			if(response!=null){
				List<PlaylistItem> lista=response.getItems();
				System.out.println("Lista:"+lista);
				System.out.println("Num.videos:"+lista.size());
				System.out.println("===================================");
				for(PlaylistItem cancion:lista){
					System.out.println("----------------");
					System.out.println("Titulo:"+cancion.getSnippet().getTitle());
//					System.out.println("Descripcion:"+cancion.getSnippet().getDescription());
					Thumbnail thumbnails=cancion.getSnippet().getThumbnails().getMedium();
					String url=thumbnails.getUrl();
					System.out.println("----------------");
				}
				System.out.println("===================================");
			}
			System.out.println("Playlist command:"+playlistItemsCommand);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QRocksException(e.getMessage(),true);
		}
    }
    
    public static void main(String[] a){
//    	String playlistId=YoutubeUtil.createNewPlayList("QRocksPlaylist-test", "Lista de prueba");
//    	System.out.println("Lista nueva creada con el id:"+playlistId);
//    	List<PlaylistItem> videos=YoutubeUtil.getVideosFromPlaylist("PLsUbc_EMhJe9OEPG4TP00x6Yc7YNIsSO3");
    	String playlistId="PLsUbc_EMhJe_Jp0s2pfYKuU0VoRmvQzU6";
    	try {
			YoutubeUtil.addVideoToPlaylist(playlistId, "acIU7yxzJ70");
		} catch (QRocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	List<PlaylistItem> videos=null;
		try {
			videos = YoutubeUtil.getVideosFromPlaylist(playlistId);
		} catch (QRocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Playlist...");
    	for(PlaylistItem video:videos){
    		String nombreVideo=video.getSnippet().getTitle();
    		nombreVideo=(nombreVideo!=null && !nombreVideo.isEmpty() && nombreVideo.length()>100)?nombreVideo.substring(0,100):nombreVideo;
    		System.out.println("Video:"+nombreVideo);
    	}
    }
    
}
