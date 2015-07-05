<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
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
	<meta name="description" content="Invitaciones para bodas, xv años, despedidas de soltera, graduaciones y cualquier tipo de evento,inbyteme"/>
	<title><decorator:title/></title>
	<link rel="stylesheet" type="text/css" href="css/jquery-plugins/jTable/themes/metro/blue/jtable.min.css" /><!-- Css del grid -->
	<link rel="stylesheet" type="text/css" href="css/ui/jquery.ui.1.10.3.min.css" />
<!-- 	<link rel="stylesheet" type="text/css" href="css/invi-general.css" />Css Invi -->
<!-- 	<link rel="stylesheet" type="text/css" href="css/invi-menu.css" />Css Invi -->
	<!-- Scripts que siempre se repiten en todas la paginas -->
	<script type="text/javascript" src="js/jquery-2.0.2.js"></script>
	<script type="text/javascript" src="js/jquery.ui.1.10.3.js"></script>
	<script type="text/javascript" src="js/jquery-plugins/jTable/jquery.jtable.js"></script>
	<script type="text/javascript" src="js/fotorama.js"></script>
	<link rel="stylesheet" type="text/css" href="css/fotorama.css"/>
	<link rel="stylesheet" type="text/css" href="css/invi.css"/>
	<link rel="stylesheet" type="text/css" href="css/invi-form.css"/>
	<link rel="stylesheet" href="css/inbytemeCSS/style.css" />
	<script type="text/javascript">
		var _ctx="${ctx}";
		
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
		
		function logout(){
			$('#logoutBtn').click();
		}
		
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
	</script>
	<decorator:head/>
</head>
<body>
	<s:hidden name="mensajeError" id="mensajeError"/>
	<s:hidden name="mensajeExito" id="mensajeExito"/>
	<div id="modalError" style="display:none;" title="Error"><s:property value="mensajeError"/> </div>
	<div id="modalExito" style="display:none;" title="inByteme.com"><s:property value="mensajeExito"/> </div>
	<s:url namespace="%{ctx}/" action="back" var="regresar"> 
		<s:param name="backNamespace" value="%{backNamespace}"></s:param>
		<s:param name="backAction" value="%{backAction}"></s:param>
	</s:url>
	<s:url namespace="%{ctx}/evento" action="inicio" var="urlMisEventos" /> 
	<s:url namespace="%{ctx}/evento" action="nuevoEvento" var="urlNuevoEvento" /> 
	<!-- <s:url namespace="%{ctx}/" action="homePage" var="urlInicio" /> -->
	<s:url namespace="%{ctx}/" action="home" var="urlInicio" /> 

	<s:url action="j_spring_security_logout" var="urlLogout"/>
	<s:url namespace="%{ctx}/log" action="accesos" var="urlLogAcceso" /> 
	<s:url namespace="%{ctx}/usuario" action="consultarUsuarios" var="urlUsuarios" /> 
	<s:form action="j_spring_security_logout" cssStyle="display:none;">
		<s:submit id="logoutBtn"></s:submit>
	</s:form>
	
	<div class="encabezado"> 
		<div class="logo">
			<img src="images/inbytemeLogo_1.png"/>
		</div>
		<div class="logout"><a href="javascript:logout();" title="Salir de inByteme" class="button button-big"><span>LOGOUT</span></a></div>
	</div>
		
	<!-- menu-->
	<div>
		<div id="procontainer_green">
			<div id="pronav_green">
				<ul>
					<li><a href="${urlInicio}" title="Ir al inicio"><span>INICIO</span></a></li>
					<li><a href="${urlMisEventos}" title="Ver mis eventos"><span>MIS EVENTOS</span></a></li>
					<sec:authorize access="hasRole('Administrador')">
						<li><a href="${urlNuevoEvento}" title="Crear nuevo evento"><span>NUEVO EVENTO</span></a></li>
						<li><a href="${urlUsuarios}" title="Crear nuevo evento"><span>USUARIOS</span></a></li>
						<li><a href="${urlLogAcceso}" title="Control de Acceso"><span>LOG</span></a></li>
					</sec:authorize>
<!--					<li><a href="javascript:logout();" title="Salir de inByteme"><span>LOGOUT</span></a></li>-->
				</ul>
			</div>
		</div>
	</div>
	<div class="contenido">
		<div class="contenedorPrincipal">
			<a href="${regresar}" title="Regresar"  class="button button-big back"><span>Regresar</span></a>
			<decorator:body/>
		</div>
	</div>
	
	<div class="pie"> <!--Seccion de pie de pagina -->
		<div class="social">
			<img src="images/fb32x32.png"/> | <img src="images/twitter32x32.png"/>
		</div>
		inByteme &copy; 2013-Derechos Reservados
		
	</div>
</body>
</html>