package br.com.hospidata.appointment_service.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentNotification (
        UUID appointmentId,
        LocalDateTime scheduledDate,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail
) {
}
