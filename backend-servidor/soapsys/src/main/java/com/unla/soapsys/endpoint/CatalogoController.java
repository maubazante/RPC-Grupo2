package com.unla.soapsys.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ObtenerProductoPorCatalogoResponse;
import com.unla.soapsys.helper.CatalogoHelper;
import com.unla.soapsys.response.CatalogoDTOFronted;
import com.unla.soapsys.response.Producto;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "http://localhost:4200")
public class CatalogoController {

	@Autowired
	@Qualifier("webServiceTemplateCatalogo")
	private WebServiceTemplate webServiceTemplate;

	@GetMapping
	public ResponseEntity<List<CatalogoDTOFronted>> getAllCatalogos(@RequestParam String username) {
		ListCatalogoResponse listCatalogoResponse = (ListCatalogoResponse) webServiceTemplate
				.marshalSendAndReceive(CatalogoHelper.getCatalogosRequest(username));
		List<CatalogoDTOFronted> resp = CatalogoHelper.getCatalogos(listCatalogoResponse);
		return new ResponseEntity<List<CatalogoDTOFronted>>(resp, HttpStatus.OK);
	}

	@GetMapping("/{catalogoId}/productos")
	public ResponseEntity<List<Producto>> obtenerProductosPorCatalogo(@PathVariable Long catalogoId) {
		ObtenerProductoPorCatalogoResponse obtenerProductoPorCatalogoResponse = (ObtenerProductoPorCatalogoResponse) webServiceTemplate
				.marshalSendAndReceive(CatalogoHelper.crearObtenerProductoPorCatalogoRequest(catalogoId));
		List<Producto> response = CatalogoHelper.obtenerProductoCatalogoResponseToProductos(obtenerProductoPorCatalogoResponse);
		return new ResponseEntity<List<Producto>>(response, HttpStatus.OK);
	}
}
