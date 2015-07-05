package com.quuiko.beans;

import static com.quuiko.util.Utileria.castToDate;
import static com.quuiko.util.Utileria.isNotNull;
import static com.quuiko.util.Utileria.isValid;
import static com.quuiko.util.Utileria.obtenerSoloFecha;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name="businessUser")
@Table(name="_businessUser")
public class BusinessUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@Column(name="username")
	private String username;
	@Column(name="name")
	private String name;
	@Column(name="businessKey")
	private String businessKey;
	@Column(name="enabled")
	private Integer enabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate")
	private Date creationDate;
	
	@Transient
	private String creationDateStr;
	
	@Temporal(TemporalType.DATE)
	@Column(name="expirationDate")
	private Date expirationDate;
	
	@Transient
	private String expirationDateStr;
	
	@Column(name="usrType")
	private String usrType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getUsrType() {
		return usrType;
	}
	public void setUsrType(String usrType) {
		this.usrType = usrType;
	}
	public String getCreationDateStr() {
		if(isNotNull(creationDate)){
			creationDateStr=obtenerSoloFecha(creationDate);
		}
		return creationDateStr;
	}
	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
		if(isValid(creationDateStr)){
			Date fecha=castToDate(creationDateStr);
			creationDate=fecha;
		}
	}
	public String getExpirationDateStr() {
		if(isNotNull(expirationDate)){
			expirationDateStr=obtenerSoloFecha(expirationDate);
		}
		return expirationDateStr;
	}
	public void setExpirationDateStr(String expirationDateStr) {
		this.expirationDateStr = expirationDateStr;
		this.expirationDateStr = expirationDateStr;
		if(isValid(expirationDateStr)){
			Date fecha=castToDate(expirationDateStr);
			expirationDate=fecha;
		}
	}
}
