package com.auth.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "tokens_tbl")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; // we have to map this from users_tbl
    private String accessToken;
    private String refreshToken;
    private boolean revoked=false;
    private LocalDateTime createdAt;
    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;
    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;
    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;
}
