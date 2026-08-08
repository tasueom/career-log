package com.careerlog.interview.dto;

import com.careerlog.interview.entity.Interview;
import com.careerlog.interview.entity.InterviewResult;
import com.careerlog.interview.entity.InterviewType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record InterviewResponse(
        @Schema(description = "면접 ID", example = "1")
        Long id,

        @Schema(description = "지원 건 ID", example = "1")
        Long applicationId,

        @Schema(description = "면접 유형", example = "TECHNICAL")
        InterviewType interviewType,

        @Schema(description = "면접 예정 일시", example = "2026-08-12T14:00:00")
        LocalDateTime scheduledAt,

        @Schema(description = "면접 장소 또는 방식", example = "Google Meet")
        String location,

        @Schema(description = "면접관 수", example = "2")
        Integer interviewerCount,

        @Schema(description = "체감 난이도", example = "3")
        Short difficulty,

        @Schema(description = "면접 분위기")
        String atmosphere,

        @Schema(description = "전체 면접 회고")
        String overallReview,

        @Schema(description = "면접 결과", example = "PENDING")
        InterviewResult result,

        @Schema(description = "다음 준비 사항")
        String nextPreparation,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt
) {
    public static InterviewResponse from(Interview interview) {
        return new InterviewResponse(
                interview.getId(),
                interview.getApplication().getId(),
                interview.getInterviewType(),
                interview.getScheduledAt(),
                interview.getLocation(),
                interview.getInterviewerCount(),
                interview.getDifficulty(),
                interview.getAtmosphere(),
                interview.getOverallReview(),
                interview.getResult(),
                interview.getNextPreparation(),
                interview.getCreatedAt(),
                interview.getUpdatedAt()
        );
    }
}