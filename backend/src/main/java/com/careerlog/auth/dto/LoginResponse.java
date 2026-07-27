package com.careerlog.auth.dto;

import com.careerlog.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@example.com")
        String email,

        @Schema(description = "닉네임", example = "taesu")
        String nickname
) {
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}