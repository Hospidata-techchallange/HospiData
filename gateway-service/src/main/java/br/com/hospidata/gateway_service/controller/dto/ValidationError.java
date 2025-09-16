package br.com.hospidata.gateway_service.controller.dto;

import java.util.List;

public record ValidationError(
        List<String> erros,
        int status,
        String method,
        String path
) {
}
