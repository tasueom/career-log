package com.careerlog.interview.dto;

import com.careerlog.interview.entity.InterviewResult;
import com.careerlog.interview.entity.InterviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InterviewUpdateRequest(
        @Schema(description = "면접 유형", example = "TECHNICAL")
        @NotNull
        InterviewType interviewType,

        @Schema(description = "면접 예정 일시", example = "2026-08-12T14:00:00")
        @NotNull
        LocalDateTime scheduledAt,

        @Schema(description = "면접 장소 또는 방식", example = "Google Meet")
        String location,

        @Schema(description = "면접관 수", example = "2")
        @Min(1)
        Integer interviewerCount,

        @Schema(description = "체감 난이도", example = "3")
        @Min(1)
        @Max(5)
        Short difficulty,

        @Schema(description = "면접 분위기", example = "기술 질문이 깊게 들어왔습니다.")
        String atmosphere,

        @Schema(description = "전체 면접 회고", example = "트랜잭션과 동시성 답변이 부족했습니다.")
        String overallReview,

        @Schema(description = "면접 결과", example = "PENDING")
        InterviewResult result,

        @Schema(description = "다음 준비 사항", example = "동시성 제어와 멱등성 설계를 복습합니다.")
        String nextPreparation
) {
}