package com.quuiko.actions.mobileactions;

import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.AppUser;
import com.quuiko.dtos.AppUserDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AppUserService;
import com.quuiko.util.PasswordEncoderGenerator;
import com.quuiko.util.Utileria;

@Namespace("/m/droid/appusr")
@ParentPackage("qrocks-default")
public class MobileAppUserAction extends QRocksAction{
	private static final String ACTION_REGISTRAR_USUARIO="newAppUsr";
	private static final String ACTION_CONSULTAR_USUARIO="gtAppUsr";
	private static final String ACTION_ACTUALIZAR_USUARIO="updateAppUsr";
	private static final String ACTION_AUTH="auth";
	
	@Autowired
	private AppUserService appUserService;
	private AppUser appUser;
	private AppUserDTO appUserDto;
	private String userEmail;
	private String userPwd;
	private String birthDay;
	
	@Action(value=ACTION_REGISTRAR_USUARIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"}),
		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"})
	})
	public String guardarUsuario(){
		appUserDto=new AppUserDTO();
		try {
			appUserService.guardarUsuario(appUser);
			appUserDto.setAppUser(appUser);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, appUserDto);
		}
		return result;
	}
	
	@Action(value=ACTION_ACTUALIZAR_USUARIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"}),
		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"})
	})
	public String actualizarUsuario(){
		appUserDto=new AppUserDTO();
		try {
			if(birthDay!=null && appUser!=null){
				try{
					Date fechaNacimiento=(birthDay!=null)?Utileria.castToDate(birthDay):null;
					if(fechaNacimiento==null){
						throw new QRocksException("Formato de la fecha debe ser: dd/mm/aaaa");
					}
					appUser.setFechaNacimiento(fechaNacimiento);
				}catch(Exception e){
					throw new QRocksException("Formato de la fecha debe ser: dd/mm/aaaa");
				}
			}
			appUser=appUserService.actualizarUsuario(appUser);
			appUserDto.setAppUser(appUser);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, appUserDto);
		}
		return result;
	}
	
	@Action(value=ACTION_CONSULTAR_USUARIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"}),
		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"})
	})
	public String consultarUsuario(){
		appUserDto=new AppUserDTO();
		try {
			Long id=(appUser!=null)?appUser.getId():null;
			String userEmail=(appUser!=null)?appUser.getUserEmail():null;
			if(id!=null){
				appUser=appUserService.getUserById(id);
			}else{
				appUser=appUserService.getUserByUserEmail(userEmail);
			}
			appUserDto.setAppUser(appUser);
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, appUserDto);
		}
		return result;
	}
	
	@Action(value=ACTION_AUTH,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"}),
		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","appUserDto"})
	})
	public String autenticar(){
		appUserDto=new AppUserDTO();
		try {
			if(userEmail==null || !Utileria.isValid(userEmail)){
				Utileria.onError("Favor de proporcionar su email para autenticarse");
			}
			if(userPwd==null || !Utileria.isValid(userPwd)){
				Utileria.onError("Favor de proporcionar su clave para autenticarse");
			}
			appUser=appUserService.getUserByUserEmail(userEmail);
			if(appUser!=null){
				String currentEncodedPwd=appUser.getPwd();
				boolean isCorrect=PasswordEncoderGenerator.matches(userPwd, currentEncodedPwd);
				if(isCorrect){
					appUserDto.setRequestCode(200);
				}else{
					Utileria.onError("Sus credenciales son incorrectas");	
				}
			}else{
				Utileria.onError("No se encontro un usuario con el correo proporcionado");
			}
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, appUserDto);
		}
		return result;
	}
	
	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public AppUserDTO getAppUserDto() {
		return appUserDto;
	}

	public void setAppUserDto(AppUserDTO appUserDto) {
		this.appUserDto = appUserDto;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
}
