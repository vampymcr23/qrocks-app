package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quuiko.beans.GCMUser;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class GCMDAO extends DAO<GCMUser, String>{
	
	/**
	 * Metodo para registrar un nuevo usuario
	 * @param usuario
	 * @throws QRocksException
	 */
	public GCMUser registrarUsuario(GCMUser usuario) throws QRocksException{
		if(isNull(usuario)){
			onError("Favor de proporcionar los datos del usuario a registrar");
		}
		if(!isValid(usuario.getGcmId()) ){
			onError("Favor de proporcionar su 'google id'");
		}
		GCMUser u=null;
		u= consultarPorId(GCMUser.class, usuario.getGcmId());
		if(u==null){
//			usuario.setNombre("no-name");
			crear(usuario);
			return usuario;
		}else{//Si ya existe, actualiza los datos de este usuario
//			if(isValid(usuario.getEmail())){
//				u.setEmail(usuario.getEmail());
//			}
//			if(usuario.getFechaNacimiento()!=null){
//				u.setFechaNacimiento(usuario.getFechaNacimiento());
//			}
//			if(isValid(usuario.getNombre())){
//				u.setNombre(usuario.getNombre());
//			}
//			if(isValid(usuario.getAlias())){
//				u.setAlias(usuario.getAlias());
//			}
			if(isValid(usuario.getTipoDispositivo())){
				u.setTipoDispositivo(usuario.getTipoDispositivo());
			}
//			if(isValid(usuario.getTelefono())){
//				u.setTelefono(usuario.getTelefono());
//			}
//			boolean aunNoHaActualizadoSusDatos=(( "no-name".equalsIgnoreCase(u.getNombre()) ));
			//Si no tiene un alias el usuario registrado y tampoco la actualizacion entonces se debe de proporcionar (ademas si ya actualizo sus datos y no puso su alias, va a marcar excepcion)
//			if(!isValid(u.getAlias()) && !isValid(usuario.getAlias()) && !aunNoHaActualizadoSusDatos){
//				onError("Favor de proporcionar su alias");
//			}
			actualizar(u);
		}
		return u;
	}
	/**
	 * Consutla el usuario por el id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarUsuario(String id) throws QRocksException{
		if(isNull(id)){
			onError("Favor de proporcionar su id");
		}
		GCMUser usuario=consultarPorId(GCMUser.class, id);
		if(isNull(usuario)){
			onError("El usuario con id:"+id+" no existe!");
		}
		return usuario;
	}
	
	/**
	 * Consulta el usuario GCM mediante el id de google play
	 * @param gcmId
	 * @return
	 * @throws QRocksException
	 */
	public GCMUser consultarPorGCMId(String gcmId) throws QRocksException{
		if(isNull(gcmId)){
			onError("Favor de proporcionar su id de google play");
		}
		GCMUser usuario=new GCMUser();
		usuario.setGcmId(gcmId);
		List<GCMUser> list=filtrar(usuario);
		if(!isEmptyCollection(list)){
			usuario=list.get(0);
		}else{
			onError("El usuario con id:"+gcmId+" no existe en google play");
		}
		return usuario;
	}
	
	/**
	 * Elimina un usuario registrado en la base de datos
	 * @param gcmId
	 * @throws QRocksException
	 */
	public void eliminarRegistroUsuario(String gcmId) throws QRocksException{
		GCMUser usuario=consultarPorGCMId(gcmId);
		eliminar(usuario);
	}
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<GCMUser> filtrar(GCMUser filtro){
		List<GCMUser> lista=new ArrayList<GCMUser>();
		StringBuilder str=new StringBuilder("select g from gcmUser g ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by g.id desc ";
		lista=(List<GCMUser>)consultarPorQuery(query, GCMUser.class,parametros);
		return lista;
	}
	
	public int conteoFiltrar(GCMUser filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(g) from gcmUser g ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by g.id desc ";
		conteo=obtenerConteoPorQuery(query, GCMUser.class, parametros);
		return conteo;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,GCMUser filtro){
		if(isNotNull(filtro)){
			String id=filtro.getId();
//			String email=filtro.getEmail();
			String gcmId=filtro.getGcmId();
			String tipoDispositivo=filtro.getTipoDispositivo();
			if(isValid(id)){
				agregarCondicion(str, " g.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(gcmId)){
					agregarCondicion(str, " g.gcmId = :gcmId ", CondicionLogica.AND);
					parametros.put("gcmId",gcmId);
				}
//				if(isValid(email)){
//					agregarCondicion(str, " g.email = :email ", CondicionLogica.AND);
//					parametros.put("email",email);
//				}
				if(isValid(tipoDispositivo)){
					agregarCondicion(str, " g.tipoDispositivo = :tipoDispositivo ", CondicionLogica.AND);
					parametros.put("tipoDispositivo",tipoDispositivo);
				}
			}
		}
	}
}
