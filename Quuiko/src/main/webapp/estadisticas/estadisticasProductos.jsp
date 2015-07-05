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
<s:url namespace="/analytics" action="showProductsAnalysisJson" var="urlShowProductsAnalysisJson"/>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" media="all" />
	
	<!-- MetisMenu CSS -->
	<link type="text/css" href="css/plugins/morris.css" rel="stylesheet" media="all" />
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Estadisticas</title>
	
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
	<s:url namespace="/analytics" action="gtTopProducts" var="urlTopProducts"/>
	<script type="text/javascript">
		var currentTable=null;
		var grafica=null;
		var llavesGrafica=[];
		var arregloMeses=[];
		var etiquetasProducto=[];
		$(document).ready(function(){
			//cargarGrafica();
		});
		
		function parsearArregloAGrafica(productos){
			if(productos!=null && productos.length>0){
				for(var i=0;i<productos.length;i++){
					var producto=productos[i];
					var claveProducto=producto.clave;
					var mes=producto.mesStr;
					var indiceMes=existeEnArregloMeses(mes);
					var elementoMes=null;
					if(indiceMes==-1){
						arregloMeses.push({'mes':mes});
						elementoMes=arregloMeses[(arregloMeses.length-1)];
						elementoMes['mes']=mes;
						indiceMes=(arregloMeses.length-1);
					}else{
						elementoMes=arregloMeses[indiceMes];
					}
					var etiquetaProducto=producto.producto;
					var cantidad=producto.numVecesPedida;
					var llaveGraficaNumVeces='numVeces_'+claveProducto;
					var existe=existeLlaveEnNumVeces(llaveGraficaNumVeces);
					if(!existe){
						llavesGrafica.push(llaveGraficaNumVeces);	
					}
					var existeEtiquetaProducto=existeProductoEnArreglo(etiquetaProducto);
					if(!existeEtiquetaProducto){
						etiquetasProducto.push(etiquetaProducto);
					}
					arregloMeses[indiceMes][llaveGraficaNumVeces]=cantidad;
				}
				if(arregloMeses!=null && arregloMeses.length>0){
					for(var i=0;i<arregloMeses.length;i++){
						var e=arregloMeses[i];
						for(var x=0;x<llavesGrafica.length;x++){
							var llave=llavesGrafica[x];
							if(e[llave]==null || e[llave]=='undefined'){
								e[llave]=0;
							}
						}
					}
					
				}
			}
			return arregloMeses;
		}
		
		function existeProductoEnArreglo(etiquetaProducto){
			if(etiquetasProducto.length>0){
				for(var i=0;i<etiquetasProducto.length;i++){
					var llave=etiquetasProducto[i];
					if(llave==etiquetaProducto){
						return true;
					}
				}
			}else{
				return false;
			}
		}
		
		function existeEnArregloMeses(nuevaLlave){
			var posicion=-1;
			if(arregloMeses.length>0){
				for(var i=0;i<arregloMeses.length;i++){
					var llave=arregloMeses[i];
					if(llave.mes==nuevaLlave){
						posicion=i;
						return posicion;
					}
				}
			}else{
				return posicion;
			}
			return posicion;
		}
		
		function existeLlaveEnNumVeces(nuevaLlave){
			if(llavesGrafica.length>0){
				for(var i=0;i<llavesGrafica.length;i++){
					var llave=llavesGrafica[i];
					if(llave==nuevaLlave){
						return true;
					}
				}
			}else{
				return false;
			}
		}
		
		function cargarGrafica(){
			/*
			var graficaAreas=new Morris.Area({
				element:'graficaProductos',
				data:[{
					'mes':'Octubre','numVecesPedidaPiza':1,'numVecesPedidaCoca':2,
					'mes':'Noviembre','numVecesPedidaPiza':1,'numVecesPedidaCoca':3
				}]
			});
			*/
			/*
			var grafica=new Morris.Donut({
				  resize:true,
				  // ID of the element in which to draw the chart.
				  element: 'graficaProductos',
				  // Chart data records -- each entry in this array corresponds to a point on
				  // the chart.
				  data: [
				    { label: 'Usuarios Quicko', value: '${numUsuariosQuicko}' },
				    { label: 'Mis usuarios', value: '${numClientesPorNegocio}' }
				  ]
				});
			*/
			var dataChart=[];
			$.ajax({
				dataType:'json',
				url:"${urlShowProductsAnalysisJson}",
				data:{
					'mesInicio':$('#mesInicio').val(),
					'mesFin':$('#mesFin').val()
				},
				async:false,
				success:function(data){
					if(data!=null && data.length>0){
						var productos=parsearArregloAGrafica(data);
						renderearTablaProductos(data);
						/*
						var dataChart=[];
						for(var i=0;i<data.length;i++){
							var p=data[i];
							var cantidad=p.numVecesPedida;
							var producto=p.producto;
							dataChart[i]={'label':producto,'value':cantidad};
						}
						*/
						if(grafica==null){
							grafica=new Morris.Area({
								  resize:true,
								  // ID of the element in which to draw the chart.
								  element: 'graficaProductos',
								  // Chart data records -- each entry in this array corresponds to a point on
								  // the chart.
								  data: productos,
								  xkey:'mes',
								  ykeys:llavesGrafica,
								  labels:etiquetasProducto,
								  xLabels:'month'
								});
						}else{
							grafica.setData(productos);
						}
					}
				}
			});
			
		}
		
		function submitConsultar(){
			$.ajax({
				dataType:"json",
				url:"${urlShowProductsAnalysisJson}",
				async:false,
				data:{
					'mesInicio':$('#mesInicio').val(),
					'mesFin':$('#mesFin').val()
				},
				success:function(productos){
					if(productos!=null && productos.length>0){
						//alert('Datos:'+productos.length);
						cargarGrafica();
					}
				}
			});
		}
		
		function renderearTablaProductos(productos){
			var html='<table class="table table-striped">';
			html+="		<thead><td>Mes</td><td>Producto</td><td>Cantidad</td></thead>";
			html+="		<tbody>";
			for(var i=0;i<productos.length;i++){
				var p=productos[i];
				var producto=p.producto;
				html+="<tr>";
				html+="	<td>"+p.mesStr+"</td>";
				html+="	<td>"+producto+"</td>";
				html+="	<td>"+p.numVecesPedida+"</td>";
				html+="</tr>";
			}
			html+="		</tbody>";
			html+="</table>";
			$('#tablaProductos').text('');
			$('#tablaProductos').append(html);
		}
	</script>
