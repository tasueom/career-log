package com.careerlog.application.dto;

import com.careerlog.application.entity.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusUpdateRequest(
        @Schema(description = "변경할 지원 상태", example = "INTERVIEW_PLANNED")
        @NotNull
        ApplicationStatus status
) {
}