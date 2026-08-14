package com.careerlog.user.controller;

import com.careerlog.auth.security.AuthUser;
import com.careerlog.user.dto.MeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "사용자 API")
public class MeController {

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 기본 정보를 조회합니다.")
    @GetMapping("/api/me")
    public MeResponse me(@AuthenticationPrincipal AuthUser authUser) {
        return new MeResponse(
                authUser.id(),
                authUser.email(),
                authUser.nickname()
        );
    }
}