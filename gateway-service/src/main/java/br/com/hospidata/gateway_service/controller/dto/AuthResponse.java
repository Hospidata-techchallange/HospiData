package br.com.hospidata.gateway_service.controller.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
