package com.auth.server.service.impl;

import com.auth.server.dto.AuthResponse;
import com.auth.server.dto.LoginRequest;
import com.auth.server.dto.UserResponseDto;
import com.auth.server.entity.Token;
import com.auth.server.repository.TokenRepository;
import com.auth.server.service.IJwtService;
import com.auth.server.service.fiegnclient.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final IJwtService jwtService;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest loginRequest) {
        UserResponseDto user = userServiceClient.getUserByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        if (user.getStatus().equals("INACTIVE")) {
            throw new RuntimeException("User is inactive password");
        }

        Optional<Token> token = tokenRepository.findByUsernameAndRevokedFalse(user.getUsername());
        if (token.isPresent()) {
            token.get().setRevoked(true);
            token.get().setRevokedAt(LocalDateTime.now());
            tokenRepository.save(token.get());
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
        Token newToken = new Token();
        newToken.setUsername(user.getUsername());
        newToken.setAccessToken(authResponse.getAccessToken());
        newToken.setRefreshToken(authResponse.getRefreshToken());
        newToken.setCreatedAt(LocalDateTime.now());
        newToken.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
        newToken.setRefreshTokenExpiresAt(LocalDateTime.now().plusDays(7));
        tokenRepository.save(newToken);
        return authResponse;
    }

    public AuthResponse refreshToken(String refreshToken) {
        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        if (token.isPresent()) {
            if(token.get().getRefreshTokenExpiresAt().isBefore(LocalDateTime.now()) ||
                                                token.get().isRevoked()) {
                throw new RuntimeException("Refresh token is expired or revoked");
            }
            UserResponseDto user = UserResponseDto.builder()
                    .username(token.get().getUsername())
                    .build();
            String accessToken = jwtService.generateAccessToken(user);
            token.get().setAccessToken(accessToken);
            token.get().setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
            tokenRepository.save(token.get());
            return authResponse;
        }
        else {
            throw new RuntimeException("Refresh token not found");
        }
    }

    public void logout(String token) {
        Optional<Token> accessToken = tokenRepository.findByAccessToken(token);
        if (accessToken.isPresent() && !accessToken.get().isRevoked()) {
            accessToken.get().setRevoked(true);
            accessToken.get().setRevokedAt(LocalDateTime.now());
            tokenRepository.save(accessToken.get());
        }else{
            throw new RuntimeException("Access token not found");
        }
    }
}
