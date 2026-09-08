package com.careerlog.question.repository;

import com.careerlog.question.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findAllByInterviewIdAndUserId(Long interviewId, Long userId);

    Optional<InterviewQuestion> findByIdAndUserId(Long id, Long userId);

    List<InterviewQuestion> findAllByUserIdAndNeedReviewTrue(Long userId);

    @Modifying
    @Query("""
        DELETE FROM InterviewQuestion q
        WHERE q.interview.id = :interviewId
          AND q.user.id = :userId
        """)
    void deleteAllByInterviewIdAndUserId(Long interviewId, Long userId);

    @Modifying
    @Query("""
        DELETE FROM InterviewQuestion q
        WHERE q.user.id = :userId
          AND q.interview.id IN (
              SELECT i.id
              FROM Interview i
              WHERE i.application.id = :applicationId
                AND i.user.id = :userId
          )
        """)
    void deleteAllByApplicationIdAndUserId(Long applicationId, Long userId);
}