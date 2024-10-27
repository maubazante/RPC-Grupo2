package com.unla.stockearte.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unla.stockearte.service.UsuarioService;

@RestController
public class UsuarioCargaController {

	@Autowired
	private UsuarioService usuarioCargaMasivaService;

	@PostMapping("/cargar-usuarios")
	public ResponseEntity<List<String>> cargarUsuarios(@RequestParam("archivo") MultipartFile archivo) {
		try {
			List<String> errores = usuarioCargaMasivaService.procesarArchivoCSV(archivo);
			if (errores.isEmpty()) {
				return ResponseEntity.ok(List.of("Usuarios cargados exitosamente."));
			} else {
				return ResponseEntity.ok(errores);
			}
		} catch (IOException e) {
			return ResponseEntity.status(500).body(List.of("Error al procesar el archivo."));
		}
	}
}
