package com.quuiko.actions;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.Mesa;
import com.quuiko.beans.Mesero;
import com.quuiko.beans.Negocio;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.MesaService;
import com.quuiko.services.MeseroService;
import com.quuiko.services.NegocioService;
import com.quuiko.services.QRService;
import com.quuiko.util.Utileria;

@Namespace("/negocio")
@ParentPackage("qrocks-default")
public class NegocioAction extends QRocksAction{
	private static Logger log=Logger.getLogger(NegocioAction.class.getCanonicalName());
	private static final String ACTION_MI_NEGOCIO="miNegocio";
	private static final String ACTION_OBTENER_IMAGEN_NEGOCIO="getImg";
	private static final String ACTION_GUARDAR_NEGOCIO="guardarNegocio";
	private static final String ACTION_VER_MIS_MESAS="misMesas";
	private static final String ACTION_OBTENER_MIS_MESAS="obtenerMisMesas";
	private static final String ACTION_AGREGAR_MESA="agregarMesa";
	private static final String ACTION_ELIMINAR_MESA="eliminarMesa";
	private static final String ACTION_VER_QR="gtQRCode";
	private static final String ACTION_AGREGAR_MESERO="addWaitress";
	private static final String ACTION_ELIMINAR_MESERO="removeWaitress";
	private static final String ACTION_VER_MESEROS="showAllWaitress";
	private static final String ACTION_ASIGNAR_MESERO_MESA="asignWaitress";
	private static final String PAGINA_MI_NEGOCIO="/negocio/miNegocio.jsp";
	private static final String PAGINA_MIS_MESAS="/mesa/misMesas.jsp";
	private static final String CURRENT_NAMESPACE="/negocio";
	@Valid
	private Negocio negocio;
	private File fileUploaded;
	private String contentType;
    private String filename;
    private InputStream stream;
	private String contentDisposition;
	private List<Mesa> mesas=new ArrayList<Mesa>();
	private Mesa mesa;
	private Long idMesaEliminar;
	private List<Mesero> meseros=new ArrayList<Mesero>();
	
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private MesaService mesaService;
	@Autowired
	private QRService qrService;
	@Autowired
	private MeseroService meseroService;
	private Mesero mesero;

