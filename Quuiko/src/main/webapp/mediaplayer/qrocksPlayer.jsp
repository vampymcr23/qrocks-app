<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Player</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="fonts/fingerpaint-regular.css">
	<title>Bienvenida</title>
	<script src="js/mobile/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<!-- PLaylist -->
<%-- 	<script src="js/youtube/auth.js"></script> --%>
    <script src="https://www.youtube.com/iframe_api"></script>
<%--     <script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"></script> --%>
	<!-- End Playlist -->
	<!-- END Scripts -->
	<style>
		html {
		  height: 100%;
		  width: 100%;
		  overflow: hidden;
		  min-width: 100%;
		  min-height: 100%;
		}
		
		body {
		  height: 100%;
		  width: 100%;
		  padding: 0;
		  margin: 0;
		}

		.fondo{
 			background-color: rgba(0, 0,0, 0.9); 
 			height:100%;
/* 			background-color: rgba(79, 80,81, 0.15); */
		}
		#playListDiv{
			text-align:center;
		}
		
		/**ESTILO PARA EL VIDEO DE YOUTUBE**/
		html,body,.container
		{
		    height:100%;
		}
		.container-fluid{
		    display:table;
		    width: 100%;
		    margin-top: -50px;
		    padding: 50px 0 0 0; /*set left/right padding according to needs*/
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		header{
		    background: #000;
		    height: 150px;
		    font-size:1.8em;
		    color:#fff;
		    padding-top:50px;
		}
		
		.row-fluid{
		    height: 100%;
		    display: table-row;
		}
		.row-fluid > .col-md-3{
		    display: table-cell;
		    background: pink;
		  	float: none;
		}
		.row-fluid > .col-md-9
		{
		    display: table-cell;
		    background: yellow;
		  	float: none;
		}
		
		.quuiko{
			font-family: 'FingerPaintRegular';
		}
		
	</style>
	<style type="text/css">
	/*
		form label{
			display:inline-block;
		}
		
		.contenedor label{
			width:45%;
			max-width:100px;
			display:inline-block;
			text-align: left;
		}
		
		.contenedor span{
			font-weight: bold;
			width:54%;
			min-width: 100px;
			display:inline-block;
			text-align: left;
		}
		
		.iconoMobil{
			float:left;
			margin:10px;
			vertical-align: middle;
		}
		.songStyle{
			background: #1e1e1e;
			box-shadow: inset 0px 0px 0px 1px rgba(0,0,0,0.15), 0px 2px 3px 0px rgba(0,0,0,0.1);
			text-align: center;
			padding: 1em 1em 1.5em 1em;
			text-align:left;
			display:inline.block;
 			background-color: #1e1e1e; 
			color:#e8e7e5;
			margin-bottom: 0em;
		}
		.songStyle h4{
			font-weight: bold;
			color:#e6eff2;
		}
		.songStyle h5{
			color:#bcedff;
		}
		.panelCentral{
			height:800px;
		}
		*/
/* 		#playListDiv{ */
/* 			height:500px; */
/* 			width:600px; */
/* 		} */
/*
/*
		#contenedorPlaylist{
			height:620px;
			overflow-y:auto;
		}
		*/
		.activeImageSong{
			display:inline-block;
			vertical-align:bottom;
			width:15%;
			max-width:50px;
			padding-right:10px;
		}
		h4.activeSong,h5.activeSong{
			color:#00fffa;
		}
		.songDiv{
			width:70%;
			display:inline-block;
		}
		.songDiv div.songImage{
			display:inline-block;
			height:80px;
			width: 20%;
			max-width: 140px;
		}
		div.songImage img{
			display:inline;
		}
		
		.songDiv div.songInfo{
			display:inline-block;
			vertical-align: top;
			height:80px;
			max-width: 79%;
		}
		.songDiv h4.songTitle{
			display:block;
		}
		
		.songDiv h5.songDescription{
			display:inline-block;
		}
		/*
		div.currentPlayList{
			height:100px;
			width:100%;
			padding-top:25px;
			padding-left:5px;
			padding-right:5px;
			background:none repeat scroll 0 0 #1E1E1E;
			overflow-x:auto;
			display:block;
		}
		*/
		
		.playlist_song{
 			width:100%; 
			height:100%;
			background: #1e1e1e;
			box-shadow: inset 0px 0px 0px 1px rgba(0,0,0,0.15), 0px 2px 3px 0px rgba(0,0,0,0.1);
			text-align: center;
			padding-top:0.3em;
			padding-left:0.5em;
			text-align:left;
			display:inline.block;
 			background-color: #1e1e1e; 
			color:#e8e7e5;
			margin-bottom: 0em;
		}
		.playlist_songDiv{
			display:inline-block;
			height:auto;
			width:200px;
		}
		.playlist_songImage{
			width: 28%;
			max-width: 65px;
			display:inline-block;
			height:99%;
		}
		.playlist_songInfo{
			display:inline-block;
			vertical-align: top;
			height:100%;
			max-width:65%;
			width:55%;
			font-size: 0.8em;
			text-align: left;
			padding-left: 5px;
			padding-top: 5px;
		}
		.playlist_songTitle{
			color:rgba(255,255,255,0.75);
			font-size:1em;
			padding-left:10px;
			
		}
		.playlist_songDescription{
			font-size: 9px;
		}
		input.playlist{
			pagging:0.2em 0.4em;
			font-size: 0.5em;
		}
		span.numResultPlaylist{
			font-size: 0.7em;
		}
		div#overlay{
			width:100%;
			height:18%;
			position: absolute;
			bottom: 0;
			left: 0;
			background:#000;
			color:white;
			z-index: 999;
			overflow-x:scroll;
		}
		div#contenedorPlaylist{
			background:#000;
			text-align: center;
		}
		div.formulario{
			padding-top: 0.1em;
			padding-bottom:0.1em;
			height:100px;
		}
		.currentSong{
			background-color:rgba(51, 50, 50, 0.75);
		}
		.titleQRocksPlayer{
			display:inline-block;
			vertical-align: top;
			padding-top: 30px;
			font-size: 1.7em;
		}
		
	</style>
	<s:url namespace="/mediaplayer" action="getPlaylist" var="urlPlaylist"/>
	<s:url namespace="/mediaplayer" action="gtVidsByUsrPlyLst" var="urlVidsUsrPlyLst"/>
	<s:url namespace="/mediaplayer" action="storeCurrentSong" var="urlCurrentSong"/>
	<s:url namespace="/mediaplayer" action="addNewPlaylist" var="urlAddPlaylist"/>
	<s:url namespace="/mediaplayer" action="updPlayedVideo" var="urlUpdateVideoPlayed"/>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<s:url namespace="/negocio" action="getImg" var="urlLogo"/>
	<script type="text/javascript">
		var playListid="${youtubeUserPlaylist.id}";
		var player=null;
		var pctWidth=0.95;
		var pctHeight=0.80;
		var pctHeightCurrentPlaylist=0.08;
		var pctHeightPlayer=0.78;
