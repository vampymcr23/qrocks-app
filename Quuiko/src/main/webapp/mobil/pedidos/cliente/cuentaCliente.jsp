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
<s:url namespace="/productos" action="imagenProducto" var="urlImagen"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>QRocks-Menu</title>
	<!-- Scripts -->
	<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel.min.js"></script>
	<script type="text/javascript" src="js/mobile/skel-panels.min.js"></script>
	<script type="text/javascript" src="js/mobile/init.js"></script>
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
 			vertical-align:baseline; 
 			width:100px; 
 			max-width:100px; 
 			padding-right:10px; 
 		} 
		
 		.contenidoProducto{ 
 			width:70%; 
 			max-width:600px; 
 			display:inline-block; 
 		} 
		
 		.opcionesProducto{ 
 			display:inline; 
 			vertical-align:middle; 
 			width:100px; 
 			max-width:100px; 
 		} 
		
 		section{ 
 			margin-bottom: 0em; 
 		} 
		
 		.cuentaNombreProducto{ 
 			display:inline-block; 
 			width:60%; 
 			max-width: 320px; 
 		} 
 		
 		.contenidoProducto h5{
 			font-size:14px;
 		}
 		
 		.cuentaProductoCosto{ 
 			display:inline-block; 
 		} 
		
 		.cuentaEliminarProducto{ 
 			display:inline-block; 
 			vertical-align:middle; 
 			height:40px; 
 		} 
 		#totalPedidos{ 
 			display:inline-block; 
 			width:250px; 
 		} 
 		#totalPedidos h3.costoTotal{ 
 			font-size:2em; 
 			font-weight:bold; 
 			color:#fff; 
 		} 
 		.leyenda{
 			font-size:18px;
 			color:#fff;
 			padding:10px;
 		}
	</style>
	<s:url action="misPedidos" namespace="/m/pedidos/cliente" var="urlMisPedidos"/>
	<script type="text/javascript">
		var arregloPedidos=new Array();
		$(document).ready(function(){
			cargarMiCuenta();
		});
		
		function cargarMiCuenta(){
			$.ajax({
				dataType:'json',
				url:"${urlMisPedidos}",
				data:{qrCID:"${qrCID}",qrMKEY:"${qrMKEY}"},
				success:function(pedidos){
					if(pedidos!=null && pedidos.length>0){
						for(var i=0;i<pedidos.length;i++){
							var pedido=pedidos[i];
							if(pedido!=null){
								var idProducto=pedido.producto.id;
								var idPedido=pedido.id;
								agregarProducto(idProducto,idPedido,i);
							}
						}
					}
				}
			});
		}
		
		function agregarProducto(idProducto,idPedido,contador){
			var nombreProducto=$('#productoNombre_'+idProducto).html();
			var costoProducto=$('#productoCosto_'+idProducto).val();
			var producto={
				"idPedido":idPedido,
				"id":idProducto,
				"nombre":nombreProducto,
				"costo":costoProducto
			};
			arregloPedidos.push(producto);
			//Si no tiene un contador, entonces sera el numero del arreglo-1
			if(contador==null){
				contador=(arregloPedidos.length-1);
			}
			agregarAMiCuenta(producto,contador);
			calcularTotal();
			$('#btnEnviarPedido').attr("disabled",false);
		}
	
		function agregarAMiCuenta(producto,contador){
			//Si el pedido es nulo, entonces es nuevo pedido y puede eliminarse, ya que no se ha pedido completamente.
			if(producto.idPedido==null){
				$('#miCuentaDiv').append("<div id='cuentaProducto_"+contador+"'><h4 class='cuentaNombreProducto'>"+producto.nombre+"</h4><h4 class='cuentaProductoCosto'>$"+producto.costo+"</h4><div class='cuentaEliminarProducto'><a href='javascript:removerDeLaCuenta("+contador+");'><img src='images/qrocks_delete2.png' width='35' height='35'></a></div></div>");
			}else{
				//Si ya tiene un idPedido, entonces ya no puede eliminarse
				$('#miCuentaDiv').append("<div id='cuentaProducto_"+contador+"'><h4 class='cuentaNombreProducto'>"+producto.nombre+"</h4><h4 class='cuentaProductoCosto'>$"+producto.costo+"</h4><div class='cuentaEliminarProducto'></div></div>");
			}
		}
		
		function removerDeLaCuenta(contador){
			if(contador!=null && $('#cuentaProducto_'+contador)!=null ){
				$('#cuentaProducto_'+contador).remove();
			}
			if(arregloPedidos!=null && arregloPedidos.length>0){
				var arregloTemporal=arregloPedidos;
				arregloTemporal[contador]=null;
				//Ciclo para actualizar la lista de pedidos
				arregloPedidos=new Array();
				if(arregloTemporal.length>0){
					for(var i=0;i<arregloTemporal.length;i++){
						var producto=arregloTemporal[i];
						if(producto!=null){
							arregloPedidos.push(producto);
						}
					}
				}
				calcularTotal();
				if(arregloPedidos.length==0){
					$('#btnEnviarPedido').attr("disabled",true);
				}
			}
		}
		
		function calcularTotal(){
			var total=0;
			$.each($('.cuentaProductoCosto'),function(i,elemento){
				var costo=$(elemento).html();
				costo=costo.replace("$","");
				costo=parseFloat(costo);
				total+=costo;
			});
			$('#totalPedidos').text("");
			$('#totalPedidos').append("<h3>TOTAL:</h3><h3 class='costoTotal'>$"+total+" pesos</h3>");
		}
		
		function enviarPedido(){
			if(arregloPedidos!=null && arregloPedidos.length>0){
				var arregloFinalPedidos=new Array();
				var idCliente="${qrCID}";
				for(var i=0;i<arregloPedidos.length;i++){
					var pedido=arregloPedidos[i];
					var idPedido=pedido.idPedido;
					//Si no tiene un idPedido significa que es nuevo pedido, entonces se tiene que registrar, de lo contrario, se ignora
					if(idPedido==null){
						arregloFinalPedidos.push(pedido);
					}
				}
				if(arregloFinalPedidos.length>0){
					$('#pedidosHidden').text="";
					for(var i=0;i<arregloFinalPedidos.length;i++){
						var pedido=arregloFinalPedidos[i];
						$('#pedidosHidden').append("<input type='hidden' name='pedidos["+i+"].producto.id' value='"+pedido.id+"' />");
						$('#pedidosHidden').append("<input type='hidden' name='pedidos["+i+"].cliente.id' value='"+idCliente+"' />");
					}
					$('#pedidosForm').submit();
				}
				
			}
		}
		
	</script>
