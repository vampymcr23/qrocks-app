package com.quuiko.actions.mobileactions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.GCMUserNegocio;
import com.quuiko.beans.Mesa;
import com.quuiko.dtos.mobile.GCMUserNegocioDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.GCMUserNegocioService;
import com.quuiko.services.MesaService;


@Namespace("/m/droid/gcm/un")
public class MobileGCMUsuarioNegocioAction  extends QRocksAction{
	private static final String ACTION_REGISTRAR_USUARIO_RESTAURANTE="regUsrNeg";
	private static final String ACTION_ACTIVAR_DESACTIVAR_NOTIFICACIONES="enabledNotif";
	private String gcmId;
	private Long idNegocio;
	private String claveMesa;
	private String gcmEmail;
	private GCMUserNegocio gcmUsuarioNegocio;
	private GCMUserNegocioDTO gcmUserNegocioDTO;
	private Integer activar;
	@Autowired
	private GCMUserNegocioService gcmUsuarioNegocioService;
	@Autowired
	private MesaService mesaService;
	
	/**
	 * Action para registrar al usuario que ya visito a este restaurante
	 * @return
	 */
	@Action(value=ACTION_REGISTRAR_USUARIO_RESTAURANTE,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","gcmUserNegocioDTO"})
	})
	public String registrarVisitaAlRestaurante(){
		GCMUserNegocio usuarioNegocio=new GCMUserNegocio();
		try {
			//Si no proporciona la llave del negocio, entonces se localiza por la clave de la mesa.
			if(idNegocio==null){
				Mesa mesa=mesaService.obtenerMesaPorClave(claveMesa);
				idNegocio=(mesa!=null && mesa.getNegocio()!=null)?mesa.getNegocio().getId():null;
			}
			usuarioNegocio=gcmUsuarioNegocioService.registrarVisita(gcmId, idNegocio);
			gcmUserNegocioDTO=new GCMUserNegocioDTO();
			usuarioNegocio.setFechaCreacion(null);
			usuarioNegocio.setUltimaVisita(null);
			usuarioNegocio.getGcmUser().setFechaCreacion(null);
			usuarioNegocio.getNegocio().setArchivoImagen(null);
			usuarioNegocio.getNegocio().setFecha(null);
			usuarioNegocio.getNegocio().setImagen(null);
			gcmUserNegocioDTO.setUsuarioNegocio(usuarioNegocio);
			
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, gcmUserNegocioDTO);
		}
		return result;
	}
	
	@Action(value=ACTION_ACTIVAR_DESACTIVAR_NOTIFICACIONES,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","gcmUserNegocioDTO"})
	})
	public String activarDesactivarNotificaciones(){
		try {
			GCMUserNegocio usuarioNegocio=gcmUsuarioNegocioService.obtenerUsuarioNegocio(gcmId, idNegocio);
			gcmUsuarioNegocioService.activarDesactivarNotificaciones(gcmId, idNegocio, activar);
			gcmUserNegocioDTO=new GCMUserNegocioDTO();
			usuarioNegocio.setFechaCreacion(null);
			usuarioNegocio.setUltimaVisita(null);
			usuarioNegocio.getGcmUser().setFechaCreacion(null);
			usuarioNegocio.getNegocio().setArchivoImagen(null);
			usuarioNegocio.getNegocio().setFecha(null);
			usuarioNegocio.getNegocio().setImagen(null);
			gcmUserNegocioDTO.setUsuarioNegocio(usuarioNegocio);
			
		} catch (QRocksException e) {
			onErrorAndroidMessage(e, gcmUserNegocioDTO);
		}
		return result;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public GCMUserNegocio getGcmUsuarioNegocio() {
		return gcmUsuarioNegocio;
	}

	public void setGcmUsuarioNegocio(GCMUserNegocio gcmUsuarioNegocio) {
		this.gcmUsuarioNegocio = gcmUsuarioNegocio;
	}

	public GCMUserNegocioDTO getGcmUserNegocioDTO() {
		return gcmUserNegocioDTO;
	}

	public void setGcmUserNegocioDTO(GCMUserNegocioDTO gcmUserNegocioDTO) {
		this.gcmUserNegocioDTO = gcmUserNegocioDTO;
	}

	public Integer getActivar() {
		return activar;
	}

	public void setActivar(Integer activar) {
		this.activar = activar;
	}

	public void setGcmUsuarioNegocioService(
			GCMUserNegocioService gcmUsuarioNegocioService) {
		this.gcmUsuarioNegocioService = gcmUsuarioNegocioService;
	}

	public String getGcmEmail() {
		return gcmEmail;
	}

	public void setGcmEmail(String gcmEmail) {
		this.gcmEmail = gcmEmail;
	}

	public String getClaveMesa() {
		return claveMesa;
	}

	public void setClaveMesa(String claveMesa) {
		this.claveMesa = claveMesa;
	}

	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}
}
