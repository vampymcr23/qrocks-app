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

    <title>Terminos y condiciones</title>

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
	 			<h4>Términos y condiciones</h4>
	 			<div class="hline"></div>
	 			<h5>Aceptación de términos y condiciones</h5>
	 			<p>
				Gracias por usar los productos y servicios de Quuiko S.A, una sociedad anónima promotora de inversión, debidamente constituida de conformidad con las leyes de los 
				Estados Unidos Mexicanos (“Quuiko ”), con domicilio en la Avenida XXXX #314, Colonia YYYY, en San Nicolás de los Garza, Nuevo León, México, Código Postal 66413.
				Las presentes menciones legales y condiciones particulares tienen como objeto definir las condiciones y modalidades de la puesta en funcionamiento de un servicio 
				gratuito de búsqueda y elección online de platos, bebidas y postres en Restaurantes,Cafeterías,Locales, Bares y Antros. Las presentes condiciones pueden ser 
				completadas o modificadas, si fuera necesario, para incluir condiciones específicas de utilización.
				Nos reservamos el derecho a modificar los presentes Términos y Condiciones. La última actualización de los presentes términos y condiciones tuvo lugar el 
				01 de Noviembre de 2014.
				Le recomendamos que se imprima una copia de estos Términos y condiciones para futuras referencias.
				</p>
				<p>
				<b>Aceptación de las condiciones generales</b> <br/>
				Quuiko.com y Quuiko App propone un servicio gratuito de elección online de platos, bebidas y postres en locales de ocio, 
				bajo la condición de aceptación incondicional de las presentes condiciones generales.<br/>
				El usuario declara y reconoce haber leído íntegramente los términos de uso de las presentes condiciones generales. Por otro lado, la conexión a uno cualquiera de 
				los servidores propuestos está disponible en la siguiente dirección: http://www.quuiko.com y a través de app store de apple y google play (de ahora en adelante Quuiko) 
				y conlleva la aceptación de las presentes condiciones generales por parte del usuario. 
				La sociedad Quuiko S.A. se reserva el derecho a modificar, en su totalidad o en parte, las presentes condiciones generales. En consecuencia es el usuario el que debe 
				consultar regularmente la última versión de las mismas publicada en la dirección http://www.quuiko.com. El usuario aceptará la versión actualizada de las condiciones 
				generales en cada nueva conexión a la página web.
				En caso de que el usuario no respete las presentes condiciones generales, la sociedad Quuiko S.A se reserva el derecho de suspender sin previo aviso la cuenta del 
				cliente y podrá denegarle el acceso al servicio.<br/>
				<b>Descripción del servicio</b><br/>
				La página web www.quuiko.com así como Quuiko App permite al usuario buscar restaurantes, elegir su pedido  en tiempo real en un local de ocio como restaurantes, 
				cafeterías, bares y antros afiliados a nuestra red así como llamar al mesero del local, pedir la cuenta, ver el calculo de la cuenta y recibir promociones de los 
				restaurantes afiliados a nuestros servicios.
				Los servicios propuestos y descritos en las presentes condiciones generales son gratuitos, así como a descarga de la aplicación Quuiko App. La sociedad Quuiko S.A a 
				través de su web y de su aplicación móbil ofrece información acerca de los precios y la gama de precios correspondiente a los prestatarios del servicio 
				(los restaurantes). Estas informaciones se incluyen contactando a nuestra área de ventas de Quuiko. En ningún caso la sociedad Quuiko S.A garantiza la exactitud de 
				estas informaciones ni la disponibilidad de la comida y/o bebida comandada a través de la aplicación. La información compartida en Quuiko App es meramente administrada 
				por cada uno de nuestros prestarios del servicio (el mismo restaurante).<br/>
				<b>Responsabilidad</b><br/> 
				La sociedad Quuiko no garantiza de ninguna manera los productos, servicios y/o prácticas comerciales presentes en la web, ya que son los locales adscritos a nuestra red
				 los responsable de parametrizar su oferta gastronómica, sus ofertas y las bebidas de las que disponen. Del mismo modo la sociedad Quuiko S.A no garantiza que el 
				 usuario quede satisfecho con los productos, prácticas y/o servicios obtenidos a partir de una comanda realizada a través de la página web quuiko.com o a través de 
				 la aplicación Quuiko App.<br/>
				<b>ALTA DEL USUARIO</b><br>
				Cuando el usuario crea su cuenta se atribuye un nombre y un alias, la aplicación mobil obtiene un registro con Google o Apple llamado token o gcmId (ahora en adelante identificador) que le permiten acceder a su cuenta privada. 
				La información a proporcionar son el nombre, alias y como opcionales el email, telefono (para reservaciones) y fecha de nacimiento (para promociones de cumpleaños). 
				Así mismo, se solicitará autorización para compartir su ubicación para la busqueda de restaurantes cercanos. El usuario autoriza expresamente a Quuiko S.A a ceder a 
				su red de locales los datos proporcionados por el Usuario con fines comerciales y de marketing, respetando en todo las condiciones de privacidad.
				Los identificadores son personales y confidenciales. Solo pueden ser cambiados bajo petición del usuario o por iniciativa de la sociedad Quuiko S.A
				El usuario es enteramente responsable de la utilización de sus identificadores y se compromete a conservar en secreto los mismos y no divulgarlos de manera alguna.
				<br/>
				<b>Intercambio de informaciones:</b><br/> 
				El usuario acepta el uso de la mensajería electrónica para la transmisión de informaciones que le son necesarias y que conciernen a la conclusión o ejecución de un contrato.
				<br/>
				<b>Protección de los datos personales</b><br/> 
				La sociedad Quuiko S.A, dentro del estricto respecto a las leyes y reglamentos vigentes, desea recopilar informaciones sobre sus usuarios. Estas informaciones son recogidas de 
				forma conforme a las disposiciones relativas a la protección de datos personales, y están destinadas poder ofrecer el servicio descrito en estas condiciones. 
				<!-- La sociedad Quuiko S.A, que trata datos de carácter personal, tiene su base del sitio web www.quuiko.com declarada en Agencia Nacional de Protección de Datos y hace uso de ella conforme a los reglamentos.-->
				Los datos personales no serán compartidos, unicamente se compartiran con restaurantes en los que usted desee reservar esto para cumplir con el servicio de reservación 
				de restaurantes. Asi mismo, se le notifica al restaurante las veces que usted ha visitado un local esto con el motivo de poder ofrecerle al usuario de Quuiko App mejores
				promociones por su lealtad con los mismos restaurantes. Quuiko S.A no se encargará de cumplir con las promociones de cada local o prestarios de servicios, esto es responsabilidad 
				del prestario de servicio a cual el usuario acude. Quuiko S.A solo es un portal cuyo sus prestarios de servicios configuran sus promociones y vigencia de las mismas.
				<br/>
				<b>Uso de su información personal</b><br/>
				www.quuiko.com y Quuiko App recopila y usa su información personal para trabajar y mejorar sus sitios y servicios. Estos usos pueden incluir un servicio más efectivo al 
				usuario, la modificación de sitios o servicios para que sean fáciles de usar al eliminar la necesidad de que usted escriba repetidamente los mismos datos; también se 
				incluye la realización de investigaciones y análisis dirigidos a mejorar nuestros productos, servicios y tecnologías; y, finalmente, la visualización de contenidos y 
				anuncios publicitarios personalizados, según sus intereses y preferencias. 
				También usamos su información personal para comunicarnos con usted. Puede que le enviemos determinadas comunicaciones de servicio obligatorias tal como información acerca de 
				nuestros servicios, promociones y/o anuncios de seguridad. Inclusive en ocasiones podemos enviarle para informarle acerca de nuevos locales adheridos a nuestra red.
				Uso compartido de su información personal
				Salvo lo descrito en esta declaración, sin consentimiento de usted no divulgaremos su información personal fuera de www.quuiko.com y Quuiko App y de sus filiales así como de las 
				empresas que comercializan sus productos a través de la web o locales y marcas adheridos a Quuiko App.
				<br/> 
				<b>Seguridad de su información personal</b><br/>
				www.quuiko.com y Quuiko App se compromenten a proteger la seguridad de la información personal de los usuarios. La aplicación esta alojada en un sitio seguro mediante  
				nuestro host ubicado en USA quienes a su vez ofrecen altos niveles de seguridad. 
				El usuario reconoce y acepta que la sociedad Quuiko SA se reserva la posibilidad de implantar un archivo temporal en el dispositivo móbil para evitar el consumo de 
				datos al usuario y pedirle repetidamente esta información para accesar asi como administrar las versiones del la aplicación instalada en el dispositivo. 
				Utilización de los datos El usuario es informado por las presentes condiciones generales de que los datos de carácter personal señalizados como obligatorios en los 
				formularios y que son recogidos dentro del cuadro del servicio descrito en las presentes condiciones generales, son necesarios para la utilización del servicio 
				y son utilizados únicamente dentro del cuadro de servicio descrito anteriormente y destinados exclusivamente a la sociedad Quuiko S.A y sus locales adheridos que 
				pondrán atención a fin de preservar, en la medida de lo posible, la seguridad de los datos personales. El usuario autoriza a la sociedad Quuiko S.A a 
				proporcionar ciertas informaciones a sus servidores técnicos, con el fin de que el usuario se pueda beneficiar de ciertas funciones de la página Web.
				El usuario autoriza a la sociedad Quuiko S.A a facilitar todas las informaciones que le conciernen a un asociado restaurador de la sociedad Quuiko S.A.
				<br/>
				<b>Advertencia</b>
				El usuario reconoce que, de manera general y teniendo en cuenta las características de la tecnología actual, cada vez que proporciona informaciones personales online, 
				estas informaciones pueden ser recogidas y utilizadas por terceros. En consecuencia, el usuario exime a la sociedad Quuiko S.A de toda responsabilidad o consecuencia 
				dañina derivada de la utilización por parte de terceros, de las informaciones personales intercambiadas a través de los medios de comunicación de Internet 
				(especialmente los foros o anuncios) propuestos por la web www.quuiko.com.
				<br/>
				<b>LIMITACIÓN DE LA RESPONSABILIDAD. Funcionamiento de la red</b>
				Teniendo en cuenta la especificidad del medio Internet, la sociedad Quuiko S.A no ofrece ninguna garantía de continuidad del servicio, únicamente existe una obligación de medios.
				La aplicación Quuiko App no es responsable en caso de daños ligados a la imposibilidad temporal de acceder a alguno de los servicios propuestos por el portal 
				www.quuiko.com.
				La sociedad Quuiko S.A declina toda responsabilidad por cualquier daño o pérdida ligados a la utilización o imposibilidad de utilización de la página web www.quuiko.com
				 o su contenido, salvo excepción prevista por ley.
				Quuiko S.A no garantiza la efectividad del servicio de pedidos. La sociedad Quuiko S.A no puede verificar de forma material la exactitud de las informaciones recogidas 
				y/o ofrecidas por los prestatarios del servicio (restaurantes), el usuario acepta que la responsabilidad de la sociedad Quuiko S.A no existe si este no llega a 
				beneficiarse de las prestaciones del servicio de un restaurante.
				<br/>
				<b>Obligaciones del usuario</b>
				<br/> 
				Al abrir una cuenta, el usuario acepta, expresamente y sin excepción, los términos de las presentes condiciones generales y eventualmente las condiciones particulares, presentes en la Web.
				El usuario está obligado a transmitir informaciones exactas y veraces especialmente sobre su fórmula de tratamiento de cortesía, sus apellidos, su nombre,  
				su teléfono, necesarios para el buen funcionamiento del manejo de reservaciones.<br/>
				<b>Solicitar un pedido</b> 
				Cuando elijas tu pedido ten en cuenta que es importante que corrijas los errores antes de hacer clic en el botón de “Enviar pedido” puesto que una vez que haga clic ya no podrás cancelar tu pedido y el local restaurantero procesará tu solicitud.
				La aplicación se encuentra instalada en un Hosting de USA, y para hacer uso de esta aplicación los afiliados deberán acceder a un software vía internet en la página web www.quuiko.com en la sección "Eres Restaurante?" para poder administrar 
				la información de su local, promociones, y pedidos. 
				<b>Precio de productos</b>
				Los precios serán los indicados en Quuiko App. Estos precios incluyen el IVA . La APP contiene un gran número de menús y siempre puede ocurrir que algunos de los menús 
				contengan un precio incorrecto. Si el precio correcto de un pedido fuera superior al precio establecido en la Página Web, o si el producto no estuviera disponible, 
				el mesero del local donde se encuentra usted pidiendo se pondrá en contacto con usted antes de que el pedido en cuestión sea despachado y Quuiko APP no estará obligada 
				a garantizar que el pedido se le sirva a un precio incorrecto inferior ni a compensarle por la incorrección en el precio.
				En el supuesto de que el usuario tenga queja sobre la calidad de la comida o del servicio prestado por los restaurantes en esta APP, deberá reclamar directamente al 
				local cualquier compensación. Quuiko no podrá realizar reembolsos en nombre de los locales y no será responsable por ninguna de dichas reclamaciones de reembolsos. 
				Todas las reclamaciones deberán presentarse inicialmente ante el local y, cuando corresponda, deberán seguirse los procedimientos de reclamación del propio restaurante.
				<br/>
				</p>
				
				<h4>Política de privacidad de www.quuiko.com y Quuiko APP</h4>
	 			<div class="hline"></div>
				<p>
				<!-- Quuiko S.A con CIF B-86794542 y domicilio en Madrid, C/ Arganda núm.12, 28005, inscrita en el Registro Mercantil de Madrid tomo 31378, folio 32, sección 8, hoja M-564810.-->
				<b>ALTA DEL USUARIO</b><br>
				Cuando el usuario crea su cuenta se atribuye un nombre y un alias, la aplicación mobil obtiene un registro con Google o Apple llamado token o gcmId (ahora en adelante identificador) que le permiten acceder a su cuenta privada. 
				La información a proporcionar son el nombre, alias y como opcionales el email, telefono (para reservaciones) y fecha de nacimiento (para promociones de cumpleaños). 
				Así mismo, se solicitará autorización para compartir su ubicación para la busqueda de restaurantes cercanos. El usuario autoriza expresamente a Quuiko S.A a ceder a 
				su red de locales los datos proporcionados por el Usuario con fines comerciales y de marketing, respetando en todo las condiciones de privacidad.
				Los identificadores son personales y confidenciales. Solo pueden ser cambiados bajo petición del usuario o por iniciativa de la sociedad Quuiko S.A
				El usuario es enteramente responsable de la utilización de sus identificadores y se compromete a conservar en secreto los mismos y no divulgarlos de manera alguna.
				<br/>
				<b>Intercambio de informaciones:</b><br/> 
				El usuario acepta el uso de la mensajería electrónica para la transmisión de informaciones que le son necesarias y que conciernen a la conclusión o ejecución de un contrato.
				<br/>
				<b>Protección de los datos personales</b><br/> 
				La sociedad Quuiko S.A, dentro del estricto respecto a las leyes y reglamentos vigentes, desea recopilar informaciones sobre sus usuarios. Estas informaciones son recogidas de 
				forma conforme a las disposiciones relativas a la protección de datos personales, y están destinadas poder ofrecer el servicio descrito en estas condiciones. 
				<!-- La sociedad Quuiko S.A, que trata datos de carácter personal, tiene su base del sitio web www.quuiko.com declarada en Agencia Nacional de Protección de Datos y hace uso de ella conforme a los reglamentos.-->
				Los datos personales no serán compartidos, unicamente se compartiran con restaurantes en los que usted desee reservar esto para cumplir con el servicio de reservación 
				de restaurantes. Asi mismo, se le notifica al restaurante las veces que usted ha visitado un local esto con el motivo de poder ofrecerle al usuario de Quuiko App mejores
				promociones por su lealtad con los mismos restaurantes. Quuiko S.A no se encargará de cumplir con las promociones de cada local o prestarios de servicios, esto es responsabilidad 
				del prestario de servicio a cual el usuario acude. Quuiko S.A solo es un portal cuyo sus prestarios de servicios configuran sus promociones y vigencia de las mismas.
				<br/>
				<b>Uso de su información personal</b><br/>
				www.quuiko.com y Quuiko App recopila y usa su información personal para trabajar y mejorar sus sitios y servicios. Estos usos pueden incluir un servicio más efectivo al 
				usuario, la modificación de sitios o servicios para que sean fáciles de usar al eliminar la necesidad de que usted escriba repetidamente los mismos datos; también se 
				incluye la realización de investigaciones y análisis dirigidos a mejorar nuestros productos, servicios y tecnologías; y, finalmente, la visualización de contenidos y 
				anuncios publicitarios personalizados, según sus intereses y preferencias. 
				También usamos su información personal para comunicarnos con usted. Puede que le enviemos determinadas comunicaciones de servicio obligatorias tal como información acerca de 
				nuestros servicios, promociones y/o anuncios de seguridad. Inclusive en ocasiones podemos enviarle para informarle acerca de nuevos locales adheridos a nuestra red.
				Uso compartido de su información personal
				Salvo lo descrito en esta declaración, sin consentimiento de usted no divulgaremos su información personal fuera de www.quuiko.com y Quuiko App y de sus filiales así como de las 
				empresas que comercializan sus productos a través de la web o locales y marcas adheridos a Quuiko App.
				<br/> 
				<b>Seguridad de su información personal</b><br/>
				www.quuiko.com y Quuiko App se compromenten a proteger la seguridad de la información personal de los usuarios. La aplicación esta alojada en un sitio seguro mediante  
				nuestro host ubicado en USA quienes a su vez ofrecen altos niveles de seguridad. 
				El usuario reconoce y acepta que la sociedad Quuiko SA se reserva la posibilidad de implantar un archivo temporal en el dispositivo móbil para evitar el consumo de 
				datos al usuario y pedirle repetidamente esta información para accesar asi como administrar las versiones del la aplicación instalada en el dispositivo. 
				Utilización de los datos El usuario es informado por las presentes condiciones generales de que los datos de carácter personal señalizados como obligatorios en los 
				formularios y que son recogidos dentro del cuadro del servicio descrito en las presentes condiciones generales, son necesarios para la utilización del servicio 
				y son utilizados únicamente dentro del cuadro de servicio descrito anteriormente y destinados exclusivamente a la sociedad Quuiko S.A y sus locales adheridos que 
				pondrán atención a fin de preservar, en la medida de lo posible, la seguridad de los datos personales. El usuario autoriza a la sociedad Quuiko S.A a 
				proporcionar ciertas informaciones a sus servidores técnicos, con el fin de que el usuario se pueda beneficiar de ciertas funciones de la página Web.
				El usuario autoriza a la sociedad Quuiko S.A a facilitar todas las informaciones que le conciernen a un asociado restaurador de la sociedad Quuiko S.A.
				<br/>
				<b>Advertencia</b>
				El usuario reconoce que, de manera general y teniendo en cuenta las características de la tecnología actual, cada vez que proporciona informaciones personales online, 
				estas informaciones pueden ser recogidas y utilizadas por terceros. En consecuencia, el usuario exime a la sociedad Quuiko S.A de toda responsabilidad o consecuencia 
				dañina derivada de la utilización por parte de terceros, de las informaciones personales intercambiadas a través de los medios de comunicación de Internet 
				(especialmente los foros o anuncios) propuestos por la web www.quuiko.com.
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
