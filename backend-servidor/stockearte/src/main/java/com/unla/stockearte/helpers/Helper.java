package com.unla.stockearte.helpers;

import java.security.SecureRandom;
import java.util.List;

import com.example.filtroordenes.FiltroOrdenesDTO;
import com.example.filtroordenes.FiltroOrdenesDeleteResponse;
import com.example.filtroordenes.FiltroOrdenesList;
import com.example.filtroordenes.GetFiltroOrdenResponse;
import com.example.filtroordenes.ListFiltroOrdenesResponse;
import com.example.filtroordenes.ObjectFactory;
import com.example.filtroordenes.UpdateFiltroOrdenRequest;
import com.example.filtroordenes.UpdateFiltroOrdenResponse;
import com.unla.stockearte.model.FiltroOrdenes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Helper {

	private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int LONGITUD_CADENA = 10;
	private static final SecureRandom random = new SecureRandom();

	public static String generarCadenaAleatoria() {
		StringBuilder sb = new StringBuilder(LONGITUD_CADENA);
		for (int i = 0; i < LONGITUD_CADENA; i++) {
			int indiceAleatorio = random.nextInt(CARACTERES.length());
			char caracterAleatorio = CARACTERES.charAt(indiceAleatorio);
			sb.append(caracterAleatorio);
		}
		return sb.toString();
	}

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
	 * Helper para crear un objeto FiltroOrdenesDeleteResponse a partir de los
	 * valores primitivos que necesita el objeto
	 * 
	 * @param resp
	 * @return {@code FiltroOrdenesDeleteResponse}
	 */
	public static FiltroOrdenesDeleteResponse crearFiltroOrdenesDeleteResponse(Boolean resp) {
		ObjectFactory factory = new ObjectFactory();
		FiltroOrdenesDeleteResponse filtroDeleteResponse = new FiltroOrdenesDeleteResponse();
		filtroDeleteResponse.setResponse(factory.createFiltroOrdenesDeleteResponseResponse(resp));

		return filtroDeleteResponse;
	}

	/**
	 * Helper para crear ListFiltroOrdenesResponse a partir de una lista de filtro
	 * de ordenes
	 * 
	 * @param ordenes {@code List<FiltroOrdenes>}
	 * @return {@code ListFiltroOrdenesResponse}
	 */
	public static ListFiltroOrdenesResponse crearListFiltroOrdenesResponse(List<FiltroOrdenes> ordenes) {
		ListFiltroOrdenesResponse filtroOrdenesResponse = new ListFiltroOrdenesResponse();
		FiltroOrdenesList filtroOrdenesList = new FiltroOrdenesList();
		filtroOrdenesResponse.setFiltroOrdenesList(filtroOrdenesList);
		for (FiltroOrdenes o : ordenes) {
			filtroOrdenesResponse.getFiltroOrdenesList().getFiltroOrdenes().add(filtroOrdenesToSoapDTO(o));
		}

		return filtroOrdenesResponse;
	}
	
	/**
	 * Helper para crear un GetFiltroOrdenResponse a partir de un FiltroOrdenes
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
	
	/**
	 * Helpar para crear un UpdateFiltroOrdenResponse a partir de un FiltroOrdenes
	 * @param filtroOrden {@code FiltroOrdenes}
	 * @return {@code UpdateFiltroOrdenResponse}
	 */
	public static UpdateFiltroOrdenResponse crearupdateFiltroOrdenResponse(FiltroOrdenes entity) {
		ObjectFactory factory = new ObjectFactory();
		UpdateFiltroOrdenResponse updateFiltroOrdenResponse = new UpdateFiltroOrdenResponse();
		FiltroOrdenesDTO filtroOrdenesDTO = new FiltroOrdenesDTO();
		filtroOrdenesDTO.setFiltroEstado(factory.createFiltroOrdenesDTOFiltroEstado(entity.getFiltroEstado()));
		filtroOrdenesDTO.setFiltroFecha(factory.createFiltroOrdenesDTOFiltroFecha(entity.getFiltroFecha()));
		filtroOrdenesDTO.setFiltroProducto(factory.createFiltroOrdenesDTOFiltroProducto(entity.getFiltroProducto()));
		filtroOrdenesDTO.setFiltroTienda(factory.createFiltroOrdenesDTOFiltroTienda(entity.getFiltroTienda()));
		filtroOrdenesDTO.setFkUsuariosId(factory.createFiltroOrdenesDTOFkUsuariosId(entity.getFkUsuariosId()));
		filtroOrdenesDTO.setId(factory.createFiltroOrdenesDTOId(entity.getId()));
		filtroOrdenesDTO.setNombre(entity.getNombre());
		
		updateFiltroOrdenResponse.setFiltroOrdenes(factory.createUpdateFiltroOrdenResponseFiltroOrdenes(filtroOrdenesDTO));
		
		return updateFiltroOrdenResponse;
	}
	
	
	public static FiltroOrdenes crearFiltroOrdenAtravesUpdateFiltroRequest(UpdateFiltroOrdenRequest request) {
		FiltroOrdenes filtroOrdenes = new FiltroOrdenes();
		filtroOrdenes.setFiltroEstado(request.getFiltroEstado().getValue());
		filtroOrdenes.setFiltroFecha(request.getFiltroFecha().getValue());
		filtroOrdenes.setFiltroProducto(request.getFiltroProducto().getValue());
		filtroOrdenes.setFiltroTienda(request.getFiltroTienda().getValue());
		filtroOrdenes.setFkUsuariosId(request.getUserId().getValue());
		filtroOrdenes.setNombre(request.getNombre());
		filtroOrdenes.setId(request.getId().getValue());
		
		return filtroOrdenes;
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	public static class UnauthorizedException extends RuntimeException {
		public UnauthorizedException(String message) {
			super(message);
		}
	}
}
