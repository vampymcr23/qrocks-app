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
	<title>Quicko Player</title>
	<!-- PLaylist -->
	<script src="js/youtube/auth.js"></script>
    <script src="js/youtube/youtubeUtil.js"></script>
    <script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"></script>
	<!-- End Playlist -->
	<!-- END Scripts -->
	<style type="text/css">
		.infoVideo h4{
			font-size:1em;
			color:#fff;
		}
		.infoVideoBusqueda h4{
			font-size:1em;
			color:rgba(20,20,20,0.9);
		}
		.infoVideoBusqueda h5{
			font-size:0.8em;
			color:rgba(20,20,20,0.6);
		}
		.playlistActual{
			background-color:rgba(45,45,45,0.8);
			color:#fff;
		}
		
		.panelPlaylistActual{
			background-color:rgba(45,45,45,0.9);
		}
		
		.rowSearch{
			padding-top:5px;
			border-bottom:1px solid #ddd;
		}
	</style>
	<s:url namespace="/mediaplayer" action="getPlaylist" var="urlPlaylist"/>
	<s:url namespace="/mediaplayer" action="storeCurrentSong" var="urlCurrentSong"/>
	<s:url namespace="/mediaplayer" action="addNewPlaylist" var="urlAddPlaylist"/>
	<s:url namespace="/mediaplayer" action="addNewVid" var="urlAddNewVidToPlaylist"/>
	<s:url namespace="/mediaplayer" action="removeVid" var="urlRemoveVidToPlaylist"/>
	<s:url namespace="/mediaplayer" action="gtVidsByPlyLst" var="urlGetVidsByPlaylist"/>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<s:url namespace="/mediaplayer" action="clearUsrPlaylist" var="urlCleanUserPlaylist"/>
	<script type="text/javascript">
		var cancionAnterior=-1;
		var playListid="${youtubePlaylist.id}";
		var tokenSiguiente=null;
		var tokenAnterior=null;
		var selectedVideo=null;
		var searchResults=new Array();//Lista de videos que son resultados de la busqueda
		$(document).ready(function(){
			$('#rightPanel').text('PRUEBA');
		});
		
		//Funcion que se ejecuta despues de que se carga el api de Youtube
		function handleAPILoaded(){
			$('#searchVideos').attr("disabled",false);
			if("${requiereCrearPlaylist}"=="true"){
				renderearVideosDelPlaylist();
			}else{
				renderearVideosDelPlaylist();
			}
		}
		
		function onSuccessCreatePlaylist(playlistInfo){
			renderearVideosDelPlaylist();
		}
		
		function getVideos(response){
			$('#myVideoList').html("");
			var html="";
			if(response.items!=null){
				var videos=response.items;
				tokenSiguiente=response.nextPageToken;
				tokenAnterior=response.prevPageToken;
				$('#numResultsPlayList').html("("+response.pageInfo.totalResults+") videos");
				videos.forEach(function(item){
					var info=item.snippet;
		    		var idVideo=item.id.videoId;
		    		var titulo=info.title;
		    		titulo=(titulo!=null && titulo.length>45)?titulo.substring(0,45)+"...":titulo;
		    		var imgUrl=info['thumbnails']['default']['url'];
		    		var descripcion=info.description;
		    		descripcion=(descripcion!=null && descripcion.length>100)?descripcion.substring(0,100)+"...":descripcion;
		    		html+='<section class="playlist_song">';
		    		html+=' <div class="playlist_songDiv">';
		    		html+=" 	<div class='playlist_songImage'><img src='"+imgUrl+"' width='65px' height='65px'/></div>";
					html+=' 	<div class="playlist_songInfo"><h4 class="playlist_songTitle">'+titulo+'</h4><h5 class="playlist_songDescription">'+descripcion+'</h5></div>';
		    		html+=' </div>';
					html+='</section>';
				});
				$('#myVideoList').html(html);
			}
		}
		
		function  habilitarBotonesPaginado(){
			if(tokenSiguiente!=null){
				$('#nextBtn').attr("disabled",false);
			}else{
				$('#nextBtn').attr("disabled",true);
			}
			if(tokenAnterior!=null){
				$('#prevBtn').attr("disabled",false);
			}else{
				$('#prevBtn').attr("disabled",true);
			}
		}
		
		function buscarVideos(){
			var q=$('#cancionBuscar').val();
			searchVideo(q,onSuccessSearchVideos);
		}
		
		//CHECAR QUE SIGA FUNCIONANDO LA OPCION DE AGREGAR
		function onSuccessSearchVideos(videos){
			if(videos!=null){
				var html='<div class="panel panel-default">';
				html+='<div class="panel-heading"><div class="row"><div class="col-sm-8">Resultados de la busqueda</div></div></div>';
				html+='	<div class="panel-body"> ';
				var index=0;
				searchResults=new Array();//Se limpia el arreglo
				videos.forEach(function(item){
		    		var info=item.snippet;
		    		var idVideo=item.id.videoId;
		    		var titulo=info.title;
		    		var descripcion=info.description;
		    		descripcion=(descripcion!=null && descripcion.length>200)?descripcion.substring(0,200)+"...":descripcion;
		    		var imgUrl=info['thumbnails']['default']['url'];
		    		var desc=info.description;
		    		var imgUrl=info['thumbnails']['default']['url'];
		    		var imgLargeUrl=info['thumbnails']['high']['url'];
		    		var imgMediumUrl=info['thumbnails']['medium']['url'];
		    		var v={
		    			idVideo:idVideo,
		    			titulo:titulo,
		    			desc:desc,
		    			imgUrl:imgUrl,
		    			imgLargeUrl:imgLargeUrl,
		    			imgMediumUrl:imgMediumUrl,
		    			playlistId:"${youtubePlaylist.id}"
		    		};
		    		searchResults.push(v);
		    		html+='	<div class="row rowSearch">';
		    		html+='			<div class="col-sm-3">';
		    		html+='				<img src="'+imgUrl+'" width="65px" height="65px" onClick="agregarVideoALaLista('+index+');"/>';
		    		html+='			</div>';
		    		html+='			<div class="col-sm-8 infoVideoBusqueda">';
		    		html+='				<h4>'+titulo+'</h4>';
		    		html+='				<h5>'+descripcion+'</h5>';
		    		html+='			</div>';
		    		html+='	</div>';
		    		
// 		    		html+='<section class="songStyle">';
// 		    		html+=' <div class="songDiv">';
// 					html+=" <div class='songImage'><img src='"+imgUrl+"' onClick='agregarVideoALaLista("+index+");'/></div>";
// 					html+=' <div class="songInfo"><h4 id="songTitle" class="songTitle">'+titulo+'</h4><h5 class="songDescription">'+descripcion+'</h5></div>';
// 		    		html+=' </div>';
// 					html+='</section>';
					index++;
		    	});
				$('#playListDiv').html(html);
			}
		}
		
		function mostrarVideoAgregado(resultado){
			if(resultado!=null){
				alert("Su video ha sido agregado a la lista!");
				registrarVideoAgregado();
				renderearVideosDelPlaylist();
			}
		}
		
		function paginaSiguiente(){
			showPaginatedAllVideosFromPlayList("${youtubePlaylist.id}",tokenSiguiente,getVideos);
		}
		
		function paginaAnterior(){
			showPaginatedAllVideosFromPlayList("${youtubePlaylist.id}",tokenAnterior,getVideos);
		}
		
		function agregarVideoALaLista(index){
			selectedVideo=searchResults[index];
			var idVid=selectedVideo.idVideo;
			var playlistId=selectedVideo.playlistId;
			var resultado=1; 
			mostrarVideoAgregado(resultado);
		}
		
		
		function registrarVideoAgregado(id){
			$.ajax({
				dataType:'json',
				url:"${urlAddNewVidToPlaylist}",
				data:{
					"youtubeVideo.idVideo":selectedVideo.idVideo,
					"youtubeVideo.titulo":selectedVideo.titulo,
					"youtubeVideo.descripcion":getDescription(selectedVideo.desc),
					"youtubeVideo.urlImagenDefault":selectedVideo.imgUrl,
					"youtubeVideo.urlImagenMediana":selectedVideo.imgMediumUrl,
					"youtubeVideo.urlImagenGrande":selectedVideo.imgLargeUrl,
					"currentPl":selectedVideo.playlistId
				},
				async:false,
				success:function(data){
					selectedVideo=null;
				}
			});
		}
		
		function actionEliminarVideo(id){
			$.ajax({
				dataType:'json',
				url:"${urlRemoveVidToPlaylist}",
				data:{
					"youtubeVideo.id":id
				},
				async:false,
				success:function(data){
					renderearVideosDelPlaylist();
				}
			});
		}
		
		var MAX_LIMIT_DESCRIPTION=60;
		
		function getDescription(description){
			var desc="";
			if(description!=null && description.length>MAX_LIMIT_DESCRIPTION){
				desc=description.substring(0,MAX_LIMIT_DESCRIPTION)+"...";
			}else{
				desc=description;
			}
			return desc;
		}
		
		function renderearVideosDelPlaylist(){
			$.ajax({
				dataType:'json',
				url:"${urlGetVidsByPlaylist}",
				data:{
					"currentPl":"${youtubePlaylist.id}"
				},
				async:false,
				success:function(data){
// 					renderearVideos(data);
					renderearVideosNuevo(data);
				}
			});
		}
		
		//@Deprecated
		function renderearVideos(videos){
			$('#myVideoList').html("");
			var html="";
			if(videos!=null){
				$('#numResultsPlayList').html("("+videos.length+") videos");
				videos.forEach(function(v){
					var id=v.id;
		    		var idVideo=v.idVideo;
		    		var titulo=v.titulo;
		    		var imgUrl=v.urlImagenDefault;
		    		var urlImagenGrande=v.urlImagenDefault;
		    		var urlImagenMediana=v.urlImagenDefault;
		    		var descripcion=v.descripcion;
		    		descripcion=(descripcion!=null && descripcion.length>150)?descripcion.substring(0,150)+"...":descripcion;
		    		html+='<section class="playlist_song">';
		    		html+=' <div class="playlist_songDiv">';
		    		html+=" 	<div class='playlist_songImage'><img src='"+imgUrl+"' width='65px' height='65px'/></div>";
					html+=' 	<div class="playlist_songInfo"><h4 class="playlist_songTitle">'+titulo+'</h4><h5 class="playlist_songDescription">'+descripcion+'</h5></div>';
		    		html+=' </div>';
					html+='</section>';
				});
				$('#myVideoList').html(html);
			}
		}
		
		function renderearVideosNuevo(videos){
			$('#rightPanel').html("");
			var html='<div class="panel panel-default panelPlaylistActual">';
			html+='<div class="playlistActual panel-heading "><div class="row"><div class="col-sm-8">Mi playlist ('+videos.length+' videos)</div><div class="col-sm-4" id="totalVideos"></div></div></div>';
			html+='	<div class="panel-body"> ';
			if(videos!=null){
				videos.forEach(function(v){
					var id=v.id;
		    		var idVideo=v.idVideo;
		    		var titulo=v.titulo;
		    		var imgUrl=v.urlImagenDefault;
		    		var urlImagenGrande=v.urlImagenDefault;
		    		var urlImagenMediana=v.urlImagenDefault;
		    		var descripcion=v.descripcion;
		    		titulo=(titulo!=null && titulo.length>80)?titulo.substring(0,80)+"...":titulo;
		    		descripcion=(descripcion!=null && descripcion.length>150)?descripcion.substring(0,150)+"...":descripcion;
		    		html+='	<div class="row ">';
		    		html+='			<div class="col-sm-3">';
		    		html+='				<img src="'+imgUrl+'" width="65px" height="65px"/>';
		    		html+='			</div>';
		    		html+='			<div class="col-sm-7 infoVideo">';
		    		html+='				<h4>'+titulo+'</h4>';
		    		html+='			</div>';
		    		html+='			<div class="col-sm-2">';
		    		html+='				<span class="glyphicon glyphicon-remove-circle iconoProducto" onclick="actionEliminarVideo('+v.id+');" title="Eliminar"></span>';
		    		html+='			</div>';
		    		html+='	</div>';
				});
				html+='</div>';
				html+='</div>';
				$('#rightPanel').html(html);
			}
		}
		
		function limpiarPlaylistUsuarios(){
			$.ajax({
				dataType:'json',
				url:"${urlCleanUserPlaylist}",
				async:false,
				success:function(data){
					if(data!=null){
						var mensaje=data.messageResult;
						var codigo=data.messageCode;
						alert(mensaje);
					}
				}
			});
		}
	</script>
