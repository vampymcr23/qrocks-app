package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quuiko.beans.ContratoNegocio;
import com.quuiko.daos.ContratoNegocioDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ContratoNegocioService;

@Service
public class ContratoNegocioServiceImpl implements ContratoNegocioService{
	@Autowired
	private ContratoNegocioDAO contratoNegocioDao;
	
	public void guardarContrato(ContratoNegocio contrato) throws QRocksException {
		contratoNegocioDao.guardarContrato(contrato);
	}

	public ContratoNegocio consultarContrato(Long id) throws QRocksException {
		return contratoNegocioDao.consultarContrato(id);
	}

	public void habilitarDeshabilitarContrato(Long id) throws QRocksException {
		contratoNegocioDao.habilitarDeshabilitarContrato(id);
	}

	public List<ContratoNegocio> filtrar(ContratoNegocio filtro) {
		return contratoNegocioDao.filtrar(filtro);
	}

	public List<ContratoNegocio> consultarFiltrosDeUnNegocio(Long idNegocio) {
		return null;
	}

	public int conteoDetallePorBusqueda(ContratoNegocio filtro) {
		return contratoNegocioDao.conteoDetallePorBusqueda(filtro);
	}

	public List<ContratoNegocio> filtrarPaginado(ContratoNegocio filtro, int inicio, int numRegistros) {
		return contratoNegocioDao.filtrarPaginado(filtro, inicio, numRegistros);
	}

}
