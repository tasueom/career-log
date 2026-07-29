package com.careerlog.auth.jwt;

import com.careerlog.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("nickname", user.getNickname())
                .issuedAt(now)
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(User user) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return LocalDateTime.now()
                .plusSeconds(refreshTokenExpirationMs / 1000);
    }
}