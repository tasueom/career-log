package com.careerlog.auth.dto;

import com.careerlog.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@example.com")
        String email,

        @Schema(description = "사용자 이름", example = "엄태수")
        String nickname
) {
    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}