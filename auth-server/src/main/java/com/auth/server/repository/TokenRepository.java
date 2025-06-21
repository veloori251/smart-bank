package com.auth.server.repository;

import com.auth.server.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByUsername(String username);
    @Query("SELECT t FROM Token t WHERE t.username = ?1 AND t.revoked = false")
    Optional<Token> findByUsernameAndRevokedFalse(String username);
}
