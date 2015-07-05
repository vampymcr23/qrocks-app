package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name="ContractDTO.consultaReporteExcel",entities={
	@EntityResult(entityClass=ContratoDTO.class,fields={
		@FieldResult(name="id",column="contrato"),
		@FieldResult(name="empleado",column="empleado"),
		@FieldResult(name="fecha",column="cashback")
	})
		
})
public class ContratoDTO implements Serializable{
	@Id
	private Long id;
	private String empleado;
	private String fecha;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
