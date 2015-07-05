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

    <title>Beneficios</title>

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
	 			<h4>Beneficios</h4>
	 			<div class="hline"></div>
	 			<p><a href="#">Olvídate de pagar en publicidad</a></p>
	 			<p>
	 				Si estás acostumbrado a pagar para que aparezcas en revistas o folletos, con Quuiko te aseguramos 100% que cada promoción que agregues le llegará a tus clientes 
	 				sin error a fallar. No hay nada mejor que enviarle un mensaje directo a tus clientes.
	 			</p>
	 			
	 			<p><a href="#">Indicadores</a></p>
	 			<p>
	 				Con Quuiko podrás tener un mayor control sobre tus productos más vendidos, podrás saber quiénes son tus clientes más frecuentes para que puedas tomar una decisión 
	 				sobre ofrecerles algo especial y tratarlos por su lealtad hacia tu restaurante. Tus clientes amarán las recompensas que su restaurante favorito les puede dar.
	 				<br/>
	 				Además podrás ver cuanta gente usa Quuiko, cuántos de ellos te siguen y cuantos no. Esta información es muy importante ya que de esta manera puedes saber cuanta 
	 				gente estará enterada de tus promociones. 
	 			</p>
	 			
	 			<p><a href="#">Siempre actualizado!</a></p>
	 			<p>
	 				Desde el momento en el que te afilias con nosotros, no te preocuparás por estar a la moda, nosotros estaremos trabajando constantemente, innovando para ofrecer el 
					mejor servicio sin ningún costo adicional. Agregaremos nuevas funcionalidades para los usuarios (móvil) asi como nuevas características para ti. Este es solo el comienzo,
					no te limites, déjanos ayudarte a crecer. 
	 			</p>
	 			
	 			<p><a href="#">Ofrece un servicio VIP a tus clientes</a></p>
	 			<p>
	 				Trata a tus clientes como realmente se merecen, logra que regresen más de una y otra vez. Imagina que llega tu cliente, y desde el momento que él se encuentra en su mesa 
	 				pidiendo desde Quuiko, sabrás que esa persona cumple años. Sorprende a tu cliente, felicítalo y regálale un obsequio por elegirte en su día tan especial.
	 				<br/>
	 				Adicional a esto, deja que tus clientes puedan poner su propio ambiente, deja que ellos elijan que música/video escuchar!
	 			</p>
	 			
	 			<p><a href="#">Pagas para que otros te manejen tus reservaciones?</a></p>
	 			<p>
	 				Olvídate de pagar más por que alguien te maneje tus reservaciones. Con Quuiko en tu móvil puedes solicitar una reservación con 7 días de anticipación, y tu como restaurante 
	 				tendrás el poder de autorizar si hay mesas disponibles o no. No ocupas llamar a tu cliente, Quuiko enviará una notificación de "Autorización" de la reservación y 
	 				él podrá confirmar si asistirá o no. Además Quuiko tiene la capacidad de enviar recordatorios a tus clientes el mismo día de su reservación para que no olvide asistir.
	 			</p>
	 			
	 			<p><a href="#">Todo en línea!</a></p>
	 			<p>
	 				Quuiko te da la facilidad de modificar tu menú, promociones, logo, imágenes, descripciones, costos sin ningún problema y todo en línea. Esto quiere decir que tus clientes
	 				que usan Quuiko podrán ver en tiempo real todos tus cambios.  
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
