package com.quuiko.services.impl;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.BusinessUser;
import com.quuiko.beans.ContratoView;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.NegocioDAO;
import com.quuiko.dtos.RestauranteUsuarioDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.NegocioService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class NegocioServiceImpl implements NegocioService{
	private static final Logger LOG=Logger.getLogger(NegocioServiceImpl.class.getCanonicalName());
	@Autowired
	private NegocioDAO negocioDao;
	private final static Integer numRegistrosPaginados=5;
	
	/**
	 * Metodo para guardar los datos de un negocio
	 * @param negocio
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED,rollbackFor=SQLException.class)
	public void guardarNegocio(Negocio negocio) throws QRocksException{
		if(isNull(negocio)){
			onError("negocio.error.negocioNull");
		}
		Long id=negocio.getId();
		if(isNull(id)){
			File archivo=negocio.getArchivoImagen();
			if(isNotNull(archivo)){
				byte[] data=obtenerImagenPorArchivo(negocio.getArchivoImagen());
				negocio.setImagen(data);
			}
			negocioDao.crear(negocio);
		}else{
			File archivo=negocio.getArchivoImagen();
			if(isNotNull(archivo)){
				byte[] data=obtenerImagenPorArchivo(negocio.getArchivoImagen());
				negocio.setImagen(data);
			}else{
				Negocio anterior=consultarPorId(negocio.getId());
				byte[] data=anterior.getImagen();
				negocio.setImagen(data);
			}
			try{
				negocioDao.actualizar(negocio);
			}catch(Exception e){
				LOG.log(Level.SEVERE, e.getMessage());
				Utileria.onError("No se logro actualizar la informacion correctamente.");
			}
		}
	}
	
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
//	public void actualizarPlayList(Negocio negocio) throws QRocksException{
//		if(isNull(negocio) || isNull(negocio.getId())){
//			onError("negocio.error.negocioNull");
//		}
//		if(isNull(negocio.getPlaylist()) || !isValid(negocio.getPlayListName())){
//			onError("Favor de proporcioanr el nombre de la lista");
//		}
//		Negocio n=consultarPorId(negocio.getId());
//		n.setPlaylist(negocio.getPlaylist());
//		n.setPlayListName(negocio.getPlayListName());
//		negocioDao.actualizar(n);
//	}
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
//	public void actualizarUserPlayList(Negocio negocio) throws QRocksException{
//		if(isNull(negocio) || isNull(negocio.getId())){
//			onError("negocio.error.negocioNull");
//		}
//		if(isNull(negocio.getUserPlaylistId()) || !isValid(negocio.getUserPlaylistName())){
//			onError("Favor de proporcioanr el nombre de la lista");
//		}
//		Negocio n=consultarPorId(negocio.getId());
//		n.setUserPlaylistId(negocio.getUserPlaylistId());
//		n.setUserPlaylistName(negocio.getUserPlaylistName());
//		negocioDao.actualizar(n);
//	}
	/**
	 * Metodo para eliminar un negocio por medio de su id
	 * @param idNegocio
	 * @throws QRocksException
	 */
	public void eliminarNegocio(Long idNegocio) throws QRocksException{
		Negocio negocio=consultarPorId(idNegocio);
		if(isNull(negocio)){
			onError("");
		}
		negocioDao.eliminar(negocio);
	}
	/**
	 * Elimina un negocio, cuya propiedad id no debe ser nula
	 * @param negocio
	 * @throws QRocksException
	 */
	public void eliminarNegocio(Negocio negocio) throws QRocksException{
		if(isNull(negocio)){
			onError("");
		}
		eliminarNegocio(negocio.getId());
	}
	/**
	 * Consulta el negocio por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public Negocio consultarPorId(Long id)  throws QRocksException{
		return negocioDao.consultarPorId(Negocio.class,id);
	}
	
	/**
	 * Consulta un negocio por el usuario
	 * @param usuario
	 * @return
	 * @throws QRocksException
	 */
	public Negocio consultarPorUsuario(String usuario) throws QRocksException{
		Negocio negocio=null;
		if(!isValid(usuario)){
			onError("Favor de proporcionar el usuario");
		}
		Negocio filtro=new Negocio();
		filtro.setUsuario(usuario);
		List<Negocio> negocios=negocioDao.filtrar(filtro);
		negocio=(isEmptyCollection(negocios))?null:negocios.get(0);
		if(isNull(negocio)){
			onError("No existe el negocio del usuario:"+usuario);
		}
		return negocio;
	}
	/**
	 * Consulta todos los negocios existentes
	 * @return
	 */
	public List<Negocio> consultarTodos(){
		List<Negocio> lista=filtrar(null);
		return lista;
	}
	
	public List<Negocio> consultarNegociosActivos(){
		Negocio filtro=new Negocio();
		filtro.setActivo(1);
		List<Negocio> lista=filtrar(filtro);
		return lista;
	}
	
	public List<Negocio> consultarNegociosActivosPaginados(int paginaActual){
		Negocio filtro=new Negocio();
		filtro.setActivo(1);
		List<Negocio> lista=filtrarPaginado(filtro, paginaActual, numRegistrosPaginados);
		return lista;
	}
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @return
	 */
	public List<Negocio> filtrar(Negocio filtro){
		return negocioDao.filtrar(filtro);
	}
	
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Negocio> filtrarPaginado(Negocio filtro,int inicio, int numRegistros){
		return negocioDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Negocio filtro){
		return negocioDao.conteoDetallePorBusqueda(filtro);
	}
	
	public List<ContratoView> consultarVista(){
		return negocioDao.consultarVista();
	}
	
	/**
	 * Obtiene el ultimo negocio
	 * @return
	 */
	public Negocio obtenerMiNegocio(){
		Negocio negocio=null;
		List<Negocio> lista=consultarTodos();
		if(!isEmptyCollection(lista)){
			negocio=lista.get(0);
		}
		return negocio;
	}
	
	public InputStream obtenerImagenProducto(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			//FIXME
			onError("");
		}
		Negocio negocio=consultarPorId(idNegocio);
		InputStream stream=null;
		byte[] data=negocio.getImagen();
		if(data!=null){
			try {
				File tmp=File.createTempFile("_"+System.currentTimeMillis(), ".png");
				FileUtils.writeByteArrayToFile(tmp, data);
				stream=new FileInputStream(tmp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stream;
	}
	
	private byte[] obtenerImagenPorArchivo(File archivo) throws QRocksException{
		if(isNull(archivo)){
			onError("");
		}
		byte[] data=null;
		try {
			data=FileUtils.readFileToByteArray(archivo);
			if(data==null){
				onError("");
			}
		}catch(IOException e){
			onError("");
		}
		return data;
	}
	
	/**
	 * Actualiza el contexto de persistencia
	 */
	public void flushCache(){
		negocioDao.flushCache();
	}
	/**
	 * Obtiene la lista de restaurantes considerando si el usuario que lo consulta esta o no registrado.
	 * @param gcmId
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<RestauranteUsuarioDTO> consultarRestaurantesUsuario(String gcmId,String nombreRestaurante,String tipoComida,int inicio){
		return negocioDao.consultarRestaurantesUsuario(gcmId,nombreRestaurante,tipoComida, inicio, numRegistrosPaginados);
	}
	
	public void setNegocioDao(NegocioDAO negocioDao) {
		this.negocioDao = negocioDao;
	}
}
