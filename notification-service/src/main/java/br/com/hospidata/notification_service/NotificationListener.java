package br.com.hospidata.notification_service;

import br.com.hospidata.notification_service.dto.AppointmentNotification;
import br.com.hospidata.notification_service.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper; // <-- IMPORTE A CLASSE
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public NotificationListener(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "appointment-events",
            groupId = "notification-service"
    )

    public void consume(String message) {
        try {
            AppointmentNotification notification = objectMapper.readValue(message, AppointmentNotification.class);

            logger.info("Mensagem deserializada recebida do Kafka: " + notification);
            emailService.sendAppointmentConfirmation(notification);
        } catch (Exception e) {
            logger.error("Erro ao processar a notificação: ", e);
        }
    }
}