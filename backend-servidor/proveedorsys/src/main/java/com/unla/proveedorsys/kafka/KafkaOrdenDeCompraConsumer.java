package com.unla.proveedorsys.kafka;

import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaOrdenDeCompraConsumer {

	@KafkaListener(topics = "/orden-de-compra", groupId = "stockearte-group")
	public void listen(String mensajeJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		
	}
}

