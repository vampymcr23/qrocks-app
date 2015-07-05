package com.quuiko.actions.mobileactions;

import static com.quuiko.util.Utileria.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Promocion;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.PromocionesDTO;
import com.quuiko.services.PromocionService;

@Namespace("/m/droid/promo")
public class MobilePromoAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MobilePromoAction.class.getCanonicalName());
	public final static String JSON_ACTION_IMAGEN_PROMO="gtPromoPng";
	public final static String JSON_ACTION_VER_PROMO="gtPromo";
	public final static String JSON_ACTION_LISTA_PROMOCIONES="gtAllPromos";
	private PromocionesDTO promocionesDTO;
	private String contentType;
    private String filename;
    private InputStream inputStream;
	private String contentDisposition;
	private Long idPromo;
	private Long idNegocio;
	@Autowired
	private PromocionService promocionService;
	
	@Action(value=JSON_ACTION_IMAGEN_PROMO,results={
		@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","inputStream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerImagenProducto(){
		try {
			inputStream=promocionService.obtenerImagenPromo(idPromo);
			String nombre="productImage.png";
			System.out.println("Imagen producto:"+idPromo);
			System.out.println("Stream:"+inputStream);
			contentDisposition="attachment;filename="+nombre;
		} catch (QRocksException e) {
//			onErrorMessage(e);
			log.log(Level.WARNING,"No se logro cargar la imagen de la promocion:"+idPromo);
		}
		return result;
	}
	
	@Action(value=JSON_ACTION_LISTA_PROMOCIONES,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","promocionesDTO"})
	})
	public String consultarPromocionesPorNegocio(){
		promocionesDTO=new PromocionesDTO();
		List<Promocion> promociones=new ArrayList<Promocion>();
		if(idNegocio!=null){
			try {
				promociones=promocionService.consultarTodasPromocionesPorNegocioActivas(idNegocio);
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}else{
			promociones=promocionService.consultarTodasPromocionesActivas();
		}
		if(!isEmptyCollection(promociones)){
			clearJSON(promociones);
		}
		promocionesDTO.setPromociones(promociones);
		return result;
	}
	
	@Action(value=JSON_ACTION_VER_PROMO,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","promocionesDTO"})
		})
		public String consultarPromocion(){
			promocionesDTO=new PromocionesDTO();
			List<Promocion> promociones=new ArrayList<Promocion>();
//			if(idNegocio!=null){
				try {
					Promocion promo=promocionService.consultarPromocion(idPromo);
					if(promo!=null){
						promociones.add(promo);
					}
				} catch (QRocksException e) {
					e.printStackTrace();
				}
//			}
			if(!isEmptyCollection(promociones)){
				clearJSON(promociones);
			}
			promocionesDTO.setPromociones(promociones);
			return result;
		}
	
	private void clearJSON(List<Promocion> promociones){
		for(Promocion p:promociones){
			p.getNegocio().setImagen(null);
			p.setImagen(null);
		}
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public Long getIdPromo() {
		return idPromo;
	}

	public void setIdPromo(Long idPromo) {
		this.idPromo = idPromo;
	}

	public PromocionService getPromocionService() {
		return promocionService;
	}

	public void setPromocionService(PromocionService promocionService) {
		this.promocionService = promocionService;
	}

	public PromocionesDTO getPromocionesDTO() {
		return promocionesDTO;
	}

	public void setPromocionesDTO(PromocionesDTO promocionesDTO) {
		this.promocionesDTO = promocionesDTO;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}
}
