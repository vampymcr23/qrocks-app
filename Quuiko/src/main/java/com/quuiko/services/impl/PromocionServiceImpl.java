package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.beans.Promocion;
import com.quuiko.daos.PromocionDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PromocionService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PromocionServiceImpl implements PromocionService{
	@Autowired
	private PromocionDAO promocionDao;

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void guardar(Promocion promo) throws QRocksException {
		if( promo!=null ){
			File archivo=promo.getArchivoImagen();
			if(isNotNull(archivo)){
//				byte[] data=obtenerImagenPorArchivo(promo.getArchivoImagen());
				byte[] data=Utileria.getResizeImage(promo.getArchivoImagen());
				promo.setImagen(data);
			}else if(promo.getId()!=null){
				Promocion anterior=consultarPromocion(promo.getId());
				byte[] data=anterior.getImagen();
				promo.setImagen(data);
			}
			promocionDao.guardar(promo);
		}
	}
	
	/**
	 * COnsulta una promocion por su id
	 * @param promo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Promocion promo) throws QRocksException{
		return promocionDao.consultarPromocion(promo.getId());
	}
	
	/**
	 * Consulta una promocion de su id
	 * @param idPromo
	 * @return
	 * @throws QRocksException
	 */
	public Promocion consultarPromocion(Long idPromo) throws QRocksException{
		return promocionDao.consultarPromocion(idPromo);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void activarDesactivarPromo(Long idPromo) throws QRocksException {
		promocionDao.activarDesactivarPromo(idPromo);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void enviarNotificacionPromocion(Long idPromo) throws QRocksException {
		promocionDao.enviarNotificacionPromocion(idPromo);
	}

	public List<Promocion> consultarTodasPromocionesPorNegocioActivas( Long idNegocio) throws QRocksException {
		return promocionDao.consultarTodasPromocionesPorNegocioActivas(idNegocio);
	}

	public List<Promocion> consultarTodasPromocionesPorNegocio(Negocio negocio) throws QRocksException {
		return promocionDao.consultarTodasPromocionesPorNegocio(negocio);
	}

	public List<Promocion> consultarTodasPromocionesPorNegocio(Long idNegocio) throws QRocksException {
		return promocionDao.consultarTodasPromocionesPorNegocio(idNegocio);
	}

	public List<Promocion> consultarTodasPromocionesActivas() {
		return promocionDao.consultarTodasPromocionesActivas();
	}

	public List<Promocion> filtrar(Promocion filtro) {
		return promocionDao.filtrar(filtro);
	}
	
	public InputStream obtenerImagenPromo(Long idPromo) throws QRocksException{
		if(isNull(idPromo)){
			onError("Favor de promocionar la promocion");
		}
		Promocion promo=consultarPromocion(idPromo);
		InputStream stream=null;
		byte[] data=promo.getImagen();
		data=(data==null && promo.getNegocio()!=null)?promo.getNegocio().getImagen():data;
		if(data!=null){
			try {
				File tmp=File.createTempFile("_"+System.currentTimeMillis(), ".png");
				FileUtils.writeByteArrayToFile(tmp, data);
				stream=new FileInputStream(tmp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stream;
	}
	
	private byte[] obtenerImagenPorArchivo(File archivo) throws QRocksException{
		if(isNull(archivo)){
			onError("");
		}
		byte[] data=null;
		try {
			data=FileUtils.readFileToByteArray(archivo);
			if(data==null){
				onError("");
			}
		}catch(IOException e){
			onError("");
		}
		return data;
	}
	
	/**
	 * Elimina una promocion que no este activa, o si esta activa, que no se haya enviado la notificacion
	 * @param idPromo Id de la promocion a eliminar.
	 * @throws QRocksException
	 */
	public void eliminarPromo(Long idPromo) throws QRocksException{
		Promocion promo=consultarPromocion(idPromo);
		if(promo==null){
			onError("La promocion no existe con este codigo de promocion");
		}
		int activa=(promo.getEnabled()!=null && promo.getEnabled().intValue()==1)?1:0;
		int notificacionEnviada=(promo.getNotificacionEnviada()!=null && promo.getNotificacionEnviada().intValue()==1)?1:0;
		//Se revisa si la notificacion esta activa y ya fue enviada la notificacion
		if(activa==0 || (activa==1 && notificacionEnviada==0)){
			promocionDao.eliminarPorId(Promocion.class,idPromo);
		}else{
			onError("No se puede eliminar esta promocion, desactive primero esta notificacion y posteriormente proceda a eliminarla.");
		}
	}

	public void setPromocionDao(PromocionDAO promocionDao) {
		this.promocionDao = promocionDao;
	}
}
