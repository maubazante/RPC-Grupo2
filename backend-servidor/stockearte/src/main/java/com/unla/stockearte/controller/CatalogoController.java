package com.unla.stockearte.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.stockearte.dto.CatalogoDTO;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.service.CatalogoService;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;

	@GetMapping
	public ResponseEntity<List<Catalogo>> getAllCatalogos(@RequestParam String username) {
		List<Catalogo> catalogos = catalogoService.getAllCatalogos(username);
		return new ResponseEntity<>(catalogos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Catalogo> getCatalogoById(@PathVariable Long id, @RequestParam String username) {
		return catalogoService.getCatalogoById(id, username)
				.map(catalogo -> new ResponseEntity<>(catalogo, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Catalogo> createCatalogo(@RequestBody CatalogoDTO catalogoDTO,
			@RequestParam String username) {
		Catalogo createdCatalogo = catalogoService.createCatalogo(catalogoDTO, username);
		return new ResponseEntity<>(createdCatalogo, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Catalogo> updateCatalogo(@PathVariable Long id, @RequestBody CatalogoDTO catalogoDTO,
			@RequestParam String username) {
		Catalogo updatedCatalogo = catalogoService.updateCatalogo(id, catalogoDTO, username);
		return new ResponseEntity<>(updatedCatalogo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCatalogo(@PathVariable Long id, @RequestParam String username) {
		catalogoService.deleteCatalogo(id, username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{catalogoId}/productos")
	public List<Producto> obtenerProductosPorCatalogo(@PathVariable Long catalogoId) {
		return catalogoService.obtenerProductosPorCatalogo(catalogoId);
	}

	@GetMapping("/exportar/pdf/{id}")
	public ResponseEntity<?> exportCatalogoPDF(@PathVariable Long id, @RequestParam String username) {
		try {
			byte[] pdfBytes = catalogoService.exportCatalogoPDF(id, username);

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

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al generar el PDF: " + e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Catálogo no encontrado: " + e.getMessage());
		}
	}

}
