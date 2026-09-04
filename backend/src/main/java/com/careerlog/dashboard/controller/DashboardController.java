package com.careerlog.dashboard.controller;

import com.careerlog.auth.security.AuthUser;
import com.careerlog.dashboard.dto.DashboardSummaryResponse;
import com.careerlog.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "대시보드 API")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "대시보드 요약 조회", description = "로그인 사용자의 지원 현황과 복습 필요 질문 수를 조회합니다.")
    @GetMapping("/api/dashboard/summary")
    public DashboardSummaryResponse getSummary(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return dashboardService.getSummary(authUser.id());
    }
}