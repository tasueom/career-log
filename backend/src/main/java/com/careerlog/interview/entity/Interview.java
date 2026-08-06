package com.careerlog.interview.entity;

import com.careerlog.application.entity.Application;
import com.careerlog.global.common.BaseTimeEntity;
import com.careerlog.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "interviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 사용자별 조회와 소유권 검증을 단순하게 하기 위해 Interview도 user를 직접 가집니다.
     * interviews.user_id는 applications.user_id와 항상 일치해야 합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
     * 하나의 지원 건에는 여러 면접이 연결될 수 있습니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type", nullable = false, length = 30)
    private InterviewType interviewType;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(length = 255)
    private String location;

    private Integer interviewerCount;

    private Short difficulty;

    @Column(columnDefinition = "TEXT")
    private String atmosphere;

    @Column(columnDefinition = "TEXT")
    private String overallReview;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InterviewResult result;

    @Column(columnDefinition = "TEXT")
    private String nextPreparation;

    public Interview(
            User user,
            Application application,
            InterviewType interviewType,
            LocalDateTime scheduledAt,
            String location,
            Integer interviewerCount,
            Short difficulty,
            String atmosphere,
            String overallReview,
            InterviewResult result,
            String nextPreparation
    ) {
        this.user = user;
        this.application = application;
        this.interviewType = interviewType;
        this.scheduledAt = scheduledAt;
        this.location = location;
        this.interviewerCount = interviewerCount;
        this.difficulty = difficulty;
        this.atmosphere = atmosphere;
        this.overallReview = overallReview;
        this.result = result != null ? result : InterviewResult.PENDING;
        this.nextPreparation = nextPreparation;
    }

    public void update(
            InterviewType interviewType,
            LocalDateTime scheduledAt,
            String location,
            Integer interviewerCount,
            Short difficulty,
            String atmosphere,
            String overallReview,
            InterviewResult result,
            String nextPreparation
    ) {
        this.interviewType = interviewType;
        this.scheduledAt = scheduledAt;
        this.location = location;
        this.interviewerCount = interviewerCount;
        this.difficulty = difficulty;
        this.atmosphere = atmosphere;
        this.overallReview = overallReview;
        this.result = result != null ? result : InterviewResult.PENDING;
        this.nextPreparation = nextPreparation;
    }
}