package com.careerlog.application.dto;

import com.careerlog.application.entity.ApplicationStatus;
import com.careerlog.application.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApplicationCreateRequest(
        @NotBlank
        String companyName,

        @NotBlank
        String positionTitle,

        String jobUrl,

        @NotNull
        ApplicationStatus status,

        @NotNull
        Priority priority,

        LocalDate appliedAt,
        LocalDate resultExpectedAt,
        String nextAction,
        LocalDateTime nextActionAt,
        String memo
) {
}