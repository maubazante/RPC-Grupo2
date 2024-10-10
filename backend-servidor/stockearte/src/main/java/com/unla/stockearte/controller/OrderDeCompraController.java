package com.unla.stockearte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.unla.stockearte.dto.OrdenCompraRequest;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.service.OrdenDeCompraService;

@RestController
@RequestMapping("/api/ordenDeCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderDeCompraController {

	@Autowired
	OrdenDeCompraService orderDeCompraService;

	@PostMapping
	public ResponseEntity<OrdenDeCompra> addOrdenDeCompra(@RequestBody OrdenCompraRequest request) {
		OrdenDeCompra oc = orderDeCompraService.crearOrdenCompra(request);
		return new ResponseEntity<OrdenDeCompra>(oc, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteOrdenDeCompra(@PathVariable Long id) {
		Boolean p = orderDeCompraService.deleteById(id);
		return new ResponseEntity<Boolean>(p, HttpStatus.OK);
	}

	@GetMapping(path = "/list")
	public ResponseEntity<List<OrdenDeCompra>> getListOrdenDeCompra() {
		List<OrdenDeCompra> list = orderDeCompraService.getList();
		return new ResponseEntity<List<OrdenDeCompra>>(list, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<OrdenDeCompra> getOrdenDeCompra(Long id) {
		OrdenDeCompra orden = orderDeCompraService.getById(id);
		return new ResponseEntity<OrdenDeCompra>(orden, HttpStatus.CREATED);
	}

	@PostMapping("/marcarComoRecibida/{id}")
	public ResponseEntity<OrdenDeCompra> marcarOrdenComoRecibida(@PathVariable Long id) {
		OrdenDeCompra ordenRecibida = orderDeCompraService.marcarOrdenComoRecibida(id);
		return new ResponseEntity<>(ordenRecibida, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrdenDeCompra> updateOrdenDeCompra(@PathVariable Long id, @RequestBody OrdenDeCompra ordenDeCompra) {
	    try {
	        OrdenDeCompra updatedOrder = orderDeCompraService.updateOrdenDeCompra(id, ordenDeCompra);
	        return ResponseEntity.ok(updatedOrder);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

}
