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
  <s:url namespace="/" action="contact" var="urlContact"/>
  <head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>Quuiko.app</title>
    <style type="text/css">
    	.tituloCaption{
    		font-size:2em;
    		font-weight: bolder;
    	}
    	.carousel-caption p{
    		text-align: justify;
    	}
    </style>

    <!-- Bootstrap core CSS -->
<!--     <link href="css/bootstrap.min.css" rel="stylesheet"> -->

    <!-- Custom styles for this template -->
<!--     <link href="css/styleQuicko.css" rel="stylesheet"> -->
<!--     <link href="css/font-awesome.min.css" rel="stylesheet"> -->
<!--     <link href="fonts/fingerpaint-regular.css" rel="stylesheet"> -->

  </head>

  <body>

    <!-- Fixed navbar -->
    <!-- 
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" style="font-family:'FingerPaintRegular'">Quicko</a>
        </div>
        <div class="navbar-collapse collapse navbar-right">
          <ul class="nav navbar-nav">
            <li class="active"><a href="index.html">Home</a></li>
            <li><a href="contact.html">Contacto</a></li>
            <li><a href="${urlLogin}">Eres restaurante?</a></li>
            <li><a href="release/demo/quicko-release.apk">Demo</a></li>
          </ul>
        </div>
      </div>
    </div>
	-->
	<!-- *****************************************************************************************************************
	 HEADERWRAP
	 ***************************************************************************************************************** -->
	<div id="headerwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
					<h1 style="font-family:'FingerPaintRegular'">Quuiko</h1>
					<h5>Una manera m&aacute;s r&aacute;pida de encontrar ese restaurante que tanto te gusta.</h5>
					<h5>Recibe las mejores promociones de cada uno de los restaurantes de tu interés, descubre como te consentir&aacute;n el día de tu cumpleaños</h5>				
				</div>
                <div class="col-lg-12 col-md-12 col-sm-12">          
                                <!--Carrusel-->
                                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="8000">
                                          <!-- Indicators -->
                                          <ol class="carousel-indicators">
                                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="4"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="5"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="6"></li>
                                            <li data-target="#carousel-example-generic" data-slide-to="7"></li>
                                          </ol>

                                          <div class="carousel-inner">
                                            <div class="item active">
                                              <img src="images/webPage/pic_1.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                    <h3 class="tituloCaption">Prueba la nueva aplicaci&oacute;n</h3>
                                                    <p>Aprovecha los grandes beneficios que tus restaurantes favoritos te pueden ofrecer!</p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_2.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">Encuentra tu restaurante favorito!</h3>
                                                <p>Sigue a tu restaurante favorito para que recibas notificaciones sobre promociones</p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_3.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">Promociones y reservaciones!</h3>
                                                <p>Reserva desde tu dispositivo m&oacute;vil, ubica a tu restaurante y consulta las promociones que tienen en el momento!
                                                </p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_4.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">Olvidaste tu reservaci&oacute;n ?</h3>
                                                <p>Consulta el estatus, la fecha/hora y confirma!
                                                </p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_5.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">No encuentras al mesero?</h3>
                                                <p>Arma tu pedido desde tu smartphone y env&iacute;alo. Necesitas al mesero? Solo presiona el bot&oacute;n y él vendr&aacute; hasta tu mesa.  
                                                </p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_6.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">Pide cuantas veces quieras!</h3>
                                                <p>Olvidate de perseguir al mesero para que te traiga un refresco m&aacute;s, solo agrega lo que vas a querer y env&iacute;a tu pedido! 
                                                </p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_7.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">No más calculadora para saber cuanto vas a pagar!</h3>
                                                <p>Escanea el c&oacute;digo en tu mesa 
                                                y pide lo que gustes. Al final solo consulta tu cuenta!  
                                                </p>
                                              </div>
                                            </div>
                                            
                                            <div class="item">
                                              <img src="images/webPage/pic_8.png" alt="..."  class="img-responsive">
                                              <div style="height:200px;display:block;width:100%;"></div>
                                              <div class="carousel-caption">
                                                <h3 class="tituloCaption">Elige tu música!</h3>
                                                <p>Creíste que tu d&iacute;a no podr&iacute;a mejorar? Escanea el c&oacute;digo de tu mesa y elige el video que deseas ver en tu restaurante.  
                                                </p>
                                              </div>
                                            </div>

                                          </div>

                                              
                                          <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                                            <span class="glyphicon glyphicon-chevron-left"></span>
                                          </a>
                                          <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                                            <span class="glyphicon glyphicon-chevron-right"></span>
                                          </a>
                                    </div>
                                    
               </div>                 
			</div>
	    </div> 
	</div>
	 
	<!-- *****************************************************************************************************************
	 MIDDLE CONTENT
	 ***************************************************************************************************************** -->

	 <div class="container mtb">
	 	<div class="row">
	 		<div class="col-lg-4 col-lg-offset-1">
		 		<h4>Eres restaurante? <br/>Afiliate con nosotros!</h4>
		 		<p>
                                    Visita la secci&oacute;n de <a href="${urlContact}">Contacto</a>, nosotros te informamos sobre precios. 
                                    Te vas a sorprender! nuestro servicio es muy accesible, y la recompensa es muy grande para tu negocio. Atrae m&aacute;s clientes, 
                                    usa la tecnolog&iacute;a a tu favor. 
                                </p>
 				<p><br/><a href="webPage/beneficios.jsp" class="btn btn-theme">Descubre m&aacute;s beneficios</a></p>
	 		</div>
	 		
	 		<div class="col-lg-6">
	 			<h4>FAQ's</h4>
	 			<div class="hline"></div>
	 			<p><a href="webPage/faqs.jsp">Es gratuita la aplicaci&oacute;n m&oacute;vil?</a></p>
	 			<p><a href="webPage/faqs.jsp">Como es el proceso de contrataci&oacute;n de este servicio?</a></p>
	 			<p><a href="webPage/faqs.jsp">Que funciones tiene esta app?</a></p>
	 			<p><a href="webPage/faqs.jsp">Soporta tabletas?</a></p>
	 			<p><a href="webPage/faqs.jsp">Consume muchos datos esta aplicaci&oacute;n?</a></p>
	 		</div>
	 		
	 	</div><! --/row -->
	 </div><! --/container -->
	 
	 
	<!-- *****************************************************************************************************************
	 CLIENTES
	 ***************************************************************************************************************** -->
         <!--
	 <div id="cwrap">
		 <div class="container">
		 	<div class="row centered">
			 	<h3>Nuestros clientes</h3>
			 	<div class="col-lg-3 col-md-3 col-sm-3">
			 		<img src="../images/webPage/clients/client01.png" class="img-responsive">
			 	</div>
			 	<div class="col-lg-3 col-md-3 col-sm-3">
			 		<img src="../images/webPage/clients/client02.png" class="img-responsive">
			 	</div>
			 	<div class="col-lg-3 col-md-3 col-sm-3">
			 		<img src="../images/webPage/clients/client03.png" class="img-responsive">
			 	</div>
			 	<div class="col-lg-3 col-md-3 col-sm-3">
			 		<img src="../images/webPage/clients/client04.png" class="img-responsive">
			 	</div>
		 	</div>
		 </div>
	 </div>
        -->
	<!-- *****************************************************************************************************************
	 FOOTER
	 ***************************************************************************************************************** -->
	 <!-- 
	 <div id="footerwrap">
	 	<div class="container">
		 	<div class="row">
		 		<div class="col-lg-4">
		 			<h4>Nosotros</h4>
		 			<div class="hline-w"></div>
		 			<p>Somos una nueva empresa con enfoque a la innovaci&oacute;n de las empresas, utilizando la tecnolog&iacute; como
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
		 		<div class="col-lg-4">
		 			<h4>Ubicanos</h4>
		 			<div class="hline-w"></div>
		 			<p>
		 				Some Ave, 987,<br/>
		 				23890, New York,<br/>
		 				United States.<br/>
		 			</p>
		 		</div>
		 	
		 	</div>
	 	</div>
	 </div>
	 -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
