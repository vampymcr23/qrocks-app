package com.quuiko.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quuiko.dtos.Contacto;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.ContactoService;
import com.quuiko.services.EmailService;
import com.quuiko.util.Email;

@Service
public class ContactoServiceImpl implements ContactoService{
	@Autowired
	private EmailService emailService;
	
	public void enviarCorreoDeContacto(Contacto contacto) throws QRocksException {
		String to=emailService.getAccountEmail();
		String template="qrocks/vm/templates/contactInformation.vm";
		Map<String,Object> parametros=new HashMap<String, Object>();
		String from=contacto.getEmail();
		parametros.put("nombreContacto", contacto.getNombre());
		parametros.put("asunto", contacto.getAsunto());
		parametros.put("comentarios",contacto.getComentario());
		parametros.put("telefonoContacto", contacto.getTelefono());
		parametros.put("emailContacto", contacto.getEmail());
		Email email=new Email();
		email.setFrom(from);
		email.setTo(to);
		email.setSubject(contacto.getAsunto());
		email.setParametrosTemplate(parametros);
		email.setVelocityTemplate(template);
		emailService.sendEmail(email);
	}

}
