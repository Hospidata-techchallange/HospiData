package br.com.hospidata.appointment_service.controller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        //UUID appointmentId,
        LocalDateTime scheduledDate,
        String description,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail
) {}