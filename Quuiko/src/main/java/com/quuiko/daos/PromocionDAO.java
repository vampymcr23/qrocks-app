package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Promocion;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria;
import com.quuiko.util.Utileria.CondicionLogica;
@Component
public class PromocionDAO extends DAO<Promocion,Long>{
	
	/**
	 * Registra una nueva promocion o edita una existente
	 * @param promo
	 * @throws QRocksException
	 */
	public void guardar(Promocion promo) throws QRocksException{
		if(isNull(promo) || isNull(promo.getNegocio()) || !isValid(promo.getNombrePromo())){
			onError("Favor de proporcionar todos los datos de la promocion.");
		}
		Date  fechaVigencia=promo.getFechaExp();
		if(isNull(fechaVigencia)){
			onError("Favor de proporcionar una fecha de vigencia");
		}
		
		Long id=promo.getId();
		if(isNull(id)){
			//Si es una nueva promocion, asegurarse que la fecha sea valida
			Date fechaActual=new Date();
			//Si la fecha de vigencia no es superior a la fecha actual, lanzar excepcino
			if(fechaVigencia.before(fechaActual)){
				onError("Favor de proporcionar una fecha de vigencia superior a la fecha actual");
			}
			promo.setNotificacionEnviada(0);
			promo.setEnabled(1);
			crear(promo);
		}else{
			actualizar(promo);
		}
	}
	
	/**
	 * COnsulta una promocion por su id
	 * @param promo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Promocion promo) throws QRocksException{
		if(isNull(promo)){
			onError("Favor de promorcionar la promocion ");
		}
		return consultarPromocion(promo.getId());
	}
	
	/**
	 * Consulta una promocion de su id
	 * @param idPromo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Long idPromo) throws QRocksException{
		if(isNull(idPromo)){
			onError("Favor de proporcionar la promocion");
		}
		Promocion promocion=consultarPorId(Promocion.class,idPromo);
		return promocion;
	}
	
	/**
	 * Activa y desactiva una promocion
	 * @param idPromo
	 * @throws QRocksException
	 */
	public void activarDesactivarPromo(Long idPromo) throws QRocksException{
		if(isNull(idPromo)){
			onError("Favor de promorcionar la promocion a activar/desactivar");
		}
		Promocion promocion=consultarPorId(Promocion.class,idPromo);
		if(isNull(promocion)){
			onError("No existe la promocion con esta clave:"+idPromo);
		}
		Integer enabled=promocion.getEnabled();
		enabled=(enabled!=null  && enabled.intValue()==0)?1:0;
		promocion.setEnabled(enabled);
		guardar(promocion);
	}
	
	/**
	 * Metodo que cambia el estatus de notificacion enviada a la PROMO
	 * @param idPromo
	 * @throws QRocksException
	 */
	public void enviarNotificacionPromocion(Long idPromo) throws QRocksException{
		if(isNull(idPromo)){
			onError("Favor de promorcionar la promocion a activar/desactivar");
		}
		Promocion promocion=consultarPorId(Promocion.class,idPromo);
		if(isNull(promocion)){
			onError("No existe la promocion con esta clave:"+idPromo);
		}
		Integer enviarNotificacion=promocion.getNotificacionEnviada();
		if(enviarNotificacion!=null && enviarNotificacion.intValue()==1){
			onError("Ya se ha enviado la notifiacion anteriormente!");
		}else{
			promocion.setNotificacionEnviada(1);
			guardar(promocion);
		}
		
	}
	
