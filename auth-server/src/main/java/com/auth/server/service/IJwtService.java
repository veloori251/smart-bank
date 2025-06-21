package com.auth.server.service;

import com.auth.server.dto.UserResponseDto;

public interface IJwtService {
    String extractUsername(String token);
    String generateAccessToken(UserResponseDto user);
    String generateRefreshToken(UserResponseDto user);
    boolean isTokenValid(String token);
}
