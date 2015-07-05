package com.quuiko.actions;
import static com.quuiko.util.Utileria.isEmptyCollection;
import static com.quuiko.util.Utileria.isNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.bval.jsr303.ApacheValidationProvider;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.quuiko.beans.BusinessUser;
import com.quuiko.beans.Negocio;
import com.quuiko.dtos.Contacto;
import com.quuiko.dtos.mobile.JSONDTO;
import com.quuiko.exception.QRocksException;
import com.quuiko.gcm.service.BusinessUserService;
import com.quuiko.services.ContactoService;
import com.quuiko.services.EmailService;
import com.quuiko.services.NegocioService;

@Namespace("/")
@ParentPackage("qrocks-default")
@SuppressWarnings("serial")
public class QRocksAction extends ActionSupport implements Preparable{
//	@Autowired
//	private org.springframework.validation.Validator validador;
	private ValidatorFactory validatorFactory = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
	private Validator validator = validatorFactory.getValidator();
	protected Float currentMobileVersion=1.0f;
	protected String result=SUCCESS;
	protected String messageResult;
	protected Integer messageCode;//Code 0 es sin error, cualquier otro es error
	protected final String ACTION_FLUSH_CACHE="flushCache";
	protected final String ACTION_LOGIN="login";
	protected final String ACTION_AFTER_LOGIN="afterLogin";
	protected final String ACTION_GET_VERSION="gtVersion";
	protected final String ACTION_INICIO="index";
	protected final String ACTION_DOWNLOAD_ANDROID_DEMO="demoAndroid";
	protected final String ACTION_CONTACT="contact";
	protected final String ACTION_TERMINOS="terms";
	protected final String ACTION_CONTACT_SEND="contactSendInfo";
	private static final String PAGINA_HOME="/welcome.jsp";
	private static final String PAGINA_LOGIN="/login.jsp";
	private static final String PAGINA_QUICKO_HOME="/webPage/quicko.jsp";
	private static final String PAGINA_QUICKO_CONTACT="/webPage/contacto.jsp";
	private static final String PAGINA_QUICKO_TERMINOS="/webPage/terminosCondiciones.jsp";
	private static String access_token;
	private static String code;
	private static String youtube_token;
	private static final String ACTION_YOUTUBE_CALLBACK_AUTH="oauth2callback";
	private Negocio negocioLogeado;
	public int resultCode;
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private BusinessUserService businessUserService;
	private BusinessUser buLogeado;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ContactoService contactoService;
	
	private Contacto contacto;
	
	@Action(value=ACTION_FLUSH_CACHE,results=@Result(name=SUCCESS,location=PAGINA_HOME))
	public String flushCache(){
		negocioService.flushCache();
		return result;
	}
	
	@Action(value=ACTION_INICIO,results=@Result(name=SUCCESS,location=PAGINA_QUICKO_HOME))
	public String homePage(){
		return result;
	}
	
	@Action(value=ACTION_LOGIN,results=@Result(name=SUCCESS,location=PAGINA_LOGIN))
	public String login(){
		return result;
	}
	
	@Action(value=ACTION_CONTACT,results=@Result(name=SUCCESS,location=PAGINA_QUICKO_CONTACT))
	public String contactPage(){
		return result;
	}

	@Action(value=ACTION_TERMINOS,results=@Result(name=SUCCESS,location=PAGINA_QUICKO_TERMINOS))
	public String terms(){
		return result;
	}
	
	public void validateEnviarInformacionContacto(){
		validar(contacto);
	}
	
	@Action(value=ACTION_CONTACT_SEND,results={
		@Result(name=SUCCESS,location=PAGINA_QUICKO_CONTACT),
		@Result(name=INPUT,location=PAGINA_QUICKO_CONTACT)
	})
	public String enviarInformacionContacto(){
		if(contacto!=null){
			try {
				contactoService.enviarCorreoDeContacto(contacto);
				onSuccessMessage("En breve nos comunicamos con usted. Gracias por comunicarse con nosotros.");
				contacto=new Contacto();
			} catch (QRocksException e) {
				onErrorMessage(e);
			}
		}
		return result;
	}
	
	protected <T> void validar(T o){
		Set<ConstraintViolation<T>> constraintViolations=validator.validate(o);
		if(constraintViolations!=null && !constraintViolations.isEmpty()){
			Iterator<ConstraintViolation<T>> iterador=constraintViolations.iterator();
			StringBuilder str=new StringBuilder("");
			while(iterador.hasNext()){
				ConstraintViolation<T> constraint=iterador.next();
				String mensajeError=constraint.getMessage();
				str.append(mensajeError+"<br/>");
			}
			String errores=str.toString();
			onErrorMessage(errores);
			addActionError(errores.toString());
		}
	}
	
