package com.careerlog.auth.security;

public record AuthUser(
        Long id,
        String email,
        String nickname
) {
}