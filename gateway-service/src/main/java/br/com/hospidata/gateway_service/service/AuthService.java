package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.controller.dto.AuthResponse;
import br.com.hospidata.gateway_service.controller.dto.LoginRequest;
import br.com.hospidata.gateway_service.entity.User;
import br.com.hospidata.gateway_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    public AuthService(UserRepository repository, PasswordEncoder encoder, TokenService tokenService) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public AuthResponse login(LoginRequest login) {
        User user = repository.findByEmail(login.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        if (!encoder.matches(login.password(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }


        String accessToken = tokenService.generateAccessToken(user.getEmail());
        String refreshToken = tokenService.generateRefreshToken(user.getEmail());


        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {
        if (!tokenService.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }
        String username = tokenService.getUsernameFromToken(refreshToken);
        String newAccessToken = tokenService.generateAccessToken(username);
        return new AuthResponse(newAccessToken, refreshToken);
    }



}
