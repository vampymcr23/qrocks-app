package com.quuiko.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase para el envio de datos de contacto
 * @author victorherrera
 *
 */
public class Contacto {
	@NotNull(message="Favor de proporcionar el nombre de contacto")
	@Size(min=1,max=70,message="Favor de proporcionar el nombre del contacto, maximo 70 caracteres")
	private String nombre;
	
	@NotNull(message="Favor de proporcionar el email de contacto")
	@Size(min=1,max=70,message="Favor de proporcionar el email del contacto, maximo 70 caracteres")
	private String email;
	
	@NotNull(message="Favor de proporcionar el email de contacto")
	@Size(min=1,max=20,message="Favor de proporcionar el telefono del contacto, maximo 20 digitos")
	private String telefono;
	
	@NotNull(message="Favor de proporcionar el asunto ")
	@Size(min=1,max=50,message="Favor de proporcionar el asunto, maximo 50 caracteres")
	private String asunto;
	
	@NotNull(message="Favor de proporcionar sus comentarios ")
	@Size(min=1,max=500,message="Favor de proporcionar sus comentarios, maximo 500 caracteres")
	private String comentario;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
