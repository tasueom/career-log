package com.careerlog.application.dto;

import com.careerlog.application.entity.Application;
import com.careerlog.application.entity.ApplicationStatus;
import com.careerlog.application.entity.Priority;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApplicationResponse(
        @Schema(description = "지원 건 ID", example = "1")
        Long id,

        @Schema(description = "회사명", example = "OO소프트")
        String companyName,

        @Schema(description = "지원 직무 또는 공고명", example = "백엔드 개발자")
        String positionTitle,

        @Schema(description = "채용 공고 URL", example = "https://example.com/jobs/backend")
        String jobUrl,

        @Schema(description = "지원 상태", example = "APPLIED")
        ApplicationStatus status,

        @Schema(description = "지원 우선순위", example = "HIGH")
        Priority priority,

        @Schema(description = "지원 일자", example = "2026-07-23")
        LocalDate appliedAt,

        @Schema(description = "결과 예상 일자", example = "2026-07-30")
        LocalDate resultExpectedAt,

        @Schema(description = "다음 행동", example = "기술면접 준비")
        String nextAction,

        @Schema(description = "다음 행동 예정 일시", example = "2026-07-24T19:00:00")
        LocalDateTime nextActionAt,

        @Schema(description = "메모", example = "원티드에서 지원한 공고")
        String memo,

        @Schema(description = "생성 일시", example = "2026-07-23T15:30:00")
        LocalDateTime createdAt,

        @Schema(description = "수정 일시", example = "2026-07-23T16:00:00")
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