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
<s:url namespace="/productos" action="producto" var="urlNuevoProducto"/>
<s:url namespace="/productos" action="eliminarProducto" var="urlEliminarProducto"/>


<head>
	<base href="<%=basePath%>"/>
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Productos</title>
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
		var playList=null;
		$(document).ready(function(){
			
		});
		
		function eliminarAmigo(idAmigo){
			$('#amigoEliminar').val(idAmigo);
			$('#btnEliminarAmigo').click();
		}
		
		function renderearProductos(){
			var url="";
			//ajax
			var listaProductos=new Array();
			if(listaProductos!=null && listaProductos.length>0){
				$('.productoStyle').remove();//Se eliminan todos los productos con el class="productoStyle"
				for(var i=0;i<listaProductos.length;i++){
					var producto=listaProductos[i];
					var html="<section class='productoStyle'>";
					html+="<div style='display:inline-block;vertical-align:bottom;width:10%;max-width:80px;padding-right:10px;'>";
					html+="<img src='${urlImagen}?idProducto="+producto.id+"' width='80px' height='80px' title='Agregar' alt='Agregar'/>";
					$('#divListaProductos').append();
				}
			}
		}
		
		function editarProducto(id){
			$('#idProductoHidden').val(id);
			$('#forma').attr('action',"${urlNuevoProducto}");
			$('#forma').submit();
		}
		
		function actionNuevoProducto(){
			$('#idProductoHidden').val('');
			$('#forma').attr('action',"${urlNuevoProducto}");
			$('#forma').submit();
		}
		
		function actionEliminarProducto(id){
			$('#idProductoEliminarHidden').val(id);
			$('#forma').attr('action',"${urlEliminarProducto}");
			$('#forma').submit();
		}
	</script>
</head>
<body>
<s:form namespace="/productos" method="POST" id="forma">
	<!-- Botones hidden para hacer submit -->
	<s:submit action="toQueue" id="btnToQueue" cssStyle="display:none;"/>
	<s:submit action="producto" id="btnNuevoProducto" cssStyle="display:none;"/>
	<s:hidden name="producto.id" id="idProductoHidden"/>
	<s:hidden name="idProducto" id="idProductoEliminarHidden"/>
	<!-- End de botones -->
	
	<fieldset>
		  <legend>Catalogo de productos</legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-xs-4 control-label">Administre su menú agregando productos!</label>
		    <div class="col-xs-2">
		    	<button type="submit" class="btn btn-primary" onclick="actionNuevoProducto();">Nuevo Producto</button>
		    </div>
		  </div>
		  <br/><br/>
		  <!-- Lista de productos -->
		  <div class="form-group">
		  	<ul class="list-group">
<%-- 		  	<s:iterator value="productos" var="p"> --%>
			<s:iterator value="menuProductos" var="m">
			
			  <li class="list-group-item active">${m.categoria}</li>
			  <s:iterator value="#m.productos" var="p">
			  <li class="list-group-item itemProducto">
				    <div class="imagenProducto">
				    	<img src="${urlImagen}?idProducto=${p.id}" width="50px" height="auto" title="Agregar" alt="Agregar"/>
				    </div>
				    <div class="infoProducto">
				    	<div style="display:inline-block;float:right;">
				    		<span class="glyphicon glyphicon-cog iconoProducto" onclick="editarProducto(${p.id});" title="Editar"></span>
				    		<span class="glyphicon glyphicon-remove-circle iconoProducto" onclick="actionEliminarProducto(${p.id});" title="Eliminar"></span>
				    	</div>
					    <h2>${p.nombre}-$ ${p.costo}</h2>
					    <p>
					    	${p.descripcion}
					    </p>
				    </div>
			  </li>
			  </s:iterator>
			</s:iterator>
			</ul>
		  </div>
	</fieldset>
</s:form>
</body>
</html>