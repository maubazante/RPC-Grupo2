package com.unla.proveedorsys.service;

import com.unla.proveedorsys.enums.EstadoOrden;
import com.unla.proveedorsys.model.OrdenDeCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiendaService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final String TOPIC_RECEPCION = "/recepcion";

    /**
     * Marcar una orden como recibida y enviar mensaje a Kafka.
     *
     * @param orden   La orden que se ha recibido.
     * @param fechaRecepcion Fecha en la que se recibe la orden.
     */
    public void marcarOrdenComoRecibida(OrdenDeCompra orden, String fechaRecepcion) {
        orden.setFechaRecepcion(java.time.LocalDateTime.parse(fechaRecepcion));
        orden.setEstado(EstadoOrden.RECIBIDA);

        // Enviar mensaje de recepción a Kafka
        String mensaje = String.format("{\"ordenId\": \"%d\", \"fechaRecepcion\": \"%s\"}",
                orden.getId(), orden.getFechaRecepcion().toString());
        kafkaProducerService.sendMessage(TOPIC_RECEPCION, mensaje);
    }
}