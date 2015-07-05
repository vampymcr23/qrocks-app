package com.quuiko.services.impl;

import static com.quuiko.util.Utileria.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quuiko.beans.PedidoDomicilio;
import com.quuiko.beans.PedidoProductoDomicilio;
import com.quuiko.beans.Producto;
import com.quuiko.daos.PedidoProductoDomicilioDAO;
import com.quuiko.exception.QRocksException;
import com.quuiko.services.PedidoProductoDomicilioService;
import com.quuiko.services.ProductoService;

@Service
public class PedidoProductoDomicilioServiceImpl implements PedidoProductoDomicilioService{
	@Autowired
	private PedidoProductoDomicilioDAO pedidoProductoDomicilioDao;
	@Autowired
	private ProductoService productoService;
	/**
	 * Guarda la info de un producto-pedido
	 * @param pedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void guardar(PedidoProductoDomicilio pedido) throws QRocksException{
		if(pedido==null){
			onError("Favor de elegir su producto");
		}
		if(pedido.getProducto()==null){
			onError("Favor de elegir un producto");
		}
		if(pedido.getPedido()==null ){
			onError("Favor de crear su pedido");
		}
		pedidoProductoDomicilioDao.guardar(pedido);
	}
	
	/**
	 * Elimina un pedido
	 * @param idPedido
	 * @throws QRocksException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void eliminarPedido(Long idPedido) throws QRocksException{
		pedidoProductoDomicilioDao.eliminarPedido(idPedido);
	}
	
	/**
	 * Consulta la informacion de un solo pedido
	 * @param idPedido
	 * @return
	 * @throws QRocksException
	 */
	public PedidoProductoDomicilio consultarPedido(Long idPedido) throws QRocksException{
		return pedidoProductoDomicilioDao.consultarPedido(idPedido);
	}
	
	/**
	 * Obtiene la lista de todos los productos solicitados de este pedido
	 * @param idPedidoDomicilio
	 * @return
	 * @throws QRocksException
	 */
	public List<PedidoProductoDomicilio> consultarProductosPorPedido(Long idPedidoDomicilio) throws QRocksException{
		List<PedidoProductoDomicilio> lista=new ArrayList<PedidoProductoDomicilio>();
		PedidoProductoDomicilio filtro=new PedidoProductoDomicilio();
		PedidoDomicilio pedido=new PedidoDomicilio();
		pedido.setId(idPedidoDomicilio);
		filtro.setPedido(pedido);
		lista=pedidoProductoDomicilioDao.filtrar(filtro);
		return lista;
	}
}
