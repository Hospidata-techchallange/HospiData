package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull Role role
) {}