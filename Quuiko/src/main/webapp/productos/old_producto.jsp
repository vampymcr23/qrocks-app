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
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Nuevo Producto</title>
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
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<script type="text/javascript">
		$(document).ready(function(){
		});
		
	</script>
</head>
<body>
<s:url namespace="/productos" action="imagenProducto" var="urlImagen"/>
<s:form namespace="/productos" method="POST" id="forma" action="guardarProducto" enctype="multipart/form-data">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="producto.id"/>
	<s:submit value="Guardar" id="btnGuardar" cssStyle="display:none;"/>
	<!-- End de botones -->
		<!-- Intro -->
		<section id="top" class="one">
			<div class="formulario">
				<header class="tituloProductoHeader">
					<h2 class="alt tituloProducto"><strong>Producto</strong></h2>
					<div class="imagenProducto">
							<img src="${urlImagen}?idProducto=${producto.id}" width="100px" height="auto" title="" alt=""/>
					</div>
				</header>
				<div class="panelConsulta" style="width:99%;">
					<label>Nombre:</label><input type="text" maxlength="40" name="producto.nombre" value="${producto.nombre}"/>
					<br/>
					<label>Costo:</label><input type="text" maxlength="5" name="producto.costoStr" value="${producto.costo}"/>
					<br/>
					<label>Descripcion:</label><br/>
					<textarea name="producto.descripcion" cols="20" rows="4">
						 ${producto.descripcion}
					</textarea>
					<input type="button" value="Subir imagen" onclick="$('#fileUpload').click();" class="button scrolly"/>
					<input type="button" value="Guardar" onclick="$('#btnGuardar').click();" class="button scrolly"/>
					
					<s:file id="fileUpload" name="producto.archivoImagen" value="Seleccionar Imagen" cssStyle="display:none;"/>
					<s:hidden name="filename"/>
					<s:hidden name="contentType"/>
					
				</div>
			</div>
						
		</section>
</s:form>
</body>
</html>