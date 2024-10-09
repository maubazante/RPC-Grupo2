package com.unla.stockearte.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.stockearte.dto.AltaProductoRequest;
import com.unla.stockearte.model.Novedad;
import com.unla.stockearte.service.NovedadService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novedades")
@CrossOrigin(origins = "http://localhost:4200")
public class NovedadController {

    @Autowired
    private NovedadService novedadService;

    @GetMapping
    public ResponseEntity<List<Novedad>> getAllNovedades() {
        return ResponseEntity.ok(novedadService.getAllNovedades());
    }
    
    @PutMapping
    public ResponseEntity<Map<String, String>> darDeAltaProducto(@RequestBody AltaProductoRequest request) {
        boolean success = novedadService.darDeAltaProducto(request);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Producto dado de alta correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al dar de alta el producto");
            return ResponseEntity.badRequest().body(response);
        }
    }
}