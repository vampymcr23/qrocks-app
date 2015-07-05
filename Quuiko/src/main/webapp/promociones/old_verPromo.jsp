<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Usuario</title>
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
			$('#fechaCreacion').datepicker({dateFormat:"dd/mm/yyyy"});
			$('#fechaExp').datepicker({dateFormat:"dd/mm/yyyy"});
		});
		
	</script>
</head>
<body>
<s:form namespace="/promo" id="forma" action="savePromo"  method="POST" enctype="multipart/form-data">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="promo.id"/>
	<s:hidden name="promo.notificacionEnviada"/>
	<s:hidden name="promo.negocio.id" value="%{negocioLogeado.id}"/>
	<s:submit value="savePromo" id="btnGuardar" cssStyle="display:none;"/>
	<!-- End de botones -->
		<!-- Intro -->
		<section id="top" class="one">
			<div class="formulario">
				<header class="tituloProductoHeader">
					<h2 class="alt tituloProducto"><strong>Usuario</strong></h2>
				</header>
				<div class="panelConsulta" style="width:99%;">
					<label>Nombre de la promo:</label><input type="text" maxlength="100" name="promo.nombrePromo" value="${promo.nombrePromo}"/>
					<br/>
					<label>Descripcion:</label><br/>
					<textarea type="text" cols="40" maxlength="400" name="promo.descripcionPromo" >${promo.descripcionPromo}</textarea>
					<br/>
					<label>Fecha creacion:</label><input type="text"  id="fechaCreacion" name="promo.fechaCreacionStr" value="${promo.fechaCreacionStr}" readonly="true"/>
					<br/>
					<label>Fecha expiracion:</label><input name="promo.fechaExpStr" id="fechaExp" type="text" value="${promo.fechaExpStr}"/>
					<br/>
					<label>Estatus:</label>
					<select name="promo.enabled" style="width:200px;display:inline-block;">
						<option value="1">Activo</option>
						<option value="0">Inactivo</option>
					</select>
					<br/>
					<s:file id="fileUpload" name="promo.archivoImagen" value="Seleccionar Imagen" cssStyle="display:none;"/>
					<s:hidden name="filename"/>
					<input type="button" value="Subir imagen" onclick="$('#fileUpload').click();" class="button scrolly"/>
					<input type="button" value="Guardar" onclick="$('#btnGuardar').click();" class="button scrolly"/>
				</div>
			</div>
						
		</section>
</s:form>
</body>
</html>