package br.com.hospidata.gateway_service.controller.dto;

import java.util.UUID;

public record HistoryRequest(
        UUID appointmentId,
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail,
        String description
) {}