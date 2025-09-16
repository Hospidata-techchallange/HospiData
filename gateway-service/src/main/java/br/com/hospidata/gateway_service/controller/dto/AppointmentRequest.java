package br.com.hospidata.gateway_service.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        LocalDateTime scheduledDate,
        UUID patientId,
        String description,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail
) {}
