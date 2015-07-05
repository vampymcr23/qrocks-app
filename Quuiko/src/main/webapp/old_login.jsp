<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QRocks</title>
<link type="text/css" href="css/mobile/style.css" rel="stylesheet" media="all" />
<link type="text/css" href="css/qrocks.css" rel="stylesheet" />
<style type="text/css">
	body,html{
		height:100%;
		width:100%;
		min-width:1000px;
	}
	.borderLayout{
		width:100%;
		height:100%;
		min-width:1000px;
	}

	.north,.south,.east,.west,.center{
		position:fixed;
		display:inline-block;
		background-color:#e3e6e8;
	}
	.north,.south{
		height:30%;
		width:100%;
	}
	.north{
		top:0;
		left:0;
	}	
	.south{
		bottom:0;
		left:0;
	}
	.east,.west{
		width:35%;
		display:inline-block;
		height:40%;
		min-width:
	}
	.west{
		top:30%;
		left:0;
	}
	.east{
		top:30%;
		left:65%;
	}
	.center{
		height:40%;
		width:30%;
		top:30%;
		left:35%;
		text-align: center;
	}
	section.formulario{
		width:auto;
		height:100%;
		background: #fff;
		background: rgba(255,255,255,0.75);
		transition: none;
	}
	
	section.formulario label{
		color:#000;
		width:120px;
	}
	section.formulario label, section.formulario input{
		font-size:0.8em;
		padding-top:10px;
	}
	section.formulario input{
		width:150px;
		display:inline-block;
	}
	.inCenter{
		text-align:center;
		display:block;
		width:100%;
	}
	.logo{
		display:block;
		text-align:center;
		width:100%;
	}
	div.logo h2{
		margin:0em;
	}
	.button{
		background:none repeat scroll 0 0 rgba(92, 136, 1, 0.75);
	}
</style>
</head>
<body>
<div class="borderLayout">
	<div class="north"></div>
	<div class="west"></div>
	
	<div class="center">
		<section class="formulario">
			<form action="j_spring_security_check" method="post" >
			<div class="logo"><h2>QRocks</h2></div>
			<label>Usuario:</label><input type="text" name="j_username">
			<br/>
			<label>Contraseña:</label><input type="password" name="j_password">
			<br/>
			<div class="inCenter">
				<input type="submit" class="button" value="Iniciar sesión"/>
			</div>
			</form>
		</section>
	
	</div>
	
	<div class="east"></div>
	<div class="south"></div>
</div>
</body>
</html>