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

import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.BusinessUser;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.NegocioService;
import com.quuiko.util.PasswordEncoderGenerator;
import com.quuiko.util.Utileria.CondicionLogica;

public class BusinessUserDAO extends DAO<BusinessUser,Long>{
	@Autowired
	private NegocioService negocioService;
	
	public void guardar(BusinessUser user) throws QRocksException{
		if(isNull(user)){
			onError("Favor de proporcionar el usuario");
		}
		if(isNull(user.getId())){
			boolean crearNegocio=true;
			if(isValid(user.getUsrType()) && "GV".equals(user.getUsrType())){
				crearNegocio=false;
			}else{
				user.setUsrType("USR");
			}
			user.setEnabled(1);
			user.setCreationDate(new Date());
			String pwd=user.getBusinessKey();
			if(isValid(pwd)){
				String encPwd=PasswordEncoderGenerator.encode(pwd);
				user.setBusinessKey(encPwd);
			}
//			user.setExpirationDate(null);//FIXME Agregar regla posteriormente por fecha de pago.
			crear(user);
			if(crearNegocio){
				Negocio negocio=new Negocio();
				negocio.setActivo(1);
				negocio.setFecha(new Date());
				negocio.setLicencia("");
				negocio.setNombre(user.getName());
				negocio.setSerial("");
				negocio.setUsuario(user.getUsername());
				negocio.setOnline(1);
				negocio.setDescripcion("Descripcion breve del restaurante "+user.getName());
				negocioService.guardarNegocio(negocio);
			}
		}else{
			String pwd=user.getBusinessKey();
			if(isValid(pwd)){
				String encPwd=PasswordEncoderGenerator.encode(pwd);
				user.setBusinessKey(encPwd);
			}
			actualizar(user);
		}
	}
	
	/**
	 * Elimina de la base de datos al usuario
	 * @param username
	 * @throws QRocksException
	 */
	public void eliminarUsuario(String username) throws QRocksException{
		BusinessUser usuario=consultarUsuario(username);
		if(isNull(usuario)){
			onError("No existe el usuario "+username+" para desactivarlo");
		}
		eliminar(usuario);
	}
	
	/**
	 * Desactiva un usuario mediante el username
	 * @param username 
	 * @throws QRocksException se lanza la excepcion cuando no se encuentra al usuario 
	 */
	public void desactivarUsuario(BusinessUser user) throws QRocksException{
		if(isNull(user)){
			onError("Favor de proporcionar el usuario a desactivar");
		}
		desactivarUsuario(user.getUsername());
	}
	
	/**
	 * Activa/Desactiva un usuario mediante el username
	 * @param username 
	 * @throws QRocksException se lanza la excepcion cuando no se encuentra al usuario 
	 */
	public void desactivarUsuario(String username) throws QRocksException{
		BusinessUser usuario=consultarUsuario(username);
		if(isNull(usuario)){
			onError("No existe el usuario "+username+" para desactivarlo");
		}
		Integer enabled=usuario.getEnabled();
		enabled=(enabled!=null && enabled.intValue()==1)?0:1;
		usuario.setEnabled(enabled);
		guardar(usuario);
	}
	
	public List<BusinessUser> listaUsuarios(){
		List<BusinessUser> lista=new ArrayList<BusinessUser>();
		lista=filtrar(null);
		return lista;
	}
	
	/**
	 * Consulta los datos del usuario por el nombre del usuario
	 * @param username
	 * @return
	 * @throws QRocksException
	 */
	public BusinessUser consultarUsuario(String username) throws QRocksException{
		BusinessUser user=null;
		if(!isValid(username)){
			onError("Favor de proporcionar el usuario");
		}
		BusinessUser filtro=new BusinessUser();
		filtro.setUsername(username);
		List<BusinessUser> lista=filtrar(filtro);
		if(!isEmptyCollection(lista)){
			user=lista.get(0);
		}
		return user;
	}
	
	/**
	 * Consulta los datos del usuario por el nombre del usuario
	 * @param username
	 * @return
	 * @throws QRocksException
	 */
	public BusinessUser consultarUsuario(Long id) throws QRocksException{
		BusinessUser user=null;
		if(isNull(id)){
			onError("Favor de proporcionar el usuario");
		}
		BusinessUser filtro=new BusinessUser();
		filtro.setId(id);
		List<BusinessUser> lista=filtrar(filtro);
		if(!isEmptyCollection(lista)){
			user=lista.get(0);
		}
		return user;
	}
	
	public List<BusinessUser> filtrar(BusinessUser filtro){
		List<BusinessUser> lista=new ArrayList<BusinessUser>();
		StringBuilder str=new StringBuilder("select b from businessUser b ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by b.id desc ";
		lista=(List<BusinessUser>)consultarPorQuery(query, BusinessUser.class,parametros);
		return lista;
	}
	
	/**
	 * asigna los parametros al query
	 * @param str
	 * @param parametros
	 * @param filtro
	 */
	private void setParametersToQuery(StringBuilder str,Map<String,Object> parametros,BusinessUser filtro){
		if(isNotNull(filtro)){
			Long id=filtro.getId();
			String username=filtro.getUsername();
			String nombre=filtro.getName();
			Integer enabled=filtro.getEnabled();
			if(isNotNull(id)){
				agregarCondicion(str, " b.id=:id ", CondicionLogica.AND);
				parametros.put("id",id);
			}else{
				if(isValid(username)){
					agregarCondicion(str, " b.username=:username ", CondicionLogica.AND);
					parametros.put("username",username);
				}
				if(isValid(nombre)){
					agregarCondicion(str, " b.name like :nombre ", CondicionLogica.AND);
					parametros.put("nombre","%"+nombre+"%");
				}
				if(isNotNull(enabled)){
					agregarCondicion(str, " b.enabled =:enabled ", CondicionLogica.AND);
					parametros.put("enabled",enabled);
				}
			}
		}
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(BusinessUser filtro){
		int conteo=0;
		StringBuilder str=new StringBuilder("select count(b) from businessUser b ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by b.id desc ";
		conteo=obtenerConteoPorQuery(query, BusinessUser.class, parametros);
		return conteo;
	}
	
	public List<BusinessUser> filtrarPaginado(BusinessUser filtro, int inicio,int numRegistros){
		List<BusinessUser> lista=new ArrayList<BusinessUser>();
		StringBuilder str=new StringBuilder("select b from businessUser b ");
		Map<String,Object> parametros=new HashMap<String, Object>();
		setParametersToQuery(str, parametros, filtro);
		String query=obtenerQuery(str);
		query+=" order by b.id desc ";
		lista=consultarPorQueryPaginado(query,BusinessUser.class,parametros,inicio, numRegistros);
		return lista;
	}
}
