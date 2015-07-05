package com.quuiko.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.quuiko.beans.BusinessUser;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.Producto;
import com.quuiko.exception.QRocksException;
import com.quuiko.gcm.service.BusinessUserService;
import com.quuiko.services.NegocioService;
import com.quuiko.services.ProductoService;

@Namespace("/console")
@ParentPackage("qrocks-default")
public class AdminAction extends QRocksAction{
	private static Logger log=Logger.getLogger(MesaControlAction.class);
	private static final String ACTION_ALL_USERS="users";
	private static final String ACTION_SEARCH_USERS="searchUsers";
	private static final String PAGE_ALL_USERS="/admin/usuariosNegocios.jsp";
	private static final String ACTION_ENABLED_DISABLED_USER="updateUsr";
	private static final String ACTION_SIMULATE_MASSIVE_RECORDS="massive";
	private static final int NUM_NEGOCIOS=1000;
	private static final int NUM_PRODUCTOS_POR_NEGOCIO=30;
	private int paginaActual=0;
	private int conteo=0;
	private int numPaginas=0;
	private static final Integer numRegistrosPaginados=10;
	private BusinessUser filtro;
	private List<BusinessUser> usuarios=new ArrayList<BusinessUser>();
	private BusinessUser usuario;
	
	@Autowired
	private BusinessUserService businessUserService;
	@Autowired
	private NegocioService negocioService;
	@Autowired
	private ProductoService productoService;
	
	@Action(value=ACTION_ALL_USERS,results={@Result(name=SUCCESS,location=PAGE_ALL_USERS)})
	public String mostrarUsuarios(){
		conteo=businessUserService.conteoDetallePorBusqueda(filtro);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		usuarios=businessUserService.filtrarPaginado(filtro, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_SEARCH_USERS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","numPaginas,conteo,usuarios\\[\\d+\\]\\..*"})
	})
	public String consultarUsuarios(){
		conteo=businessUserService.conteoDetallePorBusqueda(filtro);
		numPaginas=(conteo%numRegistrosPaginados>0)?conteo/numRegistrosPaginados+1:conteo/numRegistrosPaginados;
		usuarios=businessUserService.filtrarPaginado(filtro, paginaActual*numRegistrosPaginados, numRegistrosPaginados);
		return result;
	}
	
	@Action(value=ACTION_ENABLED_DISABLED_USER,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","numPaginas,conteo,usuarios\\[\\d+\\]\\..*"})
	})
	public String actualizarUsuario(){
		try {
			businessUserService.desactivarUsuario(usuario);
		} catch (QRocksException e) {
			e.printStackTrace();
		}
		consultarUsuarios();
		return result;
	}
	
	@Action(value=ACTION_SIMULATE_MASSIVE_RECORDS,results={
		@Result(name=SUCCESS,type="json",params={"ignoreHierarchy","false","noCache","true","includeProperties","messageResult,messageCode"})
	})
	public String inserccionMasiva(){
		log.info("Iniciando carga masiva de negocios.Num de Negocios a insertar:"+NUM_NEGOCIOS+" con "+NUM_PRODUCTOS_POR_NEGOCIO+" productos por negocio.");
		for(int i=0;i<NUM_NEGOCIOS;i++){
			BusinessUser nuevoUsuario=new BusinessUser();
			nuevoUsuario.setBusinessKey("root");
			nuevoUsuario.setCreationDate(new Date());
			nuevoUsuario.setEnabled(1);
			nuevoUsuario.setName("TEST_"+i);
			nuevoUsuario.setUsername("test_"+i);
			nuevoUsuario.setUsrType("USR");
			try {
				businessUserService.guardar(nuevoUsuario);
				onSuccessMessage("Se registro exitosamente los negocios.");
			} catch (QRocksException e) {
				e.printStackTrace();
				onErrorMessage(e);
			}
		}
		log.info("Se termina la carga masiva de negocios");
		for(int j=0;j<NUM_NEGOCIOS;j++){
			try {
				Negocio negocio=negocioService.consultarPorUsuario("test_"+j);
				for(int i=0;i<NUM_PRODUCTOS_POR_NEGOCIO;i++){
					Producto producto=new Producto();
					producto.setActivo(1);
					producto.setCosto(99.99d);
					producto.setDescripcion("Nara de nara");
					producto.setNombre("producto_"+i);
					producto.setNegocio(negocio);
					producto.setTipoProducto("OTROS");
					try{
						productoService.registrarProducto(producto);
					}catch(QRocksException e){
						log.info("No se logro guardar el producto: producto_"+i+"  debido a:"+e.getMessage());
					}
				}
			} catch (QRocksException e) {
				log.info("No se encontro un negocio con el usuario: test_"+j);
			}
		}
		return result;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}

	public BusinessUser getFiltro() {
		return filtro;
	}

	public void setFiltro(BusinessUser filtro) {
		this.filtro = filtro;
	}

	public List<BusinessUser> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<BusinessUser> usuarios) {
		this.usuarios = usuarios;
	}

	public static Integer getNumregistrospaginados() {
		return numRegistrosPaginados;
	}

	public BusinessUser getUsuario() {
		return usuario;
	}

	public void setUsuario(BusinessUser usuario) {
		this.usuario = usuario;
	}
}
