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
	<!-- END Scripts -->
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
		.genderStyle, .findBox{
			background-color: #000;
			color:#fff;
			padding:1em;
			text-align: left;
		}
		.genderStyle h2{
			display:inline-block;
			text-align:left;
			width:38%;
			padding-left: 10px;
			text-transform: capitalize;
			color:#fcfcfc;
		}
		section{
			margin-bottom: 0em;
		}
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		var playList=null;
		$(document).ready(function(){
		});
		
		function eliminarAmigo(idAmigo){
			$('#amigoEliminar').val(idAmigo);
			$('#btnEliminarAmigo').click();
		}
		
// 		function encolarCancion(cancion,artista,genero){
// 			$('#genero').val(genero);
// 			$('#nombreCancion').val(cancion);
// 			$('#artista').val(artista);
// 			$('#btnToQueue').click();
// 		}
		
		function encolarCancion(indiceCancion,indiceGenero){
			var cancion=$('#song_'+indiceCancion+'_gender_'+indiceGenero).val();
			var artista=$('#artist_'+indiceCancion+'_gender_'+indiceGenero).val();
			var genero=$('#gender_'+indiceCancion+'_gender_'+indiceGenero).val();
			$('#genero').val(genero);
			$('#nombreCancion').val(cancion);
			$('#artista').val(artista);
			$('#btnToQueue').click();
		}
	</script>
</head>
<body>
<s:form namespace="/m/play" method="POST" id="forma">
	<s:hidden name="qrMKEY"/>
	<s:hidden name="cancionAgregar.genero" id="genero"/>
	<s:hidden name="cancionAgregar.nombre" id="nombreCancion"/>
	<s:hidden name="cancionAgregar.artista" id="artista"/>
	<!-- Botones hidden para hacer submit -->
	<s:submit action="toQueue" id="btnToQueue" cssStyle="display:none;"/>
	<!-- End de botones -->
	<!-- Header -->
	
		<!-- Intro -->
		<section id="top" class="one">
			<div class="findBox">
				<header>
					<h2 class="alt"><strong>Playlist</strong></h2>
				</header>
				<div class="contenedor" style="width:99%;">
					<label>Buscar cancion:</label><input type="text" maxlength="40"/>
					<input type="button" value="Buscar" class="button scrolly"/>
				</div>
			</div>
			<div>
				<!-- Iterar la lista de amigos -->
				<s:iterator value="generos" var="genero" status="k">
					<div class="genderStyle">
						<div style="display:inline-block;vertical-align:top;width:19%;max-width:50px;"><img src="images/qrocks_song.png" width="50px" height="50px"/></div>
						<h2 style="display:inline-block;text-align:left;width:38%;">${genero.nombre}</h2>
					</div>
					<s:iterator value="#genero.canciones" var="song" status="i">
						
						<section class="songStyle">
							<input type="hidden" id="song_${i.index}_gender_${k.index}" value="${song.nombre}"/>
							<input type="hidden" id="artist_${i.index}_gender_${k.index}" value="${song.artista}"/>
							<input type="hidden" id="gender_${i.index}_gender_${k.index}" value="${song.genero}"/>
							<div style="width:80%;display:inline-block;">
								<h4>${song.nombre}</h4>
								<h5>${song.artista}-${song.album}</h5>
							</div>
							<div style="display:inline-block;vertical-align:bottom;width:10%;max-width:50px;padding-right:10px;">
								<a href="javascript:encolarCancion(${i.index},${k.index});" style="text-decoration: none;">
									<img src="images/qrocks_playSong2.png" width="40px" height="40px" title="Agregar" alt="Agregar"/>
								</a>
							</div>
						</section>
						
					</s:iterator>
				</s:iterator>
			</div>
		</section>
					
</s:form>
</body>
</html>