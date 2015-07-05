<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String _ctx=(session.getAttribute("_ctx")!=null)?session.getAttribute("_ctx").toString():"/Invi";
	String nombreNegocio=(session.getAttribute("_nomNeg")!=null)?session.getAttribute("_nomNeg").toString():"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<base href="<%=basePath%>"/>
	<meta name="description" content="Quicko, restaurantes,app"/>
	<title><decorator:title/></title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>Quuiko.app</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/styleQuicko.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="fonts/fingerpaint-regular.css" rel="stylesheet">
    <script src="js/mobile/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/retina-1.1.0.js"></script>
	<script src="js/jquery.hoverdir.js"></script>
	<script src="js/jquery.hoverex.min.js"></script>
	<script src="js/jquery.prettyPhoto.js"></script>
  	<script src="js/jquery.isotope.min.js"></script>
  	<script src="js/custom.js"></script>
	<decorator:head/>
	
</head>
<s:url namespace="/" action="login" var="urlLogin"/>
<s:url namespace="/" action="index" var="urlHome"/>
<s:url namespace="/" action="contact" var="urlContact"/>
<s:url namespace="/" action="terms" var="urlTerminos"/>

<body>
	<!-- HEADER -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" style="font-family:'FingerPaintRegular';font-size:1.8em;">Quuiko</a>
        </div>
        <div class="navbar-collapse collapse navbar-right">
          <ul class="nav navbar-nav">
            <li class="active"><a href="${urlHome}">Home</a></li>
            <li><a href="${urlContact}">Contacto</a></li>
            <li><a href="${urlLogin}">Eres restaurante?</a></li>
            <li><a href="${urlTerminos}">Términos y Condiciones</a></li>
<!--             <li><a href="release/demo/quicko-release.apk">Demo</a></li> -->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
	<!-- END HEADER -->
	
	<!-- CONTAINER -->
	    		<!-- Contenedor -->
<!-- 	    		<div > -->
<!-- 	    		<div class="alert alert-dismissable alert-success"  style="display:none;" id="resultDialog"> -->
<!-- 				  <button type="button" class="close" data-dismiss="alert">×</button> -->
<%-- 				  <div id="textMessageResult"><s:actionmessage escape="false"/> </div> --%>
<!-- 				</div> -->
	    		<decorator:body/>
<!-- 	    		</div> -->
	<!-- FIN-Contenedor -->
		
	<!-- END CONTAINER -->
	
	<!-- FOOTER -->
	
	
	<div id="footerwrap">
	 	<div class="container">
		 	<div class="row">
		 		<div class="col-lg-4">
		 			<h4>Nosotros</h4>
		 			<div class="hline-w"></div>
		 			<p>Somos una nueva empresa con enfoque a la innovaci&oacute;n de las empresas, utilizando la tecnolog&iacute;a como
                                        la mejor herramienta para optimizar tus procesos, reducir tiempos e incrementar tus ganancias.</p>
		 		</div>
		 		<div class="col-lg-4">
		 			<h4>Redes Sociales</h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				<a href="#"><i class="fa fa-facebook"></i></a>
		 				<a href="#"><i class="fa fa-twitter"></i></a>
		 			</p>
		 		</div>
		 		<!-- 
		 		<div class="col-lg-4">
		 			<h4>Ubicanos</h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				Some Ave, 987,<br/>
		 				23890, New York,<br/>
		 				United States.<br/>
		 			</p>
		 		</div>
		 		-->
		 	</div><!--/row -->
	 	</div><!--/container -->
	 </div><!--/footerwrap -->
	<!-- END FOOTER -->	
	
	<!-- Placed at the end of the document so the pages load faster -->
    
  	
  	<script type="text/javascript">
		$(document).ready(function(){
			var mensajeExito=$('#mensajeExito').val();
			var mensajeError=$('#mensajeError').val();
			if(mensajeError!=null && mensajeError!=''){
				mostrarMensaje(true);
			}
			if(mensajeExito!=null && mensajeExito!=''){
				mostrarMensaje(false);
			}
			var codigo=$('#messageCode').val();
			var msg=$('#messageResult').val();
			showDialog(codigo,msg);
		});
		
		function showDialog(codigo,mensaje){
			if(codigo!=null && codigo.length>0){
				$('#textMessageResult').text("");
				var numSegundos=5;
				if(codigo=="0"){
					$('#resultDialog').css('display','inherit');
					$('#resultDialog').attr('class','alert alert-dismissable alert-success');
					$('#resultDialog').fadeOut({'duration':(1000*numSegundos)});
				}else if(codigo=="-1"){
					$('#resultDialog').css('display','inherit');
					$('#resultDialog').attr('class','alert alert-dismissable  alert-danger');
					$('#textMessageResult').append("Se han generado los siguientes errores:<br/>");
				}
				$('#textMessageResult').append(mensaje);
			}
		}
		
		function mostrarMensaje(esError){
			var idDiv=(esError)?"modalError":"modalExito";
			$( "#"+idDiv ).dialog({
		    	modal: true,
		      	buttons: {
		        	Ok: function() {
		          	$( this ).dialog( "close" );
		        	}
		     	}
		    });
		}
	</script>
</body>
</html>