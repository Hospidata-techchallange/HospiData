package br.com.hospidata.gateway_service.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestGateway(
        LocalDateTime scheduledDate,
        UUID patientId,
        UUID doctorId
) {
}
