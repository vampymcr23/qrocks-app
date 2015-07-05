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
<s:url namespace="/negocio" action="removeWaitress" var="urlEliminarMesero"/>
<s:url namespace="/negocio" action="eliminarMesa" var="urlEliminarMesa"/>
<s:url namespace="/negocio" action="agregarMesa" var="urlAgregarMesa"/>
<s:url namespace="/negocio" action="obtenerMisMesas" var="urlObtenerMisMesas"/>

<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>QRocks-Mi Negocio</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
	</style>
	<s:url namespace="/m/play" action="addSong" var="urlAddSong"/>
	<s:url namespace="/m/play" action="getArtWork" var="urlArtwork"/>
	<s:url namespace="/negocio" action="guardarNegocio" var="urlGuardarNegocio"/>
	<script type="text/javascript">
		var currentTable=null;
		$(document).ready(function(){
			$('#soportaPedidos').val("${negocio.soportaPedidos}");
		});
		
		function eliminarMesa(idMesa){
			showInProcessDialog();
			$('#idMesaEliminar').val(idMesa);
			$('#forma').attr('action','${urlEliminarMesa}');
			$('#forma').submit();
		}
		
		function agregarMesa(){
			$('#forma').attr('action','${urlAgregarMesa}');
			$('#forma').submit();
		}
		
		function generarQR(clave){
			$('#idMesaQR').val(clave);
			$('#qrForm').submit();
		}
		var mesaActualSeleccionada=null;
		function verMeseros(idM,claveM,ocultarModal){
			$('#modal').modal('show');
			if(ocultarModal==true){
// 				$('#modal').css('display','none');
			}else{
// 				$('#modal').css('display','inherit');
				$('#tituloModal').text("Asignacion de mesero para la mesa: "+claveM);
			}
			mesaActualSeleccionada=claveM;
			currentTable=idM;
			$.ajax({
				data:{},
				url:"${urlMeseros}",
				dataType: "json",
				async:false,
				success:function(data){
					var meseros=data.meseros;
					if(meseros!=null && meseros.length>0){
						renderearMeseros(meseros);
					}
				}
			});
		}
		
		function renderearMeseros(meseros){
			var html="<div class='list-group'>";
			for(var i=0;i<meseros.length;i++){
				html+=renderearMeseroHTML(meseros[i]);
			}
			html+="</div>";
			$('#gridMeseros').text("");
			$('#gridMeseros').append(html);
		}
		
		function renderearMeseroHTML(mesero){
			var html="<div class='list-group-item'>";
			html+=" <div class='row'>";
			html+="		<div class='col-md-9 col-sm-9 col-xs-9'><a href='javascript:asignarMesa("+mesero.id+");' >"+mesero.nombre+"</a></div><div class='col-md-3 col-sm-3 col-xs-3' onclick='eliminarMesero("+mesero.id+");' ><span class='glyphicon glyphicon-remove'></span></div>";
			html+=" </div>";
			html+=" </div>";
			return html;
		}
		
		function agregarMesero(){
			var nomMesero=$("#nomMesero").val();
			showInProcessDialog();
			if(nomMesero==null || nomMesero.length==0){
				showDialog("-1","Favor de proporcionar el nombre del mesero que desea agregar");
			}else{
				$.ajax({
					data:{"mesero.nombre":nomMesero},
					url:"${urlAgregarMesero}",
					dataType: "json",
					async:false,
					success:function(){
						closeProcessDialog();
						verMeseros(currentTable,mesaActualSeleccionada,true);
					}
				});
			}
		}
		
		function eliminarMesero(idMesero){
			if(idMesero==null){
				showDialog("-1","Favor de proporcionar el mesero que desea eliminar");
			}else{
				$.ajax({
					data:{"mesero.id":idMesero},
					url:"${urlEliminarMesero}",
					dataType: "json",
					async:false,
					success:function(){
						verMeseros(currentTable,mesaActualSeleccionada,true);
						renderearMesas();
					}
				});
			}
		}
		
		function asignarMesa(idW){
			$.ajax({
				data:{"mesa.id":currentTable,"mesero.id":idW},
				url:"${urlAsignarMesero}",
				dataType: "json",
				async:false,
				success:function(result){
					if(result==1){
						showDialog("0","Se ha asignado correctamente el mesero.");
					}else{
						showDialog("-1",'Ocurrio un error al intentar asignar la mesa al mesero.');
					}
					renderearMesas();
				}
			});
		}
		
		function renderearMesas(){
			$.ajax({
				url:"${urlObtenerMisMesas}",
				dataType: "json",
				async:false,
				success:function(data){
					var mesas=data.mesas;
					if(mesas!=null && mesas.length>0){
						var html="";
						for(var i=0;i<mesas.length;i++){
							var m=mesas[i];
							var nombreMesero=(m.mesero!=null && m.mesero.nombre!=null)?m.mesero.nombre:'Asignar mesero';
							html+='<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">';
							html+='	<div class="panel panel-primary">';
							html+=' 	<div class="panel-heading"> ';
							html+='    		<h3 class="panel-title" style="display:inline-block;">Mesa: '+m.claveMesa+'</h3> ';
							html+='    		<span class="glyphicon glyphicon-remove" style="text-align:right;float:right;" onClick="eliminarMesa('+m.id+');"></span> ';
							html+='  	</div> ';
							html+='  	<div class="panel-body"> ';
							html+='    		<div> ';
							html+='    			<h4 style="display:inline-block;">Mesero:</h4><a style="font-size:1.2em;padding-left:5px;" href="javascript:verMeseros('+m.id+',\''+m.claveMesa+'\')">'+nombreMesero+'</a> ';
							html+='    		</div> ';
							html+='    		<div onclick="generarQR(\''+m.claveMesa+'\');"><span class="glyphicon glyphicon-qrcode" ></span>Generar QR</div> ';
							html+='	 	</div> ';
							html+='	</div> ';
							html+='</div> ';
						}
						$('#contenedorMesas').text("");
						$('#contenedorMesas').append(html);
					}
				}
			});
			
			
		}
		
		function submitGuardarNegocio(){
			$('#forma').attr('action','${urlGuardarNegocio}');
			$('#forma').submit();
		}
	</script>