	@Action(value=ACTION_AFTER_LOGIN,results=@Result(name=SUCCESS,location=PAGINA_HOME))
	public String afterLogin(){
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		HttpSession session=ServletActionContext.getRequest().getSession(false);
		session.setAttribute("_user", currentUser.getName());
		try {
			buLogeado=businessUserService.consultarUsuario(currentUser.getName());
			session.setAttribute("_bu", buLogeado);
			negocioLogeado=negocioService.consultarPorUsuario(currentUser.getName());
			session.setAttribute("_neg", negocioLogeado);
			String nomNegocio=(negocioLogeado!=null)?negocioLogeado.getNombre():"";
			session.setAttribute("_nomNeg", nomNegocio);
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Se utiliza para mostrar el mensaje de error
	 * @param e
	 */
	protected void onErrorMessage(String e){
		result=INPUT;
		messageResult=e;
		messageCode=-1;
	}
	
	/**
	 * Se utiliza para mostrar mensajes de error en pantalla
	 * @param e
	 */
	protected void onErrorMessage(Throwable e){
		String message=e.getMessage();
		onErrorMessage(message);
	}
	
	/**
	 * Metodo que inicializa las propiedades del dto para poner elmensaje de error y el codigo de error.
	 * @param e
	 * @param dto
	 */
	protected void onErrorAndroidMessage(Throwable e, JSONDTO dto){
		dto.setErrorMessage(e.getMessage());
		dto.setRequestCode(-1);
		result=SUCCESS;//No importa que sea error, ya que se maneja en la parte mobil, solo se inyecta el codigo de error y el mensaje de error
	}
	
	protected void onSuccessMessage(String m){
		result=SUCCESS;
		messageResult=m;
		messageCode=0;
	}
	/**
	 * Obtiene un valor en sesion
	 * @param name
	 * @return
	 */
	protected Object getValueFromSession(String name){
		Object value=null;
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(isNotNull(session)){
			value=session.getAttribute(name);
		}
		return value;
	}
	/**
	 * Agrega un valor en sesion
	 * @param name
	 * @param value
	 */
	protected void setValueToSession(String name, Object value){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(isNotNull(session)){
			session.setAttribute(name,value);
		}
	}

	public void setNegocioService(NegocioService negocioService) {
		this.negocioService = negocioService;
	}

	public Negocio getNegocioLogeado() {
		Object valor=getValueFromSession("_neg");
		if(valor!=null && valor instanceof Negocio){
			negocioLogeado=(Negocio)valor;
		}
		return negocioLogeado;
	}

	public void setNegocioLogeado(Negocio negocioLogeado) {
		this.negocioLogeado = negocioLogeado;
	}

	public BusinessUser getBuLogeado() {
		Object valor=getValueFromSession("_bu");
		if(valor!=null && valor instanceof BusinessUser){
			buLogeado=(BusinessUser)valor;
		}
		return buLogeado;
	}

	public void setBuLogeado(BusinessUser buLogeado) {
		this.buLogeado = buLogeado;
	}

	public void setBusinessUserService(BusinessUserService businessUserService) {
		this.businessUserService = businessUserService;
	}
	
	public boolean tienePermiso(String nombrePermiso){
		boolean tienePermiso=false;
		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
        List<SimpleGrantedAuthority> listaRoles=parseAuthorities(nombrePermiso);
        if(!isEmptyCollection(listaRoles)){
        	for(SimpleGrantedAuthority rol:listaRoles){
        		tienePermiso=granted.contains(rol);
        		if(tienePermiso){
        			break;
        		}
        	}
        }
        return tienePermiso;
	}
	
	private List<SimpleGrantedAuthority> parseAuthorities(String authorities){
		List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
		String[] arr=authorities.split(",");
		if(arr!=null && arr.length>0){
			for(String rol:arr){
				SimpleGrantedAuthority auth=new SimpleGrantedAuthority(rol);
				list.add(auth);
			}
		}
		return list;
	}
	
    private Collection<? extends GrantedAuthority> getPrincipalAuthorities() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (null == currentUser) {
            return Collections.emptyList();
        }
        if ((null == currentUser.getAuthorities()) || (currentUser.getAuthorities().isEmpty())) {
            return Collections.emptyList();
        }
        Collection<? extends GrantedAuthority> granted = currentUser.getAuthorities();
        return granted;
    }

	public void prepare() throws Exception {
	}
	
	@Action(value=ACTION_YOUTUBE_CALLBACK_AUTH,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","root","negociosDTO"})
	})
	public String obtenerTokenYoutube(){
		System.out.println("Token :"+access_token);
		youtube_token=access_token;
		System.out.println("Tokeb 2:"+code);
		youtube_token=code;
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public static String getAccess_token() {
		return access_token;
	}

	public static void setAccess_token(String access_token) {
		QRocksAction.access_token = access_token;
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		QRocksAction.code = code;
	}

	public static String getYoutube_token() {
		return youtube_token;
	}

	public static void setYoutube_token(String youtube_token) {
		QRocksAction.youtube_token = youtube_token;
	}

	public Integer getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(Integer messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageResult() {
		return messageResult;
	}

	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}

//	public void setValidador(Validator validador) {
//		this.validador = validador;
//	}

	@Action(value=ACTION_GET_VERSION,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","currentMobileVersion,messageResult"})
	})
	public String getCurrentVersion(){
		System.out.println("Version actual:"+currentMobileVersion);
		return result;
	}

	public Float getCurrentMobileVersion() {
		return currentMobileVersion;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
}
