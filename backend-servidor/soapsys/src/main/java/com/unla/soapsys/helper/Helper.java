package com.unla.soapsys.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.filtroordenes.AddFiltroOrdenResponse;
import com.example.filtroordenes.AddFiltroOrdenesRequest;
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
	
	public static FiltroOrdenesDTO filtroOrdenesToFiltroOrdenesDTO(FiltroOrdenes filtroOrdenes) {
		ObjectFactory factory = new ObjectFactory();
		FiltroOrdenesDTO filtroOrdenesDTO = new FiltroOrdenesDTO();
		filtroOrdenesDTO.setFiltroEstado(factory.createFiltroOrdenesDTOFiltroEstado(filtroOrdenes.getFiltroEstado()));
		filtroOrdenesDTO.setFiltroFecha(factory.createFiltroOrdenesDTOFiltroFecha(filtroOrdenes.getFiltroFecha()));
		filtroOrdenesDTO.setFiltroProducto(factory.createFiltroOrdenesDTOFiltroProducto(filtroOrdenes.getFiltroProducto()));
		filtroOrdenesDTO.setFiltroTienda(factory.createFiltroOrdenesDTOFiltroTienda(filtroOrdenes.getFiltroTienda()));
		filtroOrdenesDTO.setFkUsuariosId(factory.createFiltroOrdenesDTOFkUsuariosId(filtroOrdenes.getFkUsuariosId()));
		filtroOrdenesDTO.setId(factory.createFiltroOrdenesDTOId(filtroOrdenes.getId()));
		filtroOrdenes.setNombre(filtroOrdenes.getNombre());
		
		return filtroOrdenesDTO;
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
	
	public static AddFiltroOrdenesRequest crearAddFiltroOrdenesRequest(FiltroOrdenesRequest request) {
		ObjectFactory factory = new ObjectFactory();
		AddFiltroOrdenesRequest addFiltroOrdenesRequest = new AddFiltroOrdenesRequest();
		
		addFiltroOrdenesRequest.setFiltroEstado(factory.createAddFiltroOrdenesRequestFiltroEstado(request.getFiltroEstado()));
		addFiltroOrdenesRequest.setFiltroFecha(factory.createAddFiltroOrdenesRequestFiltroFecha(request.getFiltroFecha()));
		addFiltroOrdenesRequest.setFiltroProducto(factory.createAddFiltroOrdenesRequestFiltroProducto(request.getFiltroProducto()));
		addFiltroOrdenesRequest.setFiltroTienda(factory.createFiltroOrdenesDTOFiltroTienda(request.getFiltroTienda()));
		addFiltroOrdenesRequest.setId(factory.createAddFiltroOrdenesRequestId(request.getId()));
		addFiltroOrdenesRequest.setUserId(factory.createAddFiltroOrdenesRequestUserId(request.getUserId()));
		addFiltroOrdenesRequest.setNombre(request.getNombre());
		
		return addFiltroOrdenesRequest;
	}
	
	public static FiltroOrdenes createFiltroOrdenesAtravesDeAddFiltroOrdenesRequest(AddFiltroOrdenResponse response) {
		FiltroOrdenes filtro = new FiltroOrdenes();
		
		filtro.setFiltroEstado(response.getFiltroOrdenes().getValue().getFiltroEstado().getValue());
		filtro.setFiltroFecha(response.getFiltroOrdenes().getValue().getFiltroFecha().getValue());
		filtro.setFiltroProducto(response.getFiltroOrdenes().getValue().getFiltroProducto().getValue());
		filtro.setFiltroTienda(response.getFiltroOrdenes().getValue().getFiltroTienda().getValue());
		filtro.setFkUsuariosId(response.getFiltroOrdenes().getValue().getFkUsuariosId().getValue());
		filtro.setId(response.getFiltroOrdenes().getValue().getId().getValue());
		filtro.setNombre(response.getFiltroOrdenes().getValue().getNombre());
		
		return filtro;
	}
	
}
