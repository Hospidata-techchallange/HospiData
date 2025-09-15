package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdateRequestGateway(
        @NotNull AppointmentStatus status,
        @Future @NotNull LocalDateTime scheduledDate,
        @NotNull UUID doctorId,
        @NotNull UUID patientId
) {
}
