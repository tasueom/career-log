package com.careerlog.interview.controller;

import com.careerlog.auth.security.AuthUser;
import com.careerlog.interview.dto.InterviewCreateRequest;
import com.careerlog.interview.dto.InterviewResponse;
import com.careerlog.interview.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Interview", description = "면접 기록 관리 API")
public class InterviewController {

    private final InterviewService interviewService;

    @Operation(summary = "면접 기록 등록", description = "로그인 사용자의 지원 건에 면접 기록을 등록합니다.")
    @PostMapping("/api/applications/{applicationId}/interviews")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewResponse create(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long applicationId,
            @Valid @RequestBody InterviewCreateRequest request
    ) {
        return interviewService.create(authUser.id(), applicationId, request);
    }
}