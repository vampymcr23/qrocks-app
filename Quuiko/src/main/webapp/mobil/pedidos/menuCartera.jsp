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
	</style>
	<script type="text/javascript">
		function eliminarAmigo(idAmigo){
			$('#amigoEliminar').val(idAmigo);
			$('#btnEliminarAmigo').click();
		}
	
	</script>
</head>
<body>
<s:url namespace="/m/play" action="qrocksPlayer" var="urlPlaylist"/>
<s:form namespace="/m/pedidos" method="POST">
	<s:hidden name="qrMID"/>
	<s:hidden name="pedido.id"/>
	<!-- Campo hidden utilizado para eliminar un amigo -->
	<s:hidden name="amigoEliminar.id" id="amigoEliminar"/> 
	<!-- Botones hidden para hacer submit -->
	<s:submit action="eliminarAmigo" id="btnEliminarAmigo" cssStyle="display:none;"/>
	<!-- End de botones -->
	<!-- Header -->
	<div id="header" class="skel-panels-fixed">

		<div class="top">
			<!-- Logo -->
				<div id="logo">
					<span class="image avatar48">
						<img src="" width="100px" height="auto"/>
					</span>
					<h1 id="title">QRocks-Menu</h1>
					<span class="byline">www.qrocks.com</span>
				</div>
			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="#top" id="top-link" class="skel-panels-ignoreHref"><span class="icon"><img src="images/qrocks_home.png" width="22" height="22" class="iconoMobil">Mi mesa</span></a></li>
						<li><a href="#rsvp" id="rsvp-link" class="skel-panels-ignoreHref"><span class="icon"><img src="images/qrocks_user.png" width="22" height="22" class="iconoMobil">Agregar amigo</span></a></li>
						<li><a href="#location" id="map-link" class="skel-panels-ignoreHref"><span class="icon"><img src="images/qrocks_user_group.png" width="22" height="22" class="iconoMobil">Grupo</span></a></li>
						<li><a href="#gallery" id="gallery-link" class="skel-panels-ignoreHref"><span class="icon"><img src="images/qrocks_handAccount.png" width="22" height="22" class="iconoMobil">Cuenta</span></a></li>
						<li><a href="${urlPlaylist}" id="aboutUs-link" class="skel-panels"><span class="icon"><img src="images/qrocks_speakers.png" width="22" height="22" class="iconoMobil">Playlist</span></a></li>
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
		<!-- Intro -->
		<section id="top" class="one">
			<div class="container">
				<header>
					<h2 class="alt"><strong>${negocio.nombre}</strong></h2>
				</header>
				<a href="#" class="image featured">	
					<img src="" width="300px" height="auto">
				</a>
				<header>
					<h2 class="alt"><strong>${pedido.mesa.claveMesa}</strong></h2>
				</header>
				<div class="contenedor" style="width:99%;">
					<label>Folio:</label><span>${(pedido.id!=null)?pedido.id:''}</span>
					<br/>
					<label>Fecha:</label><span>${pedido.fechaStr}</span>
					<br/>
					<label>Estatus:</label><span>${(pedido.activo==0)?'Offline':'Online'}</span>
				</div>
			</div>
		</section>
					
		<section id="rsvp" class="two">
			<div class="container">
				<header>
					<h2>Agregar al grupo</h2>
				</header>
				
				<div class="contenedor">
					<label>Nombre:</label>
					<span>
						<input type="text" name="amigo.alias" width="100px" maxlength="15"/>
					</span>
					<br/>
					<input type="submit" value="Agregar" class="button scrolly" name="action:<%=actionAgregarAmigo%>"/> 
				</div>

			</div>
		</section>
				
		<!-- Como llegar-->
		<section id="location" class="three">
			<div class="container">

				<header>
					<h2>Mi Grupo</h2>
				</header>
				<div>
					<!-- Iterar la lista de amigos -->
					<s:iterator value="grupo" var="cliente">
						<div>
							<div style="display:inline-block;vertical-align:top;width:19%;max-width:50px;"><img src="images/qrocks_girl.png" width="50px" height="50px"/></div>
							<h2 style="display:inline-block;text-align:left;width:38%;">${cliente.alias}</h2>
							<div style="display:inline-block;vertical-align:bottom;width:10%;max-width:50px;">
								<a href="javascript:eliminarAmigo(${cliente.id});" style="text-decoration: none;">
									<img src="images/qrocks_shoppingCart.png" width="40px" height="40px"/>
								</a>
							</div>
							<div style="display:inline-block;vertical-align:bottom;width:10%;max-width:50px;">
								<a href="javascript:eliminarAmigo(${cliente.id});" style="text-decoration: none;">
									<img src="images/qrocks_delete1.png" width="40px" height="40px"/>
								</a>
							</div>
						</div>
						<br/>
					</s:iterator>
				</div>
				<!-- Fin de Contenido de como llegar -->
			</div>
		</section>
	</div>

	<!-- Footer -->
	<div id="footer">
		<!-- Copyright -->
		<div class="copyright">
			<p>&copy; 2013 QRocks. All rights reserved.</p>
			<ul class="menu">
				<li>Design: <a href="http://html5up.net">HTML5 UP</a> and icons:<a href="https://www.iconfinder.com/tmthymllr">Timothy Miller</a>/<a href="http://www.wpzoom.com">WPZOOM</a>/Nick Frost/<a href="http://www.webalys.com/">Webalys</a></li>
			</ul>
		</div>
	</div>
</s:form>
</body>
</html>