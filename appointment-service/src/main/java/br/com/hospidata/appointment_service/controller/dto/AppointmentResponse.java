package br.com.hospidata.appointment_service.controller.dto;

import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long patientId,
        Long doctorId,
        AppointmentStatus status,
        LocalDateTime scheduledDate,
        LocalDateTime createdAt
) {}