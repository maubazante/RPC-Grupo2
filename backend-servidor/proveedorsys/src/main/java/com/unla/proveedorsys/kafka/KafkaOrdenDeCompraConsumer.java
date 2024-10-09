package com.unla.proveedorsys.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.proveedorsys.model.OrdenDeCompra;
import com.unla.proveedorsys.repository.ItemOrdenRepository;
import com.unla.proveedorsys.service.OrdenDeCompraService;

@Service
public class KafkaOrdenDeCompraConsumer {

	@Autowired
	ItemOrdenRepository itemOrdenRepository;
	
	@Autowired
	OrdenDeCompraService ordenDeCompraService;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaOrdenDeCompraConsumer.class);

	@KafkaListener(topics = "orden-de-compra", groupId = "stockearte-orden-group", autoStartup = "true")
	public void listen(String mensajeJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			OrdenDeCompra ordenDeCompra = objectMapper.readValue(mensajeJson, OrdenDeCompra.class);
			ordenDeCompraService.procesarOrdenDeCompra(ordenDeCompra);
			logger.info("Las ordens se procesaron con exito por el topico 'ORDEN-DE-COMPRA'");
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

	}
	
	@KafkaListener(topics = "recepcion", groupId = "stockearte-orden-group", autoStartup = "true")
	public void listenRecepcion(String mensajeJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			OrdenDeCompra ordenDeCompra = objectMapper.readValue(mensajeJson, OrdenDeCompra.class);
			ordenDeCompraService.guardarOrdenDeCompra(ordenDeCompra);
			logger.info("Las ordenes se marcaron como aceptadas con exito por el topico 'RECEPCION'");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

	}

	public ItemOrdenRepository getItemOrdenRepository() {
		return itemOrdenRepository;
	}

}
