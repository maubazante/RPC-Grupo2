package com.unla.stockearte.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.stockearte.model.OrdenDeCompra;
import com.unla.stockearte.repository.OrdenDeCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.unla.stockearte.enums.*;

@Service
public class OrdenDeCompraService {

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_ORDENES = "/orden-de-compra";

    public OrdenDeCompra crearOrdenCompra(OrdenDeCompra ordenDeCompra) {
        ordenDeCompra.setEstado(EstadoOrden.SOLICITADA);
        ordenDeCompra.setFechaSolicitud(LocalDateTime.now());

        // Guardar la orden en la base de datos
        OrdenDeCompra nuevaOrden = ordenDeCompraRepository.save(ordenDeCompra);

       //Lo parsea a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mensaje = objectMapper.writeValueAsString(nuevaOrden);
            kafkaTemplate.send(TOPIC_ORDENES, mensaje);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return nuevaOrden;
    }
}
