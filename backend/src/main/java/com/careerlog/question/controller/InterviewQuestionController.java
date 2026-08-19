package com.careerlog.question.controller;

import com.careerlog.auth.security.AuthUser;
import com.careerlog.question.dto.InterviewQuestionCreateRequest;
import com.careerlog.question.dto.InterviewQuestionResponse;
import com.careerlog.question.service.InterviewQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Interview Question", description = "면접 질문 관리 API")
public class InterviewQuestionController {

    private final InterviewQuestionService interviewQuestionService;

    @Operation(summary = "면접 질문 등록", description = "로그인 사용자의 면접에 질문 기록을 등록합니다.")
    @PostMapping("/api/interviews/{interviewId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewQuestionResponse create(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long interviewId,
            @Valid @RequestBody InterviewQuestionCreateRequest request
    ) {
        return interviewQuestionService.create(authUser.id(), interviewId, request);
    }
}