<%@page import="com.quuiko.actions.MPedidoAction"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String actionAgregarAmigo=MPedidoAction.ACTION_AGREGAR_AMIGO;
	String actionGuardarPedido=MPedidoAction.ACTION_GUARDAR_PEDIDO;
	System.out.println("Action de Amigo:"+actionAgregarAmigo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<s:url action="llamarMesero" namespace="/m/pedidos" var="urlLlamarMesero"/>
<s:url action="infoPedido" namespace="/m/pedidos" var="urlInfoPedido"/>
<s:url namespace="/m/play" action="qrocksPlayer" var="urlPlaylist"/>
<s:url namespace="/m/pedidos/cliente" action="miCuenta" var="urlCuentaCliente"/>
<s:url namespace="/m/pedidos" action="gtAcc" var="urlGtAcc"/>
<s:url namespace="/m/pedidos" action="askAccnt" var="urlAskAccnt"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Menu</title>
	
	<!-- Scripts -->
<%-- 	<script type="text/javascript" src="js/mobile/jquery.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/skel.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/skel-panels.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="js/mobile/init.js"></script> --%>
	<!-- END Scripts -->
	<script type="text/javascript">
		var habilitarTrigger=false;
		var SEGUNDOS_TRIGGER=10;
		$(document).ready(function(){
			var llamarMesero="${pedido.llamarMesero}";
			habilitarTrigger=(llamarMesero!=null && llamarMesero==1);
			if(habilitarTrigger){
				iniciarTrigger();
			}
		});
	
		function eliminarAmigo(idAmigo){
			$('#amigoEliminar').val(idAmigo);
			$('#btnEliminarAmigo').click();
		}
		
		function llamarAlMesero(){
			$.ajax({
				dataType:"json",
				data:{"pedido.id":"${pedido.id}"},
				async:false,
				url:"${urlLlamarMesero}",
				success:function(pedido){
					if(pedido!=null && pedido.llamarMesero==1){
						$('#meseroSolicitado').fadeIn(4000,function(){
							$(this).fadeOut(3000);
						});
						$('#botonMesero').attr("value","Esperando...");
						$('#botonMesero').addClass("buttonDisabled");
						$('#botonMesero').attr("disabled",true);
						//Una vez que se solicita el mesero, se lanza trigger si no esta habilitado.
						if(!habilitarTrigger){
							habilitarTrigger=true;
							iniciarTrigger();	
						}
					}
				}
			});
		}
		/**
		* Esta funcion lanzara una tarea que revisar�� cada 10 segundos si el mesero ya atendio la llamada o no
		*/
		function iniciarTrigger(){
			//Si esta habilitado el trigger se lanza la peticion al servidor
			if(habilitarTrigger){
				$.ajax({
					dataType:"json",
					async:false,
					data:{"pedido.id":"${pedido.id}"},
					url:"${urlInfoPedido}",
					success:function(pedido){
						//Si esl estatus de llamarMesero es igual a 0, entonces ya no se lanza el trigger ya que fue detenido
						if(pedido!=null && pedido.llamarMesero==0){
							habilitarTrigger=false;
							$('#botonMesero').removeClass("buttonDisabled");
							$('#botonMesero').attr("disabled",false);
							$('#botonMesero').attr("value","Llamar al mesero");
						}else if(pedido!=null && pedido.llamarMesero==1){
							//Si no, entonces se vuelve a mandar a llamar la funcion para que en SEGUNDOS_TRIGGER segundos se vuelva a revisar si ya fue atendido o no
							habilitarTrigger=true;
							setTimeout("iniciarTrigger();",SEGUNDOS_TRIGGER*1000);
						}
					}
				});
			}
		}
		
		
		function obtenerCuenta(){
			var displayValue=$("#miCuentaDiv").css("display");
			//Si el valor del display es none, significa que actualmente esta oculto, por lo tanto lo va a mostrar
			//Si es diferente de "none", significa que esta mostrandolo, entonces se tiene que ocultar
			var mostrar=(displayValue!="none")?false:true;
			if(mostrar){
				$.ajax({
					dataType:"json",
					async:false,
					data:{"pedido.id":"${pedido.id}"},
					url:"${urlGtAcc}",
					success:function(cuenta){
						if(cuenta!=null && cuenta.length>0){
							var html="";
							for(var i=0;i<cuenta.length;i++){
								var pedido=cuenta[i];
								html+=obtenerHTMLPedidoPorCuenta(pedido);
							}
							var total=obtenerTotalCuenta(cuenta);
							html+='<div class="totalCuenta">TOTAL: $ '+total+'</div>';
							$("#miCuentaDiv").text("");
							$("#miCuentaDiv").append(html);
							$("#miCuentaDiv").css("display","");
						}
					}
				});
			}else{
				$("#miCuentaDiv").css("display","none");
			}
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
		
		function obtenerHTMLPedidoPorCuenta(pedido){
			var html="";
			var pagado=(pedido.pagado==1);
			html+='<h5 class="aliasProducto">'+pedido.cliente.alias+' - '+pedido.producto.nombre+'</h5>';
			if(pagado){
				html+='<h5 class="precioPedido">-$'+pedido.producto.costo+'</h5>';
			}else{
				html+='<h5 class="precioPedido">$'+pedido.producto.costo+'</h5>';
			}
			html+='<br/>';
			return html;
		}
		
		function pedirCuenta(){
			//Mostrar confirmacion
			$.ajax({
				dataType:"json",
				async:false,
				data:{"pedido.id":"${pedido.id}"},
				url:"${urlAskAccnt}",
				success:function(cuenta){
					$('#botonPedirCuenta').css("display","none");
				}
			});
		}
	</script>
	<style type="text/css">
		section{
			margin-bottom:0em;
		}
		#nuevoAmigo{
			width:230px;
			display:inline;
		}
		.formulario label{
			display:inline;
		}
		.formulario div span{
			display:inline;
		}
		.listaProductosDiv{
			padding-left:1em;
		}
		.clienteAlias{
			display:inline-block;
			text-align:left;
			width:40%;
		}
		.buttonDisabled,.buttonDisabled:hover{
			background-color: #999897;
		}
		h5.miCuenta{
			display:inline-block;
			font-size:1em;
		}
		
		div.miCuentaDiv{
			display:block;
			width:100%;
		}
		
		h5.aliasProducto{
			display:inline-block;
			color:#fff;
			width:250px;
			max-width:250px;
		}
		h5.precioPedido{
			display:inline-block;
			color:#eac841;
			max-width:100px;
			
		}
		div.totalCuenta{
			max-width:330px;
			color:#fff;
			background-color: #2e6889;
			border-radius: 0.35em;
 			padding: 0.2em 1em 0.2em 1em; 
			box-shadow: inset 0 0.1em 0.1em 0 rgba(0,0,0,0.05);
			border: solid 1px rgba(0,0,0,0.15);
		}
	</style>
</head>
<body>
<s:form namespace="/m/pedidos" method="POST">
	<s:hidden name="qrMID"/>
	<s:hidden name="qrMKEY"/>
	<s:hidden name="pedido.id"/>
	<!-- Campo hidden utilizado para eliminar un amigo -->
	<s:hidden name="amigoEliminar.id" id="amigoEliminar"/> 
	<!-- Botones hidden para hacer submit -->
	<s:submit action="eliminarAmigo" id="btnEliminarAmigo" cssStyle="display:none;"/>
	<s:submit action="llamarMesero" id="btnLlamarMesero" cssStyle="display:none;"/>
	<!-- End de botones -->
	
		<section id="negocio" class="one">
			<div class="container">
				<header>
					<h2 class="alt"><strong>${negocio.nombre}</strong></h2>
				</header>
<!-- 				<a href="#" class="image featured">	 -->
<!-- 					<img src="" width="300px" height="auto"> -->
<!-- 				</a> -->
				<header>
					<h2 class="alt"><strong>${pedido.mesa.claveMesa}</strong></h2>
				</header>
				<div class="contenedor" style="width:99%;">
					<label>Folio:</label><span>${(pedido.id!=null)?pedido.id:''}</span>
					<br/>
					<label>Fecha:</label><span>${pedido.fechaStr}</span>
					<br/>
					<label>Estatus:</label><span>${(pedido.activo==0)?'Offline':'Online'}</span>
				</div>
			</div>
		</section>
					
		<section id="nuevoAmigo" class="two">
			<div class="formulario">
				<header>
					<h2>Agregar al grupo</h2>
				</header>
				
				<div class="">
					<label>Nombre:</label>
					<span>
						<input type="text" id="nuevoAmigo" name="amigo.alias" width="100px" maxlength="15"/>
					</span>
					<input type="submit" value="Agregar" class="button scrolly" name="action:<%=actionAgregarAmigo%>"/>
					<br/>
				</div>

			</div>
		</section>
				
		<section id="grupo" class="three">
			<div class="listaProductosDiv">

				<header>
					<h2>Mi Grupo</h2>
				</header>
				<div>
					<!-- Iterar la lista de amigos -->
					<s:iterator value="grupo" var="cliente">
						<div>
							<div style="display:inline-block;vertical-align:top;width:19%;max-width:40px;"><img src="images/qrocks_witress3.png" width="40px" height="40px"/></div>
							<h2 class="clienteAlias">${cliente.alias}</h2>
							<div style="display:inline-block;vertical-align:bottom;width:50px;">
								<a href="${urlCuentaCliente}?qrCID=${cliente.id}&qrMKEY=${qrMKEY}" style="text-decoration: none;">
									<img src="images/qrocks_menu2.png" width="30px" height="30px"/>
								</a>
							</div>
							<div style="display:inline-block;vertical-align:bottom;width:50px;">
								<a href="javascript:eliminarAmigo(${cliente.id});" style="text-decoration: none;">
									<img src="images/qrocks_delete1.png" width="30px" height="30px"/>
								</a>
							</div>
						</div>
						<br/>
					</s:iterator>
				</div>
			</div>
		</section>
		<br/>
		<section id="miCuentaSection">
			<div id="meseroSolicitado" style="display:none;">Llamando al mesero</div>
			<s:if test="%{!grupo.isEmpty() && (pedido.llamarMesero==0 || pedido.llamarMesero==null)}">
				<input id="botonMesero" type="button" value="Pedir Mesero" class="button scrolly" onclick="llamarAlMesero();"/>
			</s:if>
			<s:elseif test="%{!grupo.isEmpty() && pedido.llamarMesero==1}">
				<input id="botonMesero" type="button" value="Esperando..." class="button scrolly buttonDisabled" onclick="llamarAlMesero();" disabled="true"/>
			</s:elseif>  
			<s:if test="%{!cuenta.isEmpty()}">
<!-- 				<h5 class="miCuenta">Ver Cuenta</h5><a href="javascript:obtenerCuenta();"><img src="images/qrocksFree_grayNotes.png" width="40" height="auto"></a> -->
				<input id="botonVerCuenta" type="button" value="Ver Cuenta" class="button scrolly" onclick="obtenerCuenta();"/>
				<input id="botonPedirCuenta" type="button" value="Pedir Cuenta" class="button scrolly" onclick="pedirCuenta();"/>
				<div id="miCuentaDiv">
					<s:iterator value="cuenta" var="pi">
						<h5 class="aliasProducto">${pi.cliente.alias}- ${pi.producto.nombre}</h5>
						<s:if test="#pedido.pagado==1">
							<h5 class="precioPedido">-$ ${pi.producto.costo}</h5>
						</s:if>
						<s:else>
							<h5 class="precioPedido">$ ${pi.producto.costo}</h5>
						</s:else>
						<br/>
					</s:iterator>
					<div class="totalCuenta">TOTAL: $ ${totalCuenta}</div>
				</div>
				
			</s:if>
		</section>
</s:form>
</body>
</html>