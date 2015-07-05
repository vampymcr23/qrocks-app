<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
	
	function buscar(artista){
		var artista=$('#artista').val();
		$.ajax({
			data:{"q":artista},
			url:"http://grooveshark.com/more.php?getResultsFromSearch",
			dataType:"json",
			async:false,
			type:"POST",
			success:function(results){
				alert("Resultado:"+results);
			}
		});
	}
	
	function obtenerLLave(){
		var id="35336393";
		$.ajax({
			data:{"method":"getStreamKeysFromSongIDs","parameters":{"type":0,"mobile":false,"country":{"CC4":0,"CC2":0,"ID":152,"DMA":0,"CC3":8388608,"IPR":0,"CC1":0},"prefetch":false,"songIDs":[35336393,12238296,34894273]},"header":{"country":{"CC4":0,"CC2":0,"ID":152,"DMA":0,"CC3":8388608,"IPR":0,"CC1":0},"session":"ff0532b250040c534229b93d42f881ea","uuid":"CD5E8FB2-6C61-4188-A0CB-21F565D3F2D3","clientRevision":"20130520","client":"jsqueue","privacy":0,"token":"c8b1f11c57cfc7064c57be0b8c14ff837f584fde68f1eb"}},
			url:"http://grooveshark.com/more.php?getStreamKeysFromSongIDs",
			dataType:"xml",
			async:false,
			type:"POST",
			success:function(llave){
				alert("Resultado:"+llave);
			}
		});
	}
</script>
</head>
<body>
<form id="searchForm">
	<label>Artista:</label><input type="text" id="artista" name="q" />
	<input type="button" value="Buscar" onclick="buscar();"/>
</form>
<form action="http://grooveshark.com/more.php?getStreamKeysFromSongIDs" method="POST">
	<input type="hidden" name="method" value="getStreamKeysFromSongIDs">
	<label>Id de la cancion:</label><input type="text" name="" value="35336393"/>
	<input type="button" value="Buscar" onclick="obtenerLLave(this.value);"/>
	<input type="submit" value="Submit">
</form>
</body>
</html>