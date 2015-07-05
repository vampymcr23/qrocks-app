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
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Promos</title>
	<!-- Scripts -->
	<!-- END Scripts -->
<s:url namespace="/m/droid/promo" action="gtPromoPng" var="urlImagen"/>
	<style type="text/css">
		form label{
			display:inline-block;
		}
		
		.productoStyle{
			background: #1e1e1e;
			box-shadow: inset 0px 0px 0px 1px rgba(0,0,0,0.15), 0px 2px 3px 0px rgba(0,0,0,0.1);
			text-align: center;
			padding: 0.25em 0.25em 0.75em 0.25em;
			text-align:left;
			display:inline.block;
 			background-color: #1e1e1e; 
			color:#e8e7e5;
			margin-bottom: 0em;
		}
		.productoStyle h4{
			font-weight: bold;
			color:#e6eff2;
			height: 20px;
			margin-bottom: 0.7em;
			margin-top:0.3em;
		}
		.productoStyle h5{
			color:#bcedff;
			height: 20px;
			margin-bottom: 0;
			margin-top: 0.2em;
		}
		.genderStyle, .findBox{
			background-color: #000;
			color:#fff;
			padding:1em;
			text-align: left;
			width:100%;
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
		
		.panelCentralProductos{
			top:300px;
			left:300px;
			position:fixed;
			background-color: #000;
			color:#fff;
			width: 100%;
			height:500px;
			overflow-y:auto;
			padding-left:10px;
		}
		
		.imagenProducto{
			display:inline-block;
			vertical-align:middle;
			width:100px;
			max-width:100px;
			padding-right:10px;
		}
		
		.contenidoProducto{
			width:50%;
			max-width:700px;
			display:inline-block;
		}
		
		.opcionesProducto{
			display:inline;
			vertical-align:middle;
			width:100px;
			max-width:100px;
		}
		
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		var playList=null;
		$(document).ready(function(){
		});
		
		function renderearLista(){
			var url="";
			//ajax
			var listaProductos=new Array();
			if(listaProductos!=null && listaProductos.length>0){
				$('.productoStyle').remove();//Se eliminan todos los productos con el class="productoStyle"
				for(var i=0;i<listaProductos.length;i++){
					var producto=listaProductos[i];
					var html="<section class='productoStyle'>";
					html+="<div style='display:inline-block;vertical-align:bottom;width:10%;max-width:80px;padding-right:10px;'>";
					html+="<img src='${urlImagen}?idPromo="+producto.id+"' width='80px' height='80px' title='Agregar' alt='Agregar'/>";
					$('#divListaProductos').append();
				}
			}
		}
		
		function editar(id){
			$('#idHidden').val(id);
			$('#btnNuevo').click();
		}
		
		function send(id){
			$('#idHidden').val(id);
			$('#btnSend').click();
		}
		
		function desactivar(id){
			$('#idHidden').val(id);
			$('#btnEnabled').click();
		}
	</script>
</head>
<body>
<s:form namespace="/promo" method="POST" id="forma">
	<!-- Botones hidden para hacer submit -->
	<s:submit action="openPromo" id="btnNuevo" cssStyle="display:none;"/>
	<s:submit action="disabledPromo" id="btnEnabled" cssStyle="display:none;"/>
	<s:submit action="sendNotif" id="btnSend" cssStyle="display:none;"/>
	<s:hidden name="promo.id" id="idHidden"/>
	<!-- End de botones -->

	<div class="panelConsulta" style="height:300px;">
		<header>
			<h3 class="alt"><strong>Promociones</strong></h3>
		</header>
		<div style="width:99%;">
			<label>Buscar promo:</label><input type="text" maxlength="40"/>
			<input type="button" value="Buscar" class="button scrolly"/>
			<input type="button" value="Agregar promo"  class="button scrolly" onclick="$('#btnNuevo').click();"/>
		</div>
	</div>
	<div class="panelCentralProductos">
		<!-- Iterar la lista de amigos -->
		<s:iterator value="promociones" var="p">
				
				<section class="productoStyle">
					<div class="imagenProducto">
							<img src="${urlImagen}?idPromo=${p.id}" width="100px" height="auto" title="Promo" alt="Promo"/>
					</div>
					<div class="contenidoProducto">
						<h4>${p.negocio.nombre} > ${p.nombrePromo}</h4>
						<h5>${p.descripcionPromo} </h5>
						<h6>Vigencia: ${p.fechaExpStr} [Estatus: ${(p.enabled==1)?'Activo':'Inactivo'}]- Notificacion enviada:${(p.notificacionEnviada==1)?'Si':'No'} </h6>
					</div>
					<div class="opcionesProducto">
						<s:if test="%{ #p.notificacionEnviada !=1 }">
						<a href="javascript:send(${p.id});">
							<img src="images/android-Messages.png" width="40px" height="auto" title="Notificar" alt="Notificar"/>
						</a>
						</s:if>
						<a href="javascript:editar(${p.id});">
							<img src="images/qrocks_edit2.png" width="40px" height="auto" title="Editar" alt="Editar"/>
						</a>
						<a href="javascript:desactivar(${p.id});">
							<img src="images/qrocks_delete2.png" width="40px" height="auto" title="Desactivar/Activar" alt="Desactivar/Activar"/>
						</a>
					</div>
				</section>
				
		</s:iterator>
	</div>
</s:form>
</body>
</html>