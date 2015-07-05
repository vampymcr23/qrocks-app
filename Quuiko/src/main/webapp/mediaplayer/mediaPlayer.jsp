<%@page import="com.quuiko.actions.MPedidoAction"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String actionAgregarAmigo=MPedidoAction.ACTION_AGREGAR_AMIGO;
	String actionGuardarPedido=MPedidoAction.ACTION_GUARDAR_PEDIDO;
	System.out.println("Action de Amigo:"+actionAgregarAmigo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Menu</title>
	<!-- Scripts -->
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel-panels.min.js"></script>
	<script type="text/javascript" src="js/mobile/init.js"></script>
	<!-- PLaylist -->
	<script type="text/javascript" src="js/jplayer/query.for.playlist.min.js"></script>
	<script type="text/javascript" src="js/jplayer/jquery.jplayer.min.js"></script>
	<script type="text/javascript" src="js/jplayer/jplayer.playlist.min.js"></script>
	<!-- End Playlist -->
	<!-- END Scripts -->
	<link type="text/css" href="css/jplayer/jplayer.blue.monday.css" rel="stylesheet" media="all" />
	<style type="text/css">
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
		#playListDiv{
			height:auto;
		}
		#contenedorPlaylist{
			height:620px;
			overflow-y:auto;
		}
		.activeImageSong{
			display:inline-block;
			vertical-align:bottom;
			width:15%;
			max-width:50px;
			padding-right:10px;
		}
		h4.activeSong,h5.activeSong{
/* 			color:#bbff00; */
			color:#00fffa;
		}
		.songDiv{
			width:70%;
			display:inline-block;
		}
		
	</style>
	<s:url namespace="/mediaplayer" action="getPlaylist" var="urlPlaylist"/>
	<s:url namespace="/mediaplayer" action="storeCurrentSong" var="urlCurrentSong"/>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		var playList=null;
		var listaCanciones=null;
		var urlPlaylist="${urlPlaylist}";
		var cancionAnterior=-1;
		$(document).ready(function(){
			$.ajax({
				dataType: "json",
				url:urlPlaylist,
				data: null,
				async:false,
				success: function (canciones){
					listaCanciones=canciones;
					playList=new jPlayerPlaylist({
						jPlayer: "#jquery_jplayer_1",
						cssSelectorAncestor: "#jp_container_1"
					},
					canciones
					, {
						playlistOptions:{
							autoPlay:true,
							loopOnPrevious:false,
						},
						loop:true,
						swfPath: "js/jplayer",
						supplied: "webmv, ogv, m4v, oga, mp3",
						smoothPlayBar: true,
						keyEnabled: true,
						audioFullScreen: true,
						ready:function(){
							var cancionActual=$('#cancionActual').val();
							cancionActual=(cancionActual==null)?0:cancionActual;
// 							if(cancionActual!=null){
// 								playList.play(cancionActual);	
// 							}
// 							else{
// 								registrarCancionActual(indiceCancion);
// 							}
							indicarCancionActual(cancionActual);
							var contador=0;
							while(contador<cancionActual){
								playList.next();	
								contador++;
							}
							playList.play();
							addHTMLCanciones();
						},
						play:function(){
// 							$('#cancionActual').val(indiceCancion);
							var indiceCancion=playList.current;
							registrarCancionActual(indiceCancion);
							indicarCancionActual(indiceCancion);
						}
					});
					
					setTimeout("actualizacionRecurrente();",15*1000);
				}
			});
		});
		
		/**
		* Funcion para actualizar cada 15seg la lista
		*/
		function actualizacionRecurrente(){
			actualizarPlayList();
			setTimeout("actualizacionRecurrente();",15*1000);
		}
		var cont=0;
		function actualizacionRecursiva(){
			setTimeout(function(){
				actualizarPlayList();
				cont++;
				actualizacionRecursiva();
			},15*1000);
			
		}
		
		function actualizarPlayList(){
			$.ajax({
				dataType: "json",
				url:urlPlaylist,
				data: null,
				async:false,
				success: function (canciones){
					var numCancionesActuales=canciones.length;
					//Si es diferente, se agregan las demas canciones
					if(numCancionesActuales>listaCanciones.length){
						var sigCancion=listaCanciones.length;
						for(var i=sigCancion;i<=numCancionesActuales;i++){
							var cancion=canciones[i];
							if(cancion!=null){
								agregarEnCola(cancion);
							}
						}
						//Se actualiza la lista de canciones
						listaCanciones=canciones;
					}
				}
			});
		}
		
		function indicarCancionActual(indice){
			//Si la cancion anterior es menor a 0, significa que la cancion actual es la primera, y probablemente la cancion anterior fue la ultima de la lista, de lo contrario,
			//la cancion anterior es la del indice-1.
			var anterior=((indice-1)<0)?(playList.playlist.length-1):(indice-1);
			var anteriorArtist=$('#songArtist_'+anterior);
			var anteriorTitle=$('#songTitle_'+anterior);
			var anteriorIcono=$('#icono_'+anterior);
			if(anteriorArtist!=null){
				$(anteriorArtist).attr("class","");
			}
			if(anteriorTitle!=null){
				$(anteriorTitle).attr("class","");
			}
			if(anteriorIcono!=null){
				$(anteriorIcono).remove();
			}
			var actualArtist=$('#songArtist_'+indice);
			var actualTitle=$('#songTitle_'+indice);
			var divIcono=$('#playedIcon_'+indice);
			if(actualArtist!=null){
				$(actualArtist).attr("class","activeSong");
			}
			if(actualTitle!=null){
				$(actualTitle).attr("class","activeSong");
			}
			if(divIcono!=null){
				$(divIcono).text("");
				$(divIcono).append('<img id="icono_'+indice+'" src="images/qrocks_playSong2.png" width="40px" height="40px" title="Agregar" alt="Agregar"/>');
			}
		}
		
		function agregarEnCola(cancionJson){
			var song={
				"title":cancionJson.title,
				"artist":cancionJson.artist,
				"mp3":cancionJson.mp3,
				"poster":cancionJson.post
			};
			playList.add(song);
			var html=obtenerHTMLPorCancion(song,false);
			$('#playListDiv').append(html);
		}
	
		function agregarCancion(cancion,artista,genero){
			var url="${urlAddSong}";
			var urlArtwork="${urlArtwork}";
			url+="?cancionAgregar.nombre="+cancion+"&cancionAgregar.artista="+artista+"&cancionAgregar.genero="+genero;
			urlArtwork+="?cancionAgregar.nombre="+cancion+"&cancionAgregar.artista="+artista+"&cancionAgregar.genero="+genero;
			var song={
				"title":cancion,
				"artist":artista,
				"mp3":url,
				poster:urlArtwork
			};
			playList.add(song);
		}
		
		function obtenerCancionActual(){
			var indiceCancion=-1;
			$.each($('.jp-playlist ul li'),function(i, elemento){
				var cssClass=$(elemento).attr("class");
				if(cssClass!=null && "jp-playlist-current"==cssClass){
					indiceCancion=i;
				}
			});
			return indiceCancion;
		}
		
		function registrarCancionActual(indiceCancion){
			$.ajax({
				dataType:'json',
				url:"${urlCurrentSong}",
				data:{"cancionActual":indiceCancion},
				async:false,
				success:function(data){
					
				}
			});
		}
		
		function addHTMLCanciones(){
			var listaCanciones=playList.playlist;
			var html="";
			if(listaCanciones!=null && listaCanciones.length>0){
				for(var i=0;i<listaCanciones.length;i++){
					var cancion=listaCanciones[i];
					var indiceCancionActual=$('#cancionActual').val();
					var isPlayed=(indiceCancionActual==i);
					html+=obtenerHTMLPorCancion(cancion,isPlayed,i);
				}
			}
			$('#playListDiv').text("");
			$('#playListDiv').append(html);
		}
		
		function obtenerHTMLPorCancion(cancion,isPlayed,posicion){
			var nombreCancion=cancion.title;
			var artista=cancion.artist;
			var html='';
			html+='<section class="songStyle">';
			if(isPlayed){
				html+=' <div class="songDiv">';
				html+='	 <h4 id="songTitle_'+posicion+'" class="activeSong">'+nombreCancion+'</h4>';
				html+='	 <h5 id="songArtist_'+posicion+'" class="activeSong">'+artista+'</h5>';
			}else{
				html+=' <div class="songDiv">';
				html+='	 <h4 id="songTitle_'+posicion+'">'+nombreCancion+'</h4>';
				html+='	 <h5 id="songArtist_'+posicion+'">'+artista+'</h5>';
			}
			html+=' </div>';
			html+=' <div id="playedIcon_'+posicion+'" class="activeImageSong">';
			if(isPlayed){
				html+='			<img id="icono_'+posicion+'"src="images/qrocks_playSong2.png" width="40px" height="40px" title="Agregar" alt="Agregar"/>';
			}
			html+=' </div>';
			html+='</section>';
			return html;
		}
	</script>
