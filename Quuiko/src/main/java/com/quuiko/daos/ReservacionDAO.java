package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyArray;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quuiko.beans.Reservacion;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class ReservacionDAO extends DAO<Reservacion,Long>{
	public enum EstatusReservacion{
		PENDIENTE("PEND"),CANCELADA("CAN"),AUTORIZADA("AUT"),CONFIRMADA("CONF"),SIN_ASISTENCIA("N/A"),OK("OK"),RECHAZADA("RECH");
		public String clave;
		private EstatusReservacion(String clave){
			this.clave=clave;
		}
		
		/**
		 * Busca el estatus de reservacion en el enum por la clave de estatus
		 * @param clave
		 * @return
		 */
		public static EstatusReservacion obtenerEstatusReservacion(String clave){
			EstatusReservacion estatus=null;
			for(EstatusReservacion r:EstatusReservacion.values()){
				String e=r.clave;
				if(e.equalsIgnoreCase(clave)){
					estatus=r;
					break;
				}
			}
			return estatus;
		}
	};
	
	public Reservacion guardar(Reservacion reservacion) throws QRocksException{
		if(isNull(reservacion)){
			onError("Favor de proporcionar los datos de la reservacion");
		}
		if(isNull(reservacion.getGcmUser()) || !isValid(reservacion.getGcmUser().getId())){
			onError("Favor de proporcionar su usuario para poder realizar la reservacion");
		}
		if(isNull(reservacion.getNegocio()) || isNull(reservacion.getNegocio().getId())){
			onError("Favor de proporcionar el restaurante donde desea hacer la reservacion");
		}
		if(!isValid(reservacion.getFechaReservacionStr())){
			onError("Favor de proporcionar la fecha de reservacion");
		}
		if(isNull(reservacion.getId())){
			reservacion.setFechaRegistro(new Date());
			reservacion.setEstatus(EstatusReservacion.PENDIENTE.clave);
			crear(reservacion);
		}else{
			actualizar(reservacion);
		}
		return reservacion;
	}
	
	public Reservacion consultarPorId(Long idReservacion) throws QRocksException{
		Reservacion reservacion=null;
		if(isNull(idReservacion)){
			onError("Favor de proporcionar el folio de la reservacion que desea consultar");
		}
		reservacion=consultarPorId(Reservacion.class, idReservacion);
		return reservacion;
	}
	
	public List<Reservacion> filtrar(Reservacion filtro){
		List<Reservacion> lista=new ArrayList<Reservacion>();
		StringBuilder str=new StringBuilder("select r from reservacion r join fetch r.gcmUser join fetch r.negocio join fetch r.gcmUser.appUser ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by r.fechaReservacion asc,r.gcmUser.appUser.nombre asc ";
		lista=(List<Reservacion>)consultarPorQuery(query, Reservacion.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Reservacion filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String gcmId=(filtro.getGcmUser()!=null)?filtro.getGcmUser().getId():null;
			String usuario=(filtro.getGcmUser()!=null && filtro.getGcmUser().getAppUser()!=null)?filtro.getGcmUser().getAppUser().getNombre():null;
			Long idNegocio=(filtro.getNegocio()!=null)?filtro.getNegocio().getId():null;
			Date fechaInicio=filtro.getFechaReservacion();
			Date fechaFin=filtro.getFechaReservacionFin();
			EstatusReservacion[] listaEstatus=filtro.getListaEstatus();
			String estatus=filtro.getEstatus();
			if(isNotNull(id)){
				agregarCondicion(str, " r.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(gcmId)){
					agregarCondicion(str, " r.gcmUser.id=:gcmId ", CondicionLogica.AND);
					parametros.put("gcmId",gcmId);
				}
				if(isValid(usuario)){
					agregarCondicion(str, " r.gcmUser.appUser.nombre like :usuario ", CondicionLogica.AND);
					parametros.put("usuario","%"+usuario+"%");
				}
				if(!isEmptyArray(listaEstatus)){
					List<String> list=new ArrayList<String>();
//					String cadenaEstatus="";
					for(int i=0;i<listaEstatus.length;i++){
						list.add(listaEstatus[i].clave);
//						cadenaEstatus+=listaEstatus[i].clave;
//						if(i<(listaEstatus.length -1)){
//							cadenaEstatus+=",";
//						}
					}
					agregarCondicion(str, " r.estatus in :listaEstatus ", CondicionLogica.AND);
					parametros.put("listaEstatus",list);
				}else if(isValid(estatus)){
					agregarCondicion(str, " r.estatus = :estatus ", CondicionLogica.AND);
					parametros.put("estatus",estatus);
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " r.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
				if(isNotNull(fechaInicio)){
					Calendar c=Calendar.getInstance();
					c.setTime(fechaInicio);
					c.set(Calendar.HOUR,0);
					c.set(Calendar.MINUTE,0);
					c.set(Calendar.SECOND,0);
					c.set(Calendar.MILLISECOND,0);
					fechaInicio=c.getTime();
					agregarCondicion(str, " r.fechaReservacion >= :fechaInicio ", CondicionLogica.AND);
					parametros.put("fechaInicio",fechaInicio);
				}
				
				if(isNotNull(fechaFin)){
					Calendar c=Calendar.getInstance();
					c.setTime(fechaFin);
					c.add(Calendar.DATE, 1);
					c.set(Calendar.HOUR,0);
					c.set(Calendar.MINUTE,0);
					c.set(Calendar.SECOND,0);
					c.set(Calendar.MILLISECOND,0);
					fechaFin=c.getTime();
					agregarCondicion(str, " r.fechaReservacion <= :fechaFin ", CondicionLogica.AND);
					parametros.put("fechaFin",fechaFin);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Reservacion filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(r) from reservacion r join fetch r.gcmUser join fetch r.negocio join fetch r.gcmUser.appUser ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by r.fechaReservacion asc,r.gcmUser.appUser.nombre asc ";
		conteo=obtenerConteoPorQuery(query, Reservacion.class, parametros);
		return conteo;
	}
	
	public List<Reservacion> filtrarPaginado(Reservacion filtro, int inicio,int numRegistros){
		List<Reservacion> lista=new ArrayList<Reservacion>();
		StringBuilder str=new StringBuilder("select r from reservacion r join fetch r.gcmUser join fetch r.negocio join fetch r.gcmUser.appUser ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by r.fechaReservacion asc,r.gcmUser.appUser.nombre asc ";
		lista=consultarPorQueryPaginado(query,Reservacion.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	public List<Reservacion> filtrarPaginado(Reservacion filtro, int inicio,int numRegistros,String orderBy){
		List<Reservacion> lista=new ArrayList<Reservacion>();
		StringBuilder str=new StringBuilder("select r from reservacion r join fetch r.gcmUser join fetch r.negocio join fetch r.gcmUser.appUser ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" "+orderBy;
		lista=consultarPorQueryPaginado(query,Reservacion.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	/**
	 * Metodo que actualiza el estatus de una reservacion
	 * @param idReservacion
	 * @param estatusDestino
	 * @param estatusOrigenes
	 * @throws QRocksException
	 */
	public void actualizarEstatus(Long idReservacion,EstatusReservacion estatusDestino,EstatusReservacion... estatusOrigenes) throws QRocksException{
		if(isNull(idReservacion)){
			onError("Favor de proporcionar el folio de la reservacion que desea actualizar.");
		}
		Reservacion reservacion=consultarPorId(idReservacion);
		if(isNull(reservacion)){
			onError("La reservacion con el folio:"+idReservacion+" no existe.");
		}
		if(isNull(estatusDestino)){
			onError("Favor de indicar el estatus nuevo de la reservacion");
		}
		if(!isEmptyArray(estatusOrigenes)){
			String claveEstatusActual=reservacion.getEstatus();
			boolean estatusValido=false;
			for(EstatusReservacion e:estatusOrigenes){
				if(e.clave.equalsIgnoreCase(claveEstatusActual)){
					estatusValido=true;
					break;
				}
			}
			if(!estatusValido){
				onError("No se puede cambiar el estatus de la reservacion debido al estatus actual en el que se encuentra.");
			}
			reservacion.setEstatus(estatusDestino.clave);
		}
	}
	
	/**
	 * Busca el estatus de reservacion en el enum por la clave de estatus
	 * @param clave
	 * @return
	 */
	private EstatusReservacion obtenerEstatusReservacion(String clave){
		EstatusReservacion estatus=null;
		for(EstatusReservacion r:EstatusReservacion.values()){
			String e=r.clave;
			if(e.equalsIgnoreCase(clave)){
				estatus=r;
				break;
			}
		}
		return estatus;
	}
	
}
