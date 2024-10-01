package com.unla.stockearte;

import com.unla.stockearte.kafka.KafkaHelloWorldProducer;
import com.unla.stockearte.kafka.KafkaHelloWorldConsumer;

public class KafkaHelloWorldApplication {

    public static void main(String[] args) throws InterruptedException {
        KafkaHelloWorldProducer productor = new KafkaHelloWorldProducer();
        productor.enviarMensaje("test-topic", "Hola Mundo desde Kafka!");

        KafkaHelloWorldConsumer consumidor = new KafkaHelloWorldConsumer();
        System.out.println("Esperando mensajes...");
        consumidor.escucharMensajes();
    }
}
