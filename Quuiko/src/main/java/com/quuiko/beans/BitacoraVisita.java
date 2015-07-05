package com.quuiko.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.quuiko.dtos.estadisticas.ClienteFestejado;
import com.quuiko.dtos.estadisticas.ClienteMasVisitas;
import com.quuiko.dtos.estadisticas.ProductoMasPedido;

@Entity(name="bitacoraVisita")
@Table(name="bitacoraVisita")
@NamedNativeQueries({
	@NamedNativeQuery(name="numClientesPorNegocio",query="select count(1) from (select distinct gn.idAppUser  from gcmUsrNegocio gn where gn.idNegocio=1) temp"),
	@NamedNativeQuery(name="productoMasPedido",query="" +
		"select t.id as clave,t.producto as producto,t.numVecesPedida as numVecesPedida,0 as numEstrellas from( "+
			" select p.id,p.nombre as producto, count(1) as numVecesPedida from pedidoIndividual pi "+
			" inner join producto p on pi.idProducto=p.id "+
			" inner join cliente c on c.id=pi.idCliente "+
			" inner join pedidoMesa pm on pm.id=c.idPedidoMesa "+ 
			" inner join mesa m on m.id=pm.idMesa  "+
			" where m.idNegocio=?1 "+
			" and (?2 is null or pm.fecha >= ?2 ) "+ 
			" and (?3 is null or pm.fecha <=?3 ) "+
			" group by p.id,p.nombre  "+
		" ) t order by t.numVecesPedida desc,t.producto asc ",resultClass=ProductoMasPedido.class),
	@NamedNativeQuery(name="clienteMasVisitas", query=""+
		" select id as clave,UPPER(t.nombre) as cliente, "+
		" t.numVisitas, 0 as numEstrellas from ( "+
		" 	select u.id,u.nombre, count(1) as numVisitas "+ 
		" 	from bitacoraVisita b  "+
		" 	inner join appUsers u on b.idAppUser=u.id "+ 
		" 	where b.idNegocio=?1 "+
		" 	and (?2 is null or b.fechaVisita >= ?2 ) "+ 
		" 	and (?3 is null or b.fechaVisita <=?3 ) "+
		" 	group by u.id,u.nombre "+
		" ) t order by numVisitas desc ",resultClass=ClienteMasVisitas.class),
	@NamedNativeQuery(name="clientesCumplanieros",query=""+
		"select m.claveMesa as claveMesa,u.alias,u.id as gcmId,u.nombre as nombreCliente, u.fecHaNacimiento,(year(current_date) - year(u.fechaNacimiento) )as numAnios  from pedidoMesa pm "+ 
		" inner join cliente c on c.idPedidoMesa=pm.id "+
		" inner join appUsers u on u.id=c.idAppUser  "+
		" inner join mesa m on m.id=pm.idMesa "+
		" inner join negocio n on n.id=m.idNegocio "+ 
		" where n.id=?1 "+
		" and date(pm.fecha)=current_date "+
		" and pm.activo=1 "+
		" and (day(u.fechaNacimiento)= day(current_date) and month(u.fechaNacimiento)=month(current_date))",resultClass=ClienteFestejado.class)
	
})
public class BitacoraVisita implements Serializable{
	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=GCMUser.class)
	@JoinColumn(name="gcmId")
	@NotNull(message="Favor de proporcionar la clave del usuario")
	private GCMUser user;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=AppUser.class)
	@JoinColumn(name="idAppUser")
	private AppUser appUser;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Negocio.class)
	@JoinColumn(name="idNegocio")
	@NotNull(message="Favor de proporcionar la clave del negocio")
	private Negocio negocio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaVisita")
	private Date fechaVisita;
	
	@Transient
	private Date fechaVisitaFin;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GCMUser getUser() {
		return user;
	}
	public void setUser(GCMUser user) {
		this.user = user;
	}
	public Negocio getNegocio() {
		return negocio;
	}
	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	public Date getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public Date getFechaVisitaFin() {
		return fechaVisitaFin;
	}
	public void setFechaVisitaFin(Date fechaVisitaFin) {
		this.fechaVisitaFin = fechaVisitaFin;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
}
