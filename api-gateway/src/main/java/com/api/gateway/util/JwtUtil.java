package com.api.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public boolean isTokenValid(String token) {
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build().
                    parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
       return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<String> extractRoles(String token) {
        try{
            Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String roles = claims.get("roles",String.class);
            if(roles != null){
                return Arrays.asList(roles.split(","));
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
