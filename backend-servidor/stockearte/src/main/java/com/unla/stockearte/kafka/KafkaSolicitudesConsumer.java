package com.unla.stockearte.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.repository.OrdenDeCompraRepository;

@Service
public class KafkaSolicitudesConsumer {

	@Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaSolicitudesConsumer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "solicitudes", groupId = "stockearte-group")
    public void listenNovedades(String message) {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            // Convertir el mensaje JSON en una entidad Novedad
            OrdenDeCompra ordenDeCompra = objectMapper.readValue(message, OrdenDeCompra.class);
            
            ordenDeCompraRepository.save(ordenDeCompra);

            logger.info("Se recibicio con exito la orden de Compra por el topic 'SOLICITUDES'");
            logger.info("Info de observaciones de la orden de compra recibida por el topic 'SOLICITUDES': " + ordenDeCompra.getObservaciones());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
