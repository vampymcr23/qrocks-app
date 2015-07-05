package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quuiko.beans.BitacoraVisita;
import com.quuiko.beans.GCMUser;
import com.quuiko.beans.GCMUserNegocio;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.BitacoraVisitaService;
import com.quuiko.services.GCMService;
import com.quuiko.services.NegocioService;
import com.quuiko.util.Utileria;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class GCMUserNegocioDAO extends DAO<GCMUserNegocio,Long>{
	private static final Logger log=Logger.getLogger(GCMUserNegocioDAO.class.getName());
	@Autowired
	private GCMService gcmService;
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private BitacoraVisitaService bitacoraVisitaService;
	private static Logger logger=Logger.getLogger(GCMUserNegocioDAO.class.getName());
	/**
	 * GUarda o actualiza los datos de este usuario en relacion a un negocio/restaurante en base al id del usuario y del negocio
	 * @param usuarioNegocio
	 * @throws QRocksException
	 */
	public GCMUserNegocio registrarVisita(String gcmId,Long idNegocio) throws QRocksException{
		Negocio negocio=negocioService.consultarPorId(idNegocio);
		GCMUser usr=gcmService.consultarUsuario(gcmId);
		GCMUserNegocio  usuario=null;
		try{
			usuario=obtenerUsuarioNegocio(gcmId, idNegocio);
		}catch(QRocksException e){
			logger.log(Level.WARNING,e.getMessage());
		}
		//Si ya existia una visita a este restaurante con este usuario, solo se actualiza la fecha de la ultima visita
		if(usuario!=null){
			Date fechaUltimaVisita=usuario.getUltimaVisita();
			int numVisitas=(usuario.getNumVisitas()!=null)?usuario.getNumVisitas():0;
			//Se busca si tiene una ultima visita
			if(fechaUltimaVisita!=null){
				Date fechaActual=new Date();
				int comparacion=Utileria.compararFechas(fechaUltimaVisita, fechaActual, true);
				//Si la fecha de la ultima visita es diferente a la fecha actual entonces significa que es una nueva visita 
				if(comparacion!=0){
					usuario.setUltimaVisita(new Date());
					usuario.setNumVisitas(++numVisitas);
					actualizar(usuario);
					registrarEnBitacora(negocio, usr);
				}
			}else{//Si no tiene una ultima fecha de visita, entonces se asigna una y el num. de visitas es 1 (la primera vez)
				usuario.setUltimaVisita(new Date());
				usuario.setNumVisitas(1);
				actualizar(usuario);
				registrarEnBitacora(negocio, usr);
			}
		}else{
			usuario=new GCMUserNegocio();
			usuario.setFechaCreacion(new Date());
			usuario.setNegocio(negocio);
			usuario.setGcmUser(usr);
			usuario.setRecibirNotificacion(1);
			usuario.setNumVisitas(1);
			usuario.setUltimaVisita(new Date());
			if(usr!=null && usr.getAppUser()!=null && usr.getAppUser().getId()!=null){
				usuario.setAppUser(usr.getAppUser());
			}
			crear(usuario);
			registrarEnBitacora(negocio, usr);
		}
		return usuario;
	}
	
	private GCMUserNegocio ligarUsuarioNegocio(String gcmId,Long idNegocio) throws QRocksException{
		Negocio negocio=negocioService.consultarPorId(idNegocio);
		GCMUser usr=gcmService.consultarUsuario(gcmId);
		GCMUserNegocio  usuario=null;
		//Si no existe el usuario, entonces se va a ligar con el negocio
		usuario=new GCMUserNegocio();
		usuario.setFechaCreacion(new Date());
		usuario.setNegocio(negocio);
		usuario.setGcmUser(usr);
		usuario.setRecibirNotificacion(1);
		if(usr!=null && usr.getAppUser()!=null && usr.getAppUser().getId()!=null){
			usuario.setAppUser(usr.getAppUser());
		}
//			usuario.setNumVisitas(1);
//			usuario.setUltimaVisita(new Date());
		crear(usuario);
//			registrarEnBitacora(negocio, usr);
		return usuario;
	}
	
	private void registrarEnBitacora(Negocio negocio,GCMUser usr) throws QRocksException{
		BitacoraVisita bitacora=new BitacoraVisita();
		bitacora.setNegocio(negocio);
		bitacora.setUser(usr);
		bitacora.setFechaVisita(new Date());
		if(usr!=null && usr.getAppUser()!=null && usr.getAppUser().getId()!=null){
			bitacora.setAppUser(usr.getAppUser());
		}
		bitacoraVisitaService.guardar(bitacora);
	}
	
	/**
	 * Activa o desactiva el envio de las notificaciones a los dispositivos mediante el codigo del usuario.
	 * Si se proporcionar el idNegocio, solo se activa/desactiva para este negocio (se ignora el parametro "activar"), y 
	 * cambia el valor de recibir notificaciones a la inversa (si es 0, lo cambia a 1, si es 1 lo cambia a 0).
	 * Pero si NO se proporciona el idNegocio entonces se considera el parametro "activar" y todos los registros que coincidan con
	 * este usuario en todos los restaurantes se activara o desactivara dependiendo del valor de activar (0 desactivar| 1 activar)
	 * @param gcmId
	 * @param idNegocio
	 * @param activar
	 * @throws QRocksException Se lanza excepcion si no se encuentra el usuario con este id proporcionado
	 */
	public void activarDesactivarNotificaciones(String gcmId,Long idNegocio, Integer activar) throws QRocksException{
		if(!isValid(gcmId)){
			onError("Favor de proporcionar el usuario");
		}
		GCMUser usr=gcmService.consultarPorGCMId(gcmId);
		GCMUserNegocio filtro=new GCMUserNegocio();
		filtro.setGcmUser(usr);
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		List<GCMUserNegocio> lista=filtrar(filtro);
		boolean unSoloNegocio=(idNegocio!=null);//Si se va a desactivar solamente un negocio
		if(!isEmptyCollection(lista)){
			for(GCMUserNegocio un:lista){
				//Si solo se requiere deshabilitar un solo negocio, entonces dependiendo de lo que traiga el negocio, lo aplica a la inversa
				if(unSoloNegocio){
					int recibirNotificacion=un.getRecibirNotificacion();
					recibirNotificacion=(recibirNotificacion==0)?1:0;
					un.setRecibirNotificacion(recibirNotificacion);
				}else{//Sino, entonces dependiendo de la activacion, se va a habilitar TODOS o deshabilitar TODOS.
					un.setRecibirNotificacion(activar);
				}
				actualizar(un);
			}
		}else{//Si viene vacia la lista de negocio-usuario, significa que no sea ha registrado
			ligarUsuarioNegocio(gcmId, idNegocio);
		}
	}
	
	/**
	 * GUarda o actualiza los datos de este usuario en relacion a un negocio/restaurante
	 * @param usuarioNegocio
	 * @throws QRocksException
	 */
	public void guardar(GCMUserNegocio usuarioNegocio) throws QRocksException{
		if(isNull(usuarioNegocio)){
			onError("Favor de proporcionar el usuario");
		}
		if(usuarioNegocio.getId()==null){
			usuarioNegocio.setFechaCreacion(new Date());
			crear(usuarioNegocio);
		}else{
			usuarioNegocio.setUltimaVisita(new Date());
			actualizar(usuarioNegocio);
		}
	}
	
	/**
	 * Obtiene el registro de la ultima visita de un usuario con un restaurante que esta visitando.
	 * @param gcmId
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public GCMUserNegocio obtenerUsuarioNegocio(GCMUserNegocio user) throws QRocksException{
		if(isNull(user)){
			onError("Favor de proporcionar los datos del usuario");
		}
		Negocio negocio=user.getNegocio();
		GCMUser usuario=user.getGcmUser();
		if(isNull(negocio) || isNull(negocio.getId())){
			onError("Favor de indicar el restaurante");
		}
		if(isNull(usuario) || !isValid(usuario.getId())){
			onError("Favor de proporcionar el usuario");
		}
		return obtenerUsuarioNegocio(usuario.getId(),negocio.getId());
	}
	
	/**
	 * Obtiene la ultima vez que un usuario visito este negocio. 
	 * @param gcmId
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public GCMUserNegocio obtenerUsuarioNegocio(String gcmId,Long idNegocio) throws QRocksException{
		GCMUserNegocio user=null;
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		GCMUser usuario=new GCMUser();
		usuario.setId(gcmId);
		GCMUserNegocio usuarioNegocio=new GCMUserNegocio();
		usuarioNegocio.setGcmUser(usuario);
		usuarioNegocio.setNegocio(negocio);
		List<GCMUserNegocio> lista=filtrar(usuarioNegocio);
		if(!isEmptyCollection(lista)){
			user=lista.get(0);
		}else{
			user=ligarUsuarioNegocio(gcmId, idNegocio);
//			onError("El usuario proporcionado no ha visitado al negocio con clave :"+idNegocio);
		}
		return user;
	}
	
	/**
	 * Obtiene la lista de usuarios que han visitado este negocio y han usado la aplicacion
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<GCMUserNegocio> obtenerUsuariosPorNegocio(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		GCMUserNegocio filtro=new GCMUserNegocio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		List<GCMUserNegocio> lista=filtrar(filtro);
		return lista;
	}
	
	/**
	 * Obtiene la lista de usuarios por negocio que pueden recibir notificaciones.
	 * Este metodo se utilizara al momento de enviar notificaciones.
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificaciones(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		GCMUserNegocio filtro=new GCMUserNegocio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setRecibirNotificacion(1);
		List<GCMUserNegocio> lista=filtrar(filtro);
		return lista;
	}
	
	/**
	 * Obtiene la lista de usuarios por negocio que pueden recibir notificaciones.
	 * Este metodo se utilizara al momento de enviar notificaciones.
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<GCMUserNegocio> obtenerUsuariosPorNegocioParaNotificacionesPaginado(Long idNegocio, int inicio,int numRegistros) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar la clave del negocio");
		}
		GCMUserNegocio filtro=new GCMUserNegocio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		filtro.setRecibirNotificacion(1);
		List<GCMUserNegocio> lista=filtrarPaginado(filtro,inicio,numRegistros);
		return lista;
	}
	
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<GCMUserNegocio> filtrarPaginado(GCMUserNegocio filtro, int inicio,int numRegistros){
		List<GCMUserNegocio> lista=new ArrayList<GCMUserNegocio>();
		StringBuilder str=new StringBuilder("select nu from gcmUserNegocio nu ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by nu.ultimaVisita desc ";
		lista=consultarPorQueryPaginado(query,GCMUserNegocio.class,parametros,inicio, numRegistros);
		return lista;
	}
	
	public Integer conteoUsuariosPorNegocioParaNotificaciones(Long idNegocio) throws QRocksException{
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(nu) from gcmUserNegocio nu ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		GCMUserNegocio filtro=new GCMUserNegocio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setRecibirNotificacion(1);
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		conteo=obtenerConteoPorQuery(query, GCMUserNegocio.class, parametros);
		return conteo;
	}
	
	public List<GCMUserNegocio> filtrar(GCMUserNegocio filtro){
		List<GCMUserNegocio> lista=new ArrayList<GCMUserNegocio>();
		StringBuilder str=new StringBuilder("select nu from gcmUserNegocio nu ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by nu.ultimaVisita desc ";
		lista=(List<GCMUserNegocio>)consultarPorQuery(query, GCMUserNegocio.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,GCMUserNegocio filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			GCMUser usuario=filtro.getGcmUser();
			String gcmId=(usuario!=null)?usuario.getId():null;
			Long idAppUser=(filtro.getAppUser()!=null)?filtro.getAppUser().getId():null;
			String userEmail=(filtro.getAppUser()!=null)?filtro.getAppUser().getUserEmail():null;
			Negocio negocio=filtro.getNegocio();
			Long idNegocio=(negocio!=null)?negocio.getId():null;
			Integer recibirNotificaciones=filtro.getRecibirNotificacion();
			if(isNotNull(id)){
				agregarCondicion(str, " un.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(gcmId)){
					agregarCondicion(str, " nu.gcmUser.id =:gcmId ", CondicionLogica.AND);
					parametros.put("gcmId",gcmId);
				}
				if(isValid(userEmail)){
					agregarCondicion(str, " nu.appUser.userEmail =:userEmail ", CondicionLogica.AND);
					parametros.put("userEmail",userEmail);
				}
				if(isNotNull(idAppUser)){
					agregarCondicion(str, " nu.appUser.id=:idAppUser ", CondicionLogica.AND);
					parametros.put("idAppUser",idAppUser);
				}
				if(isNotNull(idNegocio)){
					agregarCondicion(str, " nu.negocio.id=:idNegocio ", CondicionLogica.AND);
					parametros.put("idNegocio",idNegocio);
				}
				if(isNotNull(recibirNotificaciones)){
					agregarCondicion(str, " nu.recibirNotificacion =:recibirNotificaciones", CondicionLogica.AND);
					parametros.put("recibirNotificaciones",recibirNotificaciones);
				}
			}
		}
	}

	public void setGcmService(GCMService gcmService) {
		this.gcmService = gcmService;
	}
}