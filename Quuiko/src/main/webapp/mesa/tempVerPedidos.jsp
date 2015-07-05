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
<head>
	<base href="<%=basePath%>"/>
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Mesa de Control</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		form label{
			display:inline-block;
		}
		section{
			margin-bottom: 0em;
		}
		
		.imagenNegocio{
			padding-left:10px;
			display:inline;
		}
		
		header{
			margin-bottom:0em;
		}
		
		/*
		.divMesa{
			height:60px;
			width:100%;
			-webkit-appearance: none;
			border: 0;
			background: #fff;
			background: rgba(255,255,255,0.1);
			border-radius: 0.35em;
			padding: 0.75em 1em 0.75em 1em;
			box-shadow: inset 0 0.1em 0.1em 0 rgba(0,0,0,0.05);
			border: solid 1px rgba(0,0,0,0.15);
			-moz-transition: all 0.35s ease-in-out;
			-webkit-transition: all 0.35s ease-in-out;
			-o-transition: all 0.35s ease-in-out;
			-ms-transition: all 0.35s ease-in-out;
			transition: all 0.35s ease-in-out;
		}
		*/
		
		.divMesa{
			height:100px;
			width:23%;
			-webkit-appearance: none;
			border: 0;
			background: #eff2f2;
			background: rgba(239,242,242,0.4);
			border-radius: 0.35em;
			padding: 0.75em 1em 0.75em 1em;
			box-shadow: inset 0 0.1em 0.1em 0 rgba(0,0,0,0.05);
			border: solid 1px rgba(0,0,0,0.15);
			-moz-transition: all 0.35s ease-in-out;
			-webkit-transition: all 0.35s ease-in-out;
			-o-transition: all 0.35s ease-in-out;
			-ms-transition: all 0.35s ease-in-out;
			transition: all 0.35s ease-in-out;
			display:inline-block;
			margin-top:0.1em;
		}
		
		.inlineMesa{
			display:inline;
			margin:0em;
		}
		
		.divMesa h5{
			color:#fff;
			font-size:16pt;
			display:inline-block;
			text-align: center;
		}
		
		.panelCentral form{
			width:80%;
		}
		
		
		.divMesa h5.c1,.divMesaHeader h5.header1{
			width:10%;
		}
		
		.divMesa h5.c2,.divMesaHeader h5.header2{
			width:30%;
		}
		
		.divMesa h5.c3,.divMesaHeader h5.header3{
			width:10%;
		}
		
		.divMesa span.c4,.divMesaHeader h5.header4{
			width:15%;
			min-width: 50px;
			max-width: 60px;
			display: inline-block;
			text-align: right;
		}
		
		.divMesa span.c5,.divMesaHeader h5.header5{
			width:15%;
			min-width: 50px;
			max-width: 60px;
			display: inline-block;
			text-align: right;
		}
		
		.divMesa span.c6,.divMesaHeader h5.header6,.divMesa span.c7,.divMesa span.c8,.divMesaHeader h5.header7{
			width:10%;
			min-width: 60px;
			max-width: 70px;
			display: inline-block;
			height: 30px;
			vertical-align: top;
			line-height: 0.8em;
			background: #f2f4f4;
			background: rgba(242,244,244,0.7);
			border-radius: 0.35em;
			padding: 0.1em 0.1em 0.1em 0.1em;
			text-align: center;
			margin-left: 2px;
			margin-right: 2px;
		}
		
		.divMesa span.pedido{
			background: #089308;
			background: rgba(8,147,8,0.7);
		}
		
		.divMesaHeader{
			display:block;
			width:100%;
			height:60px;
		}
		.divMesaHeader h5{
			display:inline-block;
			vertical-align: top;
			margin:0em;
			color:#fff;
			font-size:0.7em;
			text-align: center;
		}
		
		.divMesaHeader h5.header0{
			display:inline-block;
			width:40px;
		}
		
		.activa{
			background: #722202;
			background: rgba(114,34,2,0.75);
		}
		.pedido{
			background: rgba(242,196,14,0.45);
		}
		.cuentaMesa{
			width:auto;
			height:auto;
			background-color: #561e1f;
			border-radius: 0.35em;
			padding: 0.2em 1em 0.2em 1em;
			box-shadow: inset 0 0.1em 0.1em 0 rgba(0,0,0,0.05);
			border: solid 1px rgba(0,0,0,0.15);
		}

		.resumenDiv h5{
			margin:0em;
			font-size:10pt;
		}
		.resumenDiv span.estatus{
			margin:0em;
			font-size:0.8em;
			display:inline-block;
			width:150px;
			padding-left:10px;
			color:#5df76f;
		}
		.resumenDiv h5.aliasCliente{
			display:inline-block;
			width:20%;
			text-align: left;
			padding-left:5px;
		}
		
		.resumenDiv h5.nombreProducto{
			display:inline-block;
			width:35%;
			text-align: left;
		}
		
		.resumenDiv span.precioProducto{
			display:inline-block;
			width:25%;
			text-align: right;
			font-size:10pt;
		}
		
		.resumenDiv div.totalCuentaMesa{
			font-size: 0.9em;
			text-align: left;
			display:inline-block;
			width:100%;
			background-color:#892e30;
			border-radius: 0.35em;
			padding: 0em;
			border: solid 1px rgba(0,0,0,0.15);
		}
		
		.resumenDiv div.totalCuentaMesa span.texto{
			width:55%;
			text-align: left;
			display:inline-block;
		}
		
		.resumenDiv div.totalCuentaMesa span.montoTotal{
			width:25%;
			text-align: left;
			display:inline-block;
		}
		
		.resumenDiv span.atender{
			display:inline-block;
			width:25%;
			text-align: right;
			font-size:10pt;
		}
		
		.nuevosPedidos{
			width:auto;
			height:auto;
			background-color: #2e6889;
			border-radius: 0.35em;
			padding: 0.2em 1em 0.2em 1em;
			box-shadow: inset 0 0.1em 0.1em 0 rgba(0,0,0,0.05);
			border: solid 1px rgba(0,0,0,0.15);
		}
		.nuevosPedidos h5{
			margin:0em;
			font-size:0.7em;
		}
		.nuevosPedidos span.estatus{
			margin:0em;
			font-size:0.8em;
			display:inline-block;
			width:150px;
			padding-left:10px;
			color:#5df76f;
		}
		.nuevosPedidos h5.aliasCliente{
			display:inline-block;
			width:20%;
			text-align: left;
		}
		
		.nuevosPedidos h5.nombreProducto{
			display:inline-block;
			width:35%;
			text-align: left;
		}
		
		.nuevosPedidos div.totalCuentaMesa{
			font-size: 0.9em;
			text-align: left;
			display:inline-block;
			width:100%;
			background-color:#428fbc;
			border-radius: 0.35em;
			padding: 0em;
			border: solid 1px rgba(0,0,0,0.15);
		}
		
		.nuevosPedidos div.totalCuentaMesa span.texto{
			width:55%;
			text-align: left;
			display:inline-block;
		}
		
		.nuevosPedidos div.totalCuentaMesa span.montoTotal{
			width:25%;
			text-align: left;
			display:inline-block;
		}
		
		div.divMesa span>a{
			font-size:0.6em;
		}
		
		div.resumenDiv{
			width:250px;
			height:100%;
			display:inline-block;
			position: fixed;
			top:0;
			right: 0;
			background: #f2f4f4;
			background: rgba(242,244,244,0.25);
			border-radius: 0.35em;
		}
		div.resumenDiv div.tituloResumen{
			display:block;
			width:100%;
			font-size: 12pt;
			padding-left:5px;
		}
		
		.comboMeseros{
			margin:0.2em;
			font-size: 0.6em;
		}
		.atiendeMesero{
			display:inline-block;
			width:99%;
			font-size: 0.5em;
			margin:0em;
			height:30px;
			vertical-align: top;
			line-height: 1em;
		}
	</style>
	<style>
		.mesaOcupada{
			color:#d9534f;
			font-size:1.4em;
		}
		.mesaLibre{
			color:#5cb85c;
			font-size:1.4em;
		}
		.mesaControl h4{
			font-size:1em;
			font-weight: bold;
		}
		.textoCuenta{
			font-size:0.9em;
		}
		
		.zero-orders{
			color:#5b5b59;
		}
		
		.non-zero-orders{
			color:#f00;
		}
	</style>
	<s:url namespace="/mesaControl" action="gtMC" var="urlGtMC"/>
	<s:url namespace="/mesaControl" action="atnWitress" var="urlAtenderMesero"/>
	<s:url namespace="/mesaControl" action="bringTheAccount" var="urlAtenderPedirCuenta"/>
	<s:url namespace="/mesaControl" action="closeAccnt" var="urlCloseAccount"/>
	<s:url namespace="/negocio" action="showAllWaitress" var="urlGtWait"/>
	<s:url namespace="/m/pedidos" action="atnNew" var="urlAtnNew"/>
	<s:url namespace="/m/pedidos" action="gtNew" var="urlGtNew"/>
	<s:url namespace="/m/pedidos" action="gtAcc" var="urlAccount"/>
	<s:url namespace="/analytics" action="gtHappyBirths" var="urlCumpleanios"/>
	<script type="text/javascript">
		var SEGUNDOS=15;
		var ALTURA_X_MESA=60;
		var ALTURA_X_PEDIDO=55;
		var mesasPorAtender=[];
		
		$(document).ready(function(){
			var stream = new EventSource('temp/agregarPedido.php?idNegocio=1');
			stream.onmessage=function(event){
				var data = event.data;
			    alert('Data:'+data);
			    $('#resultado').val(data);
			};
		});
		
	</script>
</head>
<body>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/negocio" method="POST" id="forma" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="negocio.id"/>	
	<input type="text" id="resultado" />
</s:form>
</body>
</html>