package com.careerlog.interview.repository;

import com.careerlog.interview.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findAllByApplicationIdAndUserId(Long applicationId, Long userId);

    Optional<Interview> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("""
        DELETE FROM Interview i
        WHERE i.application.id = :applicationId
          AND i.user.id = :userId
        """)
    void deleteAllByApplicationIdAndUserId(Long applicationId, Long userId);
}