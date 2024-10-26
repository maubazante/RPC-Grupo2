package com.unla.stockearte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.service.CatalogoService;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;

	@GetMapping
	public ResponseEntity<List<Catalogo>> getAllCatalogos(@RequestParam String username,
			@RequestParam(required = false) Long tiendaId) {
		List<Catalogo> catalogos = catalogoService.getAllCatalogos(username, tiendaId);
		return new ResponseEntity<>(catalogos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Catalogo> getCatalogoById(@PathVariable Long id, @RequestParam String username) {
		return catalogoService.getCatalogoById(id, username)
				.map(catalogo -> new ResponseEntity<>(catalogo, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Catalogo> createCatalogo(@RequestBody Catalogo catalogo, @RequestParam String username) {
		Catalogo createdCatalogo = catalogoService.createCatalogo(catalogo, username);
		return new ResponseEntity<>(createdCatalogo, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Catalogo> updateCatalogo(@PathVariable Long id, @RequestBody Catalogo catalogo,
			@RequestParam String username) {
		Catalogo updatedCatalogo = catalogoService.updateCatalogo(id, catalogo, username);
		return new ResponseEntity<>(updatedCatalogo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCatalogo(@PathVariable Long id, @RequestParam String username) {
		catalogoService.deleteCatalogo(id, username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
