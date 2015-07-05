package com.quuiko.actions;


import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.BusinessUser;
import com.quuiko.exception.QRocksException;
import com.quuiko.gcm.service.BusinessUserService;
	
@Namespace("/bu")
@ParentPackage("qrocks-default")
public class BusinessUserAction extends QRocksAction{
	private final String CURRENT_NAMESPACE="/bu";
	private final String ACTION_CONSULTAR_USUARIOS="showAll";
	private final String JSP_CONSULTAR_USUARIOS="/businessUser/bUsers.jsp";
	private final String ACTION_NUEVO_USUARIO="openBU";
	private final String JSP_NUEVO_USUARIO="/businessUser/businessUser.jsp";
	private final String ACTION_GUARDAR_USUARIO="saveBU";
	private final String ACTION_DESACTIVAR_USUARIO="enabledDisabledBU";
	private BusinessUser businessU;
	private List<BusinessUser> businessUsrList=new ArrayList<BusinessUser>();
	
	@Autowired
	private BusinessUserService businessUserService;
	
	@Action(value=ACTION_CONSULTAR_USUARIOS,results={@Result(name=SUCCESS,location=JSP_CONSULTAR_USUARIOS)})
	public String consultarUsuarios(){
		businessUsrList=businessUserService.listaUsuarios();
		return result;
	}
	
	@Action(value=ACTION_NUEVO_USUARIO,results={@Result(name=SUCCESS,location=JSP_NUEVO_USUARIO)})
	public String nuevoUsuario(){
		if(businessU!=null && businessU.getId()!=null){
			try {
				businessU=businessUserService.consultarUsuario(businessU.getId());
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_GUARDAR_USUARIO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName","users","namespace","/console","mensajeExito","${mensajeExito}","mensajeError","${mensajeError}"}),
		@Result(name=INPUT,location=JSP_NUEVO_USUARIO)
	})
	public String guardarUsuario(){
		try {
			businessUserService.guardar(businessU);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_DESACTIVAR_USUARIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","businessUsrList"})
	})
	public String desactivarUsuario(){
		try {
			businessUserService.desactivarUsuario(businessU);
			businessUsrList=businessUserService.listaUsuarios();
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
		
	}

	public List<BusinessUser> getBusinessUsrList() {
		return businessUsrList;
	}

	public void setBusinessUsrList(List<BusinessUser> businessUsrList) {
		this.businessUsrList = businessUsrList;
	}

	public void setBusinessUserService(BusinessUserService businessUserService) {
		this.businessUserService = businessUserService;
	}

	public BusinessUser getBusinessU() {
		return businessU;
	}

	public void setBusinessU(BusinessUser businessU) {
		this.businessU = businessU;
	}
}
