package com.quuiko.util;

import java.io.File;
import java.util.Map;
/**
 * Clase para enviar correos
 * @author vampy
 *
 */
public class Email {
	private String from;
	private String to;
	private String[] cc;
	private String subject;
	private File[] attachments;
	private String velocityTemplate;
	private Map<String,Object> parametrosTemplate;
	private String message;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public File[] getAttachments() {
		return attachments;
	}
	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}
	public String getVelocityTemplate() {
		return velocityTemplate;
	}
	public void setVelocityTemplate(String velocityTemplate) {
		this.velocityTemplate = velocityTemplate;
	}
	public Map<String, Object> getParametrosTemplate() {
		return parametrosTemplate;
	}
	public void setParametrosTemplate(Map<String, Object> parametrosTemplate) {
		this.parametrosTemplate = parametrosTemplate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
}
