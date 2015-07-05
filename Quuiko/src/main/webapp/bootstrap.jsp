<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="css/bootstrap-theme.min.css"> -->
<title>Bienvenida</title>
<script src="js/mobile/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<style>
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
</style>
</head>
<body>
<!-- <li class="active"><a href="#" class="glyphicon glyphicon-home">Link</a></li> -->
<!-- HEADER -->
<div class="navbar navbar-inverse">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">Zitla Universidad</a>
  </div>
  <div class="navbar-collapse collapse navbar-inverse-collapse">
    <ul class="nav navbar-nav">
      <li class="active"><a href="#"><span class="glyphicon glyphicon-star"></span> Mesa de Control</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-music"></span>Multimedia<b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#" ><span class="glyphicon glyphicon-th-list"></span>Crear playlist</a></li>
          <li><a href="#"><span class="glyphicon glyphicon-play-circle"></span>Reproductor de videos</a></li>
        </ul>
      </li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      
      <li class="dropdown ">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-home"></span>Restaurante <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#"><span class="glyphicon glyphicon-cutlery"></span>Mi Restaurante</a></li>
          <li><a href="#"><span class="glyphicon glyphicon-tags"></span>Promociones</a></li>
          <li><a href="#"><span class="glyphicon glyphicon-th-large"></span>Mesas</a></li>
          <li class="divider"></li>
          <li class="dropdown-header">Mi Men&uacute;</li>
          <li><a href="#"><span class="glyphicon glyphicon-list-alt"></span>Catalogo de productos</a></li>
        </ul>
      </li>
	  <li><a href="#"><span class="glyphicon glyphicon-off"></span>Salir</a></li>
    </ul>
  </div>
</div>
<!-- END HEADER -->
<div class="container">
    <div class="row row-centered">
    	<div class="col-xs-12 col-centered">
    		<!-- Contenedor -->
    		<div class="jumbotron">
				<div class="container">
					<h1>Quicko</h1>
					<p>Conoce la manera más rápida de atender a tus clientes!</p>
					<p><a class="btn btn-primary btn-lg">Learn more</a></p>
			  	</div>
			</div>
			<!-- FIN-Contenedor -->
    	</div>
    </div>
</div>
	
<div class="container">
    <div class="row row-centered">
    	<div class="col-xs-10 col-centered">
    		<!-- Contenedor -->
    		<div class="panel panel-primary">
				<div class="panel-heading">
			    	<h3 class="panel-title">Panel primary</h3>
			  	</div>
			  	<div class="panel-body">
			    	Panel content
			  	</div>
			</div>
			<!-- FIN-Contenedor -->
    	</div>
    </div>
</div>
	
</body>
</html>