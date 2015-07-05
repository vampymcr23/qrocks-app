package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.GCMUser;
import com.quuiko.beans.Reservacion;
import com.quuiko.daos.ReservacionDAO.EstatusReservacion;
import com.quuiko.dtos.ReservacionCombo;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ReservacionService;
import com.quuiko.util.Utileria;

@Namespace("/m/droid/reserv")
@ParentPackage("qrocks-default")
public class MobileReservacionAction extends QRocksAction{
	private static final String ACTION_MIS_RESERVACIONES="showMyReservations";
	private static final String ACTION_NUEVA_RESERVACION="newReservation";
	private static final String ACTION_GUARDAR_RESERVACION="saveReservation";
	private static final String ACTION_MARCAR_NOTIFICACION_AUTORIZACION_RECIBIDA="markAsReadAutNotif";
	private static final String ACTION_ACTUALIZAR_CONFIRMACION_RESERVACION="confirmReservation";
	private static final String ACTION_CANCELAR_RESERVACION="cancelReservation";
	private static final String ACTION_CONSULTAR_RESERVACION="openReservation";
	private List<ReservacionCombo> comboDias =new ArrayList<ReservacionCombo>();
	private Reservacion reservacion;
	private List<Reservacion> misReservaciones=new ArrayList<Reservacion>();
	private String gcmId;
	private Long businessId;
	private Integer puedeReservar;
	private Reservacion ultimaReservacion;
	private Long idReservacion;
	private Integer asistir;
	
	@Autowired
	private ReservacionService reservacionService;
	
