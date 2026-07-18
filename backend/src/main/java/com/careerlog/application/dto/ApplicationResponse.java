package com.careerlog.application.dto;

import com.careerlog.application.entity.Application;
import com.careerlog.application.entity.ApplicationStatus;
import com.careerlog.application.entity.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        String companyName,
        String positionTitle,
        String jobUrl,
        ApplicationStatus status,
        Priority priority,
        LocalDate appliedAt,
        LocalDate resultExpectedAt,
        String nextAction,
        LocalDateTime nextActionAt,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ApplicationResponse from(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getCompanyName(),
                application.getPositionTitle(),
                application.getJobUrl(),
                application.getStatus(),
                application.getPriority(),
                application.getAppliedAt(),
                application.getResultExpectedAt(),
                application.getNextAction(),
                application.getNextActionAt(),
                application.getMemo(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}