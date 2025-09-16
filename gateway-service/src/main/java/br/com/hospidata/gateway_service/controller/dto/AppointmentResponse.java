package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.AppointmentStatus;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@SchemaMapping("AppointmentResponse")
public record AppointmentResponse (
        UUID id,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail,
        String description,
        AppointmentStatus status,
        LocalDateTime firstScheduledDate,
        LocalDateTime scheduledDate,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt
) {
}
