<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>Historial</title>
	<!-- Scripts -->
	<!-- END Scripts -->
<s:url namespace="/m/droid/promo" action="gtPromoPng" var="urlImagen"/>
<s:url namespace="/pedidoDomicilio" action="details" var="urlVerDetalle"/>
<s:url namespace="/pedidoDomicilio" action="auth" var="urlAutorizar"/>
<s:url namespace="/pedidoDomicilio" action="reject" var="urlRechazar"/>
<s:url namespace="/pedidoDomicilio" action="searchPnd" var="urlFiltrar"/>
<s:url namespace="/pedidoDomicilio" action="markAsCancel" var="urlCancelar"/>
<style type="text/css">
		.iconoProducto{
			padding-left:10px;
			font-size:1.6em;
		}
		
		.iconoProductoMin{
			padding-left:10px;
			font-size:1.2em;
		}
		
		.itemProducto{
			font-size:1.4em;
		}

		.infoProducto >h2{
			display:inline;
			font-size:1em;
		}
		
		.infoProducto >p{
			font-size:0.8em;
		}
		
		.imagenProducto,.infoProducto{
			display:inline-block;
			vertical-align: top;
			line-height: 1.5em;
		}
		
		.imagenProducto{
			width:10%;
			min-width:50px;
			max-width: 50px;
		}
		
		.infoProducto{
			width:80%;
			padding-left:20px;
		}
		
		.deshabilitado{
			color: #ededed;
		}
		
		.iconOn{
			color:#56bf48;
		}
		
		.iconOff{
			color:#f96b5e;
		}
		
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
		});
		
		function filtrar(){
			var criteria=$('#filtroPedido').val();
			$.ajax({
				data:{'filtro.appUser.nombre':criteria},
				url:"${urlFiltrar}",
				dataType: "json",
				async:false,
				success:function(pedidos){
					if(pedidos==null || pedidos.length==0){
						showDialog('0','No se encontraron resultados para esta busqueda');
					}
					renderearPedidos(pedidos);
				}
			});
			
		}
		
		function actionVerDetalle(id){
			$('#idHidden').val(id);
			$('#forma').attr('action','${urlVerDetalle}');
			$('#forma').submit();
		}
		
		function actionRechazar(id){
			$.ajax({
				data:{'pedidoDomicilio.id':id},
				url:"${urlRechazar}",
				dataType: "json",
				async:false,
				success:function(pedidos){
					if(pedidos!=null && pedidos.length>0){
						renderearPedidos(pedidos);
						showDialog('0','Se ha rechazado el pedido no.'+id+' de manera exitosa!');
					}
				}
			});
		}
		
		function actionCancelar(id){
			if(confirm('Esta seguro de cancelar este pedido?')){
				$.ajax({
					data:{'pedidoDomicilio.id':id},
					url:"${urlCancelar}",
					dataType: "json",
					async:false,
					success:function(pedidos){
						if(pedidos!=null){
							renderearPedidos(pedidos);
							showDialog('0','Se ha cancelado el pedido no.'+id+' de manera exitosa!');
						}
					}
				});
			}
		}
		
		function actionAutorizar(id){
			$.ajax({
				data:{'pedidoDomicilio.id':id},
				url:"${urlAutorizar}",
				dataType: "json",
				async:false,
				success:function(pedidos){
					if(pedidos!=null){
						renderearPedidos(pedidos);
						showDialog('0','Se ha autorizado el pedido no.'+id+' de manera exitosa!');
					}
				}
			});
		}
		
		/**
		 * Funcion para renderear las promociones de manera dinamica
		 */
		function renderearPedidos(pedidos){
			var html='<ul class="list-group">';
			for(var i=0;i<pedidos.length;i++){
				var p=pedidos[i];
				if(p!=null){
					html+='<li class="list-group-item active">'+p.id+'</li>';
					html+='<li class="list-group-item itemProducto"> ';
					html+=' 	<div class="row">';
					html+='		<div class="col-lg-10 col-md-10 col-sm-10 col-xs-9"> ';
					html+='		    <p> ';
					html+='		    	'+p.appUser.nombre+' ';
					html+='		    </p> ';
					html+='		    <h6>Telefono: '+p.appUser.telefono+' </h6> ';
					html+='		    <h6>Estatus: '+p.estatusStr+' </h6> ';
					html+='		</div>';
					html+='		</div>';
					html+='		<div class="row">';
					html+='	    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-9"> ';
					html+='	    	<span class="glyphicon glyphicon-th-list iconoProducto" onclick="actionVerDetalle('+p.id+');" title="Ver"></span> ';
					html+='	    	<span class="glyphicon glyphicon-trash iconoProducto" onclick="actionCancelar('+p.id+');" title="Cancelar"></span> ';
					html+='	    </div> ';
					html+='		</div>';
					html+='</li> ';
				}
			}
			html+="</ul>";
			$('#divPedidos').text("");
			$('#divPedidos').append(html);
			return html;
		}
	</script>
</head>
<body>
<s:form namespace="/pedidoDomicilio" method="POST" id="forma">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="pedidoDomicilio.id" id="idHidden"/>
	<!-- End de botones -->

	<fieldset>
		  <legend>Mis pedidos</legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-lg-11 col-md-11 col-sm-12 col-xs-12 control-label">A continuacion se muestran los pedidos que estan pendientes por autorizar.</label>
		    <br/>
		    <label for="filtro" class="col-lg-2 col-md-2 col-sm-12 col-xs-12 control-label">Buscar por nombre:</label>
		    <input type="text" id="filtroPedido" class="col-lg-3 col-md-3 col-sm-6 col-xs-6"/>
		    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
		    	<button type="button" class="btn btn-primary" onclick="filtrar();">Filtrar</button>
		    </div>
		  </div>
		  <br/><br/>
		  <br/><br/>
		  <!-- Lista de productos -->
		  <div class="form-group" id="divPedidos">
		  	<ul class="list-group">
		  	<s:iterator value="pedidos" var="p">
		  	  <li class="list-group-item active">${p.id} - ${p.appUser.nombre}</li>
			  <li class="list-group-item itemProducto">
			  	<div class="row">
				    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-9">
					    <h6>Telefono: ${p.appUser.telefono} </h6>
					    <h6>Estatus: ${p.estatusStr}</h6>
				    </div>
				  </div>
				  <div class="row">
				  	<div class="col-lg-10 col-md-10 col-sm-10 col-xs-9">
				  		<span class="glyphicon glyphicon-th-list iconoProducto" onclick="actionVerDetalle(${p.id});" title="Ver"></span>
			    		<span class="glyphicon glyphicon-trash iconoProducto" onclick="actionCancelar(${p.id});" title="Cancelar"></span>
				    </div>
				  </div>
			  </li>
			</s:iterator>
			</ul>
		  </div>
	</fieldset>
</s:form>
</body>
</html>