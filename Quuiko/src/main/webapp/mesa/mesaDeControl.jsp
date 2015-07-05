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
			font-size:0.7em;
			padding-left: 10px;
			vertical-align: middle;
		}
		
		.non-zero-orders{
			color:#f00;
			font-size:0.7em;
			padding-left: 10px;
			vertical-align: middle;
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
			$('#opcionesPanel').text('');
			inicializarComboMeseros();
			setTimeout("actualizarRecurrentemente();",SEGUNDOS*1000);
		});
		
		function inicializarComboMeseros(){
			$.ajax({
				dataType:"json",
				url:"${urlGtWait}",
				async:false,
				success:function(data){
					var meseros=data.meseros;
					if(meseros!=null && meseros.length>0){
						$('#comboMeseros').find('option').remove();
						$('#comboMeseros').append("<option value=''>Todos</option>");
						for(var i=0;i<meseros.length;i++){
							var mesero=meseros[i];
							$('#comboMeseros').append("<option value='"+mesero.id+"'>"+mesero.nombre+"</option>");
						}
					}
				}
			});
			actualizarMarcadorPedidosPendientes();
		}
		
		function actualizarMarcadorPedidosPendientes(){
			var numPendientes=0;
			var numLlamadasMesero=0;
			var numPedidasCuenta=0;
			for(var i=0;i<mesasPorAtender.length;i++){
				if(!mesasPorAtender[i].atendido){
					numPendientes++;
				}
				if(mesasPorAtender[i].llamadaMesero){
					numLlamadasMesero++;
				}
				if(mesasPorAtender[i].solicitarCuenta){
					numPedidasCuenta++;
				}
			}
			$('#numPedidos').val(numPendientes);
			$('#numPedidos').text("");
			$('#numPedidos').append(numPendientes);
			if(numPendientes==0){
				$('#numPedidos').addClass("zero-orders");
				$('#numPedidos').removeClass("non-zero-orders");	
			}else{
				$('#numPedidos').addClass("non-zero-orders");
				$('#numPedidos').removeClass("zero-orders");
			}
			$('#numLlamadasMesero').val(numLlamadasMesero);
			$('#numLlamadasMesero').text("");
			$('#numLlamadasMesero').append(numLlamadasMesero);
			if(numLlamadasMesero==0){
				$('#numLlamadasMesero').addClass("zero-orders");
				$('#numLlamadasMesero').removeClass("non-zero-orders");	
			}else{
				$('#numLlamadasMesero').addClass("non-zero-orders");
				$('#numLlamadasMesero').removeClass("zero-orders");
			}
			$('#numPedidasCuenta').val(numPedidasCuenta);
			$('#numPedidasCuenta').text("");
			$('#numPedidasCuenta').append(numPedidasCuenta);
			if(numPedidasCuenta==0){
				$('#numPedidasCuenta').addClass("zero-orders");
				$('#numPedidasCuenta').removeClass("non-zero-orders");	
			}else{
				$('#numPedidasCuenta').addClass("non-zero-orders");
				$('#numPedidasCuenta').removeClass("zero-orders");
			}
		}
		function actualizarRecurrentemente(){
			actualizarMesas();
			actualizarCumplanieros();
			setTimeout("actualizarRecurrentemente();",SEGUNDOS*1000);
		}
		
		function actualizarCumplanieros(){
			$.ajax({
				dataType:"json",
				url:"${urlCumpleanios}",
				data:{},
				async:false,
				success:function(cumpleanios){
					if(cumpleanios!=null && cumpleanios.length>0){
						$('#totalPanelCumple').text("("+cumpleanios.length+" personas)");
						var html="";
						for(var i=0;i<cumpleanios.length;i++){
							var cumple=cumpleanios[i];
							html+=obtenerHTMLCumpleanios(cumple);
						}
						$('#listaCumplanierosDiv').text("");
						$('#listaCumplanierosDiv').append(html);
					}else{
						$('#totalPanelCumple').text("(O personas)");
						$('#listaCumplanierosDiv').text("No hay cumpleañeros el día de hoy");
					}
				}
			});
		}
		
		function obtenerHTMLCumpleanios(cumple){
			var html="";
			var alias=cumple.alias;
			var cliente=cumple.nombreCliente;
			var numAnios=cumple.numAnios;
			var mesa=cumple.claveMesa;
			html+='<div class="row active">';
			html+='		<div class="col-sm-12">';
			html+='			<h4>Mesa:'+mesa+'</h4>';
			html+='		</div>';
			html+='</div>';
			html+='<div class="row">';
			html+='		<div class="col-sm-8">';
			html+='			<h5>'+cliente+' (<b>'+alias+'</b>)</h5>';
			html+='		</div>';
			html+='		<div class="col-sm-4">';
			html+='			<h5>'+numAnios+' años </h5>';
			html+='		</div>';
			html+='</div>';
			return html;
		}
		
		var numPedidosNuevosSolicitados=0;
		
		/**
		* Funcion que actualiza cada X tiempo la mesa y sus pedidos
		*/
		function actualizarMesas(){
			$.ajax({
				dataType:"json",
				url:"${urlGtMC}",
				data:{"idMesero":$('#comboMeseros').val()},
				async:false,
				success:function(mesas){
					if(mesas!=null && mesas.length>0){
						var html="";
						for(var i=0;i<mesas.length;i++){
							var mesa=mesas[i];
							html+=obtenerHTMLMesaNueva(mesa);
						}
						actualizarMarcadorPedidosPendientes();
						$('#contenedorMesas').text("");
						$('#contenedorMesas').append(html);
						habilitarEfectos();
					}else{
						$('#contenedorMesas').text("");
					}
				}
			});
		}
		//CHECAR COMO ESTA FUNCIONANDO
		function obtenerHTMLMesaNueva(mesa){
			var html="";
			//Indica si alguien esta usando la mesa, o esta vacia
			var id=(mesa!=null)?mesa.idMesa:null;
			var mesaActiva=(mesa!=null  && mesa.numPersonas>0)?"activa":"";
			//Indica si alguien mando a llamar al mesero
			var llamadaMesero=(mesa!=null && mesa.llamarMesero==1)?"llamada":"";
			var solicitarLlamadaMesero=(mesa!=null && mesa.llamarMesero==1);
			var solicitarCuenta=(mesa!=null && mesa.solicitarCuenta!=null && mesa.solicitarCuenta==1);
			var solicitaronNuevoPedido=(mesa.solicitarNuevoPedido!=null && mesa.solicitarNuevoPedido==1);
			var pedidoAtendido=!solicitaronNuevoPedido;//si solicitaron nuevo pedido, entonces no se ha atendido
			var yaExisteMesa=false;
			var indexMesa=-1;
			for(var i=0;i<mesasPorAtender.length;i++){
				var m=mesasPorAtender[i];
				if(m!=null && m.mesa==mesa.claveMesa){
					yaExisteMesa=true;
					indexMesa=i;
					break;
				}
			}
			if(!yaExisteMesa){
				mesasPorAtender.push({'mesa':mesa.claveMesa,'atendido':pedidoAtendido,'llamadaMesero':solicitarLlamadaMesero,'solicitarCuenta':solicitarCuenta});
			}else{
				mesasPorAtender[indexMesa].atendido=pedidoAtendido;
				mesasPorAtender[indexMesa].llamadaMesero=solicitarLlamadaMesero;
				mesasPorAtender[indexMesa].solicitarCuenta=solicitarCuenta;
			}
			var cssNuevoPedido=(solicitaronNuevoPedido)?"pedido":"";
			var folioPedido=(mesa.folioPedido!=null)?mesa.folioPedido:"";
			var cssEstatusMesa=(mesa.numPersonas>0)?'glyphicon-ban-circle mesaOcupada':'glyphicon-ok-sign mesaLibre';
			var estatusMesa=(mesa.numPersonas>0)?'Ocupada':'Libre';
			var cssSolicitaNuevoPedido=(mesa.solicitarNuevoPedido==1)?'btn-info':'btn-primary disabled';
			var cssLlamarMesero=(mesa.llamarMesero==1)?'btn-warning':'btn-primary disabled';
			var cssSolicitarCuenta=(mesa.solicitarCuenta==1)?'btn-warning':'btn-primary disabled';
			var folioMesa=(mesa.folioPedido!=null)?mesa.folioPedido:'';
			var mesero=(mesa.nomMesero!=null)?mesa.nomMesero:"";
			html+='<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"> ';
			html+='		<div class="panel panel-primary"> ';
			html+='			<div class="panel-heading"> ';
			html+='    			<h3 class="panel-title" style="display:inline-block;">'+mesa.claveMesa+'</h3> ';
			html+='    			<span class="glyphicon '+cssEstatusMesa+'" style="text-align:right;float:right;"></span> ';
			html+='  		</div> ';
			html+='  		<div class="panel-body mesaControl"> ';
			html+='  			<div> ';
			html+='  				<h4 style="display:inline-block;">Num.Pedido:</h4><span>'+folioMesa+'</span> ';
			html+='  			</div> ';
			html+='  			<div> ';
			html+='  				<h4 style="display:inline-block;">Status:</h4><span>'+estatusMesa+'</span> ';
			html+='  			</div> ';
			html+='  			<div> ';
			html+='  				<h4 style="display:inline-block;">Atiende:</h4><span>'+mesero+'</span> ';
			html+='  			</div> ';
			html+='  			<div class="btn-group btn-group-justified"> ';
			html+='  				<div class="btn '+cssSolicitaNuevoPedido+'" onclick="verNuevosPedidos('+mesa.folioPedido+',\''+mesa.claveMesa+'\');"> ';
			html+='						<span class="glyphicon glyphicon-cutlery"></span> ';
			html+='	  				</div> ';
			html+='  				<div class="btn '+cssLlamarMesero+'" onclick="atenderLlamadoMesero('+mesa.folioPedido+');"> ';
			html+='						<span class="glyphicon glyphicon-user"></span> ';
			html+='	  				</div> ';
			html+='	  				<div class="btn '+cssSolicitarCuenta+'" onclick="atenderPedirCuenta('+mesa.folioPedido+');"> ';
			html+='						<span class="glyphicon glyphicon-list-alt"></span> ';
			html+='	  				</div> ';
			html+='  			</div> ';
			html+='  			<div class="btn-group btn-group-justified"> ';
			html+='	  				<div class="btn btn-primary" onclick="$(\'html,body\').scrollTop(0);mostrarPedido('+mesa.folioPedido+',\''+mesa.claveMesa+'\');"> ';
			html+='    					<span class="glyphicon glyphicon-th-list"></span>Cuenta ';
			html+='    				</div> ';
			html+='    				<div class="btn btn-primary" onclick="cobrarCuenta('+mesa.folioPedido+');"> ';
			html+='  					<span class="glyphicon glyphicon-usd"></span>Cobrar ';
			html+='  				</div> ';
			html+='				</div> ';
			html+='				<div id="cuentaMesa_'+mesa.idMesa+'" class="cuentaMesa" style="display:none;"></div> ';
			html+='				<div id="nuevosPedidos_'+mesa.idMesa+'" class="nuevosPedidos" style="display:none;"></div> ';
			html+='			</div> ';
			html+='		</div> ';
			html+='</div> ';
			return html;
		}
		
		function habilitarEfectos(){
			
		}
		
		function atenderLlamadoMesero(idPedidoMesa){
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlAtenderMesero}",
				success:function(pedido){
					if(pedido!=null && pedido.llamarMesero==0){
						//YA FUE ATENTIDO, quitar la imagen de verde y ponerla gris
						$("#mesero_"+pedido.id).attr("src","images/qrocks_grayWitress.png");
					}
				}
			});
		}
		
		function atenderPedirCuenta(idPedidoMesa){
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlAtenderPedirCuenta}",
				success:function(pedido){
					if(pedido!=null && pedido.llamarMesero==0){
						//YA FUE ATENTIDO, quitar la imagen de verde y ponerla gris
						$("#mesero_"+pedido.id).attr("src","images/qrocks_grayWitress.png");
					}
				}
			});
		}
		
		function mostrarPedido(idPedidoMesa,claveMesa){
			var display=$('#opcionesPanel').css("display");
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlAccount}",
				success:function(datos){
					var pedidos=(datos!=null)?datos.cuenta:null;
					if(pedidos!=null && pedidos.length>0){
						var html=obtenerCuentaHTML(claveMesa,pedidos);
						var total=obtenerTotalCuenta(pedidos);
						$('#opcionesPanel').text("");
						$('#opcionesPanel').append(html);
						$('#totalPanelCuenta').text("Total: $"+total);
					}else{
						$('#opcionesPanel').text("");
						var html="<div class='tituloResumen'>Mesa:"+claveMesa+" <br/>La cuenta no esta abierta.</div>";
						$('#opcionesPanel').append(html);
					}
				}
			});
		}
		
		function obtenerTotalCuenta(pedidos){
			var total=0;
			for(var i=0;i<pedidos.length;i++){
				var pedido=pedidos[i];
				var costo=parseFloat(pedido.producto.costo);
				//Si ya esta pagado, esto se le resta a la cuenta
				if(pedido.pagado==1){
					costo*=-1;//Ya no se cobra
				}
				total+=costo;
			}
			return total;
		}
		
		
		function indiceEnArregloClientes(arreglo,cliente){
			var posicion=-1;
			if(arreglo==null){
				arreglo=[];
			}
			for(var i=0;i<arreglo.length;i++){
				var c=arreglo[i];
				if(c.alias==cliente){
					posicion=i;
					break;
				}
			}
			return posicion;
		}
		
		
		/**
		* Funcion que obtiene el HTML de un pedido para la cuenta de una mesa
		*/
		function obtenerCuentaHTML(claveMesa,pedidos){
			var html='<div class="panel panel-default">';
			html+='<div class="panel-heading" id="tituloPanelCuenta"><div class="row"><div class="col-sm-8">Cuenta:'+claveMesa+'</div><div class="col-sm-4" id="totalPanelCuenta"></div></div></div>';
			html+='	<div class="panel-body"> ';
			if(pedidos!=null && pedidos.length>0){
				var clientes=new Array();
				for(var x=0;x<pedidos.length;x++){
					var pedido=pedidos[x];
					var cliente=pedido.cliente;
					var aliasCliente=cliente.alias;
					var indice=indiceEnArregloClientes(clientes,aliasCliente);
					//Si no se encontro el cliente, entonces se agrega el cliente a la lista de clientes
					if(indice==-1){
						indice=clientes.length;
						clientes[indice]=cliente;
						clientes[indice].pedidos=new Array();
						clientes[indice].total=0;
					}
					clientes[indice].pedidos.push(pedido);
					var costo=(pedido.pagado==1)?(-1*pedido.producto.costo):pedido.producto.costo;
					clientes[indice].total+=costo;
				}
				
				for(var a=0;a<clientes.length;a++){
					var cliente=clientes[a];
					var listaPedidos=cliente.pedidos;
					html+='<blockquote><div class="row"><div class="col-lg-8 col-md-8 col-sm-8 col-xs-8"><strong>'+cliente.alias+'</strong></div><div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><strong>$'+cliente.total+'</strong></div></div></blockquote>';
					for(var i=0;i<listaPedidos.length;i++){
						var pedido=listaPedidos[i];
						var pagado=(pedido.pagado==1);
						var strPrecio=(pagado)?"-$"+pedido.producto.costo:"$"+pedido.producto.costo;
						html+=' <div class="row">';
						html+='		<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">';
						html+='			<h5 class="textoCuenta">'+pedido.producto.nombre+'</h5>';
						html+='		</div>';
						html+='		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">';
						html+='			<h5 class="textoCuenta">'+strPrecio+'</h5>';
						html+='		</div>';
						html+='	</div>';
					}
				}
				
			}else{
				html+='<h5>No hay pedidos en la cuenta</h5>';
			}
			html+='	</div> ';
			html+='</div>';
			return html;
		}
		
		/**
		* Funcion que obtiene el HTML de un pedido para la cuenta de una mesa
		*/
		function obtenerHTMLPorPedidoIndividual(pedido){
			var html="";
			var pagado=(pedido.pagado==1);
			var strPrecio=(pagado)?"-$"+pedido.producto.costo:"$"+pedido.producto.costo;
			html+='<h5 class="aliasCliente">'+pedido.cliente.alias+'</h5><h5 class="nombreProducto">'+pedido.producto.nombre+'</h5><span class="precioProducto">'+strPrecio+'</span><br/>';
			return html;
		}
		
		function verNuevosPedidos(idPedidoMesa,claveMesa){
			var displayCuenta=$('#opcionesPanel').css("display");
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlGtNew}",
				success:function(datos){
					var pedidos=datos.cuenta;
					renderearNuevosPedidosNuevo(idPedidoMesa,pedidos,claveMesa);
				}
			});
		}
		
		function renderearNuevosPedidosNuevo(idPedidoMesa,pedidos,claveMesa){
			var html='<div class="panel panel-default">';
			html+='<div class="panel-heading">Pedidos de la mesa:'+claveMesa+'</div>';
			html+='	<div class="panel-body"> ';
			if(pedidos!=null && pedidos.length>0){
				for(var i=0;i<pedidos.length;i++){
					var pedido=pedidos[i];
					html+=' <div class="row">';
					html+='		<div class="col-sm-3">';
					html+='			<h4><strong>'+pedido.cliente.alias+'</strong></h4>';
					html+='		</div>';
					html+='		<div class="col-sm-6">';
					html+='			<h5>'+pedido.producto.nombre+'</h5>';
					html+='		</div>';
					html+='		<div class="col-sm-2">';
					html+='			<span ><a href="javascript:atenderPedido('+pedido.id+','+pedido.cliente.pedidoMesa.id+',\''+claveMesa+'\');">Atender</a></span>';
					html+='		</div>';
					html+='	</div>';
				}
			}else{
				html+='<h5>No hay pedidos pendientes</h5>';
			}
			html+='	</div> ';
			html+='</div>';
			$('#opcionesPanel').text("");
			$('#opcionesPanel').append(html);
		}
		
		function atenderPedido(idPedidoIndividual,idPedidoMesa,claveMesa){
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa,"idPedidoClienteAtender":idPedidoIndividual},
				url:"${urlAtnNew}",
				success:function(data){
					if(data!=null){
						var pedidos=data.cuenta;
						if(pedidos.length==0){
							var numPendientes=0;
							for(var i=0;i<mesasPorAtender.length;i++){
								if(mesasPorAtender[i].mesa == claveMesa){
									mesasPorAtender[i].atendido=true;
								}
							}
						}
						actualizarMarcadorPedidosPendientes();
						renderearNuevosPedidosNuevo(idPedidoMesa,pedidos,"");
					}
				}
			});
		}
		
		function cobrarCuenta(idPedidoMesa){
			if(idPedidoMesa!=null){
				var confirmacion=confirm("Esta seguro de cerrar la cuenta? Una vez cerrada la cuenta se iniciara un nuevo pedido.");
				if(confirmacion==true){
					$.ajax({
						dataType:"json",
						async:false,
						data:{"pedido.id":idPedidoMesa},
						url:"${urlCloseAccount}",
						success:function(pedidos){
							actualizarMesas();
						}
					});	
				}
			}
		}
	</script>
