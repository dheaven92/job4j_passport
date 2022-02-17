package ru.job4j.emailservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @KafkaListener(topics = {"${kafka.notification-topic}"})
    public void listenNotifications(ConsumerRecord<String, String> input) {
        log.info("Received message: {}", input.value());
    }
}