	@Action(value=ACTION_MI_NEGOCIO,results={
		@Result(name=SUCCESS,location=PAGINA_MI_NEGOCIO)
	})
	public String verMiNegocio(){
		negocio=getNegocioLogeado();
		try {
			cargarMesas();
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Action(value=ACTION_VER_MIS_MESAS,results={
		@Result(name=SUCCESS,location=PAGINA_MIS_MESAS)
	})
	public String verMisMesas(){
		negocio=getNegocioLogeado();
		try {
			cargarMesas();
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Action(value=ACTION_OBTENER_IMAGEN_NEGOCIO,results={
		@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerImagenProducto(){
		try {
//			if(Utileria.isNull(negocio) || Utileria.isNull(negocio.getId())){
//				negocio=negocioService.obtenerMiNegocio();
//			}
			Negocio negocioLogeado=getNegocioLogeado();
			Long idNegocio=(negocioLogeado!=null)?negocioLogeado.getId():null;
			stream=negocioService.obtenerImagenProducto(idNegocio);
			String nombre="businessImage.png";
			contentDisposition="attachment;filename="+nombre;
		} catch (QRocksException e) {
			log.log(Level.WARNING,"No se logro cargar la imagen del negocio");
		}
		return result;
	}
	
	private void cargarMesas() throws QRocksException{
		Long idNegocio=(negocio!=null)?negocio.getId():null;
		if(idNegocio!=null){
			mesas=mesaService.obtenerMesasPorNegocio(idNegocio);
		}
	}
	
	@Action(value=ACTION_OBTENER_MIS_MESAS,results=
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","mesas"})
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","mesas\\[\\d+\\]\\..*","excludeProperties","mesas\\[\\d+\\]\\.mesero\\.negocio\\.imagen,mesas\\[\\d+\\]\\.negocio\\.imagen"})
	)
	public String obtenerMisMesas(){
		negocio=getNegocioLogeado();
		try {
			cargarMesas();
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void validateGuardarNegocio(){
		validar(negocio);
	}
	
	@Action(value=ACTION_GUARDAR_NEGOCIO,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_MI_NEGOCIO,"namespace",CURRENT_NAMESPACE,"negocio.id","${negocio.id}","messageResult","${messageResult}","messageCode","${messageCode}"}),
		@Result(name=INPUT,location=PAGINA_MI_NEGOCIO)
	})
	public String guardarNegocio(){
		try {
			negocioService.guardarNegocio(negocio);
			setValueToSession("_neg",negocio);
			onSuccessMessage("Se han guardado los datos del negocio exitosamente!");
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	public void validateAgregarMesa(){
		validar(mesa);
		//Validar nombre de mesa
		boolean esValidoNombreMesa=validarMesa(mesa.getClaveMesa());
		if(!esValidoNombreMesa){
			String msg="Clave de la mesa invalida, favor de introducir letras o numeros, maximo 10 caracteres, sin espacios.";
			onErrorMessage(msg);
			addActionError(msg);
		}
	}
	
	public boolean validarMesa(String nombreMesa){
		boolean esValido=false;
		String patron="[a-zA-Z0-9]{1,10}";
		Pattern pattern=Pattern.compile(patron);
		Matcher match=pattern.matcher(nombreMesa);
		esValido=match.matches();
		return esValido;
	}
	
	@Action(value=ACTION_AGREGAR_MESA,results={
		@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_VER_MIS_MESAS,"namespace",CURRENT_NAMESPACE,"negocio.id","${negocio.id}","messageResult","${messageResult}","messageCode","${messageCode}"}),
		@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_VER_MIS_MESAS,"namespace",CURRENT_NAMESPACE,"negocio.id","${negocio.id}","messageResult","${messageResult}","messageCode","${messageCode}"})
	})
	public String agregarMesa(){
		try {
			mesaService.guardarMesa(mesa);
			onSuccessMessage("Se ha agregado una nueva mesa!");
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_ELIMINAR_MESA,results={
			@Result(name=SUCCESS,type="redirectAction",params={"actionName",ACTION_VER_MIS_MESAS,"namespace",CURRENT_NAMESPACE,"negocio.id","${negocio.id}","messageResult","${messageResult}","messageCode","${messageCode}"}),
			@Result(name=INPUT,type="redirectAction",params={"actionName",ACTION_VER_MIS_MESAS,"namespace",CURRENT_NAMESPACE,"negocio.id","${negocio.id}","messageResult","${messageResult}","messageCode","${messageCode}"})
		})
	public String eliminarMesa(){
		try {
			mesaService.eliminarMesa(idMesaEliminar);
			onSuccessMessage("Se ha eliminado la mesa correctamente!");
		} catch (QRocksException e) {
			onErrorMessage(e);
		}
		return result;
	}
	
	@Action(value=ACTION_VER_QR,results={
		@Result(name=SUCCESS,type="stream",params={"contentType","application/octet-stream","inputName","stream","contentDisposition","${contentDisposition}","bufferSize","9216"})
	})
	public String obtenerQR(){
		
		if(mesa!=null && Utileria.isValid(mesa.getClaveMesa())){
			HttpServletRequest request=ServletActionContext.getRequest();
			String ip="";
			try {
				ip=InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			Long idNegocio=null;
			
			try {
				Mesa m= mesaService.obtenerMesaPorClave(mesa.getClaveMesa());
				idNegocio=(m!=null && m.getNegocio()!=null)?m.getNegocio().getId():null;
			} catch (QRocksException e1) {
				e1.printStackTrace();
			}
			String path = request.getContextPath();
//			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//			String basePath = "http://"+ip+":"+request.getServerPort()+path+"/";
//			String qrPath=basePath+"m/pedidos/menu.php?qrMKEY="+mesa.getClaveMesa()+"&qrBKEY="+idNegocio;
			String qrPath="qrMKEY="+mesa.getClaveMesa()+"&qrBKEY="+idNegocio;
			StringBuilder str=new StringBuilder(qrPath);
			String nombre=mesa.getClaveMesa()+".png";
			try {
				stream=qrService.generateQR("", nombre, str);
				contentDisposition="attachment;filename="+nombre;
			} catch (QRocksException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Obtiene la lista de meseros por negocio
	 * @return
	 */
	@Action(value=ACTION_VER_MESEROS,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","meseros"})
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","meseros\\[\\d+\\]\\..*","excludeProperties","meseros\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String obtenerMeserosPorNegocio(){
		Long idNegocio=(getNegocioLogeado()!=null)?getNegocioLogeado().getId():null;
		if(idNegocio!=null){
			try {
				meseros=meseroService.listarMeserosPorNegocio(idNegocio);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	/**
	 * Action que agrega un nuevo mesero
	 * @return
	 */
	@Action(value=ACTION_AGREGAR_MESERO,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","meseros"})
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","meseros\\[\\d+\\]\\..*","excludeProperties","meseros\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String agregarMesero(){
		if(mesero!=null){
			Negocio negocioLogeado=getNegocioLogeado();
			mesero.setNegocio(negocioLogeado);
			try {
				meseroService.agregarMesero(mesero);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
			obtenerMeserosPorNegocio();
		}
		return result;
	}
	
	/**
	 * Action que elimina a un mesero y desasigna las mesas que tenia asignadas
	 * @return
	 */
	@Action(value=ACTION_ELIMINAR_MESERO,results={
//		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","meseros"})
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","meseros\\[\\d+\\]\\..*","excludeProperties","meseros\\[\\d+\\]\\.negocio\\.imagen"})
	})
	public String eliminarMesero(){
		if(mesero!=null){
			Negocio negocioLogeado=getNegocioLogeado();
			mesero.setNegocio(negocioLogeado);
			try {
				meseroService.eliminarMesero(mesero);
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
			obtenerMeserosPorNegocio();
		}
		return result;
	}
	
	@Action(value=ACTION_ASIGNAR_MESERO_MESA,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","resultCode"})
	})
	public String asignarMesero(){
		if(mesero!=null && mesa!=null){
			try {
				Mesa m=mesaService.obtenerMesaPorId(mesa.getId());
				m.setMesero(mesero);
				mesaService.guardarMesa(m);
				resultCode=1;
			} catch (QRocksException e) {
				onErrorMessage(e);
				resultCode=0;
			}
		}
		return result;
	}
	
	public Negocio getNegocio() {
		return negocio;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public File getFileUploaded() {
		return fileUploaded;
	}

	public void setFileUploaded(File fileUploaded) {
		this.fileUploaded = fileUploaded;
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

	public List<Mesa> getMesas() {
		return mesas;
	}

	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}

	public void setMesaService(MesaService mesaService) {
		this.mesaService = mesaService;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Long getIdMesaEliminar() {
		return idMesaEliminar;
	}

	public void setIdMesaEliminar(Long idMesaEliminar) {
		this.idMesaEliminar = idMesaEliminar;
	}

	public void setMeseroService(MeseroService meseroService) {
		this.meseroService = meseroService;
	}

	public List<Mesero> getMeseros() {
		return meseros;
	}

	public void setMeseros(List<Mesero> meseros) {
		this.meseros = meseros;
	}

	public Mesero getMesero() {
		return mesero;
	}

	public void setMesero(Mesero mesero) {
		this.mesero = mesero;
	}
	
	public static void main(String... a){
		double x=2.0d;
		System.out.println("Tiene decimal:"+(x%1==0));
	}
}
