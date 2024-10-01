package com.unla.stockearte.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @KafkaListener(topics = "topic-test", groupId = "group_id")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
