package com.careerlog.application.controller;

import com.careerlog.application.dto.ApplicationCreateRequest;
import com.careerlog.application.dto.ApplicationResponse;
import com.careerlog.application.dto.ApplicationStatusUpdateRequest;
import com.careerlog.application.dto.ApplicationUpdateRequest;
import com.careerlog.application.service.ApplicationService;
import com.careerlog.auth.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Application", description = "지원 건 관리 API")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "지원 건 등록", description = "새로운 지원 건을 등록합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ApplicationCreateRequest request
    ) {
        return applicationService.create(authUser.id(), request);
    }

    @Operation(summary = "지원 건 목록 조회", description = "로그인 사용자의 지원 건 목록을 조회합니다.")
    @GetMapping
    public List<ApplicationResponse> findAll(@AuthenticationPrincipal AuthUser authUser) {
        return applicationService.findAll(authUser.id());
    }

    @Operation(summary = "지원 건 상세 조회", description = "로그인 사용자의 지원 건 상세 정보를 조회합니다.")
    @GetMapping("/{applicationId}")
    public ApplicationResponse findById(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long applicationId) {
        return applicationService.findById(authUser.id(), applicationId);
    }

    @Operation(summary = "지원 건 수정", description = "로그인 사용자의 지원 건 정보를 수정합니다.")
    @PutMapping("/{applicationId}")
    public ApplicationResponse update(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationUpdateRequest request
    ) {
        return applicationService.update(authUser.id(), applicationId, request);
    }

    @Operation(summary = "지원 상태 변경", description = "로그인 사용자의 지원 건 상태만 별도로 변경합니다.")
    @PatchMapping("/{applicationId}/status")
    public ApplicationResponse updateStatus(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request
    ) {
        return applicationService.updateStatus(authUser.id(), applicationId, request);
    }

    @Operation(summary = "지원 건 삭제", description = "로그인 사용자의 지원 건을 삭제합니다.")
    @DeleteMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long applicationId
    ){
        applicationService.delete(authUser.id(), applicationId);
    }
}