package com.quuiko.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Mesa;
import com.quuiko.beans.Mesero;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.MeseroDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.MesaService;
import com.quuiko.services.MeseroService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class MeseroServiceImpl implements MeseroService{
	@Autowired
	private MeseroDAO meseroDao;
	@Autowired
	private MesaService mesaService;
	
	public List<Mesero> filtrar(Mesero filtro){
		return meseroDao.filtrar(filtro);
	}
	
	public List<Mesero> listarMeserosPorNegocio(Long idNegocio) throws QRocksException{
		return meseroDao.listarMeserosPorNegocio(idNegocio);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesero filtro){
		return meseroDao.conteoDetallePorBusqueda(filtro);
	}
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesero> filtrarPaginado(Mesero filtro, int inicio,int numRegistros){
		return meseroDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public Mesero agregarMesero(Mesero mesero) throws QRocksException{
		return meseroDao.agregarMesero(mesero);
	}
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void actualizarMesero(Mesero mesero) throws QRocksException{
		meseroDao.actualizarMesero(mesero);
	}
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void eliminarMesero(Mesero mesero) throws QRocksException{
		Long idMesero=(mesero!=null)?mesero.getId():null;
		if(idMesero !=null){
			Mesero m=meseroDao.consultarMesero(idMesero);
			if(m!=null){
				Negocio n=m.getNegocio();
				List<Mesa> mesas=mesaService.obtenerMesasPorNegocio(n);
				if(!Utileria.isEmptyCollection(mesas)){
					for(Mesa mesa:mesas){
						Mesero meseroAsignado=mesa.getMesero();
						// Si se encuentra que el mesero tiene asignada una mesa y se elimina el mesero, entonces se limpia la mesa 
						// y se queda sin asignacion
						if(meseroAsignado!=null && meseroAsignado.getId().longValue()==idMesero){
							mesa.setMesero(null);
							mesaService.guardarMesa(mesa);
						}
					}
				}
			}
			meseroDao.eliminarMesero(mesero);
		}else{
			Utileria.onError("Favor de proporcionar el mesero a eliminar");
		}
	}
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void eliminarMesero(Long idMesero) throws QRocksException{
		meseroDao.eliminarMesero(idMesero);
	}
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Mesero consultarMesero(Long idMesero) throws QRocksException{
		return meseroDao.consultarMesero(idMesero);
	}

	public void setMeseroDao(MeseroDAO meseroDao) {
		this.meseroDao = meseroDao;
	}

	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}
}
