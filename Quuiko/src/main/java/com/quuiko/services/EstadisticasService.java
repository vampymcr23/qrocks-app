package com.quuiko.services;

import java.util.Date;
import java.util.List;

import com.quuiko.dtos.estadisticas.EstadisticaProducto;
import com.quuiko.exception.QRocksException;

public interface EstadisticasService {
	/**
	 * Obtiene la lista de estadistica de un producto bajo estos criterios de busqueda.
	 * @param idNegocio El id de negocio debe ser obligatorio.
	 * @param fechaInicio
	 * @param fechaFin
	 * @param mes
	 * @return
	 * @throws QRocksException
	 */
	public List<EstadisticaProducto> consultarEstadisticasProducto(Long idNegocio,Date fechaInicio, Date fechaFin, Integer mesInicio, Integer mesFin) throws QRocksException;
}