</head>
<body>
<s:form id="qrForm" action="gtQRCode" namespace="/negocio" enctype="multipart/form-data" method="POST">
	<s:hidden id="idMesaQR" name="mesa.claveMesa"/>
</s:form>
<s:url namespace="/negocio" action="getImg" var="urlImagen"/>
<s:form namespace="/negocio" method="POST" id="forma" enctype="multipart/form-data" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="mesa.negocio.id" value="%{negocio.id}"/>
	
	<s:hidden name="idMesaEliminar" id="idMesaEliminar"/>
	<s:submit value="reload" id="btnReload" cssStyle="display:none;" action="miNegocio"/>
	<s:submit value="GuardarMesa" id="btnAgregarMesa" cssStyle="display:none;" action="agregarMesa"/>
	<s:submit value="EliminarMesa" id="btnEliminarMesa" cssStyle="display:none;" action="eliminarMesa"/>
	
	<!-- NUEVA FORMA -->
	
		<fieldset>
		  <legend>Mis mesas</legend>
		  <div class="form-group">
		    <label for="mesa.claveMesa" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Agregar mesa:</label>
		    <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
		      <input type="text" maxlength="10" name="mesa.claveMesa" value="" class="form-control" placeholder="Introduzca el nombre de la mesa"/>
		      
		    </div>
		    <div class="col-lg-2 col-md-2 col-sm-12 col-xs-11">
		    	<button type="submit" class="btn btn-primary" onclick="agregarMesa();">Guardar</button>
		    </div>
		  </div>
		  
		  <div id="contenedorMesas" class="form-group">
		  <s:iterator value="mesas" var="m">
		  	<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
		    	<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" style="display:inline-block;">Mesa: ${m.claveMesa}</h3>
				    <span class="glyphicon glyphicon-remove" style="text-align:right;float:right;" onClick="eliminarMesa(${m.id});"></span>
				  </div>
				  <div class="panel-body">
				    <div>
				    	<h4 style="display:inline-block;">Mesero:</h4><a style="font-size:1.2em;padding-left:5px;"  role="button"  data-toggle="modal" onclick="verMeseros(${m.id},'${m.claveMesa}');">${(m.mesero.nombre!=null)?m.mesero.nombre:'Asignar mesero'}</a>
				    </div>
				    <div onclick="generarQR('${m.claveMesa}');"><span class="glyphicon glyphicon-qrcode" ></span>Generar QR</div>
				    
				  </div>
				</div>
		    </div>
		  </s:iterator>
		  </div>
		  
		</fieldset>
		  
	<!-- END NUEVA FORMA -->
	
	<!-- NUEVO MODAL -->
	<div id="modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="tituloModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	        <h4 class="modal-title" id="tituloModal">Meseros</h4>
	      </div>
	      <div class="modal-body">
	      	<input type="hidden" name="mesero.negocio.id" id="meseroNegocio" value="${negocio.id}"/>
	      	<div class="form-group">
		        <label for="mesa.claveMesa" class="col-xs-3 control-label">Agregar mesero:</label>
			    <div class="col-xs-4">
			      <input type="text" id="nomMesero" name="mesero.nombre"  maxlength="20" class="form-control" placeholder="Nombre del mesero"/>
			    </div>
			    <div class="col-xs-2">
			    	<button type="submit" class="btn btn-primary" onclick="agregarMesero();">Guardar</button>
			    </div>
			</div>
			<div class="form-group">
				<div id="gridMeseros" class="gridMeseros">
					
				</div>
			</div>
	      </div>
	      <div class="modal-footer">
	      </div>
	    </div>
	  </div>
	</div>
	<!-- END NUEVO MODAL -->
		
</s:form>
</body>
</html>