</head>
<body>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/negocio" method="POST" id="forma" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="negocio.id"/>
	<s:hidden name="mesa.negocio.id" value="%{negocio.id}"/>
	<s:hidden name="idMesaEliminar" id="idMesaEliminar"/>
	<s:submit value="Guardar" id="btnGuardar" cssStyle="display:none;" action="guardarNegocio"/>
	<s:submit value="GuardarMesa" id="btnAgregarMesa" cssStyle="display:none;" action="agregarMesa"/>
	<s:submit value="EliminarMesa" id="btnEliminarMesa" cssStyle="display:none;" action="eliminarMesa"/>
	<!-- End de botones -->
	<fieldset>
		  <legend>Mesa de Control<span class="glyphicon glyphicon-bell" id="numPedidos"></span><span  class="glyphicon glyphicon-user" id="numLlamadasMesero"></span><span class="glyphicon glyphicon-list-alt" id="numPedidasCuenta"></span></legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-xs-3 control-label">Filtrar por mesero:</label>
		    <div class="col-xs-4">
<!-- 		      <input type="text" maxlength="10" name="mesa.claveMesa" value="" class="form-control" placeholder="Introduzca el nombre de la mesa"/> -->
		      	<select id="comboMeseros" class="form-control"></select>
		    </div>
		    <div class="col-xs-2">
		    	<button type="button" class="btn btn-primary" onclick="actualizarMesas();">Filtrar</button>
		    </div>
		  </div>
		  
		  <div id="contenedorMesas" class="form-group" >
		  <s:iterator value="pedidos" var="p" status="indice">
		  	<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
		    	<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" style="display:inline-block;">${p.claveMesa}</h3>
				    <span class="glyphicon ${(p.numPersonas>0)?'glyphicon-ban-circle mesaOcupada':'glyphicon-ok-sign mesaLibre'}" style="text-align:right;float:right;"></span>
				  </div>
				  <div class="panel-body mesaControl">
				  	<div>
				  		<h4 style="display:inline-block;">Num.Pedido:</h4><span> ${(p.folioPedido!=null)?p.folioPedido:''}</span>
				  	</div>
				  	<div>
				  		<h4 style="display:inline-block;">Status:</h4><span>${(p.numPersonas>0)?'Ocupada':'Libre'}</span>
				  	</div>
				  	<div>
				  		<h4 style="display:inline-block;">Atiende:</h4><span>${p.nomMesero}</span>
				  	</div>
				  	
				  	<div class="btn-group btn-group-justified">
				  		<div class="btn ${(p.solicitarNuevoPedido==1)?'btn-info':'btn-primary disabled' }" onclick="verNuevosPedidos(${p.folioPedido},'${p.claveMesa}');">
							<span class="glyphicon glyphicon-cutlery"></span>
					  	</div>
				  		<div class="btn ${(p.llamarMesero==1)?'btn-warning':'btn-primary disabled' }" onclick="atenderLlamadoMesero(${p.folioPedido});">
							<span class="glyphicon glyphicon-user"></span>
					  	</div>
					  	<div class="btn ${(p.solicitarCuenta==1)?'btn-warning':'btn-primary disabled' }" onclick="atenderPedirCuenta(${p.folioPedido});">
							<span class="glyphicon glyphicon-list-alt"></span>
					  	</div>
				  	</div>
				  	
				  	<div class="btn-group btn-group-justified">
					  	<div class="btn btn-primary" onclick="$('html,body').scrollTop(0);mostrarPedido(${(p.folioPedido!=null)?p.folioPedido:'null'},'${p.claveMesa}');">
				    		<span class="glyphicon glyphicon-th-list"></span>Cuenta
				    	</div>
				    	<div class="btn btn-primary" onclick="cobrarCuenta(${(p.folioPedido!=null)?p.folioPedido:'null'});">
				  			<span class="glyphicon glyphicon-usd"></span>Cobrar
				  		</div>
					</div>
				    <div id="cuentaMesa_${p.idMesa}" class="cuentaMesa" style="display:none;"></div>
					<div id="nuevosPedidos_${p.idMesa}" class="nuevosPedidos" style="display:none;"></div>
				  </div>
				</div>
		    </div>
		  </s:iterator>
		  </div>
		  
		</fieldset>
		<!-- Intro -->
		
</s:form>
</body>
</html>