package com.careerlog.application.entity;

public enum ApplicationStatus {
    INTERESTED,        // 관심 공고
    PLANNED,           // 지원 예정
    APPLIED,           // 지원 완료
    DOCUMENT_PASSED,   // 서류 합격
    DOCUMENT_FAILED,   // 서류 탈락
    INTERVIEW_PLANNED, // 면접 예정
    INTERVIEW_DONE,    // 면접 완료
    FINAL_PASSED,      // 최종 합격
    FINAL_FAILED,      // 최종 탈락
    ON_HOLD,           // 보류
    GIVEN_UP           // 포기
}