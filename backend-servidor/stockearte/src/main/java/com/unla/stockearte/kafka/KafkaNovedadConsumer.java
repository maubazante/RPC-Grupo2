package com.unla.stockearte.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.stockearte.model.Novedad;
import com.unla.stockearte.service.NovedadService;

@Service
public class KafkaNovedadConsumer {

    @Autowired
    private NovedadService novedadService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "novedades", groupId = "stockearte-group")
    public void listenNovedades(String message) {
        try {
            // Convertir el mensaje JSON en una entidad Novedad
            Novedad novedad = objectMapper.readValue(message, Novedad.class);
            
            // Persistir en la base de datos
            novedadService.saveNovedad(novedad);

            System.out.println("Mensaje recibido y guardado: " + novedad.getCodigoProducto());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}