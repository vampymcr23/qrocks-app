package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

import com.quuiko.util.QRocksCache;

@Entity(name="cliente")
@Table(name="cliente")
@Cache(
	isolation=CacheIsolationType.PROTECTED,
	type=CacheType.SOFT,
	size=20000,
	expiry=QRocksCache.ONE_MINUTE
)
public class Cliente implements Serializable{
	@Id
	private Long id;
	
	@Column(name="alias")
	private String alias;
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=PedidoMesa.class)
	@JoinColumn(name="idPedidoMesa")
	private PedidoMesa pedidoMesa;
	
	@Column(name="gcmId")
	private String gcmId;
	
	@Column(name="idAppUser")
	private Long idAppUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public PedidoMesa getPedidoMesa() {
		return pedidoMesa;
	}

	public void setPedidoMesa(PedidoMesa pedidoMesa) {
		this.pedidoMesa = pedidoMesa;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public Long getIdAppUser() {
		return idAppUser;
	}

	public void setIdAppUser(Long idAppUser) {
		this.idAppUser = idAppUser;
	}
}
