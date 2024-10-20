package com.unla.stockearte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.stockearte.dto.FiltroOrdenesRequest;
import com.unla.stockearte.model.FiltroOrdenes;
import com.unla.stockearte.service.FiltroOrdenesService;

@RestController
@RequestMapping("/api/filtroOrdenCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class FiltroOrdenesCompraController {

	@Autowired
	@Qualifier(FiltroOrdenesService.BEAN_NAME)
	FiltroOrdenesService filtroService;
	
	@PostMapping
	public ResponseEntity<FiltroOrdenes> addFiltroOrdenes(@RequestBody FiltroOrdenesRequest request) throws Exception {
		FiltroOrdenes filtroOrden = filtroService.crearFiltroOrdenes(request);
		return new ResponseEntity<FiltroOrdenes>(filtroOrden, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFiltroOrdenes(@PathVariable Long id) throws Exception {
		Boolean resp = filtroService.eliminarFiltroOrdenes(id);
		return new ResponseEntity<Boolean>(resp, HttpStatus.OK);
	}

	@GetMapping(path = "/list/{usuarioId}")
	public ResponseEntity<List<FiltroOrdenes>> getListFiltroOrdenes(@PathVariable Long usuarioId) throws Exception {
		List<FiltroOrdenes> list = filtroService.getFiltroOrdenesPorUsuario(usuarioId);
		return new ResponseEntity<List<FiltroOrdenes>>(list, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FiltroOrdenes> getFiltroOrdenes(@PathVariable Long id) throws Exception {
		FiltroOrdenes filtroOrden = filtroService.getPorFiltroId(id);
		return new ResponseEntity<FiltroOrdenes>(filtroOrden, HttpStatus.CREATED);
	}

	
	@PutMapping()
	public ResponseEntity<FiltroOrdenes> updateOrdenDeCompra(@RequestBody FiltroOrdenesRequest request) throws Exception {
	    FiltroOrdenes filtroOrden = filtroService.modificarFiltroOrdenes(request);
	    return new ResponseEntity<FiltroOrdenes>(filtroOrden, HttpStatus.OK);
	}
}