	/**
	 * USADO para aplicacion MOBIL, consultar las promociones activas vigentes de cada restaurante
	 * Consulta todas las promociones activas y vigentes
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocioActivas(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el negocio");
		}
		List<Promocion> lista=new ArrayList<Promocion>();
		Promocion filtro=new Promocion();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		filtro.setEnabled(1);
		Date fechaInicioExpiracion=new Date();
		Calendar cInicio=Calendar.getInstance();
		cInicio.setTime(fechaInicioExpiracion);
		cInicio.set(Calendar.HOUR_OF_DAY, 0);
		cInicio.set(Calendar.MINUTE, 0);
		cInicio.set(Calendar.SECOND, 0);
		fechaInicioExpiracion=cInicio.getTime();
		filtro.setFechaExp(fechaInicioExpiracion);
		Calendar cFin=Calendar.getInstance();
		cFin.setTime(fechaInicioExpiracion);
		cFin.add(Calendar.MONTH,2);
		cFin.set(Calendar.DATE, 1);
		cFin.add(Calendar.DATE, -1);//El ultimo dia del proximo mes
		cFin.set(Calendar.HOUR_OF_DAY,23);
		cFin.set(Calendar.MINUTE,59);
		cFin.set(Calendar.SECOND,59);
		Date fechaFinExp=cFin.getTime();
		filtro.setFechaFinExp(fechaFinExp);//Te trae las promociones cuya fecha de vigencia sea mayor a la fecha actual (osea que aun no vencen)
		lista=filtrar(filtro);
		return lista;
	}
	
	/**
	 * Consulta todas las promociones activas/inactivas de un negocio sin importar si estan vigentes 
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocio(Negocio negocio) throws QRocksException{
		if(isNull(negocio)){
			onError("Favor de proporcionar el negocio");
		}
		List<Promocion> lista=new ArrayList<Promocion>();
		Promocion filtro=new Promocion();
		filtro.setNegocio(negocio);
		lista=filtrar(filtro);
		return lista;
	}
	
	/**
	 * Consulta todas las promociones activas/inactivas de un negocio 
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Promocion> consultarTodasPromocionesPorNegocio(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el negocio");
		}
		List<Promocion> lista=new ArrayList<Promocion>();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		lista=consultarTodasPromocionesPorNegocio(negocio);
		return lista;
	}
	
	/**
	 * USADO para version MOBIL para ver todas las promociones activas vigente sde TODOS los negocios
	 * Obtiene la lista de todas las promociones de todos los negocios 
	 * @return
	 */
	public List<Promocion> consultarTodasPromocionesActivas(){
		List<Promocion> lista=new ArrayList<Promocion>();
		Promocion filtro=new Promocion();
		filtro.setEnabled(1);
		filtro.setFechaExp(new Date());//Te trae las promociones cuya fecha de vigencia sea mayor a la fecha actual (osea que aun no vencen)
		lista=filtrar(filtro);
		return lista;
	}
	
	
	public List<Promocion> filtrar(Promocion filtro){
		List<Promocion> lista=new ArrayList<Promocion>();
		StringBuilder str=new StringBuilder("select p from promocion p ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by p.negocio.nombre asc,p.fechaExp desc,p.id desc ";
		lista=(List<Promocion>)consultarPorQuery(query, Promocion.class,parametros);
		return lista;
	}
	
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,Promocion filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			Integer enabled=filtro.getEnabled();
			Negocio negocio =filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			String nombre=filtro.getNombrePromo();
			Date fechaInicioExp=filtro.getFechaExp();
			Date fechaFinExp=filtro.getFechaFinExp();
			if(isNotNull(id)){
				agregarCondicion(str, " p.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isNotNull(enabled)){
					agregarCondicion(str, " p.enabled=:enabled ", CondicionLogica.AND);
					parametros.put("enabled",enabled);
				}
				if(isValid(nombre)){
					agregarCondicion(str, " UPPER(p.nombrePromo) like :nombre ", CondicionLogica.AND);
					parametros.put("nombre","%"+nombre.toUpperCase()+"%");
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " p.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
				if(isNotNull(fechaInicioExp)){
					Calendar c1=Calendar.getInstance();
					c1.setTime(fechaInicioExp);
					c1.set(Calendar.HOUR,0);c1.set(Calendar.MINUTE,0);c1.set(Calendar.SECOND,0);c1.set(Calendar.MILLISECOND,0);
					agregarCondicion(str, " p.fechaExp >= :fechaInicioExp ", CondicionLogica.AND);
					parametros.put("fechaInicioExp",c1.getTime());
				}
				if(isNotNull(fechaFinExp)){
					Calendar c1=Calendar.getInstance();
					c1.setTime(fechaFinExp);
					c1.set(Calendar.HOUR,0);c1.set(Calendar.MINUTE,0);c1.set(Calendar.SECOND,0);c1.set(Calendar.MILLISECOND,0);
					c1.add(Calendar.DATE,1);//Se le suma 1 para que entre en el rango el ultimo dia de limite
					agregarCondicion(str, " p.fechaExp <= :fechaFinExp ", CondicionLogica.AND);
					parametros.put("fechaFinExp",c1.getTime());
				}
			}
		}
	}
}
