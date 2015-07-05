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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" media="all" />
	
	<!-- MetisMenu CSS -->
	<link type="text/css" href="css/plugins/morris.css" rel="stylesheet" media="all" />
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
	<s:url namespace="/analytics" action="gtTopProducts" var="urlTopProducts"/>
	<script type="text/javascript">
		var currentTable=null;
		var graficaBarra=null;
		$(document).ready(function(){
			$('#soportaPedidos').val("${negocio.soportaPedidos}");
			cargarGrafica();
		});
		
		function cargarGrafica(){
			var grafica=new Morris.Donut({
				  resize:true,
				  // ID of the element in which to draw the chart.
				  element: 'graficaRelacionUsuarios',
				  // Chart data records -- each entry in this array corresponds to a point on
				  // the chart.
				  data: [
				    { label: 'Usuarios Quicko', value: '${numUsuariosQuicko}' },
				    { label: 'Mis usuarios', value: '${numClientesPorNegocio}' }
				  ]
				});
			var dataChart=[];
			$.ajax({
				dataType:'json',
				url:"${urlTopProducts}",
				async:false,
				success:function(data){
					if(data!=null && data.length>0){
						var dataChart=[];
						for(var i=0;i<data.length;i++){
							var p=data[i];
							var cantidad=p.numVecesPedida;
							var producto=p.producto;
							dataChart[i]={'label':producto,'value':cantidad};
						}
						if(graficaBarra==null){
							graficaBarra=new Morris.Donut({
								  resize:true,
								  // ID of the element in which to draw the chart.
								  element: 'graficaProductos',
								  // Chart data records -- each entry in this array corresponds to a point on
								  // the chart.
								  data: dataChart
								});
						}else{
							graficaBarra.setData(dataChart);
						}
					}
				}
			});
			
			graficaBarra=new Morris.Donut({
				  resize:true,
				  // ID of the element in which to draw the chart.
				  element: 'graficaProductos',
				  // Chart data records -- each entry in this array corresponds to a point on
				  // the chart.
				  data: dataChart
				});
		}
		
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
		  <legend>Mis Clientes</legend>
		  <div class="form-group">
			<div class="row">
			  	<div class="col-xs-9">
			  		<h5>Numero de clientes que usan Quuiko:</h5>
			  	</div>
			    <div class="col-xs-3">
			     	<h5>${numUsuariosQuicko}</h5> 
			    </div>
		    </div>
		    <div class="row">
			  	<div class="col-xs-9">
			  		<h5>Numero de clientes que me siguen:</h5>
			  	</div>
			    <div class="col-xs-3">
			     	<h5>${numClientesPorNegocio}</h5> 
			    </div>
		    </div>
		    <div class="row">
			  	<div class="col-xs-9">
			  		<h5>Numero de clientes que NO me siguen:</h5>
			  	</div>
			    <div class="col-xs-3">
			     	<h5>${numUsuariosQuicko - numClientesPorNegocio}</h5> 
			    </div>
		    </div>
		     <div class="row">
			  	<div class="col-lg-6 col-md-6 col-sm-11 col-xs-11">
			  		<div class="panel panel-default">
		            	<div class="panel-heading">
		                	Relación de usuarios Quicko vs mis clientes
		            	</div>
		            	<!-- /.panel-heading -->
		            	<div class="panel-body">
		                	<div id="graficaRelacionUsuarios"></div>
		            	</div>
				  	</div>
			  	</div>
			 </div>
		  </div>
		  
		  
		  <div class="form-group">
		    		<h5>Top 10 de clientes:</h5>
		   </div>
		   
		   <div class="form-group">
			<ul class="list-group">
					<s:iterator value="clientes" var="c" status="i">
					
					  <li class="list-group-item itemProducto">
							    <div class="row">
							    	<div class="col-sm-6 col-xs-6 col-md-6">
							    		<h5 style="font-size:0.8em;"><b>${i.index +1 }.-${c.cliente}</b></h5>
							    	</div>
							    	<div class="col-sm-2 col-xs-2 col-md-2">
							    		<h5 style="font-size:0.8em;">Visitas: ${c.numVisitas}</h5>
							    	</div>
							    	<div class="col-sm-4 col-xs-4 col-md-4">
							    		<s:iterator begin="1" end="%{#c.numEstrellas}">
							    			<span class="glyphicon glyphicon-star" style="font-size:0.8em;"></span>
							    		</s:iterator>
							    		<s:if test="%{(5 -#c.numEstrellas)>0 }">
							    			<s:iterator begin="1" end="%{5 - #c.numEstrellas}">
							    				<span class="glyphicon glyphicon-star-empty" style="font-size:0.8em;"></span>
							    			</s:iterator>
							    		</s:if>
							    	</div>
							    </div>
					  </li>
					</s:iterator>
					</ul>
			  </div>
		  
		  </fieldset>
		  
		  <fieldset>
		  	<legend>Productos más vendidos</legend>
		  	
		  	<div class="form-group">
		  	<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<ul class="list-group">
						<s:iterator value="productos" var="p" status="i">
							  <li class="list-group-item itemProducto">
									    <div class="row">
									    	<div class="col-sm-8">
									    		<h5 style="font-size:0.8em;"><b>${i.index +1 }.- ${p.producto}</b>  (${p.numVecesPedida})</h5>
									    	</div>
									    	<div class="col-sm-4">
									    		<s:iterator begin="1" end="%{#p.numEstrellas}">
									    			<span class="glyphicon glyphicon-star"></span>
									    		</s:iterator>
									    		<s:if test="%{(5 -#p.numEstrellas)>0 }">
									    			<s:iterator begin="1" end="%{5 - #p.numEstrellas}">
									    				<span class="glyphicon glyphicon-star-empty"></span>
									    			</s:iterator>
									    		</s:if>
									    	</div>
									    </div>
							  </li>
						</s:iterator>
					</ul>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-11 col-xs-11">
					<div class="panel panel-default">
		             	<div class="panel-heading">
		                 	Gráfica de productos más vendidos
		             	</div>
		             	<!-- /.panel-heading -->
		             	<div class="panel-body">
		                 	<div id="graficaProductos"></div>
		             	</div>
					</div>
				</div>
			  </div>
			 </div>
		  </fieldset>
		  
		  
	
	<s:hidden name="filename"/>
	<s:hidden name="contentType"/>
	<!-- END NUEVA FORMA -->
</s:form>
    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/raphael-min.js"></script>
    <script src="js/plugins/morris.min.js"></script>
</body>
</html>