	/**
	 * Llena la lista de comboDias en base a la fecha actual de la fecha mas los 7 dias en que puede reservar.
	 * @return
	 */
	@Action(value=ACTION_CONSULTAR_RESERVACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageResult,messageCode,reservacion\\..*","excludeProperties","reservacion\\.negocio\\.imagen"})
	})
	public String consultarReservacion(){
		try {
			reservacion=reservacionService.consultarPorId(idReservacion);
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	/**
	 * Llena la lista de comboDias en base a la fecha actual de la fecha mas los 7 dias en que puede reservar.
	 * @return
	 */
	@Action(value=ACTION_MIS_RESERVACIONES,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","comboDias\\[\\d+\\]\\..*,misReservaciones\\[\\d+\\]\\..*","excludeProperties","misReservaciones\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String misReservaciones(){
		llenarComboDias();
		Reservacion filtro=new Reservacion();
		GCMUser user=new GCMUser();
		user.setId(gcmId);
		filtro.setGcmUser(user);
		misReservaciones=reservacionService.filtrarPaginadoOrderBy(filtro, 0,20,"order by r.fechaReservacion desc, r.id desc, r.negocio.nombre asc");
		return result;
	}

	@Action(value=ACTION_NUEVA_RESERVACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","comboDias\\[\\d+\\]\\..*,puedeReservar,ultimaReservacion\\..*","excludeProperties","ultimaReservacion\\.negocio\\.imagen"})
	})
	public String nuevaReservacion(){
		try {
			List<Reservacion> reservaciones=reservacionService.obtenerUltimaReservacionPorNegocioUsuario(businessId, gcmId);
			if(!isEmptyCollection(reservaciones)){
				Date fechaActual=new Date();
				boolean esMismoDia=false;
				boolean puedeReservar=true;
				for(Reservacion r:reservaciones){
					Date fechaReservacion=r.getFechaReservacion();
					String estatus=r.getEstatus();
					esMismoDia=!(Utileria.compararFechas(fechaReservacion, fechaActual, true) != 0 );//Si hay una reservacion para el dia de hoy entonces no puede reservar
					//Si no puede reservar es por que en alguna de las reservaciones tiene fecha para hoy y no puede volver a reservar.
					//Si ademas de que hay una reservacion para el mismo dia, se revisa que este autorizada o confirmada, de lo contrario no puede volver a reservar
					if(esMismoDia && (EstatusReservacion.AUTORIZADA.clave.equals(estatus) || EstatusReservacion.CONFIRMADA.clave.equals(estatus))){
						ultimaReservacion=r;
						puedeReservar=false;
						break;
					}
				}
				this.puedeReservar=(puedeReservar)?1:0;
			}else{
				this.puedeReservar=1;
			}
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		llenarComboDias();
		return result;
	}
	
	@Action(value=ACTION_ACTUALIZAR_CONFIRMACION_RESERVACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageCode"})
	})
	public String confirmarAsistencia(){
		if(isNotNull(idReservacion)){
			try {
				Reservacion reservacion=reservacionService.consultarPorId(idReservacion);
				if(asistir==1){
					reservacion.setEstatus(EstatusReservacion.CONFIRMADA.clave);
				}else if(asistir==0){
					reservacion.setEstatus(EstatusReservacion.CANCELADA.clave);
				}
				reservacionService.actualizar(reservacion);
				onSuccessMessage("Se ha confirmado su asistencia.");
			} catch (QRocksException e) {
			}
		}
		return result;
	}
	
	@Action(value=ACTION_CANCELAR_RESERVACION,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageCode"})
		})
		public String cancelarReservacoin(){
			if(isNotNull(idReservacion)){
				try {
					Reservacion reservacion=reservacionService.consultarPorId(idReservacion);
					reservacion.setEstatus(EstatusReservacion.CANCELADA.clave);
					reservacionService.actualizar(reservacion);
					onSuccessMessage("Se ha cancelado su reservacion.");
				} catch (QRocksException e) {
				}
			}
			return result;
		}
	
	private void llenarComboDias(){
		Calendar c=Calendar.getInstance();
//		c.add(Calendar.DATE, 1);
		Calendar cLimite=Calendar.getInstance();
		cLimite.add(Calendar.DATE, 7);//7 dias de anticipacion para reservar
		Date fechaInicio=c.getTime();
		Date fechaFin=cLimite.getTime();
		Date fechaN=c.getTime();
		while(compararFechas(fechaFin, fechaN, true) >=0 ){
			String etiqueta=null;
			Calendar cN=Calendar.getInstance();
			cN.setTime(fechaN);
			String fechaString=obtenerSoloFecha(fechaN);
			int mes=cN.get(Calendar.MONTH)+1;
			int dia=cN.get(Calendar.DATE);
			String diaStr=(dia<10)?"0"+dia:String.valueOf(dia);
			int diaSemana=cN.get(Calendar.DAY_OF_WEEK);
			String diaSemanaStr=null;
			switch(diaSemana){
				case 1:
					diaSemanaStr="Domingo";
				break;
				case 2:
					diaSemanaStr="Lunes";
				break;
				case 3:
					diaSemanaStr="Martes";
				break;
				case 4:
					diaSemanaStr="Miércoles";
				break;
				case 5:
					diaSemanaStr="Jueves";
				break;
				case 6:
					diaSemanaStr="Viernes";
				break;
				case 7:
					diaSemanaStr="Sábado";
				break;
				default:
					diaSemanaStr="Sábado";
				break;
			}
			String mesX=(mes<10)?"0"+mes:String.valueOf(mes);
			String mesStr=obtenerEtiquetaMes(mes);
			etiqueta=diaSemanaStr+" "+diaStr+"/"+mesX+"/"+cN.get(Calendar.YEAR); 
			ReservacionCombo combo=new ReservacionCombo();
			combo.setEtiqueta(etiqueta);
			combo.setValor(fechaString);
			comboDias.add(combo);
			cN.add(Calendar.DATE, 1);//Se incrementa la fecha en uno.
			fechaN=cN.getTime();
		}
	}
	
	private String obtenerEtiquetaMes(int mes){
		String etiqueta="";
		String[] meses={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		for(int i=0;i<meses.length;i++){
			if(mes==(i+1)){
				etiqueta=meses[i];
				break;
			}
		}
		return etiqueta;
	}
	
	
	@Action(value=ACTION_GUARDAR_RESERVACION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageCode,messageResult,reservacion\\..*","excludeProperties","reservacion\\.negocio\\.imagen"})
	})
	public String guardarReservacion(){
		try {
			reservacionService.guardar(reservacion);
			onSuccessMessage("Se ha enviado su reservacion al restaurante. Espere su autorizacion.");
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_MARCAR_NOTIFICACION_AUTORIZACION_RECIBIDA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageCode,messageResult"})
	})
	public String marcarComoNotificacionAutorizacionRecibida(){
		try {
			reservacionService.marcarNotificacionAutorizacionRecibida(reservacion.getId());
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	

	public List<ReservacionCombo> getComboDias() {
		return comboDias;
	}

	public void setComboDias(List<ReservacionCombo> comboDias) {
		this.comboDias = comboDias;
	}

	public Reservacion getReservacion() {
		return reservacion;
	}

	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

	public List<Reservacion> getMisReservaciones() {
		return misReservaciones;
	}

	public void setMisReservaciones(List<Reservacion> misReservaciones) {
		this.misReservaciones = misReservaciones;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setReservacionService(ReservacionService reservacionService) {
		this.reservacionService = reservacionService;
	}

	public Integer getPuedeReservar() {
		return puedeReservar;
	}

	public void setPuedeReservar(Integer puedeReservar) {
		this.puedeReservar = puedeReservar;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Reservacion getUltimaReservacion() {
		return ultimaReservacion;
	}

	public void setUltimaReservacion(Reservacion ultimaReservacion) {
		this.ultimaReservacion = ultimaReservacion;
	}

	public Long getIdReservacion() {
		return idReservacion;
	}

	public void setIdReservacion(Long idReservacion) {
		this.idReservacion = idReservacion;
	}

	public Integer getAsistir() {
		return asistir;
	}

	public void setAsistir(Integer asistir) {
		this.asistir = asistir;
	}
}