</head>
<body>
<s:form namespace="/m/play" method="POST">
	<s:hidden name="cancionActual" id="cancionActual"/>
	<s:hidden name="qrMID"/>
	<s:hidden name="cancionAgregar.categoria.nombre" id="nombreCategoria"/>
	<s:hidden name="cancionAgregar.nombreCancion" id="nombreCancion"/>
	<!-- Campo hidden utilizado para eliminar un amigo -->
	<s:hidden name="amigoEliminar.id" id="amigoEliminar"/> 
	<!-- Botones hidden para hacer submit -->
	<s:submit action="eliminarAmigo" id="btnEliminarAmigo" cssStyle="display:none;"/>
	<!-- End de botones -->
		<!-- Intro -->
			<div class="formulario">
				<header>
					<h2 class="alt"><strong>Playlist</strong></h2>
				</header>
			</div>
		<div id="contenedorPlaylist">
			<div id="playListDiv">
			</div>
		</div>
<%-- 		<section id="rsvp" class="two"> --%>
			<div id="jp_container_1" class="jp-video jp-video-270p" style="display:none;">
				<div class="jp-type-playlist">
					<div id="jquery_jplayer_1" class="jp-jplayer" style="display:;"></div>
					<div class="jp-gui">
						<div class="jp-video-play">
							<a href="javascript:;" class="jp-video-play-icon" tabindex="1">play</a>
						</div>
						<div class="jp-interface">
							<div class="jp-progress">
								<div class="jp-seek-bar">
									<div class="jp-play-bar"></div>
								</div>
							</div>
							<div class="jp-current-time"></div>
							<div class="jp-duration"></div>
							<div class="jp-controls-holder">
								<ul class="jp-controls">
									<li><a href="javascript:;" class="jp-previous" tabindex="1">previous</a></li>
									<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
									<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
									<li><a href="javascript:;" class="jp-next" tabindex="1">next</a></li>
									<li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
									<li><a href="javascript:;" class="jp-mute" tabindex="1" title="mute">mute</a></li>
									<li><a href="javascript:;" class="jp-unmute" tabindex="1" title="unmute">unmute</a></li>
									<li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="max volume">max volume</a></li>
								</ul>
								<div class="jp-volume-bar">
									<div class="jp-volume-bar-value"></div>
								</div>
								<ul class="jp-toggles">
									<li><a href="javascript:;" class="jp-full-screen" tabindex="1" title="full screen">full screen</a></li>
									<li><a href="javascript:;" class="jp-restore-screen" tabindex="1" title="restore screen">restore screen</a></li>
									<li><a href="javascript:;" class="jp-shuffle" tabindex="1" title="shuffle">shuffle</a></li>
									<li><a href="javascript:;" class="jp-shuffle-off" tabindex="1" title="shuffle off">shuffle off</a></li>
									<li><a href="javascript:;" class="jp-repeat" tabindex="1" title="repeat">repeat</a></li>
									<li><a href="javascript:;" class="jp-repeat-off" tabindex="1" title="repeat off">repeat off</a></li>
								</ul>
							</div>
							<div class="jp-title">
								<ul>
									<li></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="jp-playlist">
						<ul>
							<!-- The method Playlist.displayPlaylist() uses this unordered list -->
							<li></li>
						</ul>
					</div>
					<div class="jp-no-solution">
						<span>Update Required</span>
						To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
					</div>
				</div>
			</div>
</s:form>
</body>
</html>