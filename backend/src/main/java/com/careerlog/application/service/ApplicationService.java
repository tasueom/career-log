package com.careerlog.application.service;

import com.careerlog.application.dto.ApplicationCreateRequest;
import com.careerlog.application.dto.ApplicationResponse;
import com.careerlog.application.dto.ApplicationStatusUpdateRequest;
import com.careerlog.application.dto.ApplicationUpdateRequest;
import com.careerlog.application.entity.Application;
import com.careerlog.application.exception.ApplicationNotFoundException;
import com.careerlog.application.repository.ApplicationRepository;
import com.careerlog.user.entity.User;
import com.careerlog.user.exception.UserNotFoundException;
import com.careerlog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicationResponse create(Long userId, ApplicationCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Application application = new Application(
                user,
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

    public List<ApplicationResponse> findAll(Long userId) {
        return applicationRepository.findAllByUserId(userId)
                .stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    public ApplicationResponse findById(Long userId, Long applicationId) {
        Application application = applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(ApplicationNotFoundException::new);

        return ApplicationResponse.from(application);
    }

    @Transactional
    public ApplicationResponse update(Long applicationId, ApplicationUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

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
                .orElseThrow(ApplicationNotFoundException::new);

        application.updateStatus(request.status());

        return ApplicationResponse.from(application);
    }

    @Transactional
    public void delete(Long applicationId){
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        applicationRepository.delete(application);
    }
}