package br.com.hospidata.gateway_service.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @Email @NotBlank String email,
        @NotBlank String currentPassword,
        @Size(min = 6) @NotBlank String newPassword,
        @Size(min = 6) @NotBlank String confirmNewPassword
) {}