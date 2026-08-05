package com.careerlog.auth.controller;

import com.careerlog.auth.dto.*;
import com.careerlog.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 JWT Access Token을 발급합니다.")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 검증하고 새로운 Access Token을 발급합니다.")
    @PostMapping("/refresh")
    public RefreshTokenResponse refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshAccessToken(request);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 삭제하여 로그아웃 처리합니다.")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
    }
}