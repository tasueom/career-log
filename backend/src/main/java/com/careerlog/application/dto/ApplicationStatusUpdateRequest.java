package com.careerlog.application.dto;

import com.careerlog.application.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusUpdateRequest(
        @NotNull
        ApplicationStatus status
) {
}