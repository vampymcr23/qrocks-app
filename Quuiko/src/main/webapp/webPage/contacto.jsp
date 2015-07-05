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
  <s:url namespace="/" action="contactSendInfo" var="urlSend"/>
  <head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>Quuiko.app</title>
    <script type="text/javascript">
    	function submitEnviar(){
    		$('#forma').attr('action','${urlSend}');
    		$('#forma').submit();
    	}
    </script>
    <style type="text/css">
    	#headerwrap{
    		background-color:transparent;
    	}
    </style>
  </head>
	
  <body>
  <div id="headerwrap">
  	<!-- Dialogo de alerta / errores -->
  	<div class="alert alert-dismissable alert-success"  style="display:none;" id="resultDialog">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<div id="textMessageResult"><s:actionmessage escape="false"/> </div>
	</div>
	<!-- Fin del dialogo-->
	<div class="container">
	<s:hidden name="messageCode" id="messageCode" />
	<s:hidden name="messageResult" id="messageResult" />
	<s:hidden name="mensajeError" id="mensajeError"/>
	<s:hidden name="mensajeExito" id="mensajeExito"/>
	<div id="modalError" style="display:none;" title="Error"><s:property value="mensajeError"/> </div>
	<div id="modalExito" style="display:none;" title="inByteme.com"><s:property value="mensajeExito"/> </div>
	
	<s:form id="forma" cssClass="form-horizontal">
	 	<fieldset>
		  <legend>Contacto</legend>
		  <div class="form-group">
		    <label for="negocio.nombre" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Nombre:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="70" name="contacto.nombre" value="${contacto.nombre}" placeholder="Nombre del negocio..."/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for=negocio.usuario class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Email de contacto:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="70" name="contacto.email" value="${contacto.email}" placeholder="Email" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for=negocio.usuario class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Teléfono de contacto:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="20" name="contacto.telefono" value="${contacto.telefono}" placeholder="Teléfono" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for=negocio.usuario class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Asunto:</label>
		    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
		      <input type="text" class="form-control" maxlength="50" name="contacto.asunto" value="${contacto.asunto}" placeholder="Asunto a tratar" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="textArea" class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">Comentarios:</label>
		    <div class="col-lg-9 col-lg-offset-3 col-md-9 col-md-offset-3 col-sm-12 col-xs-12">
		      <textarea class="form-control" type="text"  style="min-height:0em;" maxlength="500" cols="40" rows="3" name="contacto.comentario" >${contacto.comentario}</textarea>
		      <span class="help-block">Capture sus dudas y comentarios.(Máximo 500 caracteres)</span>
		    </div>
		  </div>
		  
		  <div class="form-group">
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="submit" class="btn btn-primary" onclick="submitEnviar();">Guardar</button>
		      </div>
		    </div>
		</fieldset>
	</s:form>
	</div>
</div>
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
