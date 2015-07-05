package com.quuiko.services;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.quuiko.beans.BitacoraVisita;
import com.quuiko.dtos.estadisticas.ClienteFestejado;
import com.quuiko.dtos.estadisticas.ClienteMasVisitas;
import com.quuiko.dtos.estadisticas.ProductoMasPedido;
import com.quuiko.exception.QRocksException;

public interface BitacoraVisitaService {
	
	/**
	 * Metodo que guarda un registro en la bitacora
	 * @param bitacora
	 * @throws QRocksException
	 */
	public void guardar(BitacoraVisita bitacora) throws QRocksException;
	
	/**
	 * Metodo que obtiene todos los registros de las visitas de un negocio
	 * @param idNegocio negocio a consultar
	 * @return
	 * @throws QRocksException
	 */
	public List<BitacoraVisita> consultarBitacoraPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * Obtiene resultados de cualquier busqueda en base a los parametros proporcionados
	 * @param filtro
	 * @return
	 * @throws QRocksException
	 */
	public List<BitacoraVisita> filtrar(BitacoraVisita filtro) throws QRocksException;
	
	/**
	 * Obtiene el numero de clientes que usan la aplicacion y que estan ligados a este restaurante
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public Long consultarNumClientesPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * Obtiene la lista de productos mas pedidos por negocio
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ProductoMasPedido> obtenerEstadisticasProductoMasPedido(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException;
	
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentes(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException;
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @param numRegistrosLimite Numero de registros a mostrar
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentesPaginado(Long idNegocio, Date fechaInicio, Date fechaFin, int numRegistrosLimite) throws QRocksException;
	
	/**
	 * Obtiene la lista de cumpleanieros de la fecha actual
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteFestejado> obtenerClientesCumpleanieros(Long idNegocio) throws QRocksException;
}
