package br.com.hospidata.gateway_service.controller;

import br.com.hospidata.gateway_service.controller.docs.AuthControllerDoc;
import br.com.hospidata.gateway_service.controller.dto.AuthResponse;
import br.com.hospidata.gateway_service.controller.dto.ChangePasswordRequest;
import br.com.hospidata.gateway_service.controller.dto.LoginRequest;
import br.com.hospidata.gateway_service.service.AuthService;
import br.com.hospidata.gateway_service.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest,
                                      HttpServletResponse response) {
        AuthResponse authResponse = authService.login(loginRequest);

        Cookie refreshCookie = new Cookie("refreshToken", authResponse.refreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/auth/refresh");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
        response.addCookie(refreshCookie);

        Cookie accessCookie = new Cookie("accessToken", authResponse.accessToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60); // 15 minutos
        response.addCookie(accessCookie);

        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                        HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        AuthResponse newTokens = authService.refresh(refreshToken);

        Cookie refreshCookie = new Cookie("refreshToken", newTokens.refreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/auth/refresh");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshCookie);

        Cookie accessCookie = new Cookie("accessToken", newTokens.accessToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60);
        response.addCookie(accessCookie);

        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/auth/refresh");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);

        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);

        return ResponseEntity.ok().build();
    }

}