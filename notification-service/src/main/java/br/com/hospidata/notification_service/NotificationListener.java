package br.com.hospidata.notification_service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notification-service"
    )
    public void consume(String message) {
        System.out.println("📩 Mensagem recebida do Kafka: " + message);

    }
}
