package com.careerlog.dashboard.service;

import com.careerlog.application.entity.Application;
import com.careerlog.application.entity.ApplicationStatus;
import com.careerlog.application.repository.ApplicationRepository;
import com.careerlog.dashboard.dto.DashboardSummaryResponse;
import com.careerlog.question.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;

    public DashboardSummaryResponse getSummary(Long userId) {
        List<Application> applications = applicationRepository.findAllByUserId(userId);

        Map<ApplicationStatus, Long> statusCounts = createEmptyStatusCounts();

        applications.forEach(application -> {
            ApplicationStatus status = application.getStatus();
            statusCounts.put(status, statusCounts.get(status) + 1);
        });

        long reviewTargetCount = interviewQuestionRepository
                .findAllByUserIdAndNeedReviewTrue(userId)
                .size();

        return new DashboardSummaryResponse(
                applications.size(),
                statusCounts,
                reviewTargetCount
        );
    }

    private Map<ApplicationStatus, Long> createEmptyStatusCounts() {
        Map<ApplicationStatus, Long> statusCounts = new EnumMap<>(ApplicationStatus.class);

        for (ApplicationStatus status : ApplicationStatus.values()) {
            statusCounts.put(status, 0L);
        }

        return statusCounts;
    }
}