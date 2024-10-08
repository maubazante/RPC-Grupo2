package com.unla.proveedorsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Método para enviar mensajes genéricos a un topic.
     *
     * @param topic   Nombre del topic de Kafka.
     * @param key     Clave del mensaje.
     * @param message Mensaje en formato String.
     */
    public void sendMessage(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

    /**
     * Método para enviar mensajes a un topic sin clave.
     *
     * @param topic   Nombre del topic de Kafka.
     * @param message Mensaje en formato String.
     */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
