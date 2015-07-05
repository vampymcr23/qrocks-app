<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <s:url namespace="/" action="login" var="urlLogin"/>
  <head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>Quicko.app</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/styleQuicko.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="fonts/fingerpaint-regular.css" rel="stylesheet">


    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
<!--     <script src="js/modernizr.js"></script> -->
  </head>

  <body>
	<div class="container mtb">
	 	<div class="row">
	 		
	 		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	 			<h4>FAQ's</h4>
	 			<div class="hline"></div>
	 			<p><a href="#">Es gratuita la aplicaci&oacute;n m&oacute;vil?</a></p>
	 			<p>
	 				Para los usuarios de la aplicación móvil por supuesto que es gratuita! Solo tienes que descargarla del Apple Store o Playstore buscando la aplicación "Quuiko". 
	 				Por otro lado, para los restaurantes que desean afiliarse se les cobra una mensualidad por el servicio. <a href="#">Más información...</a>
	 			</p>
	 			
	 			<p><a href="#">Como es el proceso de contrataci&oacute;n de este servicio?</a></p>
	 			<p>
	 				Como restaurante, debes de dirigirte a la sección de <a href="${urlLogin}">Afíliate!</a> y llenar el formulario, aceptando los términos y condiciones. 
	 				Se te pedirá registrar una tarjeta de crédito a la cual se hará el cargo de la mensualidad, esta información será almacenada de manera segura (encriptada) 
	 				en nuestro hosting. Esta información no se compartirá con nadie más, puedes estar tranquilo. 
	 				Si ya cuentas con un contrato y deseas renovarlo, comunícate con nosotros.
	 			</p>
	 			<p><a href="#">Que funciones tiene esta app?</a></p>
	 			<p>
	 				Quuiko te permite encontrar tu restaurante favorito, buscar las mejores promociones en los restaurantes que más te agradan, reservar tu mesa en aquel lugar
	 				que tanto te gusta. Además puedes recibir promociones especiales por tu cumpleaños. Si estas en el restaurante puedes pedir desde tu smartphone, llamar al mesero, 
	 				o inclusive entrar en ambiente escogiendo tu música favorita. Además puedes ver la cuenta de todo lo que han consumido en tu mesa.
	 			</p>
	 			<p><a href="#">Soporta tabletas?</a></p>
	 			<p>
	 				Claro! La aplicación puede ser instalada en una tablet, y para los restaurantes que tienen mayores ingresos y tienen posibilidad de adquirir tablets para cada mesero,
	 				la aplicación puede manejarse desde su tablet!
	 			</p>
	 			<p><a href="#">Consume muchos datos esta aplicaci&oacute;n?</a></p>
	 			<p>
	 				La aplicación fue diseñada para que puedas accesar a la mayoría de las consultas sin necesidad de tener SIEMPRE internet, guardamos información en el caché 
	 				para reducir el consumo de datos.
	 			</p>
	 		</div>
	 		
	 	</div><!-- /row -->
	 
	 </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/mobile/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/retina-1.1.0.js"></script>
	<script src="js/jquery.hoverdir.js"></script>
	<script src="js/jquery.hoverex.min.js"></script>
	<script src="js/jquery.prettyPhoto.js"></script>
  	<script src="js/jquery.isotope.min.js"></script>
  	<script src="js/custom.js"></script>


  </body>
</html>