</head>
<body>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/analysis" method="POST" id="forma" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	
	<!-- NUEVA FORMA -->
	
		<fieldset>
		  <legend>Mis productos</legend>
		  <div class="form-group">
			<div class="row">
			  	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
			  		<h5>Desde:</h5>
			  	</div>
			    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
			     	<select name="mesInicio"  id="mesInicio" class="form-control">
			     		<option value="1">Enero</option>
			     		<option value="2">Febrero</option>
			     		<option value="3">Marzo</option>
			     		<option value="4">Abril</option>
			     		<option value="5">Mayo</option>
			     		<option value="6">Junio</option>
			     		<option value="7">Julio</option>
			     		<option value="8">Agosto</option>
			     		<option value="9">Septiembre</option>
			     		<option value="10">Octubre</option>
			     		<option value="11">Noviembre</option>
			     		<option value="12">Diciembre</option>
			     	</select> 
			    </div>
				  	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				  		<h5>Hasta:</h5>
				  	</div>
				    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				     	<select name="mesInicio"  id="mesFin" class="form-control">
				     		<option value="1">Enero</option>
				     		<option value="2">Febrero</option>
				     		<option value="3">Marzo</option>
				     		<option value="4">Abril</option>
				     		<option value="5">Mayo</option>
				     		<option value="6">Junio</option>
				     		<option value="7">Julio</option>
				     		<option value="8">Agosto</option>
				     		<option value="9">Septiembre</option>
				     		<option value="10">Octubre</option>
				     		<option value="11">Noviembre</option>
				     		<option value="12">Diciembre</option>
				     	</select> 
				    </div>
		    </div>
		    </div>
		    <div class="form-group">
		      <div class="col-lg-1 col-lg-offset-11">
		        <button type="button" class="btn btn-primary" onclick="submitConsultar();">Buscar</button>
		      </div>
		    </div>
		    <div class="form-group">
		     <div class="row">
			  	<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
			  		<div class="panel panel-default">
		            	<div class="panel-heading">
		                	Relacion Productos vs Tiempo
		            	</div>
		            	<!-- /.panel-heading -->
		            	<div class="panel-body">
		                	<div id="graficaProductos"></div>
		            	</div>
				  	</div>
			  	</div>
			 </div>
		  	</div>
		  	<div class="form-group">
		  	<div class="row">
		  		<div class="col-lg-10 col-md-10 col-sm-12 col-xs-12" id="tablaProductos">
		  		
		  		</div>
<!-- 		  		<table class="table table-striped"> -->
<!-- 					<thead> -->
<!-- 							<td>Producto</td><td>Cantidad</td> -->
<!-- 					</thead> -->
<!-- 					<tbody> -->
<!-- 						<tr> -->
<!-- 							<td>Coca</td><td>1</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td>Coca</td><td>1</td> -->
<!-- 						</tr> -->
<!-- 					</tbody> -->
<!-- 				</table> -->
		  	</div>
		  	</div>
		</fieldset>
		<br/>
		  
	<!-- END NUEVA FORMA -->
</s:form>
    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/raphael-min.js"></script>
    <script src="js/plugins/morris.min.js"></script>
</body>
</html>