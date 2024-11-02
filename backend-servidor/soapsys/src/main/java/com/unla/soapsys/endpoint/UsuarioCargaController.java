package com.unla.soapsys.endpoint;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.unla.soapsys.request.SendFileRequest; // Ajusta el paquete según tu estructura
import com.unla.soapsys.response.SendFileResponse; // Ajusta el paquete según tu estructura

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioCargaController {

    @Autowired
    private WebServiceTemplate webServiceTemplate; // Para comunicarse con el servicio SOAP

    @PostMapping("/cargar-usuarios")
    public ResponseEntity<String> cargarUsuarios(@RequestParam("archivo") MultipartFile archivo) {
        try {
            // Verificar si el archivo está vacío
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest().body("No se ha recibido ningún archivo o el archivo está vacío.");
            }
        	
            // Leer el contenido del archivo en un byte array
            byte[] fileContent = archivo.getBytes();

            // Crear la solicitud para SOAP
            SendFileRequest sendFileRequest = new SendFileRequest();
            sendFileRequest.setFileContent(fileContent); // Establecer el contenido del archivo
            sendFileRequest.setFileName(archivo.getOriginalFilename()); // Establecer el nombre del archivo

            // Enviar solicitud a Stockearte mediante SOAP
            SendFileResponse response = (SendFileResponse) webServiceTemplate.marshalSendAndReceive(sendFileRequest);
            
            // Manejar respuesta
            if (response != null && response.getResultado()) {
                return ResponseEntity.ok("Archivo enviado exitosamente.");
            } else {
                return ResponseEntity.status(500).body("Error al enviar el archivo a Stockearte.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar la solicitud SOAP: " + e.getMessage());
        }
    }
}
