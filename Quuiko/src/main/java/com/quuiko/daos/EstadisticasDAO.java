package com.quuiko.daos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.quuiko.dtos.estadisticas.EstadisticaProducto;
import com.quuiko.exception.QRocksException;
import com.quuiko.util.Utileria;

@Component
public class EstadisticasDAO extends DAO<EstadisticasDAO, Long>{
	/**
	 * Obtiene la lista de estadistica de un producto bajo estos criterios de busqueda.
	 * @param idNegocio El id de negocio debe ser obligatorio.
	 * @param fechaInicio
	 * @param fechaFin
	 * @param mes
	 * @return
	 * @throws QRocksException
	 */
	public List<EstadisticaProducto> consultarEstadisticasProducto(Long idNegocio,Date fechaInicio, Date fechaFin, Integer mesInicio, Integer mesFin) throws QRocksException{
		List<EstadisticaProducto> lista=new LinkedList<EstadisticaProducto>();
		if(idNegocio==null){
			Utileria.onError("Favor de proporcionar el negocio");
		}
		Query q=em.createNamedQuery("estadisticaProductos");
		q.setParameter(1, idNegocio);
		q.setParameter(2, fechaInicio);
		q.setParameter(3, fechaFin);
		q.setParameter(4, mesInicio);
		q.setParameter(5, mesFin);
		lista=q.getResultList();
		return lista;
	}
	
}
