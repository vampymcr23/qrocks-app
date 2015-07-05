package com.quuiko.actions.mobileactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.AppUser;
import com.quuiko.beans.GCMUser;
import com.quuiko.dtos.mobile.GCMUserDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AppUserService;
import com.quuiko.services.GCMService;
import com.quuiko.util.GCMAuthenticationUtil;
import com.quuiko.util.Utileria;

@Namespace("/m/droid/gcm/u")
public class MobileGCMUsersAction extends QRocksAction{
	private static final String ACTION_REGISTRAR_USUARIO="regUsr";
	private static final String ACTION_CONSULTAR_USUARIO="gtUsr";
	private static final String ACTION_ELIMINAR_USUARIO="removeUsr";
	private static final String ACTION_MOSTRAR_USUARIOS="gtAllUsr";
	private static final String ACTION_ENVIAR_NOTIFICACION="sndNotify";
	private static final String ACTION_REGISTRAR_VISITA_USUARIO_NEGOCIO="regUsrNeg";
	private String gcmId;
	private String usrEmail;
	private String usrName;
	private String usrBirthDay;
	private String usrPhone;
	private String usrAlias;
	private String regId;
	private String device;
	private GCMUser gUser;
	private GCMUserDTO userDTO;
	@Autowired
	private GCMService gcmService;
	@Autowired
	private AppUserService appUserService;
	
	
	@Action(value=ACTION_REGISTRAR_USUARIO,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userDTO"})
	})
	public String registrarUsuario(){
		userDTO=new GCMUserDTO();
		try {
			gUser=new GCMUser();
			gUser.setId(regId);
			gUser.setGcmId(regId);
			AppUser appUser=null;
			if(usrEmail!=null){
				appUser=appUserService.getUserByUserEmail(usrEmail);
			}
			GCMUser usuarioActual=null;
			try{
				usuarioActual=gcmService.consultarPorGCMId(regId);
				if(appUser==null && usuarioActual!=null && usuarioActual.getAppUser()!=null){
					appUser=usuarioActual.getAppUser();
//					gUser.setEmail(appUser.getUserEmail());
					usrEmail=appUser.getUserEmail();
				}
			}catch(QRocksException e){
			}
			//Si no existe, entonces se asignan valores default
			if(usuarioActual==null){
//				gUser.setEmail("no-email@quicko.com");
//				gUser.setNombre("no-name");
				gUser.setFechaCreacion(new Date());
				gUser.setTipoDispositivo(device);
			}else{
				//Si existe, se valida si mandan una propiedad, si no tiene valor, entonces se pone el valor default.
//				if(!Utileria.isValid(usrEmail)){
//					gUser.setEmail(usuarioActual.getEmail());
//				}else{
//					gUser.setEmail(usrEmail);
//				}
//				if(usrBirthDay!=null){
//					try{
//						Date fechaNacimiento=(usrBirthDay!=null)?Utileria.castToDate(usrBirthDay):null;
//						if(fechaNacimiento==null){
//							throw new QRocksException("Formato de la fecha debe ser: dd/mm/aaaa");
//						}
//						gUser.setFechaNacimiento(fechaNacimiento);
//					}catch(Exception e){
//						throw new QRocksException("Formato de la fecha debe ser: dd/mm/aaaa");
//					}
//				}else{
//					gUser.setFechaNacimiento(usuarioActual.getFechaNacimiento());
//				}
//				if(Utileria.isValid(usrName)){
//					gUser.setNombre(usrName);
//				}else{
//					gUser.setNombre(usuarioActual.getNombre());
//				}
//				if(Utileria.isValid(usrAlias)){
//					gUser.setAlias(usrAlias);
//				}else{
//					gUser.setAlias(usuarioActual.getAlias());
//				}
//				if(Utileria.isValid(usrPhone)){
//					gUser.setTelefono(usrPhone);
//				}else{
//					gUser.setTelefono(usuarioActual.getTelefono());
//				}
				if(Utileria.isValid(device)){
					gUser.setTipoDispositivo(device);
				}else{
					gUser.setTipoDispositivo(usuarioActual.getTipoDispositivo());
				}
			}
			gUser=gcmService.registrarUsuario(gUser);
			gUser.setAppUser(appUser);
			//Si no tiene un email ni nombre, se va a solicitar que se registre
//			if(("no-name".equalsIgnoreCase(gUser.getNombre()) )){
//				userDTO.setRequiereRegistro(1);
//			}
			gUser.setFechaCreacion(null);
			userDTO.setUsuario(gUser);
		} catch (QRocksException e) {
			System.out.println(e.getMessage());
			onErrorAndroidMessage(e, userDTO);
		}
		return result;
	}
	
	@Action(value=ACTION_CONSULTAR_USUARIO,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userDTO"})
	})
	public String consultarUsuario(){
		userDTO=new GCMUserDTO();
		try {
			GCMUser usuario=gcmService.consultarPorGCMId(gcmId);
			userDTO.setUsuario(usuario);
		} catch (QRocksException e) {
			if(gcmId!=null && Utileria.isValid(gcmId)){
				userDTO.setRequestCode(100);//Codigo para agregar el dispositivo
			}
//			onErrorAndroidMessage(e, userDTO);
		}
		return result;
	}
	
	@Action(value=ACTION_ELIMINAR_USUARIO,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userDTO"})
	})
	public String eliminarUsuario(){
		userDTO=new GCMUserDTO();
		try {
			gcmService.eliminarRegistroUsuario(regId);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, userDTO);
		}
		return result;
	}
	
	@Action(value=ACTION_ENVIAR_NOTIFICACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","userDTO"})
	})
	public String enviarNotificacion(){
		String token;
		try {
			token = GCMAuthenticationUtil.getToken("vampy.mcr.23@gmail.com","vampygina2323");
			System.out.println("TOken:"+token);
			String API_KEY="AIzaSyDY4TSMqi8Dw0wX3eCVfzj4SZlPcM-PP9M";
			Sender s=new Sender(API_KEY);
			List<String> regIds=new ArrayList<String>();
			regIds.add(regId);
			Message message=new Message.Builder().timeToLive(30).delayWhileIdle(true).addData("message","QRocks test!").build();
			com.google.android.gcm.server.Result r =s.send(message, regId, 1);
			System.out.println("Codigo respuesta:"+r.toString());
//			int code=GCMMessageUtil.sendMessage(API_KEY, regId, "QRocks test!");
//			System.out.println("Codigo respuesta:"+code);
			userDTO=new GCMUserDTO();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public String getUsrEmail() {
		return usrEmail;
	}

	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public GCMUser getgUser() {
		return gUser;
	}

	public void setgUser(GCMUser gUser) {
		this.gUser = gUser;
	}

	public GCMUserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(GCMUserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrBirthDay() {
		return usrBirthDay;
	}

	public void setUsrBirthDay(String usrBirthDay) {
		this.usrBirthDay = usrBirthDay;
	}

	public String getUsrPhone() {
		return usrPhone;
	}

	public void setUsrPhone(String usrPhone) {
		this.usrPhone = usrPhone;
	}

	public String getUsrAlias() {
		return usrAlias;
	}

	public void setUsrAlias(String usrAlias) {
		this.usrAlias = usrAlias;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
}
