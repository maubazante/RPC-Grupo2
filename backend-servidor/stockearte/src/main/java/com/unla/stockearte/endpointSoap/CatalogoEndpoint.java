package com.unla.stockearte.endpointSoap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ObtenerProductoPorCatalogoRequest;
import com.example.catalogos.ObtenerProductoPorCatalogoResponse;
import com.unla.stockearte.helpers.CatalogoHelper;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.service.CatalogoService;

@Endpoint
public class CatalogoEndpoint {

	private static final String NAMESPACE_URI = "http://example.com/catalogos";

	@Autowired
	CatalogoService catalogoService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCatalogosRequest")
	@ResponsePayload
	public ListCatalogoResponse getAllCatalogos(@RequestPayload GetAllCatalogosRequest request) throws Exception {
		List<Catalogo> catalogos = catalogoService.getAllCatalogos(request.getUsername());

		ListCatalogoResponse response = CatalogoHelper.getCatalogosResponse(catalogos);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObtenerProductoPorCatalogoRequest")
	@ResponsePayload
	public ObtenerProductoPorCatalogoResponse getAllCatalogos(@RequestPayload ObtenerProductoPorCatalogoRequest request) throws Exception {
		List<Producto> productos = catalogoService.obtenerProductosPorCatalogo(request.getCatalogId().getValue());

		ObtenerProductoPorCatalogoResponse response = CatalogoHelper.productosToObtenerProductoPorCatalogoResponse(productos);
		return response;
	}

}