// 		var pctTitlePlaylist=0.08;
		var pctTitlePlaylist=0.15;
		var maxWidth=$( window ).width();
		var maxHeight=null;
		var heightPlayer=null;
		var playlist=new Array();
		var indexCurrentVid=0;
		var numCurrentVideos=0;
		
		$(document).ready(function(){
			maxHeight=$( document ).height();
			var heightTitlePlaylist=maxHeight*pctTitlePlaylist;
			console.debug("Height-Playlist title:"+heightTitlePlaylist);
			$('#titleplaylist').css("height",heightTitlePlaylist);
			var heightCurrentPlaylist=maxHeight*pctHeightCurrentPlaylist;
			console.debug("Height-CurrentPlaylist :"+heightCurrentPlaylist);
			$('#currentPlayList').css("height",heightCurrentPlaylist);
			heightPlayer=maxHeight*pctHeightPlayer;
			console.debug("Height-Player :"+heightPlayer);
			var heightOverlay=$('#overlay').css("height");
			heightOverlay=parseInt(heightOverlay);
			var topPosition_Overlay=maxHeight-heightCurrentPlaylist-heightOverlay;
			$('#overlay').css("top",topPosition_Overlay);
			$('#overlay').css("display","");
			recursividadPlaylist();
		});
		var NUM_SEG=10;
		function recursividadPlaylist(){
			actualizarPlaylist();
			setTimeout(recursividadPlaylist,1000*NUM_SEG);
		}
		
		function actualizarPlaylist(){
			$.ajax({
				data:{"cIdx":indexCurrentVid,'currentPl':'${youtubeUserPlaylist.id}'},
				url:"${urlVidsUsrPlyLst}",
				dataType: "json",
				async:false,
				success:function(videos){
					playlist=videos;
					if(playlist==null || playlist.length==0){
						alert("La lista de videos esta vacia.");
					}else{
						numCurrentVideos=videos.length;
						renderingVideos(videos);
					}
				}
			});
			if($('#song_0')!=null){
				$('#song_0').addClass("currentSong");
			}
		}
		
		//1.- Cuando se carga el api de youtube del Player
		function onYouTubeIframeAPIReady() {
			var realWidth=maxWidth*pctWidth;
			$('#playListDiv').css("width",realWidth+1);
			$('#playListDiv').css("height",heightPlayer+1);
	        player = new YT.Player('playListDiv', {
// 	          height: heightPlayer,
// 	          width: realWidth,
	          playerVars: { 'autoplay': 1, 'controls': 1 ,'disablekb':1,'iv_load_policy':3,'loop':1,'modestbranding':1,'playsinline':0,'showinfo':0},
	          events: {
	            'onReady': onPlayerReady,
	            'onStateChange': onPlayerStateChange
	          }
	        });
	        
	    }
		
		function inicializarReproductor(){
			var quality="hd720"; //Puede ser [small, medium, large, hd720, hd1080, highres or default]
			var currentVid=playlist[indexCurrentVid];
			player.cueVideoById({
				videoId: currentVid.video.idVideo,
				startSeconds:0,
				suggestedQuality:quality
			});
			
			player.playVideo();
		}
		
		// 2. The API will call this function when the video player is ready.
	    function onPlayerReady(event) {
// 	    	var frame=$('#playListDiv iframe');
// 			if(frame!=null){
// 				$(frame).addClass("col-lg-2 col-md-6 col-sm-12 col-xs-12");
// 			}
			var quality="hd720"; //Puede ser [small, medium, large, hd720, hd1080, highres or default]
			var currentVid=playlist[indexCurrentVid];
			player.cueVideoById({
				videoId: currentVid.video.idVideo,
				startSeconds:0,
				suggestedQuality:quality
			});
			player.playVideo();
	    }
		
	 	// 5. Cuando el player cambia de estado
	    //    en esta funcion, se valida que esta en estado PLAYING (state=1),
	    //    Se va a reproducir solo 6 segundos y luego se detiene
	    var done = false;
	 	
	    function onPlayerStateChange(event) {
	        if (event.data == YT.PlayerState.PLAYING && !done) {
				console.debug("Se inicio otro video");
        	}
	        if(event.data == YT.PlayerState.ENDED){
	        	console.debug("Se detuvo el video");
				actualizarPlaylist();
				var videoActual=playlist[indexCurrentVid];
				marcarVideoComoReproducido(videoActual);
	        	cargarSiguienteVideo();
	        }
	        if(event.data == YT.PlayerState.BUFFERING){
	        	console.debug("Buffering video");
	        }
	    }
	    
	    function alerta(){
	    	
	    }
	    
	    function sigCancion(){
	    	actualizarPlaylist();
	    	cargarSiguienteVideo();
	    	moverBarra();
	    }
	    
	    function moverBarra(){
	    	var widthEachVideoDiv=($('#song_0')!=null)?$('#song_0').css("width"):0;
	    	widthEachVideoDiv=parseFloat(widthEachVideoDiv);
	    	var currentScrollPosition=$("#overlay").scrollLeft();
	    	var nextPosition=currentScrollPosition + widthEachVideoDiv;
	    	 $("#overlay").scrollLeft(nextPosition);
	    }
	    
	    function moverBarraAPosicionVideo(indiceVideo){
	    	var widthEachVideoDiv=($('#song_0')!=null)?$('#song_0').css("width"):0;
	    	widthEachVideoDiv=parseFloat(widthEachVideoDiv);
	    	var nextPosition=widthEachVideoDiv*indiceVideo;
	    	$("#overlay").scrollLeft(nextPosition);
	    }
	    
	    /**
	    * Funcion que marca al video que se acaba de terminar como 'Reproducido' (valor 1)
	    */
	    function marcarVideoComoReproducido(videoActual){
	    	$.ajax({
				data:{"idVideo":videoActual.id},
				url:'${urlUpdateVideoPlayed}',
				dataType: "json",
				async:false,
				success:function(){
				}
			});
	    }
	    
	    function cargarSiguienteVideo(){
	    	indexCurrentVid++;
	    	var quality="hd720"; //Puede ser [small, medium, large, hd720, hd1080, highres or default]
	    	//Si ya se acabaron las canciones entonces se reinicia la lista
	    	if(indexCurrentVid>=playlist.length){
	    		indexCurrentVid=0;
	    	}
	    	var currentVid=playlist[indexCurrentVid];
	    	$('#song_'+indexCurrentVid).addClass("currentSong");
	    	for(var i=0;i<playlist.length;i++){
	    		if(i!=indexCurrentVid && $('#song_'+i)!=null){
	    			$('#song_'+i).removeClass("currentSong");
	    		}
	    	}
	    	var quality="hd720"; //Puede ser [small, medium, large, hd720, hd1080, highres or default]
			player.cueVideoById({
				videoId: currentVid.video.idVideo,
				startSeconds:0,
				suggestedQuality:quality
			});
			player.playVideo();
	    }
	      
	    function stopVideo() {
	        player.stopVideo();
	    }
	    
	    function agregarVideo(){
	    	var idVideo=$('#nuevoVideoUrl').val();
	    	var youtubeUrl="http://www.youtube.com/v/"+idVideo;
	    	player.cueVideoByUrl({mediaContentUrl:youtubeUrl});
	    	console.debug("Se agrego el video con la url:"+youtubeUrl);
	    	player.playVideo();
	    }
		
		
		function renderingVideos(videos){
			var html='<div id="playlist_song" class="playlist_song">';
			var index=0;
			videos.forEach(function(v){
	    		var idVideo=v.video.idVideo;
	    		var titulo=v.video.titulo;
	    		var imgUrl=v.video.urlImagenDefault;
	    		var descripcion=v.video.descripcion;
	    		descripcion=(descripcion!=null && descripcion.length>150)?descripcion.substring(0,150)+"...":descripcion;
	    		html+=' <div class="playlist_songDiv" id="song_'+index+'" >';
	    		html+=" 	<div class='playlist_songImage'><img src='"+imgUrl+"' width='65px' height='65px'/></div>";
				html+=' 	<div class="playlist_songInfo"><h4 class="playlist_songTitle">'+titulo+'</h4></div>';
	    		html+=' </div>';
	    		index++;
			});
			html+='</div>';
			$('#overlay').html(html);
			var widthEachVideoDiv=(index>0)?$('#song_'+(index-1)).css("width"):0;
			widthEachVideoDiv=parseFloat(widthEachVideoDiv);
			var widthVideos=((index+1)*widthEachVideoDiv);
			$('#playlist_song').css("width",widthVideos);
			moverBarraAPosicionVideo(indexCurrentVid);
			$('#song_'+indexCurrentVid).addClass("currentSong");
	    	for(var i=0;i<numCurrentVideos;i++){
	    		if(i!=indexCurrentVid && $('#song_'+i)!=null){
	    			$('#song_'+i).removeClass("currentSong");
	    		}
	    	}
		}
	</script>
