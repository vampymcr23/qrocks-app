package com.quuiko.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quuiko.daos.EstadisticasDAO;
import com.quuiko.dtos.estadisticas.EstadisticaProducto;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.EstadisticasService;

@Service
public class EstadisticasServiceImpl implements EstadisticasService{
	@Autowired
	private EstadisticasDAO estadisticasDao;
	
	public List<EstadisticaProducto> consultarEstadisticasProducto(Long idNegocio,Date fechaInicio, Date fechaFin, Integer mesInicio, Integer mesFin) throws QRocksException{
		List<EstadisticaProducto>  lista=estadisticasDao.consultarEstadisticasProducto(idNegocio, fechaInicio, fechaFin, mesInicio, mesFin);
		return lista;
	}

}
