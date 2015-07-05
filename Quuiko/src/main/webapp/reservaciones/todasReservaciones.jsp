<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<s:url namespace="/productos" action="imagenProducto" var="urlImagen"/>
<s:url namespace="/reservaciones" action="showDetailsPag" var="urlSearch"/>
<s:url namespace="/reservaciones" action="okReservation" var="urlMarcarAsistencia"/>
<s:url namespace="/reservaciones" action="naReservation" var="urlMarcarFalta"/>
<s:url namespace="/reservaciones" action="sendAuthReservation" var="urlEnviarNotificacion"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Reservaciones</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		.iconoProducto{
			padding-left:10px;
			font-size:2em;
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
		
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		
		$(document).ready(function(){
			$(":input").inputmask();
		});
		
		function consultarReservaciones(paginaActual){
			$.ajax({
				dataType:"json",
				data:{
					'filtroReservacion.gcmUser.nombre':$('#nombre').val(),
					'filtroReservacion.estatus':$('#tipoEstatus').val(),
					'filtroReservacion.id':$('#idReservacion').val(),
					'filtroReservacion.fechaReservacion':$('#fechaInicio').val(),
					'filtroReservacion.fechaReservacionFin':$('#fechaInicio').val(),
					'paginaActual':paginaActual
				},
				url:"${urlSearch}",
				async:false,
				success:function(datos){
					renderearReservaciones(datos);
					renderearPaginacion(datos.numPaginas,paginaActual,datos.conteo);
				}
			});
		}
		
		/**
		 * FUncion que renderear la lista de reservaciones
		 */
		function renderearReservaciones(datos){
			if(datos!=null && datos.reservaciones!=null && datos.reservaciones.length>0){
				var html='<ul class="list-group">';
				for(var i=0;i<datos.reservaciones.length;i++){
					var reservacion=datos.reservaciones[i];
					html+=renderearReservacion(reservacion);
				}
				html+='</ul>';
				$('#listaReservacionesDiv').text("");
				$('#listaReservacionesDiv').append(html);
			}else{
				var html='<ul class="list-group">';
				html+='<li class="list-group-item active">Resultado de la búsqueda</li>';
				html+='<li class="list-group-item itemProducto">0 registros encontrados</li>';
				html+='</ul>';
				$('#listaReservacionesDiv').text("");
				$('#listaReservacionesDiv').append(html);
			}
		}
		
		function renderearReservacion(reservacion){
			var html='';
			var fecha=reservacion.fechaReservacionStr;
			var estatus=reservacion.estatusStr;
			var telefono=(reservacion.gcmUser.telefono!=null)?reservacion.gcmUser.telefono:'';
			var nombre=(reservacion.gcmUser.nombre!=null)?reservacion.gcmUser.nombre:'';
			var autorizacionEnviada=(reservacion!=null && reservacion.autorizacionEnviada!=null)?reservacion.autorizacionEnviada:0;
			var id=reservacion.id;
			var numPersonas=reservacion.numPersonas;
			html+='<li class="list-group-item active">Reservación No.'+id+' - Fecha y Hora: '+fecha+'</li>';
			html+='<li class="list-group-item itemProducto">';
			html+='		<div class="infoProducto"> ';
			html+='			<div style="display:inline-block;float:right;"> ';
			if(autorizacionEnviada==0 && (estatus=='PEND' || estatus=='PENDIENTE' || estatus=='AUT' || estatus=='AUTORIZADA')){
				html+='			<span class="glyphicon glyphicon-envelope iconoProducto" onclick="enviarNotificacionAutorizacion('+id+');" title="Enviar autorizacion"></span> ';	
			}
			html+='				<span class="glyphicon glyphicon-ok iconoProducto" onclick="marcarAsistencia('+id+');" title="Marcar asistencia"></span> ';
			html+='				<span class="glyphicon glyphicon-remove iconoProducto" onclick="marcarFalta('+id+');" title="Marcar marcarFalta"></span> ';
			html+='			</div> ';
			html+='			<h2>'+nombre+'</h2> ';
			html+='			<h5> Teléfono:'+telefono+'</h5>';
			html+='			<h5> Num. Personas:'+numPersonas+'</h5>';
			html+='			<h5> Estatus: '+estatus+' </h5>';
			html+='		</div>';
			html+='</li>';
			return html;
		}
		
		function renderearPaginacion(numPaginas,paginaActual,conteo){
			var html="";
			html+='<li><a href="javascript:consultarReservaciones(0);">«</a></li>';
			
			for(var i=0;i<numPaginas;i++){
				if(i==paginaActual){
					html+='<li id="pag_'+i+'" class="active"><a href="javascript:consultarReservaciones('+i+');">'+(i+1)+'</a></li>';
				}else{
					html+='<li id="pag_'+i+'"><a href="javascript:consultarReservaciones('+i+');">'+(i+1)+'</a></li>';
				}
			}
			html+='<li><a href="javascript:consultarReservaciones('+(numPaginas-1)+');">»</a></li>';
			$('#paginationDiv').text('');
			$('#paginationDiv').append(html);
			$('#paginadoNumRegistros').text('');
			$('#paginadoNumRegistros').append('<h4>Total de registros: '+conteo+'</h4>');
		}
		
		function marcarAsistencia(id){
			$.ajax({
				dataType:"json",
				data:{'idReservacion':id},
				url:"${urlMarcarAsistencia}",
				async:false,
				success:function(datos){
					consultarReservaciones(0);
					var codigo=datos.messageCode;
					var msg=datos.messageResult;
					if(codigo!=null && msg!=null){
						$('#messageCode').val(codigo);
						$('#messageResult').val(msg);
						alert(msg);
					}
				}
			});
		}
		
		function marcarFalta(id){
			$.ajax({
				dataType:"json",
				data:{'idReservacion':id},
				url:"${urlMarcarFalta}",
				async:false,
				success:function(datos){
					consultarReservaciones(0);
					var codigo=datos.messageCode;
					var msg=datos.messageResult;
					if(codigo!=null && msg!=null){
						$('#messageCode').val(codigo);
						$('#messageResult').val(msg);
						alert(msg);
					}
				}
			});
		}
		
		function enviarNotificacionAutorizacion(id){
			$.ajax({
				dataType:"json",
				data:{'idReservacion':id},
				url:"${urlEnviarNotificacion}",
				async:false,
				success:function(datos){
					consultarReservaciones(0);
					var codigo=datos.messageCode;
					var msg=datos.messageResult;
					if(codigo!=null && msg!=null){
						$('#messageCode').val(codigo);
						$('#messageResult').val(msg);
						alert(msg);
					}
				}
			});
		}
		
	</script>
</head>
<body>
<s:form namespace="/productos" method="POST" id="forma" cssClass="form-horizontal" enctype="multipart/form-data">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="conteo" id="conteo" value="0"/>
	<s:hidden name="paginaActual" id="paginaActual" value="0"/>
	<fieldset>
		  <legend>Mis reservaciones para el dia de hoy </legend>
		    
		  
		  <div class="form-group">
		  	<label for="mesa.claveMesa" class="col-lg-2 col-md-3 col-sm-5 col-xs-12 control-label">No. Reservacion:</label>
		    <div class="col-lg-2 col-md-3 col-sm-6 col-xs-12">
		    	<input type="text" class="form-control" maxlength="10" id="idReservacion" name="filtroReservacion.id" placeholder="folio de la reservacion" data-inputmask="'alias': 'decimal'"/>
		    </div>
		    
		    <label for="mesa.claveMesa" class="col-lg-2 col-md-3 col-sm-5 col-xs-12 control-label">Nombre del cliente:</label>
		    <div class="col-lg-2 col-md-3 col-sm-6 col-xs-12">
		    	<input type="text" class="form-control" maxlength="25" id="nombre" name="filtroReservacion.gcmUser.nombre" placeholder="nombre de la persona que desea buscar su reservacion"/>
		    </div>
		    
		    <label for="mesa.claveMesa" class="col-lg-2 col-md-3 col-sm-5 col-xs-12 control-label">Estatus:</label>
		    <div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 ">
		    	<select name="filtroReservacion.estatus"  class="form-control" id="tipoEstatus">
		    		<option value="PEND">Pendientes</option>
					<option value="AUT">Autorizadas</option>
					<option value="CONF">Confirmadas</option>
					<option value="CAN">Canceladas</option>
					<option value="N/A">Sin Asistencia</option>
					<option value="OK">Asistencia</option>
					<option value="RECH">Rechazada</option>
					<option value="" selected="true">Todas</option>
				</select>
		    </div>
		  </div>
		  
		  <div class="form-group">
			<label class="col-xs-2 control-label">Fecha de Reservación de inicio:</label>
		    <div class="col-xs-4">
		      <input id="fechaInicio" type="text" class="form-control" placeholder="Fecha en que vence la promoción ,ej:(21/08/2014)" data-inputmask="'alias': 'date','mask':'d/m/y'"/>
		    </div>
		    
		    <label  class="col-xs-2 control-label">Fecha de Reservación de fin:</label>
		    <div class="col-xs-4">
		      <input id="fechaFin" type="text" class="form-control" placeholder="Fecha en que vence la promoción ,ej:(21/08/2014)" data-inputmask="'alias': 'date','mask':'d/m/y'"/>
		    </div>
		 </div>
		  
		  <div class="form-group">
		      <div class="col-lg-11 col-lg-offset-11 col-md-9 col-md-offset-9 col-sm-8 col-sm-offset-8  col-xs-4 col-xs-offset-4">
		        <button type="button" class="btn btn-primary" onclick="consultarReservaciones();">Buscar</button>
		      </div>
		  </div>
		  <br/><br/>
		  <!-- Lista de reservaciones -->
		  <div id="paginadoNumRegistros"><h4>Total de registros: ${conteo}</h4></div>
		  <ul class="pagination pagination-sm" id="paginationDiv">
			  <li><a href="javascript:consultarReservaciones(0);">«</a></li>
			  <s:iterator begin="1" end="%{numPaginas}" status="i">
			  		<s:if test="#i.index==0">
			  			<li id="pag_${i.index}" class="active"><a href="javascript:consultarReservaciones(${i.index});">${i.index +1}</a></li>
			  		</s:if>
			  		<s:else>
			  			<li id="pag_${i.index}"><a href="javascript:consultarReservaciones(${i.index});">${i.index+1}</a></li>
			  		</s:else>
			  </s:iterator>
			  <li><a href="javascript:consultarReservaciones(${numPaginas-1});">»</a></li>
		  </ul>
		  <div class="form-group" id="listaReservacionesDiv">
		  	<ul class="list-group">
			<s:iterator value="reservaciones" var="r">
			
			  <li class="list-group-item active">Reservación No.${r.id} <br/>Fecha y Hora: ${r.fechaReservacionStr}</li>
			  <li class="list-group-item itemProducto">
				    <div class="infoProducto">
				    	<div style="display:inline-block;float:right;">
				    		<s:if test="#r.autorizacionEnviada==0 && ( #r.estatusStr=='PEND' || #r.estatusStr=='PENDIENTE' || #r.estatusStr=='AUT' || #r.estatusStr=='AUTORIZADA')">
				    			<span class="glyphicon glyphicon-envelope iconoProducto" onclick="enviarNotificacionAutorizacion(${r.id});" title="Enviar autorizacion"></span>
				    		</s:if>
				    		<span class="glyphicon glyphicon-ok iconoProducto" onclick="marcarAsistencia(${r.id});" title="Marcar asistencia"></span>
				    		<span class="glyphicon glyphicon-remove iconoProducto" onclick="marcarFalta(${r.id});" title="Marcar falta"></span>
				    	</div>
					    <h2>${r.gcmUser.nombre}</h2>
					    <h5>Teléfono: ${r.gcmUser.telefono}</h5>
					    <h5>No.Personas: ${r.numPersonas}</h5>
					    <h5>Estatus: ${r.estatusStr}</h5>
					    <h5>Aut. enviada: ${r.autorizacionEnviada}</h5>
				    </div>
			  </li>
			</s:iterator>
			</ul>
		  </div>
	</fieldset>
</s:form>
</body>
</html>