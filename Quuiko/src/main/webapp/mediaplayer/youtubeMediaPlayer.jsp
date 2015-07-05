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
<%-- 	<script type="text/javascript" src="js/mobile/jquery.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/skel.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/skel-panels.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/init.js"></script> --%>
	<!-- Youtube -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="js/youtube/auth.js"></script>
    <script src="js/youtube/playlist_updates.js"></script>
    <script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"></script>
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
	<script type="text/javascript">
		$(document).ready(function(){
			
		});
	</script>
</head>
<body>
<s:form method="POST">
	<div id="login-container" class="pre-auth">This application requires access to your YouTube account.
      Please <a href="#" id="login-link">authorize</a> to continue.
    </div>
    <div id="buttons">
      <input type="button" id="playlist-button" disabled onclick="createPlaylist()" value="Create a new Private Playlist" />
      <br>
      <label>Current Playlist Id: <input id="playlist-id" value='PLsUbc_EMhJe_zi2YpRZxorFi-l-5_8IOY' type="text"/></label>
      <br>
      <label>Video Id: <input id="video-id" value='' type="text"/></label><input type="button" onclick="addVideoToPlaylist()" value="Add to current playlist"/>
    </div>
    <h3>Playlist: <span id="playlist-title"></span></h3>
    <p id="playlist-description"></p>
    <div id="playlist-container">
      <span id="status">No Videos</span>
    </div>
	
	<!-- Search video-->
	<div id="buttons">
      <label> <input id="query" value='cats' type="text"/><input type="button" id="search-button" disabled onclick="search();" value="Search"/></label>
    </div>
    <div id="search-container">
    </div>
	<!-- End Search video -->
</s:form>
</body>
</html>