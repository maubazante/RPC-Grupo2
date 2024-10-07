package com.unla.stockearte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.service.OrdenDeCompraService;

@RestController
@RequestMapping("/api/ordenDeCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderDeCompraController {
	
	@Autowired
	OrdenDeCompraService orderDeCompraService;

	@PostMapping
	public ResponseEntity<OrdenDeCompra> addOrdenDeCompra(OrdenDeCompra request) {
		getOrderDeCompraService().crearOrdenCompra(request);
		return new ResponseEntity<OrdenDeCompra>(request, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> deleteOrdenDeCompra(Long id) {
		Boolean p = getOrderDeCompraService().deleteById(id);
		return new ResponseEntity<Boolean>(p, HttpStatus.OK);
	}
	
	@GetMapping(path = "/list")
	public ResponseEntity<List<OrdenDeCompra>> getListOrdenDeCompra() {
		List<OrdenDeCompra> list = getOrderDeCompraService().getList();
		return new ResponseEntity<List<OrdenDeCompra>>(list, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<OrdenDeCompra> getOrdenDeCompra(Long id) {
		OrdenDeCompra orden = getOrderDeCompraService().getById(id);
		return new ResponseEntity<OrdenDeCompra>(orden, HttpStatus.CREATED);
	}

	public OrdenDeCompraService getOrderDeCompraService() {
		return orderDeCompraService;
	}
	
	
}
