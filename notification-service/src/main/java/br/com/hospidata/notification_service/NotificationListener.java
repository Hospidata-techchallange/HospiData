package br.com.hospidata.notification_service;

import br.com.hospidata.notification_service.dto.AppointmentNotification;
import br.com.hospidata.notification_service.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private final EmailService emailService;

    public NotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "appointment-events",
            groupId = "notification-service"
    )

    public void consume(AppointmentNotification notification) {
        logger.info("📩 Mensagem deserializada recebida do Kafka: " + notification);
        try {
            emailService.sendAppointmentConfirmation(notification);
        } catch (Exception e) {
            logger.error("Erro ao processar a notificação: ", e);
        }
    }
}