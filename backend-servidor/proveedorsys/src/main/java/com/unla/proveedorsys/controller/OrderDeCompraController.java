package com.unla.proveedorsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.proveedorsys.model.OrdenDeCompra;
import com.unla.proveedorsys.service.OrdenDeCompraService;

@RestController
@RequestMapping("/api/ordenDeCompra")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDeCompraController {
	
	@Autowired
	OrdenDeCompraService orderDeCompraService;

	@GetMapping(path = "/list")
	public ResponseEntity<List<OrdenDeCompra>> getListOrdenDeCompra() {
		List<OrdenDeCompra> list = orderDeCompraService.getList();
		return new ResponseEntity<List<OrdenDeCompra>>(list, HttpStatus.OK);
	}

	
}
