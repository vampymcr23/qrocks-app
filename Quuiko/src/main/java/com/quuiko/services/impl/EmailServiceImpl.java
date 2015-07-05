package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.*;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.quuiko.exception.QRocksException;
import com.quuiko.services.EmailService;
import com.quuiko.util.Email;
/**
 * Clase para el envio de correos.
 * @author vampy
 *
 */
public class EmailServiceImpl implements EmailService{
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private MimeMessageHelper message;
	private String contactEmail;
	private String accountEmail;
	
	public void sendEmail(final Email email) throws QRocksException{
		if(email==null){
			throw new QRocksException("Favor de proporcionar los datos del correo");
		}
		if(!isValid(email.getFrom())){
			onError("Favor de proporcionar el remitente del correo");
		}
		if(!isValid(email.getSubject())){
			onError("Favor de proporcionar el asunto del correo");
		}
		if(!isValid(email.getTo())){
			onError("Favor de proporcionar el destinatario del correo");
		}
//		if(!isValid(email.getVelocityTemplate())){
//			onError("Favor de proporcionar el nombre del template a enviar");
//		}
		try{
			MimeMessagePreparator preparator=new MimeMessagePreparator() {
				
				public void prepare(MimeMessage mimeMessage) throws Exception {
					message=new MimeMessageHelper(mimeMessage, true);
					message.setFrom(email.getFrom());
					message.setTo(email.getTo());
					String msg=email.getMessage();
					if(msg==null || msg.isEmpty()){
						msg=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, email.getVelocityTemplate(),"utf-8",email.getParametrosTemplate());
					}
					message.setSubject(email.getSubject());
					if(!isEmptyArray(email.getCc())){
						message.setCc(email.getCc());
					}
					if(email!=null && email.getAttachments()!=null && email.getAttachments().length>0){
						for(File attach:email.getAttachments()){
							message.addAttachment(attach.getName(),attach);
						}
					}
					message.setText(msg,true);
				}
			};
			this.mailSender.send(preparator);
		}catch(Exception e){
			System.out.println("Ocurrio un erro en el envio de correo ["+email.getSubject()+"] causado por:"+e.getMessage());
		}
	}
	
	public void sendSimpleEmail(final Email email) throws QRocksException{
		if(email==null){
			throw new QRocksException("Favor de proporcionar los datos del correo");
		}
		if(!isValid(email.getFrom())){
			onError("Favor de proporcionar el remitente del correo");
		}
		if(!isValid(email.getSubject())){
			onError("Favor de proporcionar el asunto del correo");
		}
		if(!isValid(email.getTo())){
			onError("Favor de proporcionar el destinatario del correo");
		}
		if(!isValid(email.getMessage())){
			onError("Favor de proporcionar el contenido del correo");
		}
		MimeMessagePreparator preparator=new MimeMessagePreparator() {
			
			public void prepare(MimeMessage mimeMessage) throws Exception {
				message=new MimeMessageHelper(mimeMessage, true);
				message.setFrom(email.getFrom());
				message.setTo(email.getTo());
				String mailMessage=email.getMessage();
				message.setSubject(email.getSubject());
				if(email!=null && email.getAttachments()!=null && email.getAttachments().length>0){
					for(File attach:email.getAttachments()){
						message.addAttachment(attach.getName(),attach);
					}
				}
				message.setText(mailMessage,true);
			}
		};
		this.mailSender.send(preparator);
	}

	/**
	 * ================================
	 * 	Sets and Gets
	 * ================================
	 */

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
}
