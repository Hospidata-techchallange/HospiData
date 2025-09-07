package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UserRequestUpdate", description = "DTO para atualização de um usuário")
public record UserRequestUpdate(

        @Schema(description = "Nome do usuário", example = "João da Silva", required = true)
        @NotBlank
        String name,

        @Schema(description = "Email do usuário", example = "joao@email.com", required = true)
        @Email @NotBlank
        String email,

        @Schema(description = "Função/Perfil do usuário", example = "DOCTOR", required = true)
        @NotNull
        Role role
) {}
