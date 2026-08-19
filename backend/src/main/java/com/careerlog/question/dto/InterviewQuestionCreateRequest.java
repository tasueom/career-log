package com.careerlog.question.dto;

import com.careerlog.question.entity.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InterviewQuestionCreateRequest(
        @Schema(description = "면접 질문 내용", example = "트랜잭션 격리 수준에 대해 설명해주세요.")
        @NotBlank
        String questionText,

        @Schema(description = "질문 유형", example = "TECHNICAL")
        @NotNull
        QuestionType questionType,

        @Schema(description = "내 답변", example = "READ COMMITTED와 REPEATABLE READ의 차이를 중심으로 답변했습니다.")
        String myAnswer,

        @Schema(description = "답변 점수", example = "3")
        @Min(1)
        @Max(5)
        Short answerScore,

        @Schema(description = "부족했던 점", example = "팬텀 리드 설명이 부족했습니다.")
        String weakness,

        @Schema(description = "개선 답변", example = "격리 수준별로 발생 가능한 문제를 예시와 함께 설명합니다.")
        String improvedAnswer,

        @Schema(description = "기술 태그", example = "Transaction,Isolation,JPA")
        String techTags,

        @Schema(description = "복습 필요 여부", example = "true")
        Boolean needReview,

        @Schema(description = "메모", example = "다음 면접 전 트랜잭션 예제를 다시 정리하기")
        String memo
) {
}