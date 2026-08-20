package com.careerlog.application.repository;

import com.careerlog.application.entity.Application;
import com.careerlog.application.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    Optional<Application> findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT a
            FROM Application a
            WHERE a.user.id = :userId
              AND (:status IS NULL OR a.status = :status)
              AND (
                    :keyword IS NULL
                    OR LOWER(a.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(a.positionTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
            ORDER BY a.updatedAt DESC, a.id DESC
            """)
    List<Application> searchByUser(
            Long userId,
            ApplicationStatus status,
            String keyword
    );
}