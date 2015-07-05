

// Define some variables used to remember state.
var playlistId, channelId;

// After the API loads, call a function to enable the playlist creation form.
function handleAPILoaded() {
  enableForm();
}

// Enable the form for creating a playlist.
function enableForm() {
	//Hacer cosas despues de cargar el api de youtube
}
/**
 * Funcion que crea una nueva lista en youtube
 * @param title	Es el titulo de la nueva lista de reproduccion
 * @param description	Es una breve descripcion de la lista
 * @param onSuccess 	Es una funcion callback, que recibira el resultado de la respuesta para saber si se creo o no la lista.
 */
function createNewPlaylist(title,description,onSuccess) {
	  var request = gapi.client.youtube.playlists.insert({
	    part: 'snippet,status',
	    resource: {
	      snippet: {
	        title: title,
	        description: description
	      },
	      status: {
	        privacyStatus: 'private'
	      }
	    }
	  });
	  request.execute(function(response) {
		  var result = response.result;
		  if(result){
			  var playListInfo=result;
			  onSuccess(playListInfo);
		  }else{
			  alert("Could not create playlist!");
		  }
	  });
}

/**
 * Agrega un video a una lista correspondiente
 * @param playlistId	Es el id de la playlist de youtube
 * @param videoId 		Es el id del video de youtube
 * @param onSuccess		Es una funcion callback que se manda a llamar despues de que el servidor responde, para saber si se agrego o no el video.
 */
function addVideoToPlayList(playlistId,videoId,onSuccess){
	addToPlaylist(playlistId,videoId,onSuccess);
}

/**
 * Permite agrega un video a la playlist.
 * @param idPlaylist Id de la playlist de youtube
 * @param id Id del video a agregar
 * @param startPos Posicion inicial del video (0 por default)
 * @param endPos Posicion final en la cual se terminara de reproducir este video. si no se proporciona, se reproducira todo el video.
 */
function addToPlaylist(idPlaylist,id,callback, startPos, endPos) {
	  var details = {
	    videoId: id,
	    kind: 'youtube#video'
	  };
	  if (startPos != undefined) {
	    details['startAt'] = startPos;
	  }
	  if (endPos != undefined) {
	    details['endAt'] = endPos;
	  }
	  var request = gapi.client.youtube.playlistItems.insert({
	    part: 'snippet',
	    resource: {
	      snippet: {
	        playlistId: idPlaylist,
	        resourceId: details
	      }
	    }
	  });
	  request.execute(function(response) {
		  callback(response.result);
	  });	
}

/**
 * Metodo para ver la lista de videos de una lista 
 * @param playlistId id de la lista de la cual se quieren obtener todos los videos
 * @param onSuccess Funcion que se ejecutara despues de que se obtenga la lista de los videos.
 */
function showAllVideosFromPlayList(playlistId,onSuccess){
	var request = gapi.client.youtube.playlistItems.list({
		playlistId: playlistId,
	    part: 'snippet',
	    maxResults:10
	});
	 request.execute(function(response) {
		 if(response!=null){
			 onSuccess(response);
		 }
	 });
}
/**
 * Obtiene los videos pero paginados, por si la lista de reproduccion tiene mas de "maxResults" como registros, entonces se mandara el pageToken.
 * @param playlistId
 * @param token
 * @param onSuccess
 */
function showPaginatedAllVideosFromPlayList(playlistId,token,onSuccess){
	var request = gapi.client.youtube.playlistItems.list({
		playlistId: playlistId,
	    part: 'snippet',
	    maxResults:10,
	    pageToken:token
	});
	 request.execute(function(response) {
		 if(response!=null){
			 onSuccess(response);
		 }
	 });
}

/**
 * Funcion que busca un video en youtube y trae una lista los primero 20 videos.
 * See more information on: https://developers.google.com/youtube/v3/docs/search/list
 * @param q Es la palabra que se buscara en youtube, debe ser el nombre del video o algo que se le parezca.
 * @param onSuccess 	Funcion callback que se ejecutara despues de que se obtiene la respuesta del servidor de youtube
 */
function searchVideo(q,onSuccess) {
	  var request = gapi.client.youtube.search.list({
	    q: q,
	    part: 'snippet',
	    type:'video',
	    videoLicense:'youtube',
//	    videoSyndicated:'true',
	    videoDefinition:'any',
	    maxResults:20
	  });

	  request.execute(function(response) {
		  if(response!=null){
			  //Videos
			  onSuccess(response.items);
		  }
	  });
	}