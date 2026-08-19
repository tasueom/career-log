package com.careerlog.question.repository;

import com.careerlog.question.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findAllByInterviewIdAndUserId(Long interviewId, Long userId);

    Optional<InterviewQuestion> findByIdAndUserId(Long id, Long userId);

    List<InterviewQuestion> findAllByUserIdAndNeedReviewTrue(Long userId);
}