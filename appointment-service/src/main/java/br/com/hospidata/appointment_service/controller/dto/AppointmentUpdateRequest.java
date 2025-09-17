package br.com.hospidata.appointment_service.controller.dto;

import br.com.hospidata.appointment_service.entity.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentUpdateRequest(
        @NotNull AppointmentStatus status,
        @Future @NotNull LocalDateTime scheduledDate
) {}