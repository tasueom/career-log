package com.careerlog.question.dto;

import com.careerlog.question.entity.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InterviewQuestionUpdateRequest(
        @Schema(description = "면접 질문 내용", example = "JPA N+1 문제에 대해 설명해주세요.")
        @NotBlank
        String questionText,

        @Schema(description = "질문 유형", example = "TECHNICAL")
        @NotNull
        QuestionType questionType,

        @Schema(description = "내 답변", example = "지연 로딩과 fetch join을 중심으로 답변했습니다.")
        String myAnswer,

        @Schema(description = "답변 점수", example = "4")
        @Min(1)
        @Max(5)
        Short answerScore,

        @Schema(description = "부족했던 점", example = "EntityGraph 설명이 부족했습니다.")
        String weakness,

        @Schema(description = "개선 답변", example = "fetch join, batch size, EntityGraph를 상황별로 구분해 설명합니다.")
        String improvedAnswer,

        @Schema(description = "기술 태그", example = "JPA,N+1,FetchJoin")
        String techTags,

        @Schema(description = "복습 필요 여부", example = "true")
        Boolean needReview,

        @Schema(description = "메모", example = "N+1 예제 코드 다시 보기")
        String memo
) {
}