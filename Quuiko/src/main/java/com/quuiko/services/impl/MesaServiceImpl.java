package com.quuiko.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Mesa;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.MesaDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.MesaService;
import com.quuiko.services.NegocioService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class MesaServiceImpl implements MesaService{
	private static final Logger LOG=Logger.getLogger(MesaServiceImpl.class.getCanonicalName());
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private MesaDAO mesaDao;
	/**
	 * Permite filtrar los registros de la tabla de mesa de acuerdo a las propiedades asignadas al filtro
	 * @param filtro
	 * @return
	 */
	public List<Mesa> filtrar(Mesa filtro){
		return mesaDao.filtrar(filtro);
	}
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesa filtro){
		return mesaDao.conteoDetallePorBusqueda(filtro);
	}
	
	/**
	 * Obtiene la lista de mesas de acuerdo a ciertos filtros
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesa> filtrarPaginado(Mesa filtro, int inicio,int numRegistros){
		return mesaDao.filtrarPaginado(filtro, inicio, numRegistros);
	}
	/**
	 * Obtiene la lista de mesas por negocio
	 * @param negocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Negocio negocio) throws QRocksException{
		return mesaDao.obtenerMesasPorNegocio(negocio);
	}
	/**
	 * Obtiene las mesas de un negocio en especifico
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<Mesa> obtenerMesasPorNegocio(Long idNegocio) throws QRocksException{
		return mesaDao.obtenerMesasPorNegocio(idNegocio);
	}
	
	/**
	 * GUarda o actualiza los datos de una mesa
	 * @param mesa
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardarMesa(Mesa mesa) throws QRocksException{
		if(mesa!=null){
			try{
				//Si es nueva mesa entonces se debe de verificar que la clave de la mesa no exista
				//Se validara para saber si se puede insertar o no.
				if(mesa.getId()==null){
					Negocio negocio=mesa.getNegocio();
					Long idNeg=(negocio!=null)?negocio.getId():null;
					String usuarioNeg=(negocio!=null)?negocio.getUsuario():null;
					if(!Utileria.isValid(usuarioNeg) && idNeg!=null){
						negocio=negocioService.consultarPorId(idNeg);
						usuarioNeg=negocio.getUsuario();
					}
					String claveMesa=mesa.getClaveMesa();
					if(!Utileria.isValid(claveMesa)){
						Utileria.onError("Favor de proporcionar la clave de la mesa");
					}
					claveMesa=usuarioNeg+"_"+claveMesa;
					boolean sePuedeRegistrar=false;
					Mesa mesaActual=null;
					try{
						mesaActual=obtenerMesaPorClave(claveMesa);
						sePuedeRegistrar=(mesaActual==null);
					}catch(QRocksException e){
						sePuedeRegistrar=true;//Si llega a este punto es por que no existe
						System.out.println("La mesa con la clave:"+claveMesa+" no existe, se va a agregar...");
					}
					//Si no se puede registrar entonces mandar error
					if(!sePuedeRegistrar){
						Utileria.onError("La clave '"+claveMesa+"' que introdujo ya existe, favor de proporcionar otra clave para la mesa.");
					}else{
						mesa.setClaveMesa(claveMesa);
						mesaDao.guardarMesa(mesa);
					}
				}else{
					mesaDao.guardarMesa(mesa);
				}
			}catch(Exception e){
				LOG.log(Level.SEVERE,"No se logro guardar los datos de la mesa- Causado por:"+e.getMessage());
				Utileria.onError("No se logro guardar los datos de la mesa");
			}
		}
	}
	
	/**
	 * Elimina una mesa por su id
	 * @param idMesa
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED,rollbackFor=SQLException.class)
	public void eliminarMesa(Long idMesa) throws QRocksException{
		try{
			mesaDao.eliminarMesa(idMesa);
		}catch(Exception e){
			LOG.log(Level.SEVERE,"No se puede eliminar la mesa- Causado por:"+e.getMessage());
			Utileria.onError("No se puede eliminar esta mesa.");
		}
	}
	
	/**
	 * Elimina una mesa por su id
	 * @param mesa
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarMesa(Mesa mesa) throws QRocksException{
		try{
			mesaDao.eliminarMesa(mesa);
		}catch(Exception e){
			LOG.log(Level.SEVERE,"No se puede eliminar la mesa- Causado por:"+e.getMessage());
			Utileria.onError("No se puede eliminar esta mesa.");
		}
	}
	/**
	 * Consultar por id
	 * @param idMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorId(Long idMesa) throws QRocksException{
		return mesaDao.obtenerMesaPorId(idMesa);
	}
	
	/**
	 * Obtiene la mesa por la clave de la mesa
	 * @param claveMesa
	 * @return
	 * @throws QRocksException
	 */
	public Mesa obtenerMesaPorClave(String claveMesa) throws QRocksException{
		return mesaDao.obtenerMesaPorClave(claveMesa);
	}
	
	/**
	 * =================================================================
	 * 	Sets and gets
	 * =================================================================
	 */
	public void setMesaDao(MesaDAO mesaDao) {
		this.mesaDao = mesaDao;
	}
}
