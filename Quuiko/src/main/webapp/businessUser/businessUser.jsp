<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>QRocks-Usuario</title>
	<s:url namespace="/bu" action="saveBU" var="urlGuardarUsuario"/>
	<!-- Scripts -->
	<!-- END Scripts -->
	<style type="text/css">
		form textarea{
			min-height: 1em;
			text-align: left;
			width:600px;
		}
		
		.imagenProducto{
			display:inline-block;
			padding-right:20px;
			vertical-align: middle;
		}
		.tituloProducto{
			display:inline-block;
			width:250px;
		}
		.tituloProductoHeader{
			margin:0em;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#tipoUsuario').val("${businessU.usrType}");
		});
		
		function submitGuardarUsuario(){
			$('#forma').attr('action','${urlGuardarUsuario}');
			$('#forma').submit();
		}
		
	</script>
</head>
<body>
<s:form namespace="/bu" id="forma" action="saveBU" cssClass="form-horizontal">
	<!-- Botones hidden para hacer submit -->
	<s:hidden name="businessU.id"/>
	<s:hidden name="businessU.enabled"/>
	<s:submit value="Guardar" id="btnGuardar" cssStyle="display:none;"/>
	<!-- End de botones -->
		<!-- Intro -->
		<fieldset>
		  <legend>Datos del Usuario</legend>
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Nombre del negocio/usuario:</label>
		    <div class="col-xs-4">
		      <input type="text" class="form-control" maxlength="40" name="businessU.name" value="${businessU.name}" placeholder="Nombre del negocio/usuario"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Username:</label>
		    <div class="col-xs-4">
		     	<s:if test="businessU.id!=null">
		      		<input type="text" class="form-control" maxlength="10" name="businessU.username" value="${businessU.username}" readonly="true"/>
		      	</s:if>
		      	<s:else>
		      		<input type="text" class="form-control" maxlength="10" name="businessU.username" value="${businessU.username}" placeholder="usuario de acceso"/>
		      	</s:else>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Password:</label>
		    <div class="col-xs-4">
		      <input type="password" class="form-control" maxlength="10" name="businessU.businessKey" placeholder="contraseña"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Fecha de Creacion:</label>
		    <div class="col-xs-4">
		      <input type="text" class="form-control" maxlength="10" name="businessU.creationDateStr" value="${businessU.creationDateStr}" readonly="true"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Fecha de Expiracion:</label>
		    <div class="col-xs-4">
		      <input type="text" class="form-control" maxlength="10" name="businessU.expirationDateStr" value="${businessU.expirationDateStr}" placeholder="Fecha en que expira el servicio ,ej:(21/08/2014)" data-inputmask="'alias': 'date','mask':'d/m/y'"/>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label  class="col-xs-2 control-label">Estatus: </label>
		    <div class="col-xs-4">
		      <input type="text" class="form-control" maxlength="12" value="${(businessU.enabled==1)?'Activo':'Inactivo'}" readonly="true"/>
		    </div>
		  </div>
		  
		  <sec:authorize access="hasAnyRole('GV')">
		  	<div class="form-group">
		    	<label  class="col-xs-2 control-label">Tipo de Usuario: </label>
		    	<div class="col-xs-4">
		      		<select name="businessU.usrType" id="tipoUsuario">
						<option value="USR">Usuario</option>
						<option value="GV">Admin</option>
					</select>
		    	</div>
		  	</div>
		 </sec:authorize>
		 <div class="form-group">
		      <div class="col-lg-1 col-lg-offset-10 col-md-1 col-md-offset-10 col-sm-1 col-sm-offset-10 col-xs-1 col-xs-offset-10">
		        <button type="submit" class="btn btn-primary" onclick="submitGuardarUsuario();">Guardar</button>
		      </div>
		 </div>
		 
<!-- 			<div class="formulario"> -->
<!-- 				<header class="tituloProductoHeader"> -->
<%-- 					<h2 class="alt tituloProducto"><strong>Usuario</strong></h2> --%>
<!-- 				</header> -->
<!-- 				<div class="panelConsulta" style="width:99%;"> -->
<%-- 					<label>Nombre:</label><input type="text" maxlength="40" name="businessU.name" value="${businessU.name}"/> --%>
<!-- 					<br/> -->
<%-- 					<label>Usuario:</label><input type="text" maxlength="10" name="businessU.username" value="${businessU.username}" /> --%>
<!-- 					<br/> -->
<!-- 					<label>Clave:</label><input type="password" maxlength="10" name="businessU.businessKey" /> -->
<!-- 					<br/> -->
<%-- 					<label>Fecha creacion:</label><input type="text"  value="${businessU.creationDate}" readonly="true"/> --%>
<!-- 					<br/> -->
<%-- 					<label>Fecha expiracion:</label><input type="text" value="${businessU.expirationDate}" readonly="true"/> --%>
<!-- 					<br/> -->
<%-- 					<label>Estatus:</label><input type="text" maxlength="5"  value="${(businessU.enabled==1)?'Activo':'Inactivo'}"/> --%>
<!-- 					<br/> -->
<%-- 					<sec:authorize access="hasAnyRole('GV')"> --%>
<!-- 						<label>Tipo de usuario:</label> -->
<%-- 						<select name="businessU.usrType" style="width:200px;display:inline-block;"> --%>
<!-- 							<option value="USR">Usuario</option> -->
<!-- 							<option value="GV">Koala</option> -->
<%-- 						</select> --%>
<!-- 						<br/> -->
<%-- 					</sec:authorize> --%>
<!-- 					<input type="button" value="Guardar" onclick="$('#btnGuardar').click();" class="button scrolly"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
						
</s:form>
</body>
</html>