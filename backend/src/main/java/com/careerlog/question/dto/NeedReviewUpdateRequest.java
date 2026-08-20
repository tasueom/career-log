package com.careerlog.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record NeedReviewUpdateRequest(
        @Schema(description = "복습 필요 여부", example = "true")
        @NotNull
        Boolean needReview
) {
}