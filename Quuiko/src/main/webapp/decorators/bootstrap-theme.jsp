<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String _ctx=(session.getAttribute("_ctx")!=null)?session.getAttribute("_ctx").toString():"/Invi";
	String nombreNegocio=(session.getAttribute("_nomNeg")!=null)?session.getAttribute("_nomNeg").toString():"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<s:set var="ctx" value="%{''}"></s:set>
	<base href="<%=basePath%>"/>
	<meta name="description" content="QRocks, musica, restaurantes, ambiente"/>
	<title><decorator:title/></title>
	<!-- Scripts que siempre se repiten en todas la paginas -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<title>Bienvenida</title>
	<script src="js/mobile/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<!-- Script para input mask 
		Info: https://github.com/RobinHerbots/jquery.inputmask/blob/2.x/README.md
	-->
	<script src="js/jquery.inputmask.bundle.min.js"></script>
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
			var codigo=$('#messageCode').val();
			var msg=$('#messageResult').val();
			showDialog(codigo,msg);
// 			if(codigo!=null && codigo.length>0){
// 				var msg=$('#messageResult').val();
// 				$('#textMessageResult').text(msg);
// 				if(codigo==0){
// 					$('#resultDialog').css('display','inherit');
// 					$('#resultDialog').attr('class','alert alert-dismissable alert-success');
// 				}else if(codigo==-1){
// 					$('#resultDialog').css('display','inherit');
// 					$('#resultDialog').attr('class','alert alert-dismissable  alert-danger');
// 				}
// 			}
		});
		
		function showDialog(codigo,mensaje){
			if(codigo!=null && codigo.length>0){
				$('#textMessageResult').text("");
				var numSegundos=5;
				if(codigo=="0"){
					$('#resultDialog').css('display','inherit');
					$('#resultDialog').attr('class','alert alert-dismissable alert-success');
					$('#resultDialog').fadeOut({'duration':(1000*numSegundos)});
				}else if(codigo=="-1"){
					$('#resultDialog').css('display','inherit');
					$('#resultDialog').attr('class','alert alert-dismissable  alert-danger');
					$('#textMessageResult').append("Se han generado los siguientes errores:<br/>");
				}
				$('#textMessageResult').append(mensaje);
			}
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
		
		function lg(){
			$('#logoutBtn').click();
		}
		
		function showInProcessDialog(){
			$('#processModal').modal('show');
		}
		
		function closeProcessDialog(){
			$('#processModal').modal('hide');
		}
	</script>
	<style type="text/css">
		.glyphicon{
			padding-right:5px;
		}
		.glyphicon-list-alt:before{
			padding-right:5px;
		}
		/* CUSTOM- Centrar los containers de bootstrap*/
		/* centered columns styles */
		.row-centered {
		    text-align:center;
		}
		.col-centered {
		    display:inline-block;
		    float:none;
		    /* reset the text-align */
		    text-align:left;
		    /* inline-block space fix */
		    margin-right:-4px;
		}
		/* END-CUSTOM*/
	</style>
	<decorator:head/>
</head>
<s:url namespace="/mediaplayer" action="showPlaylist" var="urlQRPlayerCreator"/>
<s:url namespace="/mediaplayer" action="openPlaylist" var="urlQRPlayer"/>
<s:url namespace="/productos" action="catalogo" var="urlCatalogoProductos"/>
<s:url namespace="/negocio" action="getImg" var="urlLogo"/>
<s:url namespace="/negocio" action="miNegocio" var="urlMiNegocio"/>
<s:url namespace="/negocio" action="misMesas" var="urlMisMesas"/>
<s:url namespace="/mesaControl" action="verMesaControl" var="urlMesas"/>
<s:url namespace="/bu" action="showAll" var="urlUsuarios"/>
<s:url namespace="/promo" action="gtAll" var="urlPromos"/>
<s:url namespace="/analytics" action="show" var="urlEstadisticas"/>
<s:url namespace="/analytics" action="showProductsAnalysis" var="urlEstadisticasProductos"/>
<s:url namespace="/reservaciones" action="showAll" var="urlReservaciones"/>
<s:url namespace="/reservaciones" action="showPendingReservations" var="urlReservacionesPendientes"/>
<s:url namespace="/reservaciones" action="showDetails" var="urlReservacionesAvanzadas"/>
<s:url namespace="/pedidoDomicilio" action="open" var="urlPedidosDomicilio"/>
<s:url namespace="/pedidoDomicilio" action="history" var="urlHistorialPedidosDomicilio"/>
<s:url namespace="/console" action="users" var="urlUsuarios"/>
<s:url namespace="/bu" action="openBU" var="urlNuevoUsuario"/>
<body>

<!-- HEADER -->
<sec:authorize access="hasAnyRole('GV')">
<div class="navbar navbar-inverse">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="${urlUsuarios}">Consola de usuarios</a>
  </div>
  
  <div class="navbar-collapse collapse navbar-inverse-collapse">
  	<ul class="nav navbar-nav">
      <li class="active"><a href="${urlNuevoUsuario}"><span class="glyphicon glyphicon-user"></span> Nuevo Usuario</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
	  <li><a href="javascript:lg();"><span class="glyphicon glyphicon-off"></span>Salir</a></li>
    </ul>
  </div>
</div>
</sec:authorize>

<sec:authorize access="hasAnyRole('USR')">
<div class="navbar navbar-inverse">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="${urlMiNegocio}"><%= nombreNegocio%></a>
  </div>
  <div class="navbar-collapse collapse navbar-inverse-collapse">
    <ul class="nav navbar-nav">
      <li class="active"><a href="${urlMesas}"><span class="glyphicon glyphicon-star"></span> Mesa de Control</a></li>
      <li class="active"><a href="release/demo/quicko-release.apk"><span class="glyphicon glyphicon-save"></span> Demo</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-music"></span>Multimedia<b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="${urlQRPlayerCreator}" ><span class="glyphicon glyphicon-th-list"></span>Crear playlist</a></li>
          <li><a href="${urlQRPlayer}"><span class="glyphicon glyphicon-play-circle"></span>Reproductor de videos</a></li>
        </ul>
      </li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      
      <li class="dropdown ">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-home"></span>Restaurante <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="${urlMiNegocio}"><span class="glyphicon glyphicon-cutlery"></span>Mi Restaurante</a></li>
          <li><a href="${urlPromos}"><span class="glyphicon glyphicon-tags"></span>Promociones</a></li>
          <li><a href="${urlMisMesas}"><span class="glyphicon glyphicon-th-large"></span>Mesas</a></li>
          
          <li class="divider"></li>
          <li class="dropdown-header">Reservaciones</li>
          <li><a href="${urlReservaciones}"><span class="glyphicon glyphicon-stats"></span>Reservaciones de Hoy</a></li>
          <li><a href="${urlReservacionesPendientes}"><span class="glyphicon glyphicon-stats"></span>Reservaciones Pendientes</a></li>
          <li><a href="${urlReservacionesAvanzadas}"><span class="glyphicon glyphicon-check"></span>Busqueda Avanzada</a></li>
          
          <li class="divider"></li>
          <li class="dropdown-header">Pedidos</li>
          <li><a href="${urlPedidosDomicilio}"><span class="glyphicon glyphicon-stats"></span>Nuevos pedidos</a></li>
          <li><a href="${urlPedidosDomicilio}"><span class="glyphicon glyphicon-stats"></span>Pedidos en atencion</a></li>
          <li><a href="${urlHistorialPedidosDomicilio}"><span class="glyphicon glyphicon-check"></span>Historial</a></li>
          
          <li class="divider"></li>
          <li class="dropdown-header">Mi Men&uacute;</li>
          <li><a href="${urlCatalogoProductos}"><span class="glyphicon glyphicon-list-alt"></span>Catalogo de productos</a></li>
          
		  <li class="divider"></li>          
          <li class="dropdown-header">Extras</li>
          <li><a href="${urlEstadisticas}"><span class="glyphicon glyphicon-stats"></span>Estadisticas</a></li>
          <li><a href="${urlEstadisticasProductos}"><span class="glyphicon glyphicon-stats"></span>Análisis de producto</a></li>
        </ul>
      </li>
	  <li><a href="javascript:lg();"><span class="glyphicon glyphicon-off"></span>Salir</a></li>
    </ul>
  </div>
</div>
</sec:authorize>
<!-- END HEADER -->
<s:hidden name="messageCode" id="messageCode" />
<s:hidden name="messageResult" id="messageResult" />
<!-- old old old old old old old old old old old old old old -->
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
	
	<!-- CONTENIDO -->
	<div class="container">
	    <div class="row row-centered">
	    	<div class="col-xs-12 col-centered">
	    		<!-- Contenedor -->
	    		<div class="well">
	    		<div class="alert alert-dismissable alert-success"  style="display:none;" id="resultDialog">
				  <button type="button" class="close" data-dismiss="alert">×</button>
				  <div id="textMessageResult"><s:actionmessage escape="false"/> </div>
				</div>
	    		<decorator:body/>
	    		</div>
				<!-- FIN-Contenedor -->
	    	</div>
	    </div>
	</div>
	
	<!-- Process Modal -->
	<div class="modal fade" id="processModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title" id="myModalLabel">Procesando...</h4>
	      </div>
	      <div class="modal-body center-block">
	        Espere un momento mientras se atiende su petición.
	      </div>
	      <div class="modal-footer">
<!-- 	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>