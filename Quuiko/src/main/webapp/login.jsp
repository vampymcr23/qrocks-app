<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String rError=request.getParameter("e");
	boolean errorLogin=("1".equals(rError));
%>
<html>
<head>
	<meta name="description" content="QRocks, musica, restaurantes, ambiente"/>
	<title>Quuiko Login</title>
	<!-- Scripts que siempre se repiten en todas la paginas -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/mobile/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
<style type="text/css">
		.glyphicon{
			padding-right:5px;
		}
		.glyphicon-list-alt:before{
			padding-right:5px;
		}
		/* CUSTOM- Centrar los containers de bootstrap*/
		/* centered columns styles */
		.row-centered {
		    text-align:center;
		}
		.col-centered {
		    display:inline-block;
		    float:none;
		    /* reset the text-align */
		    text-align:left;
		    /* inline-block space fix */
		    margin-right:-4px;
		}
		/* END-CUSTOM*/
		html {
  height: 100%;
  width: 100%;
  overflow: hidden;
  min-width: 100%;
  min-height: 100%;
}

body {
  height: 100%;
  width: 100%;
  padding: 0;
  margin: 0;
}

		.container-fluid{
		  height:100%;
		  display:table;
		  width: 100%;
		  padding: 0;
		}
		.row-fluid {
			height: 100%; 
			display:table-cell; 
			vertical-align: middle;
		}
		.centering {
  			float:none;
  			margin:0 auto;
		}
		.fondo{
 			background-color: rgba(45, 68,86, 0.75); 
/* 			background-color: rgba(79, 80,81, 0.15); */
		}
		.errorLogin{
			color:#ff0000;
			text-align: left;
		}
	</style>
</head>
<body>

<div class="container-fluid fondo">
	    <div class="row-fluid ">
	    	<div class="centering col-xs-12 ">
	    		<!-- Contenedor -->
	    		<div class="row">
  					<div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
  						<div class="well">
							<form action="j_spring_security_check" method="post" class="form-horizontal">
							 <fieldset>
    						<legend>Quuiko-Login</legend>
								<div class="form-group">
									<% if(errorLogin){ %>
										<div id="msgErrorLogin" class="errorLogin col-md-9 col-lg-9 col-sm-11 control-label">El usuario o contrase&nacute;a son incorrectos</div>
									<%}%>
									<br/>
									<label for="u" class="col-sm-4 control-label">Usuario:</label>
									<div class="col-sm-5">
										<input id="u" type="text" name="j_username" class="form-control" placeholder="usuario">
									</div>
								</div>
								
								<div class="form-group">
									<label for="p" class="col-sm-4 control-label">Contraseña:</label>
									<div class="col-sm-5">
										<input id="p" type="password" name="j_password" class="form-control" placeholder="contraseña">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-3 col-sm-offset-6">
										<button type="submit" class="btn btn-primary">Iniciar sesión</button>
									</div>
								</div>
							</fieldset>
							</form>	    		
	    				</div>
  					</div>
	    		</div>
	    		
				<!-- FIN-Contenedor -->
	    	</div>
	    </div>
</div>
</body>
</html>