<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
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
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
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
	<s:url namespace="/mesaControl" action="gtMC" var="urlGtMC"/>
	<s:url namespace="/mesaControl" action="atnWitress" var="urlAtenderMesero"/>
	<s:url namespace="/mesaControl" action="bringTheAccount" var="urlAtenderPedirCuenta"/>
	<s:url namespace="/mesaControl" action="closeAccnt" var="urlCloseAccount"/>
	<s:url namespace="/negocio" action="showAllWaitress" var="urlGtWait"/>
	<s:url namespace="/m/pedidos" action="atnNew" var="urlAtnNew"/>
	<s:url namespace="/m/pedidos" action="gtNew" var="urlGtNew"/>
	<s:url namespace="/m/pedidos" action="gtAcc" var="urlAccount"/>
	<script type="text/javascript">
		var SEGUNDOS=15;
		var ALTURA_X_MESA=60;
		var ALTURA_X_PEDIDO=55;
		$(document).ready(function(){
			inicializarComboMeseros();
			setTimeout("actualizarRecurrentemente();",SEGUNDOS*1000);
		});
		
		function inicializarComboMeseros(){
			$.ajax({
				dataType:"json",
				url:"${urlGtWait}",
				async:false,
				success:function(meseros){
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
		}
		
		function actualizarRecurrentemente(){
			actualizarMesas();
			setTimeout("actualizarRecurrentemente();",SEGUNDOS*1000);
		}
		
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
							if(i%3 ==0){
								html+="<br/>";
							}
							html+=obtenerHTMLMesa(mesa);
						}
// 						$('#mesaControlDiv').remove(".divMesa");
						$('#mesaControlDiv').text("");
						$('#mesaControlDiv').append(html);
						habilitarEfectos();
					}else{
						$('#mesaControlDiv').text("");
					}
				}
			});
		}
		
		/**
		* Funcion que arma la parte HTML de cada mesa
		*
		*/
		function obtenerHTMLMesa(mesa){
			var html="";
			//Indica si alguien esta usando la mesa, o esta vacia
			var id=(mesa!=null)?mesa.idMesa:null;
			var mesaActiva=(mesa!=null  && mesa.numPersonas>0)?"activa":"";
			//Indica si alguien mando a llamar al mesero
			var llamadaMesero=(mesa!=null && mesa.llamarMesero==1)?"llamada":"";
			var solicitaronNuevoPedido=(mesa.solicitarNuevoPedido==1);
			var cssNuevoPedido=(solicitaronNuevoPedido)?"pedido":"";
			var folioPedido=(mesa.folioPedido!=null)?mesa.folioPedido:"";
			html+='<div id="mesa_'+id+'" class="divMesa '+mesaActiva+' '+llamadaMesero+' '+cssNuevoPedido+'" > ';
			html+='<h5 class="inlineMesa c1">'+folioPedido+'</h5> ';
			html+='<h5 class="inlineMesa c2">'+mesa.claveMesa+'</h5> ';
			html+='<span class="inlineMesa c4"> ';
			if (mesa.llamarMesero==1){
				html+='<a href="javascript:atenderLlamadoMesero('+folioPedido+');">';
				html+='<img id="mesero_'+id+'" class="imgMesa" src="images/qrocks_greenWitress.png" width="25" height="25"> ';
				html+='</a>';
			}else{
				html+='<img id="mesero_'+id+'" class="imgMesa" src="images/qrocks_grayWitress.png" width="25" height="25"> ';
			}
			html+='</span> ';
			html+='<span class="inlineMesa c5"> ';
			if(mesa.solicitarCuenta==1){
				html+='<a href="javascript:atenderPedirCuenta('+folioPedido+');">';
				html+='<img class="imgMesa" src="images/qrocks_greenHandAccount.png" width="25" height="25"> ';
				html+='</a>';
			}else{
				html+='<img class="imgMesa" src="images/qrocks_grayHandAccount.png" width="25" height="25"> ';
			}
			html+='</span> ';
			html+='<div class="atiendeMesero">'+mesa.nomMesero+'</div>';
			html+='<span class="inlineMesa c6"><a href="javascript:mostrarPedido('+mesa.folioPedido+',\''+mesa.claveMesa+'\');">Cuenta</a></span>';
			html+='<span class="inlineMesa c7 '+cssNuevoPedido+'"><a href="javascript:verNuevosPedidos('+mesa.folioPedido+',\''+mesa.claveMesa+'\');">Pedidos</a></span>';
			html+='<span class="inlineMesa c8"><a href="javascript:cobrarCuenta('+mesa.folioPedido+');">Cobrar</a></span>';
			html+='<div id="cuentaMesa_'+id+'" class="cuentaMesa" style="display:none;"></div>';
			html+='<div id="nuevosPedidos_'+id+'" class="nuevosPedidos" style="display:none;"></div>';
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
			var display=$('#resumenDiv').css("display");
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlAccount}",
				success:function(pedidos){
					if(pedidos!=null && pedidos.length>0){
						//YA FUE ATENTIDO, quitar la imagen de verde y ponerla gris
// 						$("#mesa_"+idPedidoMesa).removeClass("pedido");
						var html="<div class='tituloResumen'>Cuenta Mesa:"+pedidos[0].cliente.pedidoMesa.mesa.claveMesa+"</div>";
						//Se arma el HTML de un div para mostrar el nuevo pedido.
						for(var i=0;i<pedidos.length;i++){
							var pedido=pedidos[i];
							var htmlPedido=obtenerHTMLPorPedidoIndividual(pedido);
							html+=htmlPedido;
						}
						var total=obtenerTotalCuenta(pedidos);
						$('#resumenDiv').text("");
						html+='<div class="totalCuentaMesa"><span class="texto">Total:</span><span class="montoTotal">$'+total+'</span></div>';
						$('#resumenDiv').append(html);
						$('#resumenDiv').css("display",display);
						if(display!="none"){
							var numPedidos=pedidos.length;
							var altura=ALTURA_X_MESA;
							altura+=(numPedidos*ALTURA_X_PEDIDO);//60 px por cada pedido
						}else{
						}
					}else{
						$('#resumenDiv').text("");
						var html="<div class='tituloResumen'>Mesa:"+claveMesa+" <br/>La cuenta no esta abierta.</div>";
						$('#resumenDiv').append(html);
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
			var displayCuenta=$('#resumenDiv').css("display");
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa},
				url:"${urlGtNew}",
				success:function(pedidos){
					renderearNuevosPedidos(idPedidoMesa,pedidos,claveMesa);
				}
			});
		}
		
		function renderearNuevosPedidos(idPedidoMesa,pedidos,claveMesa){
			if(pedidos!=null && pedidos.length>0){
				//YA FUE ATENTIDO, quitar la imagen de verde y ponerla gris
				var html="<div class='tituloResumen'>Pedidos Mesa:"+claveMesa+"</div>";
				//Se arma el HTML de un div para mostrar el nuevo pedido.
				for(var i=0;i<pedidos.length;i++){
					var pedido=pedidos[i];
					var htmlPedido=obtenerHTMLPorNuevoPedido(pedido);
					html+=htmlPedido;
				}
				$('#resumenDiv').text("");
				$('#resumenDiv').append(html);
			}else{
				var html="<div class='tituloResumen'>Mesa:"+claveMesa+" <br/>No hay pedidos pendientes</div>";
				$('#resumenDiv').text("");
				$('#resumenDiv').append(html);
			}
		}
		/**
		*	FUncion que obtiene el html de un nuevo pedido para el mesero
		*/
		function obtenerHTMLPorNuevoPedido(pedido){
			var html="";
			html+='<h5 class="aliasCliente">'+pedido.cliente.alias+'</h5><h5 class="nombreProducto">'+pedido.producto.nombre+'</h5><span class="atender"><a href="javascript:atenderPedido('+pedido.id+','+pedido.cliente.pedidoMesa.id+');">Atender</a></span><br/>';
			return html;
		}
		
		function atenderPedido(idPedidoIndividual,idPedidoMesa){
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":idPedidoMesa,"idPedidoClienteAtender":idPedidoIndividual},
				url:"${urlAtnNew}",
				success:function(pedidos){
					renderearNuevosPedidos(idPedidoMesa,pedidos,"");
				}
			});
		}
		
		function cobrarCuenta(idPedidoMesa){
			if(idPedidoMesa!=null){
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
	</script>
</head>
<body>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/negocio" method="POST" id="forma" enctype="multipart/form-data">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="negocio.id"/>
	<s:hidden name="mesa.negocio.id" value="%{negocio.id}"/>
	<s:hidden name="idMesaEliminar" id="idMesaEliminar"/>
	<s:submit value="Guardar" id="btnGuardar" cssStyle="display:none;" action="guardarNegocio"/>
	<s:submit value="GuardarMesa" id="btnAgregarMesa" cssStyle="display:none;" action="agregarMesa"/>
	<s:submit value="EliminarMesa" id="btnEliminarMesa" cssStyle="display:none;" action="eliminarMesa"/>
	<!-- End de botones -->
		<!-- Intro -->
		<section class="one">
			<div class="formulario">
				<header>
					<h2 class="alt tituloPantalla"><strong>Mesa de Control</strong></h2>
					<br/>
					<h4 style="display:inline-block;">Mesero:</h4><select class="comboMeseros" id="comboMeseros" style="width:150px;display:inline-block;"></select><input type="button" value="Filtrar" onclick="actualizarMesas();"/>
				</header>
				<div id="mesaControlDiv">
				<s:iterator value="pedidos" var="p" status="indice">
					<s:if test="#indice.index % 3 ==0 ">
					<br/>
					</s:if>
					<div id="mesa_${p.idMesa}" class="divMesa" >
						<h5 class="inlineMesa c1">${p.folioPedido}</h5>
						<h5 class="inlineMesa c2">${p.claveMesa}</h5>
						<span class="inlineMesa c4">
						<s:if test="#p.llamarMesero==1">
							<a href="javascript:atenderLlamadoMesero(${p.folioPedido});">
							<img class="imgMesa" id="mesero_${p.idMesa}" src="images/qrocks_greenWitress.png" width="25" height="25" alt="Le hablan al mesero!" title="Le hablan al mesero!">
							</a>
						</s:if>
						<s:else>
							<img class="imgMesa" src="images/qrocks_grayWitress.png" width="25" height="25" alt="Le hablan al mesero!" title="Le hablan al mesero!">
						</s:else>
						</span>
						<span class="inlineMesa c5">
						<s:if test="#p.solicitarCuenta==1">
							<a href="javascript:atenderPedirCuenta(${p.folioPedido});">
								<img class="imgMesa" src="images/qrocks_greenHandAccount.png" width="25" height="25" alt="Pidieron la cuenta!" title="Pidieron la cuenta!">
							</a>
						</s:if>
						<s:else>
							<img class="imgMesa" src="images/qrocks_grayHandAccount.png" width="25" height="25" alt="Pidieron la cuenta!" title="Pidieron la cuenta!">
						</s:else>
						</span>
						<div class="atiendeMesero">${p.nomMesero}</div>
						<span class="inlineMesa c6"><a href="javascript:mostrarPedido(${p.folioPedido},'${p.claveMesa}');">Cuenta</a></span>
						<s:if test="#p.solicitarNuevoPedido==1">
							<span class="inlineMesa c7 pedido"><a href="javascript:verNuevosPedidos(${p.folioPedido},'${p.claveMesa}');">Pedidos</a></span>
						</s:if>
						<s:else>
							<span class="inlineMesa c7"><a href="javascript:verNuevosPedidos(${p.folioPedido},'${p.claveMesa}');">Pedidos</a></span>						
						</s:else>
						<span class="inlineMesa c8"><a href="javascript:cobrarCuenta(${p.folioPedido});">Cobrar</a></span>
						<div id="cuentaMesa_${p.idMesa}" class="cuentaMesa" style="display:none;"></div>
						<div id="nuevosPedidos_${p.idMesa}" class="nuevosPedidos" style="display:none;"></div>
					</div>
				</s:iterator>
				</div>
				<div id="resumenDiv" class="resumenDiv">
					
				</div>
				<br/>
			</div>
		</section>
		
		
</s:form>
</body>
</html>