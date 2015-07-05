package com.quuiko.services.impl;

import java.util.Date;

import com.quuiko.services.QRocksKGService;
import com.quuiko.util.Utileria;

public class QRocksKGServiceImpl implements QRocksKGService{

	public String gtQRKGFromBusiness(String bN) {
		Date currentDate=new Date();
		String str=Utileria.parseDateToString(currentDate,"ddMMYYYY");
		String k=bN+"_"+str;
		
		return null;
	}

	public boolean isValidQRKG(String bN, String qrKG) {
		return false;
	}

}
/*

id	Negocio			Forma 		Fecha			Serial							Activo	Pagado
1	Negocio	X		Mensual		01012014		JQJjkxakjsxkjbasjkkJKQH==		-1		1
2	Negocio	X		Mensual		01022014		JQJjkxakjsxkjbasjkkJKQH=z		-1		1
3	Negocio	X		Mensual		01032014		JQJjkxakjsxkjbasjkkJKQH=1		1		1
4	Negocio	X		Mensual		01012014		JQJjkxakjsxkjbasjkkJKQH==		0		0
5	Negocio	X		Mensual		01022014		JQJjkxakjsxkjbasjkkJKQH=z		0		0
6	Negocio	X		Mensual		01032014		JQJjkxakjsxkjbasjkkJKQH=1		0		0



1.- Se inserta el negocio en una nueva tabla de NegocioSerial, se genera el nuevo serial para todos los meses del anio y se pone pagado el primer mes.
2.- La aplicación local intenta conectarse cada  vez que se lanza una peticion desde la mesa de control o login para validar el serial.
3.- El servicio de serial, consulta por nombre clave de negocio, fecha del servidor y que estatus sea pagado, si encuentra alguna llave, entonces
se actualiza la llave del servidor local por la que me regresa la aplicación remota, y se marca como activo en la app. remota.
Si la app. local ya tenia un serial y es diferente al serial que regresa la app. remota, se actualiza el serial local. 
Si la app. remota no regresa ningun serial entonces marcara error ya que no se ha pagado por el servicio.
4.- Si por alguna razón la app.remota no responde, la app.local invocara un servicio interno donde obtendrá el serial por medio de la fecha y solo asi 
podra autenticar al usuario aun y no este disponible el servicio remoto.


*/