package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.Mesa;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;

public interface MesaService {
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<Mesa> filtrar(Mesa filtro);
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesa filtro);
	
	/**
	 * Obtiene la lista de mesas de acuerdo a ciertos filtros
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesa> filtrarPaginado(Mesa filtro, int inicio,int numRegistros);
	/**
	 * Obtiene la lista de mesas por negocio
	 * @param negocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Negocio negocio) throws QRocksException;
	/**
	 * Obtiene las mesas de un negocio en especifico
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * GUarda o actualiza los datos de una mesa
	 * @param mesa
	 * @throws QRocksException
	 */
	public void guardarMesa(Mesa mesa) throws QRocksException;
	/**
	 * Elimina una mesa por su id
	 * @param idMesa
	 * @throws QRocksException
	 */
	public void eliminarMesa(Long idMesa) throws QRocksException;
	/**
	 * Elimina una mesa por su id
	 * @param mesa
	 * @throws QRocksException
	 */
	public void eliminarMesa(Mesa mesa) throws QRocksException;
	/**
	 * Consultar por id
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorId(Long idMesa) throws QRocksException;
	
	/**
	 * Obtiene la mesa por la clave de la mesa
	 * @param claveMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorClave(String claveMesa) throws QRocksException;
}
