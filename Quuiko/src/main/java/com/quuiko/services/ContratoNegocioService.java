package com.quuiko.services;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.util.Date;
import java.util.List;

import com.quuiko.beans.ContratoNegocio;
import com.quuiko.exception.QRocksException;

public interface ContratoNegocioService {
	/**
	 * GUarda o actualiza un contrato
	 * @param contrato
	 * @throws QRocksException
	 */
	public void guardarContrato(ContratoNegocio contrato) throws QRocksException;
	
	/**
	 * Consulta un contrato por su id
	 * @param id
	 * @return
	 * @throws QRocksException
	 */
	public ContratoNegocio consultarContrato(Long id) throws QRocksException;
	
	/**
	 * Habilita - deshabilita un contrato
	 * @param id
	 * @throws QRocksException
	 */
	public void habilitarDeshabilitarContrato(Long id) throws QRocksException;
	
	/**
	 * Permite filtrar los registros de la tabla de lote de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<ContratoNegocio> filtrar(ContratoNegocio filtro);
	
	/**
	 * Consulta los contratos por un negocio
	 * @param idNegocio
	 * @return
	 */
	public List<ContratoNegocio> consultarFiltrosDeUnNegocio(Long idNegocio);
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(ContratoNegocio filtro);
	
	/**
	 * Filtra los resultados de forma paginada
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<ContratoNegocio> filtrarPaginado(ContratoNegocio filtro, int inicio,int numRegistros);
}
