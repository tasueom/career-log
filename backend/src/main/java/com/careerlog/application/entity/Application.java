package com.careerlog.application.entity;

import com.careerlog.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Auth 구현 후 User 연관관계 추가 예정

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String positionTitle;

    @Column(length = 500)
    private String jobUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;

    private LocalDate appliedAt;

    private LocalDate resultExpectedAt;

    @Column(length = 255)
    private String nextAction;

    private LocalDateTime nextActionAt;

    @Column(columnDefinition = "TEXT")
    private String memo;

    public Application(
            String companyName,
            String positionTitle,
            String jobUrl,
            ApplicationStatus status,
            Priority priority,
            LocalDate appliedAt,
            LocalDate resultExpectedAt,
            String nextAction,
            LocalDateTime nextActionAt,
            String memo
    ) {
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.jobUrl = jobUrl;
        this.status = status;
        this.priority = priority;
        this.appliedAt = appliedAt;
        this.resultExpectedAt = resultExpectedAt;
        this.nextAction = nextAction;
        this.nextActionAt = nextActionAt;
        this.memo = memo;
    }

    public void update(
            String companyName,
            String positionTitle,
            String jobUrl,
            ApplicationStatus status,
            Priority priority,
            LocalDate appliedAt,
            LocalDate resultExpectedAt,
            String nextAction,
            LocalDateTime nextActionAt,
            String memo
    ) {
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.jobUrl = jobUrl;
        this.status = status;
        this.priority = priority;
        this.appliedAt = appliedAt;
        this.resultExpectedAt = resultExpectedAt;
        this.nextAction = nextAction;
        this.nextActionAt = nextActionAt;
        this.memo = memo;
    }
}