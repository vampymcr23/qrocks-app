<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<link rel="shortcut icon" href="images/inbytemeImages/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="images/inbytemeImages/favicon.png" type="image/png" />
	<s:set var="ctx" value="%{''}"></s:set>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="QRocks, musica, restaurantes, ambiente"/>
	<title><decorator:title/></title>
	<link type="text/css" href="css/qrocks-androidTheme.css" rel="stylesheet" media="all" />
	<!-- Scripts que siempre se repiten en todas la paginas -->
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel-panels.min.js"></script>
	<script type="text/javascript" src="js/mobile/init-androidTheme.js"></script>
	<script type="text/javascript">
		
		$(document).ready(function(){
			var mensajeExito=$('#mensajeExito').val();
			var mensajeError=$('#mensajeError').val();
			if(mensajeError!=null && mensajeError!=''){
				mostrarMensaje(true);
			}
			if(mensajeExito!=null && mensajeExito!=''){
				mostrarMensaje(false);
			}
		});
		
		function mostrarMensaje(esError){
// 			var idDiv=(esError)?"modalError":"modalExito";
// 			$( "#"+idDiv ).dialog({
// 		    	modal: true,
// 		      	buttons: {
// 		        	Ok: function() {
// 		          	$( this ).dialog( "close" );
// 		        	}
// 		     	}
// 		    });
		}
	</script>
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
	</style>
	<decorator:head/>
</head>
<s:url namespace="/negocio" action="getImg" var="urlLogo"/>
<s:url namespace="/m/play" action="qrocksPlayer" var="urlPlaylist">
	<s:param name="qrMKEY" value="%{qrMKEY}"/>
</s:url>
<s:url namespace="/m/pedidos" action="menu" var="urlHome">
	<s:param name="qrMKEY" value="%{qrMKEY}"/>
</s:url>
<body>
	<div id="header" class="skel-panels-fixed">

		<div class="top">
			<!-- Logo -->
				<div id="logo">
					<span class="image avatar48">
						<img src="${urlLogo}?negocio.id=${negocio.id}" width="100px" height="auto" title="" alt=""/>
					</span>
					<h1 id="title">QRocks-Menu</h1>
					<span class="byline">www.qrocks.com</span>
				</div>
			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="${urlHome}#negocio" id="negocio-link" class="skel-panels"><span class="icon"><img src="images/qrocks_home2.png" width="22" height="22" class="iconoMobil">Mi mesa</span></a></li>
						<li><a href="${urlHome}#nuevoAmigo" id="rsvp-link" class="skel-panels"><span class="icon"><img src="images/qrocks_witress2.png" width="22" height="22" class="iconoMobil">Agregar amigo</span></a></li>
						<li><a href="${urlHome}#grupo" id="map-link" class="skel-panels"><span class="icon"><img src="images/qrocks_group2.png" width="22" height="22" class="iconoMobil">Grupo</span></a></li>
						<li><a href="${urlHome}#cuenta" id="gallery-link" class="skel-panels"><span class="icon"><img src="images/qrocks_handAccount2.png" width="22" height="22" class="iconoMobil">Cuenta</span></a></li>
						<li><a href="${urlPlaylist}" id="aboutUs-link" class="skel-panels"><span class="icon"><img src="images/qrocks_song2.png" width="22" height="22" class="iconoMobil">Playlist</span></a></li>
					</ul>
				</nav>
		</div>
		<div class="bottom">
			<!-- Social Icons -->
			<ul class="icons">
				<li><a href="http://www.twitter.com/inbytememx" class="icon icon-twitter"><span>Twitter</span></a></li>
				<li><a href="http://www.inbyteme.com" class="icon"><img src="images/inbytemeImages/inviIcon_inbyteme.png" width="24" height="24"/></a></li>
				<li><a href="http://www.facebook.com/inbyteme" class="icon icon-facebook"><span>Facebook</span></a></li>
			</ul>
		</div>
	</div>
	<!-- Main -->
	<div id="main">
		<decorator:body/>
	</div>
	
</body>
</html>