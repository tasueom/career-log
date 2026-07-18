package com.careerlog.application.controller;

import com.careerlog.application.dto.ApplicationCreateRequest;
import com.careerlog.application.dto.ApplicationResponse;
import com.careerlog.application.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@Valid @RequestBody ApplicationCreateRequest request) {
        return applicationService.create(request);
    }

    @GetMapping
    public List<ApplicationResponse> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{applicationId}")
    public ApplicationResponse findById(@PathVariable Long applicationId) {
        return applicationService.findById(applicationId);
    }
}