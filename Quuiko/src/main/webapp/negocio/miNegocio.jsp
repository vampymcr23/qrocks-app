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
<s:url namespace="/negocio" action="showAllWaitress" var="urlMeseros"/>
<s:url namespace="/negocio" action="asignWaitress" var="urlAsignarMesero"/>
<s:url namespace="/negocio" action="addWaitress" var="urlAgregarMesero"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" media="all" />
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Mi Negocio</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		form label{
			display:inline-block;
		}
		
		.productoStyle{
			background: #1e1e1e;
			box-shadow: inset 0px 0px 0px 1px rgba(0,0,0,0.15), 0px 2px 3px 0px rgba(0,0,0,0.1);
			text-align: center;
			padding: 1em 1em 1.5em 1em;
			text-align:left;
			display:inline.block;
 			background-color: #1e1e1e; 
			color:#e8e7e5;
			margin-bottom: 0em;
		}
		.productoStyle h4{
			font-weight: bold;
			color:#e6eff2;
		}
		.productoStyle h5{
			color:#bcedff;
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
		
		.imagenNegocio{
			padding-left:10px;
			display:inline;
		}
		
		header{
			margin-bottom:0em;
		}
		
		.divMesa{
			width:240px;
			color:#fff;
			text-align: center;
			vertical-align: middle;
			margin-left: 1em;
			margin-right: 1em;
			display:inline-block;
		}
		
		.claveMesa{
			font-size:1em;
			font-weight: bold;
			margin:0em;
			color:#fff;
			display:inline-block;
		}
		
		.idMesa{
			font-size:0.6em;
			margin:0em;
			color:#fff;
		}
		.ui-dialog .ui-dialog-content{
			background: #f4f4f2;
			background: rgba(132,131,128,0.7);
			border-radius: 0.35em;
		}
		.ui-widget-content{
			background: #f4f4f2;
			background: rgba(132,131,128,0.7);
		}
		.ui-dialog-titlebar{
			background: #f4f4f2;
			background: rgba(132,131,128,0.7);
			border-radius: 0.35em;
		}
		.modalForm label,.modalForm input{
			font-size: 0.6em;
		}
		.sMesero{
			background:#f2f1ef;
			background: rgba(242,241,239,0.75);
			border-radius: 0.35em;
		}
		.sMesero h5{
			margin:0.2em;
			color:#494949;
		}
		.gridMeseros{
			overflow-y:auto; 
		}
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<s:url namespace="/negocio" action="guardarNegocio" var="urlGuardarNegocio"/>
	<script type="text/javascript">
		var currentTable=null;
		$(document).ready(function(){
			$('#soportaPedidos').val("${negocio.soportaPedidos}");
			$('#soportaReservaciones').val("${negocio.soportaReservaciones}");
			$('#soportaPlaylist').val("${negocio.soportaPlaylist}");
			$('#tipoComida').val("${negocio.tipoComida}");
		});
		
		function submitGuardarNegocio(){
			$('#forma').attr('action','${urlGuardarNegocio}');
			$('#forma').submit();
		}
	</script>
</head>
<body>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/negocio" method="POST" id="forma" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="negocio.id"/>
	<s:hidden name="negocio.activo" value="%{negocio.activo}"/>
	<s:hidden name="mesa.negocio.id" value="%{negocio.id}"/>
	
	<s:hidden name="idMesaEliminar" id="idMesaEliminar"/>
	<s:submit value="reload" id="btnReload" cssStyle="display:none;" action="miNegocio"/>
<%-- 	<s:submit value="Guardar" id="btnGuardar" cssStyle="display:;" action="guardarNegocio"/> --%>
	<s:submit value="GuardarMesa" id="btnAgregarMesa" cssStyle="display:none;" action="agregarMesa"/>
	<s:submit value="EliminarMesa" id="btnEliminarMesa" cssStyle="display:none;" action="eliminarMesa"/>
	
	<!-- NUEVA FORMA -->
	
		<fieldset>
		  <legend>Datos del negocio</legend>
		  <div class="imagenNegocio">
			<img src="${urlImagen}?negocio.id=${negocio.id}" width="100px" height="auto" title="" alt=""/>
		</div>
		  <div class="form-group">
		    <label for="negocio.nombre" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Nombre del negocio:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="40" name="negocio.nombre" value="${negocio.nombre}" placeholder="Nombre del negocio..."/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for=negocio.usuario class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Usuario:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="10" name="negocio.usuario" value="${negocio.usuario}" placeholder="Usuario de esta cuenta" readonly="true"/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="textArea" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Descripción:</label>
		    <div class="col-lg-9 col-lg-offset-3 col-md-9 col-md-offset-3 col-sm-12 col-xs-12">
		      <textarea class="form-control" type="text"  style="min-height:0em;" maxlength="200" cols="40" rows="3" name="negocio.descripcion" >${negocio.descripcion}</textarea>
		      <span class="help-block">En este campo usted puede indicar que tipo de comida ofrece, horarios, etc.(Máximo 200 caracteres)</span>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="soportaPlaylist" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Tipo de comida:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		    	<select name="negocio.tipoComida"  id="tipoComida" class="form-control">
						<option value="MEX">Mexicana</option>
						<option value="INT">Internacional</option>
						<option value="CHINA">China</option>
						<option value="BRA">Brasileña</option>
						<option value="ARG">Argentina</option>
						<option value="BUF">Buffet</option>
						<option value="VEG">Vegetariano</option>
						<option value="TODO">De todo</option>
						<option value="OTRO">Otros</option>
					</select>
		      </div>
		    </div>  
		  
		  <div class="form-group">
		    <label for=negocio.direccion class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Dirección:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" maxlength="120" name="negocio.direccion" value="${negocio.direccion}" class="form-control" placeholder="Ubicación del negocio"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for=negocio.direccion class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Liga del Mapa:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" maxlength="400" name="negocio.ligaUbicacion" value="${negocio.ligaUbicacion}" class="form-control" placeholder="Url de google maps"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for=negocio.telefono class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Telefono:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" maxlength="20" name="negocio.telefono" value="${negocio.telefono}" class="form-control" placeholder="(51)+LD+Numero"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="soportaPedidos" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Administrar pedidos:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		    	<select name="negocio.soportaPedidos"  id="soportaPedidos" class="form-control">
						<option value="0">No</option>
						<option value="1">Si</option>
					</select>
		      </div>
		    </div>
		  
		  <div class="form-group">
		    <label for="soportaPedidos" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Soporta reservaciones:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		    	<select name="negocio.soportaReservaciones"  id="soportaReservaciones" class="form-control">
						<option value="0">No</option>
						<option value="1">Si</option>
					</select>
		      </div>
		    </div>  
		    
		   <div class="form-group">
		    <label for="soportaPlaylist" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Soporta videoPlaylist:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		    	<select name="negocio.soportaPlaylist"  id="soportaPlaylist" class="form-control">
						<option value="0">No</option>
						<option value="1">Si</option>
					</select>
		      </div>
		    </div>  
		    
		   <div class="form-group">
		    <label for="estatus" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Estatus:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" id="estatus" maxlength="10"  value='${(negocio.activo==1)?"Activo":"Inactivo"}'class="form-control" placeholder="Estatus del negocio"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for=uploadLogo class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Subir logo:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="file" name="negocio.archivoImagen" class="form-control btn btn-default" placeholder="Selecciona un archivo..." />
		    </div>
		  </div>
		  
		    <div class="form-group">
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="submit" class="btn btn-primary" onclick="submitGuardarNegocio();">Guardar</button>
		      </div>
		    </div>
		  </fieldset>
	
	<s:hidden name="filename"/>
	<s:hidden name="contentType"/>
	<!-- END NUEVA FORMA -->
</s:form>
</body>
</html>