</head>
<body>
<s:form namespace="/m/play" method="POST">
	<s:hidden name="youtubePlaylist.id" id="youtubePlaylist"/>
	<s:hidden name="messageResult" id="msgError" />
	<s:hidden name="negocio.id" id="idNegocio" />
	<s:hidden name="cancionActual" id="cancionActual"/>
	<s:hidden name="qrMID"/>
	<s:hidden name="cancionAgregar.categoria.nombre" id="nombreCategoria"/>
	<s:hidden name="cancionAgregar.nombreCancion" id="nombreCancion"/>
	<!-- Campo hidden utilizado para eliminar un amigo -->
	<s:hidden name="amigoEliminar.id" id="amigoEliminar"/>
	<fieldset>
		  <legend>Creador de playlist</legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-lg-2 col-md-2 col-sm-5 col-xs-5 control-label">Buscar:</label>
		    <div class="col-lg-5 col-md-5 col-sm-7 col-xs-7">
		      	<input type="text" id="cancionBuscar" maxlength="50" class="form-control" placeholder="Escriba el nombre del video o cantante"/>
		    </div>
		    <div class="col-lg-2 col-md-2 col-sm-6 col-xs-6">
		    	<button id="searchVideos" type="button" class="btn btn-primary" disabled="true" onclick="buscarVideos();">Buscar</button>
		    </div>
		    <div class="col-lg-2 col-md-2 col-sm-6 col-xs-6">
		    	<button id="cleanVideos" type="button" class="btn btn-primary"  onclick="limpiarPlaylistUsuarios();">Borrar PlaylistPlayer</button>
		    </div>
		  </div>
		  <br/>
		  <div class="form-group">
		  	<div id="playListDiv" class="col-xs-12"></div>
		  </div>
		  
	</fieldset>
	
	<!-- OLD --> 
		<!-- Intro -->
<!-- 			<div class="formulario"> -->
<!-- 				<div id="login-container" class="pre-auth">This application requires access to your YouTube account. -->
<!-- 			      Please <a href="#" id="login-link">authorize</a> to continue. -->
<!-- 			    </div> -->
<!-- 				<header> -->
<%-- 					<h2 class="alt"><strong>Playlist</strong></h2> --%>
<!-- 				</header> -->
<%-- 				<section> --%>
<!-- 					<label>Buscar video:</label><input type="text" id="cancionBuscar" maxlength="50"/>  -->
<!-- 					<input id="searchVideos" type="button" value="Buscar" class="button scrolly" disabled="true" onclick="buscarVideos();"/> -->
<%-- 				</section> --%>
<!-- 			</div> -->
<!-- 			<br/> -->
			<div id="contenedorPlaylist">
<!-- 				<div id="playListDiv"></div> -->
			</div>
<!--  			
			<div class="currentPlayList">
				<h4>Mis videos</h4>
				<span id="numResultsPlayList" class="numResultPlaylist"></span>
				<div id="myVideoList">
				
				</div>
			</div>
-->
</s:form>
</body>
</html>