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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidLoginException();
        }

        return LoginResponse.from(user);
    }
}