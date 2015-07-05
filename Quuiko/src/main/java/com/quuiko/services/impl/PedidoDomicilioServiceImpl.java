package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.isNull;
import static com.quuiko.util.Utileria.onError;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.AppUser;
import com.quuiko.beans.Negocio;
import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.Producto;
import com.quuiko.beans.PedidoDomicilio.EstatusPedidoDomicilio;
import com.quuiko.beans.PedidoProductoDomicilio;
import com.quuiko.daos.PedidoDomicilioDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.AppUserService;
import com.quuiko.services.PedidoDomicilioService;
import com.quuiko.services.PedidoProductoDomicilioService;
import com.quuiko.services.ProductoService;
import com.quuiko.util.Utileria;

@Service
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PedidoDomicilioServiceImpl implements PedidoDomicilioService{
	@Autowired
	private PedidoDomicilioDAO pedidoDomicilioDao;
	@Autowired
	private PedidoProductoDomicilioService pedidoProductoDomicilioService;
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private AppUserService appUserService;

	/**
	 * Guarda / Actualiza pedido domicilio
	 * @param pedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardarPedido(PedidoDomicilio pedido) throws QRocksException{
		if(pedido!=null && Utileria.isEmptyCollection(pedido.getListaProductos())){
			onError("Favor de agregar al menos un producto a su pedido");
		}
		Long idUsuario=(pedido.getAppUser()!=null)?pedido.getAppUser().getId():null;
		String userName=(pedido.getAppUser()!=null)?pedido.getAppUser().getUserEmail():null;
		AppUser appUser=null;
		if(idUsuario!=null){
			appUser=appUserService.getUserById(idUsuario);
		}
		if(appUser==null && Utileria.isValid(userName)){
			appUser=appUserService.getUserByUserEmail(userName);
		}
		pedido.setEstatus(EstatusPedidoDomicilio.PENDIENTE.clave);
		pedido.setAppUser(appUser);
		pedido.setCambio(0d);
		Double total=0d;
		pedido.setTotal(total);
		pedidoDomicilioDao.guardar(pedido);
		for(PedidoProductoDomicilio producto:pedido.getListaProductos()){
			producto.setPedido(pedido);
			Producto p=productoService.consultarProductoPorId(producto.getProducto().getId());
			if(p==null){
				onError("El producto elegido no existe en el menu del restaurante");
			}
			producto.setProducto(p);
			producto.setCosto(p.getCosto());
			total+=p.getCosto();
			pedidoProductoDomicilioService.guardar(producto);
		}
		Double feria=pedido.getFeria();
		Double cambio=feria-total;
		pedido.setCambio(cambio);
		pedido.setTotal(total);
		pedidoDomicilioDao.guardar(pedido);
	}
	
	/**
	 * Obtiene todos los peedidos del negocio
	 * @param idNegocio
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoDomicilio> consultarPedidosPorNegocio(Long idNegocio) throws QRocksException{
		PedidoDomicilio filtro=new PedidoDomicilio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		return pedidoDomicilioDao.filtrar(filtro);
	}
	
	public List<PedidoDomicilio> consultarPedidosPendientesPorNegocio(Long idNegocio) throws QRocksException{
		if(isNull(idNegocio)){
			onError("Favor de proporcionar el negocio");
		}
		List<PedidoDomicilio> pedidos=null;
		PedidoDomicilio filtro=new PedidoDomicilio();
		Negocio negocio=new Negocio();
		negocio.setId(idNegocio);
		filtro.setNegocio(negocio);
		EstatusPedidoDomicilio estatusPendiente=EstatusPedidoDomicilio.PENDIENTE;
		filtro.setEstatus(EstatusPedidoDomicilio.PENDIENTE.clave);
		pedidos=pedidoDomicilioDao.filtrar(filtro);
		return pedidos;
	}
	
	public PedidoDomicilio consultarPedido(Long idPedido) throws QRocksException{
		return pedidoDomicilioDao.consultarPedido(idPedido);
	}
	
	/**
	 * Actualiza el estatus de un pedido
	 * @param idPedido
	 * @param estatus
	 * @throws QRocksException
	 */
	public void actualizarEstatus(Long idPedido, EstatusPedidoDomicilio estatus) throws QRocksException{
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		pedido.setEstatus(estatus.clave);
		pedidoDomicilioDao.actualizar(pedido);
	}
	/**
	 * Cambia el estatus de un pedido a autorizado, 
	 * Regla: Solo los PENDIENTES pueden autorizarse
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarAutorizado(Long idPedido) throws QRocksException{
		EstatusPedidoDomicilio estatusDestino=EstatusPedidoDomicilio.AUTORIZADO;
		EstatusPedidoDomicilio[] estatusOrigenPermitidos={EstatusPedidoDomicilio.PENDIENTE};
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		String estatusAct=pedido.getEstatus();
		EstatusPedidoDomicilio estatusActual=(EstatusPedidoDomicilio.obtenerEstatus(estatusAct));
		if(isNull(estatusActual)){
			onError("Imposible actualizar el estatus, el pedido no tiene estatus actual.");
		}
		List<EstatusPedidoDomicilio> listaEstatusPermitidos=Arrays.asList(estatusOrigenPermitidos);
		if(listaEstatusPermitidos.contains(estatusActual)){
			pedido.setEstatus(estatusDestino.clave);
			pedidoDomicilioDao.actualizar(pedido);
		}
	}
	
	/**
	 * Cambia el estatus de un pedido
	 * Regla: Solo los Pendientes pueden rechazarse 
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarRechazado(Long idPedido) throws QRocksException{
		EstatusPedidoDomicilio estatusDestino=EstatusPedidoDomicilio.RECHAZADO;
		EstatusPedidoDomicilio[] estatusOrigenPermitidos={EstatusPedidoDomicilio.PENDIENTE};
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		String estatusAct=pedido.getEstatus();
		EstatusPedidoDomicilio estatusActual=(EstatusPedidoDomicilio.obtenerEstatus(estatusAct));
		if(isNull(estatusActual)){
			onError("Imposible actualizar el estatus, el pedido no tiene estatus actual.");
		}
		List<EstatusPedidoDomicilio> listaEstatusPermitidos=Arrays.asList(estatusOrigenPermitidos);
		if(listaEstatusPermitidos.contains(estatusActual)){
			pedido.setEstatus(estatusDestino.clave);
			pedidoDomicilioDao.actualizar(pedido);
		}
	}
	
	/**
	 * Cambia el estatus de un pedido a "Atendiendo"
	 * Regla: Solo los Autorizados pueden marcarse como "Atendiendo"
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarAtentido(Long idPedido) throws QRocksException{
		EstatusPedidoDomicilio estatusDestino=EstatusPedidoDomicilio.ATENDIENDO;
		EstatusPedidoDomicilio[] estatusOrigenPermitidos={EstatusPedidoDomicilio.AUTORIZADO};
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		String estatusAct=pedido.getEstatus();
		EstatusPedidoDomicilio estatusActual=(EstatusPedidoDomicilio.obtenerEstatus(estatusAct));
		if(isNull(estatusActual)){
			onError("Imposible actualizar el estatus, el pedido no tiene estatus actual.");
		}
		List<EstatusPedidoDomicilio> listaEstatusPermitidos=Arrays.asList(estatusOrigenPermitidos);
		if(listaEstatusPermitidos.contains(estatusActual)){
			pedido.setEstatus(estatusDestino.clave);
			pedidoDomicilioDao.actualizar(pedido);
		}
	}
	
	/**
	 * Cambia el estatus de un pedido a Listo para entregarse
	 * Regla: Solo los atendidos o autorizados pueden cambiarse a Listo para entregar
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarListoParaEntregar(Long idPedido) throws QRocksException{
		EstatusPedidoDomicilio estatusDestino=EstatusPedidoDomicilio.LISTO;
		EstatusPedidoDomicilio[] estatusOrigenPermitidos={EstatusPedidoDomicilio.AUTORIZADO,EstatusPedidoDomicilio.ATENDIENDO};
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		String estatusAct=pedido.getEstatus();
		EstatusPedidoDomicilio estatusActual=(EstatusPedidoDomicilio.obtenerEstatus(estatusAct));
		if(isNull(estatusActual)){
			onError("Imposible actualizar el estatus, el pedido no tiene estatus actual.");
		}
		List<EstatusPedidoDomicilio> listaEstatusPermitidos=Arrays.asList(estatusOrigenPermitidos);
		if(listaEstatusPermitidos.contains(estatusActual)){
			pedido.setEstatus(estatusDestino.clave);
			pedidoDomicilioDao.actualizar(pedido);
		}
	}
	
	/**
	 * Cambia el estatus de un pedido a cancelado
	 * Regla: Cualquier estatus menos Cancelado puede cancelarse
	 * @param idPedido
	 * @throws QRocksException
	 */
	public void marcarCancelado(Long idPedido) throws QRocksException{
		EstatusPedidoDomicilio estatusDestino=EstatusPedidoDomicilio.CANCELADO;
		EstatusPedidoDomicilio[] estatusOrigenPermitidos={EstatusPedidoDomicilio.PENDIENTE,EstatusPedidoDomicilio.ATENDIENDO,EstatusPedidoDomicilio.AUTORIZADO,EstatusPedidoDomicilio.LISTO, EstatusPedidoDomicilio.RECHAZADO};
		PedidoDomicilio pedido=consultarPedido(idPedido);
		if(isNull(pedido)){
			onError("No existe un pedido con el folio "+idPedido);
		}
		String estatusAct=pedido.getEstatus();
		EstatusPedidoDomicilio estatusActual=(EstatusPedidoDomicilio.obtenerEstatus(estatusAct));
		if(isNull(estatusActual)){
			onError("Imposible actualizar el estatus, el pedido no tiene estatus actual.");
		}
		List<EstatusPedidoDomicilio> listaEstatusPermitidos=Arrays.asList(estatusOrigenPermitidos);
		if(listaEstatusPermitidos.contains(estatusActual)){
			pedido.setEstatus(estatusDestino.clave);
			pedidoDomicilioDao.actualizar(pedido);
		}
	}
	
	/**
	 * Filtra los resultados bajo ciertos criterios de busqueda.
	 * @param filtro
	 * @return
	 */
	public List<PedidoDomicilio> filtrar(PedidoDomicilio filtro){
		return pedidoDomicilioDao.filtrar(filtro);
	}
}
