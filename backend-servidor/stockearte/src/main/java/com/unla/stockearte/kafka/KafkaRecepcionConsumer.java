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
public class KafkaRecepcionConsumer {

	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	private static final Logger logger = LoggerFactory.getLogger(KafkaRecepcionConsumer.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "recepcion", groupId = "stockearte-group")
	public void listenRecepcion(String message) {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			// Convertir el mensaje JSON en una entidad OrdenDeCompra
			OrdenDeCompra ordenDeCompra = objectMapper.readValue(message, OrdenDeCompra.class);

			// Actualizar la orden de compra en ProveedorSys
			ordenDeCompraRepository.save(ordenDeCompra);

			logger.info("Orden de compra recibida y actualizada con éxito por el topic '/recepcion'.");
			logger.info("Fecha de recepción: " + ordenDeCompra.getFechaRecepcion());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("Error al procesar el mensaje del tópico '/recepcion': ", e);
		}
	}
}
