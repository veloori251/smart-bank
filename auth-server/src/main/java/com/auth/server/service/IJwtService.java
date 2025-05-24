package com.auth.server.service;

import com.auth.server.entity.User;

public interface IJwtService {
    String extractUsername(String token);
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean isTokenValid(String token);
}
