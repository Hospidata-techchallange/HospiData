package br.com.hospidata.appointment_service.controller.dto;

import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(
        UUID id,
        String description,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail,
        AppointmentStatus status,
        LocalDateTime firstScheduledDate,
        LocalDateTime scheduledDate,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt
) {}