<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<s:url namespace="/m/droid/promo" action="gtPromoPng" var="urlImagen"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Quuiko-Usuario</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		form textarea{
			min-height: 1em;
			text-align: left;
			width:600px;
		}
		
		.imagenProducto{
			display:inline-block;
			padding-right:20px;
			vertical-align: middle;
		}
		.tituloProducto{
			display:inline-block;
			width:250px;
		}
		.tituloProductoHeader{
			margin:0em;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#estatusPromo').val("${promo.enabled}");
// 			$(":input").inputmask();
			var fechaActual=new Date();
			var anioActual=fechaActual.getFullYear();
			$('#fechaExp').inputmask('date',{yearrange: { minyear: anioActual, maxyear: 2099 }});
		});
		
	</script>
</head>
<body>
<s:form namespace="/promo" id="forma" action="savePromo"  method="POST" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="promo.id"/>
	<s:hidden name="promo.notificacionEnviada"/>
	<s:hidden name="promo.negocio.id" value="%{negocioLogeado.id}"/>
	<s:hidden name="promo.enabled"/>
	<s:submit value="savePromo" id="btnGuardar" cssStyle="display:none;"/>
	<!-- End de botones -->
	<fieldset>
		<legend>Datos de la promoción</legend>
		<div class="imagenNegocio">
			<img src="${urlImagen}?idPromo=${promo.id}" width="100px" height="auto" title="" alt=""/>
		</div>
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Promoción:</label>
		    <div class="col-xs-4">
		      <input type="text" maxlength="100" name="promo.nombrePromo" value="${promo.nombrePromo}" class="form-control" placeholder="Introduzca el nombre de la promoción"/>
		    </div>
		</div>
		<div class="form-group">
		    <label for="textArea" class="col-xs-2 control-label">Descripción:</label>
		    <div class="col-xs-6">
		      <textarea type="text" cols="40" maxlength="400" name="promo.descripcionPromo" class="form-control" >${promo.descripcionPromo}</textarea>
		      <span class="help-block">Introduzca los detalles de la promoción (Máximo 400 caracteres)</span>
		    </div>
		</div>
		
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Fecha de creación:</label>
		    <div class="col-xs-4">
		      <input type="text"  id="fechaCreacion" name="promo.fechaCreacionStr" value="${promo.fechaCreacionStr}" readonly="true" class="form-control" />
		    </div>
		</div>
		
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Fecha de vigencia:</label>
		    <div class="col-xs-4">
		      <input name="promo.fechaExpStr" id="fechaExp" type="text" value="${promo.fechaExpStr}" class="form-control" placeholder="Fecha en que vence la promoción ,ej:(21/08/2014)" data-inputmask="'alias': 'date','mask':'d/m/y'"/>
		    </div>
		</div>
		
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Estatus:</label>
		    <div class="col-xs-4">
		      	<input type="text" value="${(promo.enabled==1)?'Activo':'Inactivo'}" class="form-control" readonly="true"/>
		    </div>
		</div>
		
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Notificación enviada:</label>
		    <div class="col-xs-4">
		      	<input type="text" value="${(promo.notificacionEnviada==1)?'Si':'No'}" class="form-control" readonly="true"/>
		    </div>
		</div>
		
		<div class="form-group">
			<label for=uploadLogo class="col-xs-2 control-label">Subir imagen de la promocion:</label>
			<div class="col-xs-3">
				<input type="file" name="promo.archivoImagen" class="form-control btn btn-default" placeholder="Selecciona un archivo..." />
			</div>
		</div>
		
		<div class="form-group">
		    <div class="col-lg-10 col-lg-offset-2">
		    	<button type="submit" class="btn btn-primary" >Guardar</button>
			</div>
		</div>
		  
	</fieldset>
	<s:hidden name="filename"/>
	<s:hidden name="contentType"/>
	
	<!-- OLD -->
		<!-- Intro -->
<%-- 		<section id="top" class="one"> --%>
<!-- 			<div class="formulario"> -->
<!-- 				<header class="tituloProductoHeader"> -->
<%-- 					<h2 class="alt tituloProducto"><strong>Usuario</strong></h2> --%>
<!-- 				</header> -->
<!-- 				<div class="panelConsulta" style="width:99%;"> -->
<%-- 					<label>Nombre de la promo:</label><input type="text" maxlength="100" name="promo.nombrePromo" value="${promo.nombrePromo}"/> --%>
<!-- 					<br/> -->
<!-- 					<label>Descripcion:</label><br/> -->
<%-- 					<textarea type="text" cols="40" maxlength="400" name="promo.descripcionPromo" >${promo.descripcionPromo}</textarea> --%>
<!-- 					<br/> -->
<%-- 					<label>Fecha creacion:</label><input type="text"  id="fechaCreacion" name="promo.fechaCreacionStr" value="${promo.fechaCreacionStr}" readonly="true"/> --%>
<!-- 					<br/> -->
<%-- 					<label>Fecha expiracion:</label><input name="promo.fechaExpStr" id="fechaExp" type="text" value="${promo.fechaExpStr}"/> --%>
<!-- 					<br/> -->
<!-- 					<label>Estatus:</label> -->
<%-- 					<select name="promo.enabled" style="width:200px;display:inline-block;"> --%>
<!-- 						<option value="1">Activo</option> -->
<!-- 						<option value="0">Inactivo</option> -->
<%-- 					</select> --%>
<!-- 					<br/> -->
<%-- 					<s:file id="fileUpload" name="promo.archivoImagen" value="Seleccionar Imagen" cssStyle="display:none;"/> --%>
<%-- 					<s:hidden name="filename"/> --%>
<!-- 					<input type="button" value="Subir imagen" onclick="$('#fileUpload').click();" class="button scrolly"/> -->
<!-- 					<input type="button" value="Guardar" onclick="$('#btnGuardar').click();" class="button scrolly"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
						
<%-- 		</section> --%>
</s:form>
</body>
</html>