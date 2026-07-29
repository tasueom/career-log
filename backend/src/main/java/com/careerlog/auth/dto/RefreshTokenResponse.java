package com.careerlog.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshTokenResponse(
        @Schema(description = "새로 발급된 JWT Access Token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken
) {
    public static RefreshTokenResponse from(String accessToken) {
        return new RefreshTokenResponse(accessToken);
    }
}