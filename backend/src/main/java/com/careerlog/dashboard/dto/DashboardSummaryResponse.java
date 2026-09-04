package com.careerlog.dashboard.dto;

import com.careerlog.application.entity.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public record DashboardSummaryResponse(
        @Schema(description = "전체 지원 건 수", example = "12")
        long totalApplicationCount,

        @Schema(description = "상태별 지원 건 수")
        Map<ApplicationStatus, Long> applicationStatusCounts,

        @Schema(description = "복습 필요 질문 수", example = "5")
        long reviewTargetCount
) {
}