package com.quuiko.services;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.quuiko.beans.ContratoView;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.dtos.RestauranteUsuarioDTO;
import com.quuiko.exception.QRocksException;

public interface NegocioService {
	/**
	 * Metodo para guardar los datos de un negocio
	 * @param negocio
	 * @throws QRocksException
	 */
	public void guardarNegocio(Negocio negocio) throws QRocksException;
	/**
	 * Metodo para eliminar un negocio por medio de su id
	 * @param idNegocio
	 * @throws QRocksException
	 */
	public void eliminarNegocio(Long idNegocio) throws QRocksException;
	/**
	 * Elimina un negocio, cuya propiedad id no debe ser nula
	 * @param negocio
	 * @throws QRocksException
	 */
	public void eliminarNegocio(Negocio negocio) throws QRocksException;
	/**
	 * Consulta el negocio por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public Negocio consultarPorId(Long id)  throws QRocksException;
	
	/**
	 * Consulta un negocio por el usuario
	 * @param usuario
	 * @return
	 * @throws QRocksException
	 */
	public Negocio consultarPorUsuario(String usuario) throws QRocksException;
	
	/**
	 * Consulta todos los negocios existentes
	 * @return
	 */
	public List<Negocio> consultarTodos();
	
	/**
	 * Obtiene todos los restaurantes que se encuentran activos
	 * @return
	 */
	public List<Negocio> consultarNegociosActivos();
	
	/**
	 * Obtiene todos los restaurantes que se encuentran activos pero con resultados paginados
	 * @param paginaActual
	 * @return
	 */
	public List<Negocio> consultarNegociosActivosPaginados(int paginaActual);
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @return
	 */
	public List<Negocio> filtrar(Negocio filtro);
	/**
	 * Obtiene la lista de negocios en base al criterio de busqueda "filtro"
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Negocio> filtrarPaginado(Negocio filtro,int inicio, int numRegistros);
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Negocio filtro);
	
	public List<ContratoView> consultarVista();
	
	/**
	 * Obtiene el ultimo negocio
	 * @return
	 */
	public Negocio obtenerMiNegocio();
	/**
	 * Obtiene la image del negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public InputStream obtenerImagenProducto(Long idNegocio) throws QRocksException;
	
	/**
	 * Actualiza el contexto de persistencia
	 */
	public void flushCache();
	
	/**
	 * Obtiene la lista de restaurantes considerando si el usuario que lo consulta esta o no registrado.
	 * @param gcmId
	 * @param inicio
	 * @return
	 */
	public List<RestauranteUsuarioDTO> consultarRestaurantesUsuario(String gcmId,String nombreRestaurante,String tipoComida,int inicio);
	
//	public void actualizarPlayList(Negocio negocio) throws QRocksException;
	
//	public void actualizarUserPlayList(Negocio negocio) throws QRocksException;
}
