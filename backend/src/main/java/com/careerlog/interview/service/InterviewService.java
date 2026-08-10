package com.careerlog.interview.service;

import com.careerlog.application.entity.Application;
import com.careerlog.application.exception.ApplicationNotFoundException;
import com.careerlog.application.repository.ApplicationRepository;
import com.careerlog.interview.dto.InterviewCreateRequest;
import com.careerlog.interview.dto.InterviewResponse;
import com.careerlog.interview.entity.Interview;
import com.careerlog.interview.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public InterviewResponse create(
            Long userId,
            Long applicationId,
            InterviewCreateRequest request
    ) {
        Application application = applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(ApplicationNotFoundException::new);

        Interview interview = new Interview(
                application.getUser(),
                application,
                request.interviewType(),
                request.scheduledAt(),
                request.location(),
                request.interviewerCount(),
                request.difficulty(),
                request.atmosphere(),
                request.overallReview(),
                request.result(),
                request.nextPreparation()
        );

        Interview savedInterview = interviewRepository.save(interview);

        return InterviewResponse.from(savedInterview);
    }

    public List<InterviewResponse> findAllByApplication(
            Long userId,
            Long applicationId
    ) {
        applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(ApplicationNotFoundException::new);

        return interviewRepository.findAllByApplicationIdAndUserId(applicationId, userId)
                .stream()
                .map(InterviewResponse::from)
                .toList();
    }
}