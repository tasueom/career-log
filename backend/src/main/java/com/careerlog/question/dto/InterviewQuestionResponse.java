package com.careerlog.question.dto;

import com.careerlog.question.entity.InterviewQuestion;
import com.careerlog.question.entity.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record InterviewQuestionResponse(
        @Schema(description = "면접 질문 ID", example = "1")
        Long id,

        @Schema(description = "면접 ID", example = "1")
        Long interviewId,

        @Schema(description = "질문 내용", example = "트랜잭션 격리 수준에 대해 설명해주세요.")
        String questionText,

        @Schema(description = "질문 유형", example = "TECHNICAL")
        QuestionType questionType,

        @Schema(description = "내 답변")
        String myAnswer,

        @Schema(description = "답변 점수", example = "3")
        Short answerScore,

        @Schema(description = "부족했던 점")
        String weakness,

        @Schema(description = "개선 답변")
        String improvedAnswer,

        @Schema(description = "기술 태그", example = "Transaction,Isolation,JPA")
        String techTags,

        @Schema(description = "복습 필요 여부", example = "true")
        boolean needReview,

        @Schema(description = "메모")
        String memo,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt
) {
    public static InterviewQuestionResponse from(InterviewQuestion question) {
        return new InterviewQuestionResponse(
                question.getId(),
                question.getInterview().getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getMyAnswer(),
                question.getAnswerScore(),
                question.getWeakness(),
                question.getImprovedAnswer(),
                question.getTechTags(),
                question.isNeedReview(),
                question.getMemo(),
                question.getCreatedAt(),
                question.getUpdatedAt()
        );
    }
}