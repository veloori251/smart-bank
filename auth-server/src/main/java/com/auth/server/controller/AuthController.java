package com.auth.server.controller;

import com.auth.server.dto.AuthResponse;
import com.auth.server.dto.LoginRequest;
import com.auth.server.dto.RefreshTokenRequest;
import com.auth.server.dto.RegisterRequest;
import com.auth.server.entity.RefreshToken;
import com.auth.server.entity.User;
import com.auth.server.repository.RefreshTokenRepository;
import com.auth.server.service.IJwtService;
import com.auth.server.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final IJwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User user = userService.register(registerRequest);
        return ResponseEntity.ok("user registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.validateUser(loginRequest);
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            // Log the generated refresh token
            log.info("Generated refresh token: {}", refreshToken);

            RefreshToken rt = new RefreshToken();
            rt.setToken(refreshToken);
            rt.setUsername(user.getUsername());
            rt.setExpiry(LocalDateTime.now().plusDays(7));
            rt.setRevoked(false);

            RefreshToken savedToken = refreshTokenRepository.save(rt);
            log.info("Saved refresh token with ID: {}", savedToken.getToken());

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            // 1. Find the refresh token
            var refreshTokenOpt = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken());

            // 2. Validate the refresh token
            if (refreshTokenOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
            }

            RefreshToken refreshToken = refreshTokenOpt.get();
            if (refreshToken.isRevoked()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token was revoked");
            }

            if (refreshToken.getExpiry().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
            }

            // 3. Create a minimal user object (no need to hit the database)
            User user = new User();
            user.setUsername(refreshToken.getUsername());

            // 4. Generate new tokens
            String newAccessToken = jwtService.generateAccessToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            // 5. Revoke the old token
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);

            // 6. Save the new refresh token
            RefreshToken newToken = new RefreshToken();
            newToken.setToken(newRefreshToken);
            newToken.setUsername(user.getUsername());
            newToken.setExpiry(LocalDateTime.now().plusDays(7));
            newToken.setRevoked(false);
            refreshTokenRepository.save(newToken);

            return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));

        } catch (Exception e) {
            log.error("Error refreshing token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error refreshing token");
        }
    }
}
