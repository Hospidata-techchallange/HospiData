package br.com.hospidata.history_service.controller.dto;

import java.util.UUID;


public record MedicalHistoryRequest(
        UUID patientId,
        String patientName,
        String patientEmail,
        UUID doctorId,
        String doctorName,
        String doctorEmail,
        String description
) {}