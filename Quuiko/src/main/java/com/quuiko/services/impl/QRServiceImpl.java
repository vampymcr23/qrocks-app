package com.quuiko.services.impl;
import static com.quuiko.util.Utileria.*;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.QRService;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRServiceImpl implements QRService{
	private String QR_PATH=null;
	

	public InputStream generateQR(String carpeta,String QRFileName, StringBuilder content) throws QRocksException {
//		String rutaDirectorio=servletContextPath+File.separator+QR_PATH+File.separator+carpeta;
		String rutaDirectorio=QR_PATH;
		boolean directorioQRValido=false;
		if(isValid(rutaDirectorio)){
			directorioQRValido=true;
			File directorioEvento=new File(rutaDirectorio);
			if(!directorioEvento.exists()){
				boolean creado=directorioEvento.mkdir();
				if(!creado){
					onError("Error al crear el directorio:"+rutaDirectorio);
				}
			}
		}
		boolean hasBeenCreated=false;
		ByteArrayOutputStream out=QRCode.from(content.toString()).to(ImageType.PNG).stream();
//		File file=new File(PATH+File.separator+QRFileName);
		long num=Calendar.getInstance().getTimeInMillis();
		File file=null;
		if(directorioQRValido){
			file=new File(rutaDirectorio+File.separator+QRFileName);
		}else{
			try {
				file=File.createTempFile(num+"_",QRFileName);
			} catch (IOException e) {
				onError("Error al crear el archivo QR por permisos de escritura en temporales");
			}
		}
		FileInputStream fi=null;
		try{	
			FileOutputStream fout=new FileOutputStream(file);
			fout.write(out.toByteArray());
			fout.flush();
			fout.close();
			fi=new FileInputStream(file);
			hasBeenCreated=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return fi;
	}
	
	public static void main(String... a) throws QRocksException{
		QRService qrService=new QRServiceImpl();
		StringBuilder str=new StringBuilder("");
		str.append("http://quuiko.com");
		String QRFileName="quuiko.jpg";
		qrService.generateQR("quuiko",QRFileName, str);
	}

	public String getQR_PATH() {
		return QR_PATH;
	}

	public void setQR_PATH(String qR_PATH) {
		QR_PATH = qR_PATH;
	}
}
