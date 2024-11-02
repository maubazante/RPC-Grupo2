package com.unla.stockearte.endpointSoap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.catalogos.CrearCatalogoRequest;
import com.example.catalogos.CrearCatalogoResponse;
import com.example.catalogos.EliminarCatalogoRequest;
import com.example.catalogos.EliminarCatalogoResponse;
import com.example.catalogos.ExportarCatalogoPdfRequest;
import com.example.catalogos.ExportarCatalogoPdfResponse;
import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ModificarCatalogoRequest;
import com.example.catalogos.ModificarCatalogoResponse;
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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CrearCatalogoRequest")
	@ResponsePayload
	public CrearCatalogoResponse crearCatalogo(@RequestPayload CrearCatalogoRequest request) throws Exception {
		Catalogo catalogo = catalogoService.createCatalogo(CatalogoHelper.catalogoCreateRequestToCatalogo(request), request.getCatalogo().getUserName());
		CrearCatalogoResponse response = CatalogoHelper.crearCatalogoResponse(catalogo);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ModificarCatalogoRequest")
	@ResponsePayload
	public ModificarCatalogoResponse modificarCatalogo(@RequestPayload ModificarCatalogoRequest request) throws Exception{
		Catalogo catalogo = catalogoService.updateCatalogo(request.getCatalogo().getId().getValue(),CatalogoHelper.createCatalogoDTO(request.getCatalogo()), request.getCatalogo().getUserName());
		ModificarCatalogoResponse response = CatalogoHelper.crearModificarCatalogoResponse(catalogo);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExportarCatalogoPdfRequest")
	@ResponsePayload
	public ExportarCatalogoPdfResponse exportarCatalogoPDF(@RequestPayload ExportarCatalogoPdfRequest request) throws Exception{
		byte[] pdfBytes = catalogoService.exportCatalogoPDF(request.getCatalogId().getValue(), request.getUsername());
		ExportarCatalogoPdfResponse response = new ExportarCatalogoPdfResponse();
		response.setPdfBytes(pdfBytes);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EliminarCatalogoRequest")
	@ResponsePayload
	public EliminarCatalogoResponse eliminarCatalogo(@RequestPayload EliminarCatalogoRequest request) throws Exception{
		catalogoService.deleteCatalogo(request.getCatalogId(), request.getUsername());
		EliminarCatalogoResponse response = CatalogoHelper.crearEliminarCatalogoResponse(request.getCatalogId());
		
		return response;
	}
	
}
