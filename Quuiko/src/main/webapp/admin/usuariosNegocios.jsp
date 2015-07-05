<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<s:url namespace="/console" action="searchUsers" var="urlSearch"/>
<s:url namespace="/console" action="updateUsr" var="urlUpdateUsr"/>
<s:url namespace="/bu" action="openBU" var="urlNuevoUsuario"/>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 	<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" /> -->
	<title>QRocks-Consola de usuarios</title>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		.green{
			color:#56bf48;
		}
		
		.red{
			color:#f96b5e;
		}
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
	<script type="text/javascript">
		
		$(document).ready(function(){
			$(":input").inputmask();
		});
		
		function consultarUsuarios(paginaActual){
			$.ajax({
				dataType:"json",
				data:{
					'filtro.name':$('#nombre').val(),
					'filtro.id':$('#id').val(),
					'filtro.enabled':$('#estatus').val(),
					'paginaActual':paginaActual
				},
				url:"${urlSearch}",
				async:false,
				success:function(datos){
					renderearUsuarios(datos);
					renderearPaginacion(datos.numPaginas,paginaActual,datos.conteo);
				}
			});
		}
		
		/**
		 * FUncion que renderear la lista de reservaciones
		 */
		function renderearUsuarios(datos){
			if(datos!=null && datos.usuarios!=null && datos.usuarios.length>0){
				var html='<ul class="list-group">';
				for(var i=0;i<datos.usuarios.length;i++){
					var usuario=datos.usuarios[i];
					html+=renderearRegistro(usuario);
				}
				html+='</ul>';
				$('#listaReservacionesDiv').text("");
				$('#listaReservacionesDiv').append(html);
			}else{
				var html='<ul class="list-group">';
				html+='<li class="list-group-item active">Resultado de la búsqueda</li>';
				html+='<li class="list-group-item itemProducto">0 registros encontrados</li>';
				html+='</ul>';
				$('#listaReservacionesDiv').text("");
				$('#listaReservacionesDiv').append(html);
			}
		}
		
		function renderearRegistro(usuario){
			var html='';
			var fecha=usuario.creationDateStr;
			var fechaVigencia=usuario.expirationDateStr;
			var tipoUsuario=(usuario.usrType=='GV')?'Admin':'Restaurante';
			var nombre=(usuario.name!=null)?usuario.name:'N/A';
			var user=(usuario.username!=null)?usuario.username:'N/A';
			var businessKey=(usuario.businessKey!=null)?usuario.businessKey:'';
			var estatus=(usuario.enabled==1)?'Si':'No';
			var id=usuario.id;
			html+='<li class="list-group-item active"><h4 style="color:#fff"># '+id+' - Nombre:'+nombre+'</h4></li>';
			html+='<li class="list-group-item itemProducto">';
			html+='		<div class="infoProducto"> ';
			html+='			<div class="col-lg-10 col-md-10 col-sm-12 col-xs-12">';
			html+='				<h4>Usuario:'+user+'</h4> ';
			html+='				<h5>Activo:'+estatus+'</h5>';
			html+='				<h5>Fecha de registro:'+fecha+'</h5>';
			html+='				<h5>Fecha de vigencia:'+fechaVigencia+'</h5>';
			html+='				<h5>Tipo de usuario:'+tipoUsuario+' </h5>';
			html+='			</div>';
			html+='			<div class="col-lg-1 col-md-1 col-sm-6 col-xs-6"> ';
			html+='				<span class="glyphicon glyphicon-info-sign iconoProducto" onclick="modify('+id+');" title="Marcar asistencia"></span> ';
			html+='			</div>';
			html+='			<div class="col-lg-1 col-md-1 col-sm-6 col-xs-6">';
			if(usuario.enabled==1){
				//deshabilitar
				html+='				<span class="glyphicon glyphicon-ok-sign iconoProducto green" onclick="habilitarInhabilitar(\''+user+'\','+0+');" title="Marcar marcarFalta"></span> ';
			}else{
				//habilitar
				html+='				<span class="glyphicon glyphicon-minus-sign iconoProducto red" onclick="habilitarInhabilitar(\''+user+'\','+1+');" title="Marcar marcarFalta"></span> ';				
			}
			html+='			</div> ';
			html+='		</div>';
			html+='</li>';
			return html;
		}
		
		function renderearPaginacion(numPaginas,paginaActual,conteo){
			var html="";
			html+='<li><a href="javascript:consultarUsuarios(0);">«</a></li>';
			
			for(var i=0;i<numPaginas;i++){
				if(i==paginaActual){
					html+='<li id="pag_'+i+'" class="active"><a href="javascript:consultarUsuarios('+i+');">'+(i+1)+'</a></li>';
				}else{
					html+='<li id="pag_'+i+'"><a href="javascript:consultarUsuarios('+i+');">'+(i+1)+'</a></li>';
				}
			}
			html+='<li><a href="javascript:consultarUsuarios('+(numPaginas-1)+');">»</a></li>';
			$('#paginationDiv').text('');
			$('#paginationDiv').append(html);
			$('#paginadoNumRegistros').text('');
			$('#paginadoNumRegistros').append('<h4>Total de registros: '+conteo+'</h4>');
		}
		
		function habilitarInhabilitar(usr,habilitar){
			$.ajax({
				dataType:"json",
				data:{'usuario.username':usr},
				url:"${urlUpdateUsr}",
				async:false,
				success:function(datos){
					consultarUsuarios(0);
					var codigo=datos.messageCode;
					var msg=datos.messageResult;
					if(codigo!=null && msg!=null){
						$('#messageCode').val(codigo);
						$('#messageResult').val(msg);
						alert(msg);
					}
				}
			});
		}
		
		function modify(id){
			$('#buId').val(id);
			$('#hForm').attr('action',"${urlNuevoUsuario}");
			$('#hForm').submit();
		}
		
		var counter=0;
		function parse(k){
			counter++;
			if(counter==5){
				alert("HINT:"+k);
				counter=0;
			}
		}
	</script>
