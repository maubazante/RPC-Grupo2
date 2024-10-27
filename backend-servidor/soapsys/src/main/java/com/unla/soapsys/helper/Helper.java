package com.unla.soapsys.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.filtroordenes.FiltroOrdenesDTO;
import com.example.filtroordenes.FiltroOrdenesDeleteRequest;
import com.example.filtroordenes.GetFiltroOrdenRequest;
import com.example.filtroordenes.ListFiltroOrdenesRequest;
import com.example.filtroordenes.ListFiltroOrdenesResponse;
import com.example.filtroordenes.ObjectFactory;
import com.example.filtroordenes.UpdateFiltroOrdenRequest;
import com.example.filtroordenes.UpdateFiltroOrdenResponse;
import com.unla.soapsys.request.FiltroOrdenesRequest;
import com.unla.soapsys.response.FiltroOrdenes;

public class Helper {

	public static com.example.filtroordenes.FiltroOrdenesRequest filtroOrdenesRequestToSoapRequest(
			FiltroOrdenesRequest request) {
		ObjectFactory factory = new ObjectFactory();
		com.example.filtroordenes.FiltroOrdenesRequest filtroOrdenesRequest = new com.example.filtroordenes.FiltroOrdenesRequest();

		filtroOrdenesRequest.setFiltroEstado(factory.createFiltroOrdenesRequestFiltroEstado(request.getFiltroEstado()));
		filtroOrdenesRequest.setFiltroFecha(factory.createFiltroOrdenesRequestFiltroFecha(request.getFiltroFecha()));
		filtroOrdenesRequest
				.setFiltroProducto(factory.createFiltroOrdenesRequestFiltroProducto(request.getFiltroProducto()));
		filtroOrdenesRequest.setFiltroTienda(factory.createFiltroOrdenesRequestFiltroTienda(request.getFiltroTienda()));
		filtroOrdenesRequest.setId(factory.createFiltroOrdenesRequestId(request.getId()));
		filtroOrdenesRequest.setNombre(request.getNombre());
		filtroOrdenesRequest.setUserId(factory.createFiltroOrdenesRequestUserId(request.getUserId()));

		return filtroOrdenesRequest;
	}

	public static FiltroOrdenes filtroOrdenesDTOToFiltroOrdenes(FiltroOrdenesDTO filtro) {
		FiltroOrdenes filtroOrdenes = new FiltroOrdenes();
		filtroOrdenes.setFiltroEstado(filtro.getFiltroEstado().getValue());
		filtroOrdenes.setFiltroFecha(filtro.getFiltroFecha().getValue());
		filtroOrdenes.setFiltroProducto(filtro.getFiltroProducto().getValue());
		filtroOrdenes.setFiltroTienda(filtro.getFiltroTienda().getValue());
		filtroOrdenes.setFkUsuariosId(filtro.getFkUsuariosId().getValue());
		filtroOrdenes.setId(filtro.getId().getValue());
		filtroOrdenes.setNombre(filtro.getNombre());

		return filtroOrdenes;
	}

	public static FiltroOrdenesDeleteRequest crearFiltroOrdenesDeleteRequest(Long id) {
		ObjectFactory factory = new ObjectFactory();
		FiltroOrdenesDeleteRequest filtroDeleteRequest = new FiltroOrdenesDeleteRequest();
		filtroDeleteRequest.setId(factory.createFiltroOrdenesDeleteRequestId(id));

		return filtroDeleteRequest;
	}

	public static ListFiltroOrdenesRequest crearListFiltroOrdenesRequest(Long usuarioId) {
		ObjectFactory factory = new ObjectFactory();
		ListFiltroOrdenesRequest filtroOrdenesRequest = new ListFiltroOrdenesRequest();
		filtroOrdenesRequest.setUsuarioId(factory.createListFiltroOrdenesRequestUsuarioId(usuarioId));

		return filtroOrdenesRequest;
	}

	public static List<FiltroOrdenes> listFiltroOrdenesAtravesDeListFiltroOrdenesRequest(
			ListFiltroOrdenesResponse listFiltroOrdenesResponse) {
		List<FiltroOrdenes> listFiltro = new ArrayList<>();

		for (FiltroOrdenesDTO filtroDTO : listFiltroOrdenesResponse.getFiltroOrdenesList().getFiltroOrdenes()) {
			listFiltro.add(filtroOrdenesDTOToFiltroOrdenes(filtroDTO));
		}

		return listFiltro;
	}

	public static GetFiltroOrdenRequest crearGetFiltroOrdenRequest(Long id) {
		ObjectFactory factory = new ObjectFactory();
		GetFiltroOrdenRequest getFiltroOrdenRequest = new GetFiltroOrdenRequest();
		getFiltroOrdenRequest.setId(factory.createGetFiltroOrdenRequestId(id));

		return getFiltroOrdenRequest;
	}
	
	public static UpdateFiltroOrdenRequest crearUpdateFiltroOrdenRequest(FiltroOrdenesRequest request) {
		ObjectFactory factory = new ObjectFactory();
		UpdateFiltroOrdenRequest updateFiltroOrdenRequest = new UpdateFiltroOrdenRequest();
		
		updateFiltroOrdenRequest.setFiltroEstado(factory.createUpdateFiltroOrdenRequestFiltroEstado(request.getFiltroEstado()));
		updateFiltroOrdenRequest.setFiltroFecha(factory.createUpdateFiltroOrdenRequestFiltroFecha(request.getFiltroFecha()));
		updateFiltroOrdenRequest.setFiltroProducto(factory.createUpdateFiltroOrdenRequestFiltroProducto(request.getFiltroProducto()));
		updateFiltroOrdenRequest.setFiltroTienda(factory.createUpdateFiltroOrdenRequestFiltroTienda(request.getFiltroTienda()));
		updateFiltroOrdenRequest.setId(factory.createUpdateFiltroOrdenRequestId(request.getId()));
		updateFiltroOrdenRequest.setUserId(factory.createUpdateFiltroOrdenRequestUserId(request.getUserId()));
		updateFiltroOrdenRequest.setNombre(request.getNombre());
		
		return updateFiltroOrdenRequest;
	}
	
	public static FiltroOrdenes crearFiltroOrdenesAtravesDeUpdateFiltroOrdenResponse(UpdateFiltroOrdenResponse updateFiltroOrdenResponse) {
		FiltroOrdenes filtroOrdenes = new FiltroOrdenes();
		
		filtroOrdenes.setFiltroEstado(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getFiltroEstado().getValue());
		filtroOrdenes.setFiltroFecha(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getFiltroFecha().getValue());
		filtroOrdenes.setFiltroProducto(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getFiltroProducto().getValue());
		filtroOrdenes.setFiltroTienda(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getFiltroTienda().getValue());
		filtroOrdenes.setFkUsuariosId(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getFkUsuariosId().getValue());
		filtroOrdenes.setId(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getId().getValue());
		filtroOrdenes.setNombre(updateFiltroOrdenResponse.getFiltroOrdenes().getValue().getNombre());
		
		return filtroOrdenes;
	}
}
