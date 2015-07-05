package com.quuiko.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.quuiko.services.EmailService;
import com.quuiko.util.Email;


public class QRocksException extends Exception{
	private static final Logger LOG=Logger.getLogger(QRocksException.class.getName());
	private static EmailService emailService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4591071467682182447L;

	public QRocksException(String e){
		super(e);
	}
	
	public QRocksException(String e,boolean sendEmail){
		super(e);
		if(sendEmail){
			LOG.log(Level.INFO,"Enviando email de excepcion...");
			Email email=new Email();
			email.setFrom("vampy.mcr.23@gmail.com");
//			email.setVelocityTemplate("qrocks/vm/templates/emailException.vm");
			email.setSubject("QRocks Exception");
			email.setTo("victor.herrera_hildebrando@banorte.com");
//			Map<String,Object> parametros=new HashMap<String, Object>();
//			parametros.put("mensaje", e);
			email.setMessage(e);
//			email.setParametrosTemplate(parametros);
			try {
				getEmailService().sendEmail(email);
			} catch (QRocksException e1) {
				LOG.log(Level.INFO,"Ocurrio un error al intentar enviar el email, causado por:"+e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	public QRocksException(String e,String... params){
		//FIXME Falta agregar un metodo para obtener con todo y parametros
		super(e);
	}
	
	public static EmailService getEmailService(){
		if(emailService==null){
			ApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
			emailService=(EmailService)ctx.getBean("emailService");
		}
		return emailService;
	}
}
