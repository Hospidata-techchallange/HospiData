package br.com.hospidata.gateway_service.controller.dto;

import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@SchemaMapping("History")
public record HistoryResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String patientName,
        String patientEmail,
        String doctorName,
        String doctorEmail,
        String description,
        LocalDateTime createdAt
) {
}