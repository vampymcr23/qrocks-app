

// Define some variables used to remember state.
var playlistId, channelId;

// After the API loads, call a function to enable the playlist creation form.
function handleAPILoaded() {
  enableForm();
}

// Enable the form for creating a playlist.
function enableForm() {
  $('#playlist-button').attr('disabled', false);
  $('#search-button').attr('disabled', false);
  //FIXME Aqui se debe de obtener el id del playlist o se debe de crear una PLAYLIST con el id harcodeada y guardarse en DB por cada negocio.
  playlistId=$('#playlist-id').val();
}

// Create a private playlist.
function createPlaylist() {
  var request = gapi.client.youtube.playlists.insert({
    part: 'snippet,status',
    resource: {
      snippet: {
        title: 'Test Playlist',
        description: 'A private playlist created with the YouTube API'
      },
      status: {
        privacyStatus: 'private'
      }
    }
  });
  request.execute(function(response) {
    var result = response.result;
    if (result) {
      playlistId = result.id;
      $('#playlist-id').val(playlistId);
      $('#playlist-title').html(result.snippet.title);
      $('#playlist-description').html(result.snippet.description);
    } else {
      $('#status').html('Could not create playlist');
    }
  });
}

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
		  /*
	    var result = response.result;
	    if (result) {
	      playlistId = result.id;
	      $('#playlist-id').val(playlistId);
	      $('#playlist-title').html(result.snippet.title);
	      $('#playlist-description').html(result.snippet.description);
	    } else {
	      $('#status').html('Could not create playlist');
	    }
	    */
	  });
	}

// Add a video ID specified in the form to the playlist.
function addVideoToPlaylist() {
  addToPlaylist($('#video-id').val());
}

function addVideoToPlayListWithId(videoId){
	addToPlaylist(videoId);
}

function addVideoToPlayList2(playlistId,videoId){
	addToPlaylist2(videoId);
}


// Add a video to a playlist. The "startPos" and "endPos" values let you
// start and stop the video at specific times when the video is played as
// part of the playlist. However, these values are not set in this example.
function addToPlaylist(id, startPos, endPos) {
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
        playlistId: playlistId,
        resourceId: details
      }
    }
  });
  request.execute(function(response) {
    $('#status').html('<pre>' + JSON.stringify(response.result) + '</pre>');
  });
}

//Search for a specified string.
//See more information on: https://developers.google.com/youtube/v3/docs/search/list
function search() {
  var q = $('#query').val();
  var request = gapi.client.youtube.search.list({
    q: q,
    part: 'snippet',
    type:'video',
    maxResults:10
  });

  request.execute(function(response) {
    var str = JSON.stringify(response.result);
//    $('#search-container').html('<pre>' + str + '</pre>');
    var htmlInfo="";
    if(response!=null && response.items!=null){
    	var items=response.items;
    	items.forEach(function(item){
    		var info=item.snippet;
    		var idVideo=item.id.videoId;
    		var titulo=info.title;
    		var imgUrl=info['thumbnails']['default']['url'];
    		htmlInfo+="<p><h3>"+titulo+"</h3><img src='"+imgUrl+"' onClick='addVideoToPlayListWithId(\""+idVideo+"\");'/></p>";
    	});
    	$('#search-container').html(htmlInfo);
    }
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
	    part: 'snippet'
	});
	 request.execute(function(response) {
		 if(response!=null){
			 onSuccess(response);
		 }
	 });
}

function searchVideo(q,onSuccess) {
	  var request = gapi.client.youtube.search.list({
	    q: q,
	    part: 'snippet',
	    type:'video',
	    maxResults:10
	  });

	  request.execute(function(response) {
		  if(response!=null){
			  //Videos
			  onSuccess(response.items);
		  }
		  /*
	    var str = JSON.stringify(response.result);
	    var htmlInfo="";
	    if(response!=null && response.items!=null){
	    	var items=response.items;
	    	items.forEach(function(item){
	    		var info=item.snippet;
	    		var idVideo=item.id.videoId;
	    		var titulo=info.title;
	    		var imgUrl=info['thumbnails']['default']['url'];
	    		htmlInfo+="<p><h3>"+titulo+"</h3><img src='"+imgUrl+"' onClick='addVideoToPlayListWithId(\""+idVideo+"\");'/></p>";
	    	});
	    	$('#search-container').html(htmlInfo);
	    }
	    */
	  });
	}

function addToPlaylist2(idPlaylist,id, startPos, endPos) {
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
	    $('#status').html('<pre>' + JSON.stringify(response.result) + '</pre>');
	  });
	}