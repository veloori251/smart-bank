package com.auth.server.controller;

import com.auth.server.dto.*;
import com.auth.server.service.impl.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = tokenService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch(RuntimeException e){
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        } catch (Exception e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
           AuthResponse authResponse = tokenService.refreshToken(refreshTokenRequest.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            log.error("Error refreshing token", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }catch (Exception e) {
            log.error("Error refreshing token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader){
        try{
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logout failed");
            }
            String token = authHeader.replace("Bearer ", "");
            tokenService.logout(token);
            return ResponseEntity.ok("Logout successful");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout successful");
        }
    }

}