</head>
<body>
<s:form namespace="/m/pedidos/cliente" method="POST" action="guardarCuenta" id="pedidosForm">
	<s:hidden name="qrMKEY"/>
	<s:hidden name="qrCID"/>
	<!-- End de botones -->
	
		<section>
			<div class="formulario">
				<header>
					<h2>Mi Cuenta</h2>
				</header>
				<div id="pedidos">
					<div id="miCuentaDiv"></div>
					<!-- Iterar la lista de amigos -->
					<div id="totalPedidos">TOTAL: $0 pesos</div>
					<input type="button" id="btnEnviarPedido" value="Enviar Pedido" onclick="enviarPedido();" disabled="disabled" class="button scrolly"/>
					<div id="pedidosHidden" style="display:none;"></div>
				</div>
			</div>
		</section>
		<h5 class="leyenda">Seleccione el producto que desea agregar.</h5>
		<div>
			<!-- Iterar la lista de productos -->
			<s:iterator value="listaProductos" var="p">
					<a href="javascript:agregarProducto(${p.id});">
					<section class="productoStyle">
						<div class="imagenProducto">
							
								<img src="${urlImagen}?idProducto=${p.id}" width="60px" height="auto" title="Agregar" alt="Agregar"/>
							
						</div>
						<div class="contenidoProducto">
							<h4 id="productoNombre_${p.id}">${p.nombre}</h4>
							<h5>${p.descripcion}-$ ${p.costo}</h5>
							<input type="hidden" id="productoCosto_${p.id}" value="${p.costo}"/>
						</div>
<!-- 						<div class="opcionesProducto"> -->
<!-- 								<img src="images/qrocks_addSong.png" width="60px" height="auto" title="Editar" alt="Editar"/> -->
<!-- 						</div> -->
					</section>
					</a>
					
			</s:iterator>
		</div>

</s:form>
</body>
</html>