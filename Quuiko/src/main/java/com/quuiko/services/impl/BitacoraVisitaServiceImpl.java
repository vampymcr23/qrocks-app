package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.BitacoraVisita;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.BitacoraVisitaDAO;
import com.quuiko.dtos.estadisticas.ClienteFestejado;
import com.quuiko.dtos.estadisticas.ClienteMasVisitas;
import com.quuiko.dtos.estadisticas.ProductoMasPedido;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.BitacoraVisitaService;

public class BitacoraVisitaServiceImpl implements BitacoraVisitaService{
	Logger log=Logger.getLogger(BitacoraVisitaServiceImpl.class.getCanonicalName());
	
	@Autowired
	private BitacoraVisitaDAO bitacoraVisitaDao;
	/**
	 * Metodo que guarda un registro en la bitacora
	 * @param bitacora
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardar(BitacoraVisita bitacora) throws QRocksException{
		if(bitacora==null){
			onError("Favor de proporcionar los datos para guardar en la bitacora");
		}
		if(isNull(bitacora.getNegocio()) || isNull(bitacora.getNegocio().getId())){
			onError("Favor de proporcionar el negocio de visita");
		}
		if(isNull(bitacora.getUser()) || !isValid(bitacora.getUser().getGcmId())){
			onError("Favor de proporcionar el usuario");
		}
		bitacoraVisitaDao.crear(bitacora);
	}
	
	/**
	 * Metodo que obtiene todos los registros de las visitas de un negocio
	 * @param idNegocio negocio a consultar
	 * @return
	 * @throws QRocksException
	 */
	public List<BitacoraVisita> consultarBitacoraPorNegocio(Long idNegocio) throws QRocksException{
		List<BitacoraVisita> lista=new ArrayList<BitacoraVisita>();
		BitacoraVisita filtro=new BitacoraVisita();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		lista=bitacoraVisitaDao.filtrar(filtro);
		return lista;
	}
	
	/**
	 * Obtiene resultados de cualquier busqueda en base a los parametros proporcionados
	 * @param filtro
	 * @return
	 * @throws QRocksException
	 */
	public List<BitacoraVisita> filtrar(BitacoraVisita filtro) throws QRocksException{
		List<BitacoraVisita> lista=new ArrayList<BitacoraVisita>();
		lista=bitacoraVisitaDao.filtrar(filtro);
		return lista;
	}
	
	/**
	 * Obtiene el numero de clientes que usan la aplicacion y que estan ligados a este restaurante
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public Long consultarNumClientesPorNegocio(Long idNegocio) throws QRocksException{
		return bitacoraVisitaDao.consultarNumClientesPorNegocio(idNegocio);
	}
	
	/**
	 * Obtiene la lista de productos mas pedidos por negocio
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ProductoMasPedido> obtenerEstadisticasProductoMasPedido(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException{
		return bitacoraVisitaDao.obtenerEstadisticasProductoMasPedido(idNegocio, fechaInicio, fechaFin);
	}
	
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentes(Long idNegocio, Date fechaInicio, Date fechaFin) throws QRocksException{
		return bitacoraVisitaDao.obtenerListaClientesFrecuentes(idNegocio, fechaInicio, fechaFin);
	}
	
	/**
	 * Obtiene la lista de clientes con mayor numero de visitas al restaurante
	 * @param idNegocio
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteMasVisitas> obtenerListaClientesFrecuentesPaginado(Long idNegocio, Date fechaInicio, Date fechaFin, int numRegistrosLimite) throws QRocksException{
		return bitacoraVisitaDao.obtenerListaClientesFrecuentesPaginado(idNegocio, fechaInicio, fechaFin,numRegistrosLimite);
	}
	
	/**
	 * Obtiene la lista de cumpleanieros de la fecha actual
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<ClienteFestejado> obtenerClientesCumpleanieros(Long idNegocio) throws QRocksException{
		return bitacoraVisitaDao.obtenerClientesCumpleanieros(idNegocio);
	}

	public void setBitacoraVisitaDao(BitacoraVisitaDAO bitacoraVisitaDao) {
		this.bitacoraVisitaDao = bitacoraVisitaDao;
	}
}
