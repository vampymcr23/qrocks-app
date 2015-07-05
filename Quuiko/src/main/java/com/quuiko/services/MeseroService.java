package com.quuiko.services;

import java.util.List;

import com.quuiko.beans.Mesero;
import com.quuiko.exception.QRocksException;

public interface MeseroService {
	public List<Mesero> filtrar(Mesero filtro);
	
	public List<Mesero> listarMeserosPorNegocio(Long idNegocio) throws QRocksException;
	
	/**
	 * Obtiene el numero de registros por una busqueda dependiendo del filtro que se le pase. Se utiliza para la paginacion de resultados
	 * @param filtro
	 * @return Num de registros de esa busqueda.
	 */
	public int conteoDetallePorBusqueda(Mesero filtro);
	/**
	 * Filtra los registros bajo ciertos criterios y los pagina en rangos
	 * @param filtro
	 * @param inicio
	 * @param numRegistros
	 * @return
	 */
	public List<Mesero> filtrarPaginado(Mesero filtro, int inicio,int numRegistros);
	/**
	 * Agrega un nuevo cliente de acuerdo al pedido de una mesa
	 * @param cliente
	 * @param pedido
	 * @return
	 * @throws QRocksException
	 */
	public Mesero agregarMesero(Mesero mesero) throws QRocksException;
	/**
	 * Actualiza los datos de un cliente
	 * @param cliente
	 * @throws QRocksException
	 */
	public void actualizarMesero(Mesero mesero) throws QRocksException;
	/**
	 * Elimina o da de baja a un cliente de un pedido por mesa
	 * @param cliente
	 * @throws QRocksException
	 */
	public void eliminarMesero(Mesero mesero) throws QRocksException;
	/**
	 * Da de baja a un cliente de un pedido por mesa
	 * @param idCliente
	 * @throws QRocksException
	 */
	public void eliminarMesero(Long idMesero) throws QRocksException;
	/**
	 * Consulta los datos de un cliente
	 * @param idCliente
	 * @return
	 * @throws QRocksException
	 */
	public Mesero consultarMesero(Long idMesero) throws QRocksException;
}