</head>
<body>
<s:form namespace="/m/play" method="POST" cssClass="form-horizontal" style="height:100%;">
<header class="quuiko">Quuiko.com</header>
<div class="container-fluid fondo">
	    <div class="row-fluid ">
	    		<!-- Contenedor -->
  					<div class="col-md-10 col-sm-10">
  						<div id="playListDiv" class="col-md-9">
  						</div>
  					</div>
	    </div>
	</div>

	<s:hidden name="negocio.id" id="idNegocio" />
	<s:hidden name="youtubeUserPlaylist.id" value="%{youtubeUserPlaylist.id}"/>
	<s:hidden name="qrMID"/>
	
	
	
	<!-- OLD -->
		<!-- Intro -->
<!-- 			<div  id="titleplaylist" class="formulario"> -->
<!-- 				<div id="login-container" class="pre-auth" style="display:none;">This application requires access to your YouTube account. Please <a href="#" id="login-link">authorize</a> to continue. -->
<!-- 			    </div> -->
<!-- 				<header> -->
<%-- 					<img src="${urlLogo}?negocio.id=${negocioLogeado.id}" width="100px" height="auto" title="" alt="" onclick="sigCancion();"/> --%>
<%-- 					<h4 class="alt titleQRocksPlayer"><strong>QRocks</strong></h4> --%>
<!-- 				</header> -->
<!-- 			</div> -->
<!-- 			<div id="contenedorPlaylist"> -->
<!-- 				<div id="playListDiv"> -->
					
<!-- 				</div> -->
<!-- 			</div> -->
			
			<div id="currentPlayList" class="currentPlayList" style="display:;">
				<h4><marquee>Mis mensajes</marquee> </h4>
				<span id="numResultsPlayList" class="numResultPlaylist"></span>
				<div id="myVideoList">
									
				</div>
			</div>
			<div id="overlay" style="display:;">Texto de prueba</div>
</s:form>
</body>
</html>