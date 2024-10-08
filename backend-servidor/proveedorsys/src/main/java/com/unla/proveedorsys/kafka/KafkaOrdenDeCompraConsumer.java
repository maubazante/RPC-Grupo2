package com.unla.proveedorsys.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.proveedorsys.model.OrdenDeCompra;
import com.unla.proveedorsys.repository.ItemOrdenRepository;
import com.unla.proveedorsys.service.OrdenDeCompraService;

public class KafkaOrdenDeCompraConsumer {

	@Autowired
	ItemOrdenRepository itemOrdenRepository;
	
	@Autowired
	OrdenDeCompraService ordenDeCompraService;

	@KafkaListener(topics = "orden-de-compra", groupId = "stockearte-orden-group", autoStartup = "true")
	public void listen(String mensajeJson) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			OrdenDeCompra ordenDeCompra = objectMapper.readValue(mensajeJson, OrdenDeCompra.class);
			ordenDeCompraService.procesarOrdenDeCompra(ordenDeCompra);
			
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
