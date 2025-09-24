package br.com.hospidata.appointment_service.controller.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(

        @NotNull(message = "Scheduled date is required")
        @Future(message = "Scheduled date must be in the future")
        LocalDateTime scheduledDate,

        @NotBlank(message = "Description is required")
        @Size(min = 6, message = "Description must be at least 6 characters")
        String description,

        @NotNull(message = "Patient ID is required")
        UUID patientId,

        @NotBlank(message = "Patient name is required")
        String patientName,

        @NotBlank(message = "Patient email is required")
        @Email(message = "Invalid patient email format")
        String patientEmail,

        @NotNull(message = "Doctor ID is required")
        UUID doctorId,

        @NotBlank(message = "Doctor name is required")
        String doctorName,

        @NotBlank(message = "Doctor email is required")
        @Email(message = "Invalid doctor email format")
        String doctorEmail
) {}