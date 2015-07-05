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
	<title>Quuiko-Promos</title>
	<!-- Scripts -->
	<!-- END Scripts -->
<s:url namespace="/m/droid/promo" action="gtPromoPng" var="urlImagen"/>
<s:url namespace="/promo" action="openPromo" var="urlNuevaPromo"/>
<s:url namespace="/promo" action="disabledPromo" var="urlDeshabilitarPromo"/>
<s:url namespace="/promo" action="sendNotif" var="urlEnviarNotificacion"/>
<s:url namespace="/promo" action="dltPromo" var="urlEliminarPromo"/>
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
		
		function actionEditar(id){
			$('#idHidden').val(id);
			$('#forma').attr('action','${urlNuevaPromo}');
			$('#forma').submit();
		}
		
		function actionNuevaPromo(){
			$('#idHidden').val('');
			$('#forma').attr('action',"${urlNuevaPromo}");
			$('#forma').submit();
		}
		
		function actionDesactivarPromo(id,toggle){
			$.ajax({
				data:{'promo.id':id},
				url:"${urlDeshabilitarPromo}",
				dataType: "json",
				async:false,
				success:function(promos){
					if(promos!=null && promos.length>0){
						renderearPromos(promos);
						var tipoActualizacion=(toggle=='on')?'habilitado':'deshabilitado';
						showDialog('0','Se ha '+tipoActualizacion+' la promocion de manera exitosa!');
					}
				}
			});
		}
		
		function actionEliminarPromo(id){
			if(confirm('Esta seguro de eliminar esta promocion permanentemente?')){
				$.ajax({
					data:{'promo.id':id},
					url:"${urlEliminarPromo}",
					dataType: "json",
					async:false,
					success:function(promos){
						if(promos!=null){
							renderearPromos(promos);
							showDialog('0','Se ha eliminado la promocion de manera exitosa!');
						}
					}
				});
			}
		}
		
		function actionEnviarNotificacion(id){
			$('#idHidden').val(id);
			$('#forma').attr('action','${urlEnviarNotificacion}');
			$('#forma').submit();
		}
		
		/**
		 * Funcion para renderear las promociones de manera dinamica
		 */
		function renderearPromos(promos){
			var html='<ul class="list-group">';
			for(var i=0;i<promos.length;i++){
				var p=promos[i];
				if(p!=null){
					html+='<li class="list-group-item active">'+p.nombrePromo+'</li>';
					html+='<li class="list-group-item itemProducto"> ';
					html+=' 	<div class="row">';
					html+='		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-3"> ';
					html+='			<img src="${urlImagen}?idPromo='+p.id+'" width="50px" height="auto" title="Promo" alt="Promo"/> ';
					html+='		</div> ';
					html+='		<div class="col-lg-10 col-md-10 col-sm-10 col-xs-9"> ';
					html+='		    <p> ';
					html+='		    	'+p.descripcionPromo+' ';
					html+='		    </p> ';
					var estatus=(p.enabled==1)?'Activo':'Inactivo';
					var notificacionEnviada=(p.notificacionEnviada==1)?'Si':'No';
					html+='		    <h6>Vigencia: '+p.fechaExpStr+' [Estatus: '+estatus+']- Notificacion enviada:'+notificacionEnviada+' </h6> ';
					html+='		</div>';
					html+='		</div>';
					html+='		<div class="row">';
					html+='	    <div class="col-lg-10 col-lg-offset-2 col-md-10  col-md-offset-2 col-sm-10  col-sm-offset-2 col-xs-9 col-xs-offset-3"> ';
					if(p.notificacionEnviada==1){
						html+='	    	<span class="glyphicon glyphicon-comment iconoProducto deshabilitado" title="Notificación enviada"></span> ';	
					}else{
						html+='	    	<span class="glyphicon glyphicon-comment iconoProducto" onclick="actionEnviarNotificacion('+p.id+');" title="Notificar"></span> ';
					}
					html+='	    		<span class="glyphicon glyphicon-pencil iconoProducto" onclick="actionEditar('+p.id+');" title="Editar"></span> ';
					if(p.enabled==1){
						html+='	    	<span class="glyphicon glyphicon-ok-sign iconoProducto iconOn" onclick="actionDesactivarPromo('+p.id+',\'off\');" title="Click para deshabilitar"></span> ';	
					}else{
						html+='	    	<span class="glyphicon glyphicon-minus-sign iconoProducto iconOff" onclick="actionDesactivarPromo('+p.id+',\'on\');" title="Click para habilitar"></span> ';
					}
					html+='	    	<span class="glyphicon glyphicon-trash iconoProducto" onclick="actionEliminarPromo('+p.id+');" title="Eliminar"></span> ';
					html+='	    </div> ';
					html+='		</div>';
					html+='</li> ';
				}
			}
			html+="</ul>";
			$('#divPromos').text("");
			$('#divPromos').append(html);
			return html;
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

	<fieldset>
		  <legend>Mis promociones</legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-lg-8 col-md-8 col-sm-12 col-xs-12 control-label">Ahora usted podrá agregar sus promociones y notificar a sus clientes al instante!</label>
		    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
		    	<button type="submit" class="btn btn-primary" onclick="actionNuevaPromo();">Nuevo Promoción</button>
		    </div>
		  </div>
		  <br/><br/>
		  <br/>
		  <!-- Lista de productos -->
		  <div class="form-group" id="divPromos">
		  	<ul class="list-group">
		  	<s:iterator value="promociones" var="p">
		  	  <li class="list-group-item active">${p.nombrePromo}</li>
			  <li class="list-group-item itemProducto">
			  	<div class="row">
				    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-3">
				    	<img src="${urlImagen}?idPromo=${p.id}" width="50px" height="auto" title="Promo" alt="Promo"/>
				    </div>
				    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-9">
					    <p>
					    	${p.descripcionPromo}
					    </p>
					    <h6>Vigencia: ${p.fechaExpStr} [Estatus: ${(p.enabled==1)?'Activo':'Inactivo'}]- Notificacion enviada:${(p.notificacionEnviada==1)?'Si':'No'} </h6>
				    </div>
				  </div>
				  <div class="row">
				  	<div class="col-lg-10 col-lg-offset-2 col-md-10  col-md-offset-2 col-sm-10  col-sm-offset-2 col-xs-9 col-xs-offset-3">
				    	<s:if test="#p.notificacionEnviada==1">
			    			<span class="glyphicon glyphicon-comment iconoProducto deshabilitado" title="Notificación enviada"></span>
			    		</s:if>
			    		<s:else>
			    			<span class="glyphicon glyphicon-comment iconoProducto" onclick="actionEnviarNotificacion(${p.id});" title="Notificar"></span>
			    		</s:else>
			    		<span class="glyphicon glyphicon-pencil iconoProducto" onclick="actionEditar(${p.id});" title="Editar"></span>
			    		<s:if test="#p.enabled==1">
			    			<span class="glyphicon glyphicon-ok-sign iconoProducto iconOn" onclick="actionDesactivarPromo(${p.id},'off');" title="Click para deshabilitar"></span>
			    		</s:if>
			    		<s:else>
			    			<span class="glyphicon glyphicon-minus-sign iconoProducto iconOff" onclick="actionDesactivarPromo(${p.id},'on');" title="Click para habilitar"></span>
			    		</s:else>
			    		<span class="glyphicon glyphicon-trash iconoProducto" onclick="actionEliminarPromo(${p.id});" title="Eliminar"></span>
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