<%--     <script src="js/mobile/jquery.min.js"></script> --%>
<%--     <script src="js/bootstrap.min.js"></script> --%>
<%-- 	<script src="js/retina-1.1.0.js"></script> --%>
<%-- 	<script src="js/jquery.hoverdir.js"></script> --%>
<%-- 	<script src="js/jquery.hoverex.min.js"></script> --%>
<%-- 	<script src="js/jquery.prettyPhoto.js"></script> --%>
<%--   	<script src="js/jquery.isotope.min.js"></script> --%>
<%--   	<script src="js/custom.js"></script> --%>


    <script>
// Portfolio
(function($) {
	"use strict";
	var $container = $('.portfolio'),
		$items = $container.find('.portfolio-item'),
		portfolioLayout = 'fitRows';
		
		if( $container.hasClass('portfolio-centered') ) {
			portfolioLayout = 'masonry';
		}
				
		$container.isotope({
			filter: '*',
			animationEngine: 'best-available',
			layoutMode: portfolioLayout,
			animationOptions: {
			duration: 750,
			easing: 'linear',
			queue: false
		},
		masonry: {
		}
		}, refreshWaypoints());
		
		function refreshWaypoints() {
			setTimeout(function() {
			}, 1000);   
		}
				
		$('nav.portfolio-filter ul a').on('click', function() {
				var selector = $(this).attr('data-filter');
				$container.isotope({ filter: selector }, refreshWaypoints());
				$('nav.portfolio-filter ul a').removeClass('active');
				$(this).addClass('active');
				return false;
		});
		
		function getColumnNumber() { 
			var winWidth = $(window).width(), 
			columnNumber = 1;
		
			if (winWidth > 1200) {
				columnNumber = 5;
			} else if (winWidth > 950) {
				columnNumber = 4;
			} else if (winWidth > 600) {
				columnNumber = 3;
			} else if (winWidth > 400) {
				columnNumber = 2;
			} else if (winWidth > 250) {
				columnNumber = 1;
			}
				return columnNumber;
			}       
			
			function setColumns() {
				var winWidth = $(window).width(), 
				columnNumber = getColumnNumber(), 
				itemWidth = Math.floor(winWidth / columnNumber);
				
				$container.find('.portfolio-item').each(function() { 
					$(this).css( { 
					width : itemWidth + 'px' 
				});
			});
		}
		
		function setPortfolio() { 
			setColumns();
			$container.isotope('reLayout');
		}
			
		$container.imagesLoaded(function () { 
			setPortfolio();
		});
		
		$(window).on('resize', function () { 
		setPortfolio();          
	});
})(jQuery);
</script>
  </body>
</html>
