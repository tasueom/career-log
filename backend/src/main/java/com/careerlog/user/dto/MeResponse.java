package com.careerlog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MeResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@example.com")
        String email,

        @Schema(description = "닉네임", example = "taesu")
        String nickname
) {
}