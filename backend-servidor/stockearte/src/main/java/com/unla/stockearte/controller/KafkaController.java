package com.unla.stockearte.controller;

import com.unla.stockearte.producer.KafkaMessageProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaMessageProducer producer;

    public KafkaController(KafkaMessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public void send(@RequestBody String message) {
        producer.sendMessage("topic-test", message);
    }
}
