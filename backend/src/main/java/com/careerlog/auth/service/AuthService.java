package com.careerlog.auth.service;

import com.careerlog.auth.dto.LoginRequest;
import com.careerlog.auth.dto.LoginResponse;
import com.careerlog.auth.dto.SignupRequest;
import com.careerlog.auth.dto.SignupResponse;
import com.careerlog.auth.exception.DuplicateEmailException;
import com.careerlog.auth.exception.InvalidLoginException;
import com.careerlog.user.entity.User;
import com.careerlog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.careerlog.auth.jwt.JwtTokenProvider;
import com.careerlog.auth.entity.RefreshToken;
import com.careerlog.auth.repository.RefreshTokenRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname()
        );

        User savedUser = userRepository.save(user);

        return SignupResponse.from(savedUser);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidLoginException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        LocalDateTime refreshTokenExpiresAt = jwtTokenProvider.getRefreshTokenExpiresAt();

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        existingRefreshToken -> existingRefreshToken.update(refreshToken, refreshTokenExpiresAt),
                        () -> refreshTokenRepository.save(new RefreshToken(user, refreshToken, refreshTokenExpiresAt))
                );

        return LoginResponse.from(user, accessToken, refreshToken);
    }
}