package br.com.hospidata.notification_service.service;

import br.com.hospidata.notification_service.dto.AppointmentNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendAppointmentConfirmation(AppointmentNotification notification) {
        String to = notification.patientEmail();
        String subject = "Confirmação de consulta - " + notification.status();
        String body = String.format(
                "Olá %s,\\n\\n" +
                        "Sua consulta foi %s para o dia %s.\\n\\n" +
                        "Detalhes: %s\\n" +
                        "Médico(a): %s\\n\\n" +
                        "Atenciosamente,\\n" +
                        "Hospidata",
                notification.patientName(),
                notification.status(),
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
}