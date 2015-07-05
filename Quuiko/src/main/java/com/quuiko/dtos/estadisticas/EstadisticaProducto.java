package com.quuiko.dtos.estadisticas;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@SqlResultSetMappings({
	@SqlResultSetMapping(name="estadisticaProductosMapping",entities={
		@EntityResult(entityClass=EstadisticaProducto.class,fields={
			@FieldResult(name="id",column="id"),
			@FieldResult(name="clave",column="clave"),
			@FieldResult(name="producto",column="producto"),
			@FieldResult(name="numVecesPedida",column="numVecesPedida"),
			@FieldResult(name="numEstrellas",column="numEstrellas"),
			@FieldResult(name="mes",column="mes"),
			@FieldResult(name="mesStr",column="mesStr")
		})
	})
})
@NamedNativeQueries({
	@NamedNativeQuery(name="estadisticaProductos",resultSetMapping="estadisticaProductosMapping",query=""+
			" select concat(t.mes,concat('-',t.id)) as id,t.id as clave,t.producto as producto,t.numVecesPedida as numVecesPedida,0 as numEstrellas,mesStr, mes from( "+ 
			" 	select p.id,p.nombre as producto, count(1) as numVecesPedida, "+
			"   DATE_FORMAT(pi.fechaRegistro,'%Y-%m') as mesStr , "+
			" 	MONTH(pi.fechaRegistro) as mes "+ 
			" 	from pedidoIndividual pi  "+
			" 	inner join producto p on pi.idProducto=p.id  "+
			" 	inner join cliente c on c.id=pi.idCliente  "+
			" 	inner join pedidoMesa pm on pm.id=c.idPedidoMesa "+ 
			" 	inner join mesa m on m.id=pm.idMesa "+  
			" 	where m.idNegocio=?1 "+
			" 	and (?2 is null or pm.fecha >= ?2 )  "+
			" 	and (?3 is null or pm.fecha <=?3 )  "+
			" 	group by p.id,p.nombre,DATE_FORMAT(pi.fechaRegistro,'%Y-%m') "+  
			" ) t  "+
			" where t.mes between ?4 and ?5 "+
			" order by t.mes asc,t.producto asc "
	)
})
@Entity
public class EstadisticaProducto {
	@Id
	private String id;
	private Long clave;
	private String producto;
	private Integer numVecesPedida;
	private Integer mes;
	private String mesStr;
	
	public Long getClave() {
		return clave;
	}
	public void setClave(Long clave) {
		this.clave = clave;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Integer getNumVecesPedida() {
		return numVecesPedida;
	}
	public void setNumVecesPedida(Integer numVecesPedida) {
		this.numVecesPedida = numVecesPedida;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public String getMesStr() {
//		if(Utileria.isNotNull(mes)){
//			switch(mes){
//				case 1:
//					mesStr="Enero";
//				break;
//				case 2:
//					mesStr="Febrero";
//				break;
//				case 3:
//					mesStr="Marzo";
//				break;
//				case 4:
//					mesStr="Abril";
//				break;
//				case 5:
//					mesStr="Mayo";
//				break;
//				case 6:
//					mesStr="Junio";
//				break;
//				case 7:
//					mesStr="Julio";
//				break;
//				case 8:
//					mesStr="Agosto";
//				break;
//				case 9:
//					mesStr="Septiembre";
//				break;
//				case 10:
//					mesStr="Octubre";
//				break;
//				case 11:
//					mesStr="Noviembre";
//				break;
//				case 12:
//					mesStr="Diciembre";
//				break;
//			}
//		}
		return mesStr;
	}
	public void setMesStr(String mesStr) {
		this.mesStr = mesStr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
