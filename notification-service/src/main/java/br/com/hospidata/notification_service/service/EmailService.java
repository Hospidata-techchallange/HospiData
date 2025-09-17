package br.com.hospidata.notification_service.service;

import br.com.hospidata.notification_service.dto.AppointmentNotification;
import br.com.hospidata.notification_service.dto.enums.AppointmentStatus; // <-- IMPORTE O ENUM
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendAppointmentConfirmation(AppointmentNotification notification) {
        String statusEmPortugues = translateStatus(notification.status());

        String to = notification.patientEmail();
        String subject = "Confirmação de consulta - " + statusEmPortugues;
        String body = String.format(
                "Olá %s,\\n\\n" +
                        "Sua consulta foi %s para o dia %s.\\n\\n" +
                        "Detalhes: %s\\n" +
                        "Médico(a): %s\\n\\n" +
                        "Atenciosamente,\\n" +
                        "Hospidata",
                notification.patientName(),
                statusEmPortugues, // <-- AQUI
                notification.scheduledDate(),
                notification.description(),
                notification.doctorName()
        );

        logger.info("=================================================");
        logger.info("Simulando envio de e-mail...");
        logger.info("Para: {}", to);
        logger.info("Assunto: {}", subject);
        logger.info("Corpo: {}", body);
        logger.info("E-mail enviado!");
    }

    private String translateStatus(AppointmentStatus status) {
        return switch (status) {
            case SCHEDULED -> "agendada";
            case CANCELED -> "cancelada";
            case COMPLETED -> "concluída";
            default -> status.name().toLowerCase();
        };
    }
}