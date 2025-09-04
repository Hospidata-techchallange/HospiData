package br.com.hospidata.gateway_service.controller.dto;

import br.com.hospidata.gateway_service.entity.enums.Role;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt,
        Boolean active
) {}