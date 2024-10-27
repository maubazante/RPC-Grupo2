package com.unla.soapsys.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.catalogos.CatalogoDTO;
import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.filtroordenes.GetFiltroOrdenRequest;
import com.example.filtroordenes.ObjectFactory;
import com.unla.soapsys.response.Catalogo;
import com.unla.soapsys.response.CatalogoDTOFronted;

public class CatalogoHelper {

	public static GetFiltroOrdenRequest crearGetFiltroOrdenRequest(Long id) {
		ObjectFactory factory = new ObjectFactory();
		GetFiltroOrdenRequest getFiltroOrdenRequest = new GetFiltroOrdenRequest();
		getFiltroOrdenRequest.setId(factory.createGetFiltroOrdenRequestId(id));

		return getFiltroOrdenRequest;
	}

	public static GetAllCatalogosRequest getCatalogosRequest(String username) {
		GetAllCatalogosRequest getAllCatalogosRequest = new GetAllCatalogosRequest();
		getAllCatalogosRequest.setUsername(username);
		return getAllCatalogosRequest;
	}

	public static List<CatalogoDTOFronted> getCatalogos(ListCatalogoResponse listCatalogoResponse) {
		List<CatalogoDTOFronted> catalogoDTOFronted = new ArrayList<>();

		for (CatalogoDTO catalogo : listCatalogoResponse.getCatalogos()) {
			CatalogoDTOFronted catalogoFronted = new CatalogoDTOFronted();
			catalogoFronted.setNombre(catalogo.getNombre());
			catalogoFronted.setIdTienda(catalogo.getTiendaId().getValue());
			catalogoFronted.setId(catalogo.getId().getValue());
			catalogoFronted.setProductosIds(catalogo.getProductoIds().getProductoId());

			catalogoDTOFronted.add(catalogoFronted);
		}

		return catalogoDTOFronted;
	}

}
