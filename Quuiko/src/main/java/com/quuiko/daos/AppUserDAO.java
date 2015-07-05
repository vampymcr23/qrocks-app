package com.quuiko.daos;

import static com.quuiko.util.Utileria.agregarCondicion;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerQuery;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quuiko.beans.AppUser;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.PasswordEncoderGenerator;
import com.quuiko.util.Utileria.CondicionLogica;

@Component
public class AppUserDAO extends DAO<AppUser,Long>{
	public AppUser guardarUsuario(AppUser user) throws QRocksException{
		if(user==null){
			onError("Favor de proporcionar el usuario");
		}
		if(!isValid(user.getUserEmail())){
			onError("Favor de proporcionar el email del usuario");
		}
		Long id=user.getId();
		if(id==null){
			//Validar que no este repetido el usuario, si si, enviar error
			AppUser currentUser=getUserByUserEmail(user.getUserEmail());
			if(currentUser!=null){
				onError("Ya existe un usuario registrado con este correo!");
			}else{
				user.setEstatus(1);
				String pwd=user.getPwd();
				if(isValid(pwd)){
					String enc=PasswordEncoderGenerator.encode(pwd);
					user.setPwd(enc);
				}
				//TODO Encriptar password
				crear(user);
			}
		}else{
			String pwd=user.getPwd();
			if(isValid(pwd)){
				String enc=PasswordEncoderGenerator.encode(pwd);
				user.setPwd(enc);
			}
			actualizar(user);
		}
		return user;
	}
	
	/**
	 * Actualizar datos del usuario
	 * @param user
	 * @return
	 * @throws QRocksException
	 */
	public AppUser actualizarUsuario(AppUser user) throws QRocksException{
		if(user==null){
			onError("Favor de proporcionar el usuario");
		}
		if(!isValid(user.getUserEmail())){
			onError("Favor de proporcionar el email del usuario");
		}
		AppUser currentUser=getUserByUserEmail(user.getUserEmail());
		if(currentUser==null){
			onError("No existe un usuario con este correo");
		}
		if(user.getAlias()!=null){
			currentUser.setAlias(user.getAlias());
		}
		if(user.getFechaNacimiento()!=null){
			currentUser.setFechaNacimiento(user.getFechaNacimiento());
		}
		if(user.getNombre()!=null){
			currentUser.setNombre(user.getNombre());
		}
		if(user.getTelefono()!=null){
			currentUser.setTelefono(user.getTelefono());
		}
		actualizar(currentUser);
		return currentUser;
	}
	
	/**
	 * Actualizacion de contrasenia
	 * @param email
	 * @param newPassword
	 * @throws QRocksException
	 */
	public void resetPassword(String email, String newPassword) throws QRocksException{
		if(!isValid(email)){
			onError("Favor de proporcionar el email del usuario");
		}
		AppUser currentUser=getUserByUserEmail(email);
		if(currentUser==null){
			onError("No existe un usuario con este correo");
		}
		currentUser.setPwd(newPassword);
		actualizar(currentUser);
		
	}
	
	/**
	 * Consulta un usuario
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public AppUser getUserById(Long id) throws QRocksException{
		if(id==null){
			onError("Favor de proporcionar el id");
		}
		AppUser user=consultarPorId(AppUser.class,id);
		if(user==null){
			onError("No se encontro el usuario");
		}
		return user;
	}
	
	/**
	 * Consulta un usuario por email
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public AppUser getUserByUserEmail(String userEmail) throws QRocksException{
		if(!isValid(userEmail)){
			onError("Favor de proporcionar el email");
		}
		AppUser filtro=new AppUser();
		AppUser user=null;
		filtro.setUserEmail(userEmail);
		List<AppUser> lista=filtrar(filtro);
		if(!isEmptyCollection(lista)){
			user=lista.get(0);
		}
		return user;
	}
	
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<AppUser> filtrar(AppUser filtro){
		List<AppUser> lista=new ArrayList<AppUser>();
		StringBuilder str=new StringBuilder("select a from appUser a ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by a.id desc ";
		lista=(List<AppUser>)consultarPorQuery(query, AppUser.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,AppUser filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String userEmail=filtro.getUserEmail();
			String nombre=filtro.getNombre();
			String telefono=filtro.getTelefono();
			Integer estatus=filtro.getEstatus();
			if(isNotNull(id)){
				agregarCondicion(str, " a.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(userEmail)){
					agregarCondicion(str, " a.userEmail = :userEmail ", CondicionLogica.AND);
					parametros.put("userEmail",userEmail);
				}
				if(isValid(nombre)){
					agregarCondicion(str, " a.nombre = :nombre ", CondicionLogica.AND);
					parametros.put("nombre",nombre);
				}
				if(isValid(telefono)){
					agregarCondicion(str, " a.telefono = :telefono ", CondicionLogica.AND);
					parametros.put("telefono",telefono);
				}
				if(isNotNull(estatus)){
					agregarCondicion(str, " a.estatus = :estatus ", CondicionLogica.AND);
					parametros.put("estatus",estatus);
				}
			}
		}
	}
}
