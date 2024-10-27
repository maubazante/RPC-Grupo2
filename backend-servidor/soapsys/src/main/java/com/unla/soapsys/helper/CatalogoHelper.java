package com.unla.soapsys.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.catalogos.CatalogoDTO;
import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.filtroordenes.GetFiltroOrdenRequest;
import com.example.filtroordenes.ObjectFactory;
import com.unla.soapsys.response.Catalogo;

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
	
	
	public static List<Catalogo> getCatalogos(ListCatalogoResponse listCatalogoResponse) {
		List<Catalogo> listCatalogo = new ArrayList<>();
		
		for(CatalogoDTO catalogoDTO: listCatalogoResponse.getCatalogos()) {
			
		}
		
		return null;
	}
	
	
}
