package com.careerlog.application.service;

import com.careerlog.application.dto.ApplicationCreateRequest;
import com.careerlog.application.dto.ApplicationResponse;
import com.careerlog.application.dto.ApplicationStatusUpdateRequest;
import com.careerlog.application.dto.ApplicationUpdateRequest;
import com.careerlog.application.entity.Application;
import com.careerlog.application.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private static final String NOT_FOUND_APPLICATION_MESSAGE = "지원 건을 찾을 수 없습니다.";

    @Transactional
    public ApplicationResponse create(ApplicationCreateRequest request) {
        Application application = new Application(
                request.companyName(),
                request.positionTitle(),
                request.jobUrl(),
                request.status(),
                request.priority(),
                request.appliedAt(),
                request.resultExpectedAt(),
                request.nextAction(),
                request.nextActionAt(),
                request.memo()
        );

        Application savedApplication = applicationRepository.save(application);

        return ApplicationResponse.from(savedApplication);
    }

    public List<ApplicationResponse> findAll() {
        return applicationRepository.findAll()
                .stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    public ApplicationResponse findById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_APPLICATION_MESSAGE));

        return ApplicationResponse.from(application);
    }

    @Transactional
    public ApplicationResponse update(Long applicationId, ApplicationUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_APPLICATION_MESSAGE));

        application.update(
                request.companyName(),
                request.positionTitle(),
                request.jobUrl(),
                request.status(),
                request.priority(),
                request.appliedAt(),
                request.resultExpectedAt(),
                request.nextAction(),
                request.nextActionAt(),
                request.memo()
        );

        return ApplicationResponse.from(application);
    }

    @Transactional
    public ApplicationResponse updateStatus(Long applicationId, ApplicationStatusUpdateRequest request){
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_APPLICATION_MESSAGE));

        application.updateStatus(request.status());

        return ApplicationResponse.from(application);
    }

    @Transactional
    public void delete(Long applicationId){
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException(NOT_FOUND_APPLICATION_MESSAGE));

        applicationRepository.delete(application);
    }
}