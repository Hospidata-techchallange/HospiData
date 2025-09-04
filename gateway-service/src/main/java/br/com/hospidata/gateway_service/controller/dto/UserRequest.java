package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @Size(min = 6) @NotBlank String password,
        @NotNull Role role
) {}