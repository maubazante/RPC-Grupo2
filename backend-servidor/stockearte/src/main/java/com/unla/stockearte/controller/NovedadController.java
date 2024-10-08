package com.unla.stockearte.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.stockearte.model.Novedad;
import com.unla.stockearte.service.NovedadService;

import java.util.List;

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
}