</head>
<body>
<s:form id="hForm" cssStyle="display:none;">
	<s:hidden name="businessU.id" id="buId"/>
</s:form>
<s:form namespace="/productos" method="POST" id="forma" cssClass="form-horizontal" enctype="multipart/form-data">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="conteo" id="conteo" value="0"/>
	<s:hidden name="paginaActual" id="paginaActual" value="0"/>
	<fieldset>
		  <legend>Consulta de Usuarios </legend>
		    
		  
		  <div class="form-group">
		  	<label for="mesa.claveMesa" class="col-lg-3 col-md-3 col-sm-5 col-xs-11 control-label">ID:</label>
		    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-11">
		    	<input type="text" class="form-control" maxlength="10" id="id" name="filtro.id" placeholder="folio del usuario" data-inputmask="'alias': 'decimal'"/>
		    </div>
		    
		    <label for="mesa.claveMesa" class="col-lg-3 col-md-3 col-sm-5 col-xs-11 control-label">Nombre del negocio:</label>
		    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-11">
		    	<input type="text" class="form-control" maxlength="25" id="nombre" name="filtro.name" placeholder="nombre del negocio"/>
		    </div>
		 </div>
		 <div class="form-group">
		    <label for="mesa.claveMesa" class="col-lg-3 col-md-3 col-sm-5 col-xs-11 control-label">Estatus:</label>
		    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-11 ">
		    	<select name="filtro.enabled"  class="form-control" id="estatus" >
		    		<option value="1">Activo</option>
					<option value="0">Inactivo</option>
					<option value="" selected="true">Todos</option>
				</select>
		    </div>
		    
		    <div class="col-lg-3 col-lg-offset-3 col-md-3 col-md-offset-3 col-sm-5 col-sm-offset-6 col-xs-6 col-xs-offset-6">
		        <button type="button" class="btn btn-primary" onclick="consultarUsuarios();">Buscar</button>
		      </div>
		  </div>
		  
		  <br/><br/>
		  <!-- Lista de reservaciones -->
		  <div id="paginadoNumRegistros"><h4>Total de registros: ${conteo}</h4></div>
		  <ul class="pagination pagination-sm" id="paginationDiv">
			  <li><a href="javascript:consultarUsuarios(0);">«</a></li>
			  <s:iterator begin="1" end="%{numPaginas}" status="i">
			  		<s:if test="#i.index==0">
			  			<li id="pag_${i.index}" class="active"><a href="javascript:consultarUsuarios(${i.index});">${i.index +1}</a></li>
			  		</s:if>
			  		<s:else>
			  			<li id="pag_${i.index}"><a href="javascript:consultarUsuarios(${i.index});">${i.index+1}</a></li>
			  		</s:else>
			  </s:iterator>
			  <li><a href="javascript:consultarUsuarios(${numPaginas-1});">»</a></li>
		  </ul>
		  <div class="form-group" id="listaReservacionesDiv">
		  	<ul class="list-group">
			<s:iterator value="usuarios" var="u">
			
			  <li class="list-group-item active"><h4 style="color:#fff"># ${u.id} - Nombre: ${u.name}</h4></li>
			  <li class="list-group-item itemProducto">
				    <div class="row infoProducto">
				    	<div class="col-lg-10 col-md-10 col-sm-12 col-xs-12">
				    		<h4 onclick="parse('${u.businessKey}')">Usuario:${u.username}</h4>
						    <h5>Activo: ${(u.enabled==1)?'Si':'No'}</h5>
						    <h5>Fecha de Registro: ${u.creationDateStr}</h5>
						    <h5>Fecha de Vigencia: ${u.expirationDateStr}</h5>
						    <h5>Tipo Usuario: ${(u.usrType=='GV')?'Admin':'Restaurante'}</h5>
				    	</div>
				    	<div class="col-lg-1 col-md-1 col-sm-6 col-xs-6">
				    		<span class="glyphicon glyphicon-info-sign iconoProducto" onclick="modify(${u.id});" title="Marcar asistencia"></span>
				    	</div>
				    	<div class="col-lg-1 col-md-1 col-sm-6 col-xs-6">
				    		<s:if test="#u.enabled==1">
				    			<span class="glyphicon glyphicon-ok-sign iconoProducto green" onclick="habilitarInhabilitar('${u.username}');" title="Deshabilitar"></span>
				    		</s:if>
				    		<s:else>
				    			<span class="glyphicon glyphicon-minus-sign iconoProducto red" onclick="habilitarInhabilitar('${u.username}');" title="Habilitar"></span>
				    		</s:else>
				    	</div>
<!-- 				    	<div style="display:inline-block;float:right;"> -->
<%-- 				    		<span class="glyphicon glyphicon-ok iconoProducto" onclick="marcarAsistencia(${r.id});" title="Marcar asistencia"></span> --%>
<%-- 				    		<span class="glyphicon glyphicon-remove iconoProducto" onclick="marcarFalta(${r.id});" title="Marcar falta"></span> --%>
<!-- 				    	</div> -->
<%-- 					    <h2>Usuario:${u.username}</h2> --%>
<%-- 					    <h5>Activo: ${(u.enabled==1)?'Si':'No'}</h5> --%>
<%-- 					    <h5>Fecha de Registro: ${u.creationDate}</h5> --%>
<%-- 					    <h5>Tipo Usuario: ${(u.usrType=='GV')?'Admin':'Restaurante'}</h5> --%>
				    </div>
			  </li>
			</s:iterator>
			</ul>
		  </div>
	</fieldset>
</s:form>
</body>
</html>