package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.onError;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.quuiko.dtos.NotificationMessage;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.GCMNotificationService;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GCMNotificationServiceImpl implements GCMNotificationService{
	private String API_KEY;
	private int numNotificacionesPorBloque;
	private String iOSkeyS;
	private String iOSp="gyv712";
	private boolean iOSproductionMode;
	
	public void sendNotifications(List<String> gcmIds, NotificationMessage message) throws QRocksException {
		Sender s=new Sender(API_KEY);
		if(isNull(message) || !isValid(message.getTitle()) || !isValid(message.getMessage())){
			onError("Favor de proporcionar el mensaje");
		}
		String titulo=message.getTitle();
		String mensaje=message.getMessage();
		Map<String,String> parameters=message.getParams();
		Message.Builder builder=new Message.Builder();
		builder.timeToLive(86400).delayWhileIdle(true);
		builder.addData("message",mensaje).addData("titleMessage", titulo);
		if(parameters!=null && !parameters.isEmpty()){
			for(String key:parameters.keySet()){
				builder.addData(key, parameters.get(key));
			}
		}
		Message notification=builder.build();
		try {
			MulticastResult r =s.send(notification, gcmIds,0);
			System.out.println("Codigo respuest:"+r.toString());
		} catch (Exception e) {
			e.printStackTrace();
			onError("Error al enviar la notificacion, causado por:"+e.getMessage());
		}
	}
	
	public void sendNotificationsIOS(List<String> gcmIds,NotificationMessage message) throws QRocksException{
		if(isNull(message) || !isValid(message.getTitle()) || !isValid(message.getMessage())){
			onError("Favor de proporcionar el mensaje");
		}
		String titulo=message.getTitle();
		String mensaje=message.getMessage();
		Map<String,String> parameters=message.getParams();
		try {
			PushNotificationPayload payload=PushNotificationPayload.complex();
			payload.addAlert(titulo);
			payload.addSound("default");
			if(parameters!=null && !parameters.isEmpty()){
				for(String key:parameters.keySet()){
					String value=parameters.get(key);
					payload.addCustomDictionary(key, value);
				}
			}
			URL urlK=GCMNotificationService.class.getClassLoader().getResource("/qrocks/services/impl/aps_dev.p12");
			if(urlK==null){
				onError("Error en configuracion de APNS");
			}
//			URL url =  ClassLoader.getSystemResource("myConfig.txt");
            File f=new File(urlK.getFile());
            byte[] kStore=FileUtils.readFileToByteArray(f);
			PushedNotifications notificacionesEnviadas=Push.payload(payload, kStore, iOSp, iOSproductionMode, gcmIds);
			System.out.println("Notificaciones enviadas:"+notificacionesEnviadas);
		}catch(JSONException e) {
			e.printStackTrace();
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String... a){
		List<String> lista=new ArrayList<String>();
		lista.add("aaaaa");
		lista.add("bbbbb");
		lista.add("ccccc");
		lista.add("ddddd");
		lista.add("eeeee");
		lista.add("fffff");
		lista.add("ggggg");
		lista.add("hhhhh");
		lista.add("iiiii");
		int numNotificacionesPorBloque=4;
		int numNotificaciones=lista.size();
		int numBloques=(numNotificaciones/numNotificacionesPorBloque);
		for(int i=0;i<numNotificaciones;i+=numNotificacionesPorBloque){
			List<String> sub=null;
			if((i+numNotificacionesPorBloque)>numNotificaciones){
				sub=lista.subList(i, numNotificaciones);
			}else{
				sub=lista.subList(i, i+numNotificacionesPorBloque);
			}
			System.out.println("********Lista del bloque:"+i+"*********");
			for(String e:sub){
				System.out.println("Elemento:"+e);
			}
		}
	}
	
	public String getAPI_KEY() {
		return API_KEY;
	}
	public void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}
	public int getNumNotificacionesPorBloque() {
		return numNotificacionesPorBloque;
	}
	public void setNumNotificacionesPorBloque(int numNotificacionesPorBloque) {
		this.numNotificacionesPorBloque = numNotificacionesPorBloque;
	}
	public void setiOSkeyS(String iOSkeyS) {
		this.iOSkeyS = iOSkeyS;
	}
	public void setiOSproductionMode(boolean iOSproductionMode) {
		this.iOSproductionMode = iOSproductionMode;
	}
}
