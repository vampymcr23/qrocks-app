package com.quuiko.actions.mobileactions;
import static com.quuiko.util.Utileria.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.actions.QRocksAction;
import com.quuiko.beans.Negocio;
import com.quuiko.daos.NegocioDAO;
import com.quuiko.dtos.RestauranteUsuarioDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.dtos.mobile.NegociosDTO;
import com.quuiko.services.NegocioService;
import com.quuiko.util.Utileria;

@Namespace("/m/droid/negocio")
@ParentPackage("qrocks-default")
public class MobileNegocioAction extends QRocksAction{
	private static final String ACTION_OBTENER_LISTA_RESTAURANTES="gtRests";
	private static final String ACTION_OBTENER_LISTA_RESTAURANTES_PAG="gtRestsPag";
	private static final String ACTION_OBTENER_IMAGEN_NEGOCIO="getImg";
	private static final String ACTION_OBTENER_CONF_NEGOCIO="gtConfRest";
	private Negocio negocio;
	private Long idNegocio;
	private String contentType;
    private String filename;
    private InputStream stream;
	private String contentDisposition;
	private NegociosDTO negociosDTO;
	@Autowired
	private NegocioService negocioService;
	private Integer paginaActual;
	private String gcmId;
	private String nombreRestaurante;
	private String tipoComida;
	
	@Action(value=ACTION_OBTENER_IMAGEN_NEGOCIO,results={
		@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerImagenProducto(){
		System.out.println("Entrando a obtener la imagen");
		try {
			stream=negocioService.obtenerImagenProducto(idNegocio);
			String nombre="businessImage.png";
			System.out.println("Streaming:"+stream);
			contentDisposition="attachment;filename="+nombre;
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
		return result;
	}
	
	@Action(value=ACTION_OBTENER_CONF_NEGOCIO,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negociosDTO"})
	})
	public String obtenerConfiguracionRestaurante(){
		try {
			negocio=negocioService.consultarPorId(idNegocio);
			if(negocio!=null){
				negocio.setImagen(null);
			}
			negociosDTO=new NegociosDTO();
			List<Negocio> negocios=new ArrayList<Negocio>();
			negocios.add(negocio);
			negociosDTO.setNegocios(negocios);
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Action(value=ACTION_OBTENER_LISTA_RESTAURANTES,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negociosDTO"})
	})
	public String obtenerRestaurantes(){
		List<Negocio> negocios=negocioService.consultarNegociosActivos();
		if(!isEmptyCollection(negocios)){
			for(Negocio n:negocios){
				n.setImagen(null);
			}
		}
		negociosDTO=new NegociosDTO();
		negociosDTO.setNegocios(negocios);
		return result;
	}
	
	@Action(value=ACTION_OBTENER_LISTA_RESTAURANTES_PAG,results={
			@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negociosDTO"})
	})
	public String obtenerRestaurantesPaginado(){
//		List<Negocio> negocios=negocioService.consultarNegociosActivosPaginados(paginaActual);
//		if(!isEmptyCollection(negocios)){
//			for(Negocio n:negocios){
//				n.setImagen(null);
//			}
//		}
		List<RestauranteUsuarioDTO> restaurantes=negocioService.consultarRestaurantesUsuario(gcmId,nombreRestaurante,tipoComida, paginaActual);
		negociosDTO=new NegociosDTO();
		negociosDTO.setRestaurantes(restaurantes);
//		negociosDTO.setNegocios(negocios);
		return result;
	}
	
	/**
	 * ==================================================
	 *	Sets and Gets 
	 * ==================================================
	 */
	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
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

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public void setNegocioService(NegocioService negocioService) {
		this.negocioService = negocioService;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public NegociosDTO getNegociosDTO() {
		return negociosDTO;
	}

	public void setNegociosDTO(NegociosDTO negociosDTO) {
		this.negociosDTO = negociosDTO;
	}


	public void setPaginaActual(Integer paginaActual) {
		this.paginaActual = paginaActual;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}
	
	
}
