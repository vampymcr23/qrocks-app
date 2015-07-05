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
	<title>QRocks-Nuevo Producto</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		.imagenNegocio{
			padding-left:10px;
			display:inline;
		}
	
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		$(document).ready(function(){
			 $(":input").inputmask();
			 $('#tipoProducto').val("${producto.tipoProducto}");
// 			$('#costo').inputmask({'alias':'decimal',showTooltip:true});
		});
		
	</script>
</head>
<body>
<s:url namespace="/productos" action="imagenProducto" var="urlImagen"/>
<s:form namespace="/productos" method="POST" id="forma" action="guardarProducto" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="producto.id"/>
	<s:hidden name="producto.activo" />	
	<fieldset>
		<legend>Datos del Producto</legend>
		<div class="imagenNegocio">
			<img src="${urlImagen}?idProducto=${producto.id}" width="100px" height="auto" title="" alt=""/>
		</div>
		<div class="form-group">
			<label for="producto.nombre" class="col-xs-2 control-label">Nombre:</label>
		    <div class="col-xs-4">
		      <input type="text" maxlength="40" name="producto.nombre" value="${producto.nombre}" class="form-control" placeholder="Introduzca el nombre del nuevo producto"/>
		    </div>
		</div>
		<div class="form-group">
			<label for="producto.costoStr" class="col-xs-2 control-label">Costo:</label>
		    <div class="col-xs-4">
		      <input id="costo" type="text" maxlength="5" name="producto.costoStr" value="${producto.costo}" class="form-control" placeholder="Precio del producto" data-inputmask="'alias': 'decimal'"/>
		    </div>
		</div>
		<div class="form-group">
		    <label for="textArea" class="col-xs-2 control-label">Descripción:</label>
		    <div class="col-xs-6">
		      <textarea class="form-control" type="text"  style="min-height:0em;" maxlength="120" cols="20" rows="4" name="producto.descripcion" >${producto.descripcion}</textarea>
		      <span class="help-block">En este campo usted puede indicar las caracteristicas del producto(Máximo 120 caracteres)</span>
		    </div>
		</div>
		
		<div class="form-group">
		    <label for="textArea" class="col-xs-2 control-label">Descripción:</label>
		    <div class="col-xs-4">
		      <select id="tipoProducto" class="form-control" name="producto.tipoProducto">
		      	<option value="ENTRADA">Appetizers</option>
		      	<option value="CORTES">Cortes</option>
		      	<option value="MARISCOS">Mariscos</option>
		      	<option value="POSTRES">Postres</option>
		      	<option value="BEBIDAS">Bebidas</option>
		      	<option value="ENSALADAS">Ensaladas</option>
		      	<option value="SOPAS">Sopas y cremas</option>
		      	<option value="OTROS">Otros</option>
		      </select>
		    </div>
		</div>
		
		<div class="form-group">
			<label for=uploadLogo class="col-xs-2 control-label">Subir imagen del producto:</label>
			<div class="col-xs-3">
				<input type="file" name="producto.archivoImagen" class="form-control btn btn-default" placeholder="Selecciona un archivo..." />
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
	<!-- End de botones -->
		<!-- Intro -->
<%-- 		<section id="top" class="one"> --%>
<!-- 			<div class="formulario"> -->
<!-- 				<header class="tituloProductoHeader"> -->
<%-- 					<h2 class="alt tituloProducto"><strong>Producto</strong></h2> --%>
<!-- 					<div class="imagenProducto"> -->
<%-- 							<img src="${urlImagen}?idProducto=${producto.id}" width="100px" height="auto" title="" alt=""/> --%>
<!-- 					</div> -->
<!-- 				</header> -->
<!-- 				<div class="panelConsulta" style="width:99%;"> -->
<%-- 					<label>Nombre:</label><input type="text" maxlength="40" name="producto.nombre" value="${producto.nombre}"/> --%>
<!-- 					<br/> -->
<%-- 					<label>Costo:</label><input type="text" maxlength="5" name="producto.costoStr" value="${producto.costo}"/> --%>
<!-- 					<br/> -->
<!-- 					<label>Descripcion:</label><br/> -->
<!-- 					<textarea name="producto.descripcion" cols="20" rows="4"> -->
<%-- 						 ${producto.descripcion} --%>
<!-- 					</textarea> -->
					
					
					
<!-- 					<input type="button" value="Subir imagen" onclick="$('#fileUpload').click();" class="button scrolly"/> -->
<!-- 					<input type="button" value="Guardar" onclick="$('#btnGuardar').click();" class="button scrolly"/> -->
					
<%-- 					<s:file id="fileUpload" name="producto.archivoImagen" value="Seleccionar Imagen" cssStyle="display:none;"/> --%>
<%-- 					<s:hidden name="filename"/> --%>
<%-- 					<s:hidden name="contentType"/> --%>
					
<!-- 				</div> -->
<!-- 			</div> -->
						
<%-- 		</section> --%>
</s:form>
</body>
</html>