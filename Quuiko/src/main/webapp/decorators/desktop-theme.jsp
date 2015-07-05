<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String _ctx=(session.getAttribute("_ctx")!=null)?session.getAttribute("_ctx").toString():"/Invi";
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
	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" />
	<link type="text/css" href="css/qrocks.css" rel="stylesheet" media="all" />
	<!-- Scripts que siempre se repiten en todas la paginas -->
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
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
			var idDiv=(esError)?"modalError":"modalExito";
			$( "#"+idDiv ).dialog({
		    	modal: true,
		      	buttons: {
		        	Ok: function() {
		          	$( this ).dialog( "close" );
		        	}
		     	}
		    });
		}
		
		function lg(){
			$('#logoutBtn').click();
		}
	</script>
	<style type="text/css">
		.iconoMobil{
			float:left;
			margin:10px;
			vertical-align: middle;
		}
	</style>
	<decorator:head/>
</head>
<s:url namespace="/mediaplayer" action="showPlaylist" var="urlQRPlayerCreator"/>
<s:url namespace="/mediaplayer" action="openPlaylist" var="urlQRPlayer"/>
<s:url namespace="/productos" action="catalogo" var="urlCatalogoProductos"/>
<s:url namespace="/negocio" action="getImg" var="urlLogo"/>
<s:url namespace="/negocio" action="miNegocio" var="urlMiNegocio"/>
<s:url namespace="/mesaControl" action="verMesaControl" var="urlMesas"/>
<s:url namespace="/bu" action="showAll" var="urlUsuarios"/>
<s:url namespace="/promo" action="gtAll" var="urlPromos"/>
<body>
	<s:form id="_logoutForm" cssStyle="display:none;" action="j_spring_security_logout">
		<s:submit id="logoutBtn"/>
	</s:form>
	<s:hidden name="mensajeError" id="mensajeError"/>
	<s:hidden name="mensajeExito" id="mensajeExito"/>
	<div id="modalError" style="display:none;" title="Error"><s:property value="mensajeError"/> </div>
	<div id="modalExito" style="display:none;" title="inByteme.com"><s:property value="mensajeExito"/> </div>
	<s:url namespace="/" action="home" var="urlInicio" /> 

	<s:url action="j_spring_security_logout" var="urlLogout"/>
<%-- 	<s:form action="j_spring_security_logout" cssStyle="display:none;"> --%>
<%-- 		<s:submit id="logoutBtn"></s:submit> --%>
<%-- 	</s:form> --%>
	<div id="skel-panels-fixedWrapper">
	<div class="skel-panels-fixed" id="header">
		<div class="top">
			<div id="logo">
				<span class="image avatar48">
					<img src="${urlLogo}" width="100px" height="auto" title="" alt=""/>
				</span>
				<h1 id="title">${negocioLogeado.nombre}</h1>
				<span class="byline">www.qrocks.com</span>
			</div>
			<nav id="nav">
				<ul>
					<li>
						<a href="#" class="skel-panels active">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_home2.png">Home</span>
						</a>
					</li>
					<li>
						<a href="${urlCatalogoProductos}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_grayProduct.png">Productos</span>
						</a>
					</li>
					<li>
						<a href="${urlMesas}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_table2.png">Mesas</span>
						</a>
					</li>
					<li>
						<a href="${urlMiNegocio}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_grayBusiness.png">Mi Negocio</span>
						</a>
					</li>
					<li>
						<a href="${urlQRPlayerCreator}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_song2.png">Playlist creator</span>
						</a>
					</li>
					<li>
						<a href="${urlQRPlayer}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_song2.png">QRockPlayer</span>
						</a>
					</li>
					<sec:authorize access="hasAnyRole('GV')">
					<li>
						<a href="${urlUsuarios}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_song2.png">Usuarios</span>
						</a>
					</li>
					</sec:authorize>
					<li>
						<a href="${urlPromos}" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_song2.png">Promos</span>
						</a>
					</li>
					
					<li>
						<a href="javascript:lg();" class="skel-panels">
							<span class="icon"><img class="iconoMobil" width="28" height="28" src="images/qrocks_grayLogout.png">Cerrar sesi&oacute;n</span>
						</a>
					</li>
				</ul>
			</nav>
		</div>
		<div class="bottom">
		<!-- 
			<ul class="icons">
				<li><a class="icon icon-twitter" href="#"></a></li>
				<li><a class="icon icon-facebook" href="#"></a></li>
			</ul>
		-->
		</div>
	</div>
	</div>
	<!-- Main -->
	<div class="panelCentral">
		<decorator:body/>
	</div>
	
	<div class="pie"> <!--Seccion de pie de pagina -->
		<div class="social">
			<img src="images/fb32x32.png"/> | <img src="images/twitter32x32.png"/>
		</div>
		QRock &copy; 2013-Derechos Reservados
		<li>Design: <a href="http://html5up.net">HTML5 UP</a>/Images:<a href="https://www.iconfinder.com/tmthymllr">Timothy Miller</a>
		/<a href="http://www.wpzoom.com">WPZOOM</a>
		/Nick Frost
		/<a href="http://www.webalys.com/">Webalys</a>
		/Designmodo
		/<a href="http://ionicons.com/">Ionicons</a>
		/<a href="http://icons8.com/">Visual Pharm</a>
		/<a href="http://mapbox.com/">Mapbox</a>
		/<a href="http://www.brightmix.com">Brightmix</a>
		/<a href="https://www.iconfinder.com/icons/183217/list_notes_icon#size=128">Free Icons</a>
		</li>
	</div>
</body>
</html>