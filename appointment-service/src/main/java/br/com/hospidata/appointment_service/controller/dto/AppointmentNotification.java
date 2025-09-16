package br.com.hospidata.appointment_service.controller.dto;

import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentNotification (
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
