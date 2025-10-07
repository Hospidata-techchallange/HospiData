package br.com.hospidata.notification_service.dto;

import br.com.hospidata.notification_service.dto.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppointmentNotification(
        UUID appointmentId,
        String description,
        LocalDateTime scheduledDate,
        AppointmentStatus status,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail
) {
}