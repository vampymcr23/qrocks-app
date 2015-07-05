package com.quuiko.dtos.mobile;
/**
 * Clase que deben de heredar cada clase que manejara objetos JSON via http cuando se solicite por el dispositivo mobil.
 * Esto con la finalidad de que cada clase tenga automaticamente estas 2 propiedades en la respuesta JSON por si sale 
 * algun error.
 * Cuando requestCode=-1 entonces significa que hubo un error
 * @author victorherrera
 *
 */
public class JSONDTO {
	private Integer requestCode;
	private String errorMessage;
	public Integer getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(Integer requestCode) {
		this.requestCode = requestCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
