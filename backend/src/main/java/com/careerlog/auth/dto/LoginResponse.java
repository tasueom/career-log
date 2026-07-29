package com.careerlog.auth.dto;

import com.careerlog.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@example.com")
        String email,

        @Schema(description = "닉네임", example = "taesu")
        String nickname,

        @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "JWT Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken
) {
    public static LoginResponse from(User user, String accessToken, String refreshToken) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                accessToken,
                refreshToken
        );
    }
}