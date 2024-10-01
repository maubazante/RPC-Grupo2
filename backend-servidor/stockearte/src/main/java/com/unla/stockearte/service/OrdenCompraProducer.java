package com.unla.stockearte.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrdenCompraProducer {

    private final KafkaProducer<String, String> producer;

    @Value("${kafka.topic.ordenCompra}")
    private String ordenCompraTopic;

    public OrdenCompraProducer(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public void enviarOrden(String mensaje) {
        producer.send(new ProducerRecord<>(ordenCompraTopic, mensaje));
    }
}
