package com.quuiko.actions;

import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isValid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.GCMUserNegocio;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.Promocion;
import com.quuiko.dtos.NotificationMessage;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.GCMNotificationService;
import com.quuiko.services.GCMUserNegocioService;
import com.quuiko.services.PromocionService;

@Namespace("/promo")
@ParentPackage("qrocks-default")
public class PromocionAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MesaControlAction.class);
	private final static String CURRENT_NAMESPACE="/promo";
	private final static String ACTION_CONSULTAR_PROMOS="gtAll";
	private final static String ACTION_VER_PROMO="openPromo";
	private final static String ACTION_ACTIVAR_DESACTIVAR_PROMO="disabledPromo";
	private final static String ACTION_GUARDAR_PROMO="savePromo";
	private final static String PAGINA_CONSULTAS="/promociones/promos.jsp";
	private final static String PAGINA_VER_PROMO="/promociones/verPromo.jsp";
	private final static String ACTION_ENVIAR_NOTIFICACION="sendNotif";
	private final static String ACTION_ELIMINAR_PROMO="dltPromo";
	
	private File fileUploaded;
	private String contentType;
    private String filename;
	
	private Promocion promo;
	private List<Promocion> promociones=new ArrayList<Promocion>();
	@Autowired
	private PromocionService promocionService;
	@Autowired
	private GCMUserNegocioService gcmUsuarioNegocioService;
	@Autowired
	private GCMNotificationService gcmNotificationService;
	
	@Action(value=ACTION_CONSULTAR_PROMOS,results={
		@Result(name=SUCCESS,location=PAGINA_CONSULTAS)
	})
	public String consultarPromos(){
		Negocio negocio=getNegocioLogeado();
		if(negocio!=null){
			try {
				promociones=promocionService.consultarTodasPromocionesPorNegocio(negocio);
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}else{
			promociones=promocionService.consultarTodasPromocionesActivas();
		}
		return result;
	}
	
	@Action(value=ACTION_VER_PROMO,results={
		@Result(name=SUCCESS,location=PAGINA_VER_PROMO)
	})
	public String abrirPromo(){
		if(promo!=null && promo.getId()!=null){
			try {
				promo=promocionService.consultarPromocion(promo);
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_ELIMINAR_PROMO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","promociones"}),
		@Result(name=INPUT,type="json",params={"ignoreHierarchy","false","noCache","true","root","promociones"})
	})
	public String eliminarPromo(){
		if(promo!=null && promo.getId()!=null){
			try {
				promocionService.eliminarPromo(promo.getId());
				Negocio negocio=getNegocioLogeado();
				if(negocio!=null){
					promociones=promocionService.consultarTodasPromocionesPorNegocio(negocio);
				}
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	public void validateGuardarPromo(){
		validar(promo);
	}
	
	@Action(value=ACTION_GUARDAR_PROMO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_CONSULTAR_PROMOS,"namespace",CURRENT_NAMESPACE,"messageResult","${messageResult}","messageCode","${messageCode}"}),
		@Result(name=INPUT,location=PAGINA_VER_PROMO)
	})
	public String guardarPromo(){
		try {
			Negocio negocio=getNegocioLogeado();
			promo.setNegocio(negocio);
			promocionService.guardar(promo);
			onSuccessMessage("Se ha agregado la nueva promocion:"+promo.getNombrePromo()+"");
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_ACTIVAR_DESACTIVAR_PROMO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","promociones"})
	})
	public String consultarPromociones(){
		if(promo!=null && promo.getId()!=null){
			try {
				promocionService.activarDesactivarPromo(promo.getId());
				Negocio negocio=getNegocioLogeado();
				if(negocio!=null){
					try {
						promociones=promocionService.consultarTodasPromocionesPorNegocio(negocio);
					} catch (QRocksException e) {
						onErrorMessage(e);
					}
				}else{
					promociones=promocionService.consultarTodasPromocionesActivas();
				}
			} catch (QRocksException e) {
				log.warn(e);
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	@Action(value=ACTION_ENVIAR_NOTIFICACION,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_CONSULTAR_PROMOS,"namespace",CURRENT_NAMESPACE,"mensajeExito","${mensajeExito}","mensajeError","${mensajeError}"}),
		@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_CONSULTAR_PROMOS,"namespace",CURRENT_NAMESPACE,"mensajeExito","${mensajeExito}","mensajeError","${mensajeError}"})
	})
	public String enviarNotificacion(){
		try {
			Negocio negocio=getNegocioLogeado();
			if(negocio!=null && negocio.getId()!=null && promo!=null){
				int numRegPorBloque=gcmNotificationService.getNumNotificacionesPorBloque();
				int totalUsuarios=gcmUsuarioNegocioService.conteoUsuariosPorNegocioParaNotificaciones(negocio.getId());
				promo=promocionService.consultarPromocion(promo.getId());
				String tituloNotificacion=promo.getNombrePromo();
				String contenidoNotificacion=promo.getDescripcionPromo();
				NotificationMessage notificacion=new NotificationMessage();
				notificacion.setMessage(contenidoNotificacion);
				notificacion.setTitle(tituloNotificacion);
				Map<String,String> params=new HashMap<String, String>();
				params.put("qrIdPromo",promo.getId().toString());
				params.put("qrBKEY",negocio.getId()+"");
				params.put("qrBName",negocio.getNombre());
				params.put("title",tituloNotificacion);
				notificacion.setParams(params);
				int numPaginas=(totalUsuarios/numRegPorBloque);
				if(totalUsuarios % numRegPorBloque >0){
					numPaginas++;
				}
				for(int pagina=0;pagina<numPaginas;pagina++){
					int registroInicio=(pagina*numRegPorBloque);
					List<GCMUserNegocio> usuarios=gcmUsuarioNegocioService.obtenerUsuariosPorNegocioParaNotificacionesPaginado(negocio.getId(), registroInicio, numRegPorBloque);
					if(!isEmptyCollection(usuarios)){
						List<String> gcmIds=new ArrayList<String>();
						List<String> tokens=new ArrayList<String>();
						for(GCMUserNegocio u:usuarios){
							String gcmId=(u!=null && u.getGcmUser()!=null)?u.getGcmUser().getGcmId():null;
							String tipoDispositivo=(u!=null && u.getGcmUser()!=null && isValid(u.getGcmUser().getTipoDispositivo()))?u.getGcmUser().getTipoDispositivo():null;
							if(isValid(gcmId)){
								if("android".equalsIgnoreCase(tipoDispositivo)){
									gcmIds.add(gcmId);
								}else if("iOS".equalsIgnoreCase(tipoDispositivo)){
									tokens.add(gcmId);
								}
							}
						}
						if(!isEmptyCollection(tokens)){
							gcmNotificationService.sendNotificationsIOS(tokens, notificacion);
						}
						if(!isEmptyCollection(gcmIds)){
							gcmNotificationService.sendNotifications(gcmIds, notificacion);
						}
					}
				}
				promocionService.enviarNotificacionPromocion(promo.getId());
			}
		} catch (QRocksException e) {
			log.warn(e);
			onErrorMessage(e);
		}
		return result;
	}

	public Promocion getPromo() {
		return promo;
	}

	public void setPromo(Promocion promo) {
		this.promo = promo;
	}

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}

	public void setPromocionService(PromocionService promocionService) {
		this.promocionService = promocionService;
	}

	public void setGcmUsuarioNegocioService(
			GCMUserNegocioService gcmUsuarioNegocioService) {
		this.gcmUsuarioNegocioService = gcmUsuarioNegocioService;
	}

	public void setGcmNotificationService(
			GCMNotificationService gcmNotificationService) {
		this.gcmNotificationService = gcmNotificationService;
	}

	public File getFileUploaded() {
		return fileUploaded;
	}

	public void setFileUploaded(File fileUploaded) {
		this.fileUploaded = fileUploaded;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
