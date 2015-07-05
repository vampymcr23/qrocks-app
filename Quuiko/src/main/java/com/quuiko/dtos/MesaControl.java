package com.quuiko.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;

@SqlResultSetMapping(name="mesaControl.resumen",entities={
	@EntityResult(entityClass=MesaControl.class,fields={
		@FieldResult(name="idMesa", column="idMesa"),
		@FieldResult(name="folioPedido", column="folioPedido"),
		@FieldResult(name="claveMesa", column="claveMesa"),
		@FieldResult(name="numPersonas", column="numPersonas"),
		@FieldResult(name="llamarMesero", column="llamarMesero"),
		@FieldResult(name="solicitarCuenta", column="solicitarCuenta"),
		@FieldResult(name="solicitarNuevoPedido", column="solicitarNuevoPedido"),
		@FieldResult(name="idMesero", column="idMesero"),
		@FieldResult(name="nomMesero", column="nomMesero")
	})
})
@NamedNativeQueries({
	@NamedNativeQuery(name="verMesaControl",query="select pm.id as folioPedido,m.claveMesa,(select count(c.id) from cliente c where c.idPedidoMesa=pm.id) as numPersonas,pm.llamarMesero,pm.solicitarCuenta,pm.solicitarNuevoPedido   from mesa m left join pedidoMesa pm on m.id=pm.idMesa and pm.activo=1",resultSetMapping="mesaControl.resumen"),
	@NamedNativeQuery(name="verMesaControlPorNegocio",query="select m.id as idMesa, pm.id as folioPedido,m.claveMesa,(select count(c.id) from cliente c where c.idPedidoMesa=pm.id) as numPersonas,pm.llamarMesero,pm.solicitarCuenta,pm.solicitarNuevoPedido , me.id as idMesero,me.nombre as nomMesero  from mesa m left join pedidoMesa pm on m.id=pm.idMesa and pm.activo=1 inner join negocio n on m.idNegocio=n.id left join mesero me on me.id=m.idMesero where n.id= ? order by pm.solicitarNuevoPedido desc, m.claveMesa asc ",resultSetMapping="mesaControl.resumen"),
	@NamedNativeQuery(name="verMesaControlPorNegocioMesero",query="select m.id as idMesa, pm.id as folioPedido,m.claveMesa,(select count(c.id) from cliente c where c.idPedidoMesa=pm.id) as numPersonas,pm.llamarMesero,pm.solicitarCuenta,pm.solicitarNuevoPedido , me.id as idMesero,me.nombre as nomMesero  from mesa m left join pedidoMesa pm on m.id=pm.idMesa and pm.activo=1 inner join negocio n on m.idNegocio=n.id left join mesero me on me.id=m.idMesero where n.id= ? and me.id=? order by pm.solicitarNuevoPedido desc, m.claveMesa asc ",resultSetMapping="mesaControl.resumen")
})
@Entity
@Cache(
	type=CacheType.SOFT,//se genera cache hasta que el limite de la memoria sea baja
	isolation=CacheIsolationType.PROTECTED,//Protegida para que las relaciones que no sean Cacheables puedan establecerse correctamente
	expiry=QRocksCache.MIDDLE_MINUTE,
	size=1000//num de objetos a almacenar en cache
)
public class MesaControl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5318324082957435061L;
	@Id
	private Long idMesa;
	private Integer folioPedido;
	private String claveMesa;
	private Integer numPersonas;
	private Integer llamarMesero;
	private Integer solicitarCuenta;
	private Integer solicitarNuevoPedido;
	private Long idMesero;
	private String nomMesero;
	
	public Long getIdMesa() {
		return idMesa;
	}
	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}
	public Integer getFolioPedido() {
		return folioPedido;
	}
	public void setFolioPedido(Integer folioPedido) {
		this.folioPedido = folioPedido;
	}
	public String getClaveMesa() {
		return claveMesa;
	}
	public void setClaveMesa(String claveMesa) {
		this.claveMesa = claveMesa;
	}
	public Integer getNumPersonas() {
		return numPersonas;
	}
	public void setNumPersonas(Integer numPersonas) {
		this.numPersonas = numPersonas;
	}
	public Integer getLlamarMesero() {
		return llamarMesero;
	}
	public void setLlamarMesero(Integer llamarMesero) {
		this.llamarMesero = llamarMesero;
	}
	public Integer getSolicitarCuenta() {
		return solicitarCuenta;
	}
	public void setSolicitarCuenta(Integer solicitarCuenta) {
		this.solicitarCuenta = solicitarCuenta;
	}
	public Integer getSolicitarNuevoPedido() {
		return solicitarNuevoPedido;
	}
	public void setSolicitarNuevoPedido(Integer solicitarNuevoPedido) {
		this.solicitarNuevoPedido = solicitarNuevoPedido;
	}
	public Long getIdMesero() {
		return idMesero;
	}
	public void setIdMesero(Long idMesero) {
		this.idMesero = idMesero;
	}
	public String getNomMesero() {
		return nomMesero;
	}
	public void setNomMesero(String nomMesero) {
		this.nomMesero = nomMesero;
	}
}
