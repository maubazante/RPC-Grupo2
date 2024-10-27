package com.unla.stockearte.helpers;

import java.util.List;

import com.example.catalogos.CatalogoDTO;
import com.example.catalogos.ListCatalogoResponse;
import com.example.filtroordenes.FiltroOrdenesDTO;
import com.example.filtroordenes.GetFiltroOrdenResponse;
import com.example.filtroordenes.ObjectFactory;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.FiltroOrdenes;

public class CatalogoHelper {

	/**
	 * Helper para crear un FiltroOrdenesDTO a partir de un objeto FiltroOrdenes
	 * 
	 * @param entity {@code FiltroOrdenes}
	 * @return {@code FiltroOrdenesDTO}
	 */
	public static FiltroOrdenesDTO filtroOrdenesToSoapDTO(FiltroOrdenes entity) {
		ObjectFactory factory = new ObjectFactory();
		FiltroOrdenesDTO filtroOrdenesDTO = new FiltroOrdenesDTO();
		filtroOrdenesDTO.setFiltroEstado(factory.createFiltroOrdenesDTOFiltroEstado(entity.getFiltroEstado()));
		filtroOrdenesDTO.setFiltroFecha(factory.createFiltroOrdenesDTOFiltroFecha(entity.getFiltroFecha()));
		filtroOrdenesDTO.setFiltroProducto(factory.createFiltroOrdenesDTOFiltroProducto(entity.getFiltroProducto()));
		filtroOrdenesDTO.setFiltroTienda(factory.createFiltroOrdenesDTOFiltroTienda(entity.getFiltroTienda()));
		filtroOrdenesDTO.setFkUsuariosId(factory.createFiltroOrdenesDTOFkUsuariosId(entity.getFkUsuariosId()));
		filtroOrdenesDTO.setId(factory.createFiltroOrdenesDTOId(entity.getId()));
		filtroOrdenesDTO.setNombre(entity.getNombre());

		return filtroOrdenesDTO;
	}

	/**
	 * Helper para crear un GetFiltroOrdenResponse a partir de un FiltroOrdenes
	 * 
	 * @param filtroOrden {@code FiltroOrdenes}
	 * @return {@code GetFiltroOrdenResponse}
	 */
	public static GetFiltroOrdenResponse crearGetFiltroResponse(FiltroOrdenes filtroOrden) {
		ObjectFactory factory = new ObjectFactory();
		GetFiltroOrdenResponse getFiltroOrdenResponse = new GetFiltroOrdenResponse();
		FiltroOrdenesDTO filtroOrdenesDTO = filtroOrdenesToSoapDTO(filtroOrden);
		getFiltroOrdenResponse.setFiltroOrdenes(factory.createGetFiltroOrdenResponseFiltroOrdenes(filtroOrdenesDTO));

		return getFiltroOrdenResponse;
	}

	public static ListCatalogoResponse getCatalogosResponse(List<Catalogo> catalogos) {
		com.example.catalogos.ObjectFactory factory = new com.example.catalogos.ObjectFactory();
		ListCatalogoResponse listCatalogoResponse = new ListCatalogoResponse();

		for (Catalogo c : catalogos) {
			CatalogoDTO catalogoDTO = new CatalogoDTO();
			catalogoDTO.setId(factory.createCatalogoDTOId(c.getId()));
		}
		// return getFiltroOrdenResponse;
		return null;
	}

}
