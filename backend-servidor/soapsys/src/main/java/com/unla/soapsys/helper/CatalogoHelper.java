package com.unla.soapsys.helper;

import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.filtroordenes.GetFiltroOrdenRequest;
import com.example.filtroordenes.ObjectFactory;

public class CatalogoHelper {

	
	public static GetFiltroOrdenRequest crearGetFiltroOrdenRequest(Long id) {
		ObjectFactory factory = new ObjectFactory();
		GetFiltroOrdenRequest getFiltroOrdenRequest = new GetFiltroOrdenRequest();
		getFiltroOrdenRequest.setId(factory.createGetFiltroOrdenRequestId(id));

		return getFiltroOrdenRequest;
	}
	
	public static GetAllCatalogosRequest getCatalogosRequest(String username) {
		GetAllCatalogosRequest listCatalogoRequest = new GetAllCatalogosRequest();
		listCatalogoRequest.setUsername(username);
		return listCatalogoRequest;
	}
	
	/*
	public static List<Catalogo> getCatalogos(ListCatalogoResponse listCatalogoResponse) {
		// TODO armar lista, con todo mapeado
		//return listCatalogoRequest;
		return null;
	}
	*/
	
}
