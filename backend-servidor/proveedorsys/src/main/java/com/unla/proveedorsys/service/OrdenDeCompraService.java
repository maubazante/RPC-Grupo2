package com.unla.proveedorsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.proveedorsys.enums.EstadoOrden;
import com.unla.proveedorsys.model.OrdenDeCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrdenDeCompraService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final String TOPIC_ORDENES = "/orden-de-compra";

    /**
     * Enviar orden de compra al topic correspondiente.
     *
     * @param orden La orden de compra que se va a enviar.
     */
    public void enviarOrdenDeCompra(OrdenDeCompra orden) {
        orden.setFechaSolicitud(LocalDateTime.now());
        orden.setEstado(EstadoOrden.SOLICITADA);

        // Convertir la orden a JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mensaje = mapper.writeValueAsString(orden);
            kafkaProducerService.sendMessage(TOPIC_ORDENES, String.valueOf(orden.getId()), mensaje);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de la orden de compra.
     *
     * @param orden La orden de compra que se va a actualizar.
     * @param estado El nuevo estado a asignar.
     */
    public void actualizarEstadoOrden(OrdenDeCompra orden, String estado) {
        orden.setEstado(EstadoOrden.valueOf(estado));
        // Se envía la actualización al mismo topic para reflejar el cambio.
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mensaje = mapper.writeValueAsString(orden);
            kafkaProducerService.sendMessage(TOPIC_ORDENES, String.valueOf(orden.getId()), mensaje);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}