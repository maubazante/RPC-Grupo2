package com.unla.stockearte.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.common.errors.ResourceNotFoundException;
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
import com.unla.stockearte.model.CatalogoProducto;
import com.unla.stockearte.model.Producto;
import com.unla.stockearte.model.Tienda;
import com.unla.stockearte.repository.ProductoRepository;
import com.unla.stockearte.repository.TiendaRepository;
import com.unla.stockearte.service.CatalogoService;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private ProductoRepository productoRepository;

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
	public ResponseEntity<Catalogo> createCatalogo(@RequestBody CatalogoDTO catalogoDTO,
			@RequestParam String username) {
		Catalogo catalogo = new Catalogo();
		catalogo.setNombre(catalogoDTO.getNombre());

		Tienda tienda = tiendaRepository.findById(catalogoDTO.getTiendaId())
				.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada"));
		catalogo.setTienda(tienda);

		List<CatalogoProducto> catalogoProductos = new ArrayList<>();
		for (Long productoId : catalogoDTO.getProductoIds()) {
			Producto producto = productoRepository.findById(productoId)
					.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));

			CatalogoProducto catalogoProducto = new CatalogoProducto();
			catalogoProducto.setCatalogo(catalogo);
			catalogoProducto.setProducto(producto);
			catalogoProductos.add(catalogoProducto);
		}

		catalogo.setCatalogoProductos(catalogoProductos);

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

	@GetMapping("/exportar/pdf/{id}")
	public ResponseEntity<?> exportCatalogoPDF(@PathVariable Long id, @RequestParam String username) {
		try {
			byte[] pdfBytes = catalogoService.exportCatalogoPDF(id, username);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al generar el PDF: " + e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Catálogo no encontrado: " + e.getMessage());
		}
	}

	@GetMapping("/{catalogoId}/productos")
	public List<Producto> obtenerProductosPorCatalogo(@PathVariable Long catalogoId) {
		return catalogoService.obtenerProductosPorCatalogo(catalogoId);
	}

}
