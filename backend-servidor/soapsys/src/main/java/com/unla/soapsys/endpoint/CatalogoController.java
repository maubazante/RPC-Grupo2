package com.unla.soapsys.endpoint;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.catalogos.CrearCatalogoResponse;
import com.example.catalogos.ExportarCatalogoPdfRequest;
import com.example.catalogos.ExportarCatalogoPdfResponse;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ModificarCatalogoResponse;
import com.example.catalogos.ObtenerProductoPorCatalogoResponse;
import com.unla.soapsys.helper.CatalogoHelper;
import com.unla.soapsys.response.Catalogo;
import com.unla.soapsys.response.CatalogoDTO;
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
		List<Producto> response = CatalogoHelper
				.obtenerProductoCatalogoResponseToProductos(obtenerProductoPorCatalogoResponse);
		return new ResponseEntity<List<Producto>>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Catalogo> createCatalogo(@RequestBody CatalogoDTO catalogoDTO,
			@RequestParam String username) {
		CrearCatalogoResponse crearCatalogoResponse = (CrearCatalogoResponse) webServiceTemplate
				.marshalSendAndReceive(CatalogoHelper.crearCatalogoRequest(catalogoDTO, username));

		Catalogo response = CatalogoHelper.crearCatalogo(crearCatalogoResponse);
		return new ResponseEntity<Catalogo>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Catalogo> updateCatalogo(@PathVariable Long id, @RequestBody CatalogoDTO catalogoDTO,
			@RequestParam String username) {
		ModificarCatalogoResponse modificarCatalogoResponse = (ModificarCatalogoResponse) webServiceTemplate
				.marshalSendAndReceive(CatalogoHelper.crearModificarCatalogoRequest(id, catalogoDTO, username));
		Catalogo response = CatalogoHelper.updatedCatalogo(modificarCatalogoResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/exportar/pdf/{id}")
	public ResponseEntity<?> exportCatalogoPDF(@PathVariable Long id, @RequestParam String username) throws IOException {
		try {
			ExportarCatalogoPdfResponse exportarCatalogoPdfResponse = (ExportarCatalogoPdfResponse) webServiceTemplate
					.marshalSendAndReceive(CatalogoHelper.crearExportarCatalogoPdfRequest(id, username));
			byte[] pdfBytes = exportarCatalogoPdfResponse.getPdfBytes();

			// Convertir los bytes a Base64
			String base64PDF = Base64.getEncoder().encodeToString(pdfBytes);

			/*
			 * Retorno en MediaType return
			 * ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
			 *
			 */

			// Crear la respuesta como un objeto JSON que contenga el PDF en Base64
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
					.body(Collections.singletonMap("pdfBase64", base64PDF));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Catálogo no encontrado: " + e.getMessage());
		}
	}

}
