package br.com.hospidata.gateway_service.controller.docs;

import br.com.hospidata.gateway_service.controller.dto.LoginRequest;
import br.com.hospidata.gateway_service.controller.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Endpoints de autenticação e tokens")
@RequestMapping("/auth")
public interface AuthControllerDoc {

    @Operation(summary = "Login do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado")
    })
    @PostMapping("/login")
    ResponseEntity<Void> login(
            @Parameter(description = "Credenciais do usuário") @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    );

    @Operation(summary = "Atualiza tokens usando refresh token")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Tokens atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Refresh token ausente ou inválido")
    })
    @PostMapping("/refresh")
    ResponseEntity<Void> refresh(
            @Parameter(description = "Refresh token enviado via cookie")
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    );

    @Operation(summary = "Logout do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    })
    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpServletResponse response);

    @Operation(summary = "Retorna informações do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do usuário retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
    })
    @GetMapping("/me")
    ResponseEntity<UserResponse> me(
            @Parameter(description = "Access token enviado via cookie")
            @CookieValue(value = "accessToken", required = false) String accessToken
    );

}
