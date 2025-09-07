package br.com.hospidata.gateway_service.controller.dto;

import java.time.Instant;

public record ErrorResponseInternal(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path, String method
) {
}
