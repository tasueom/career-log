package com.careerlog.interview.exception;

public class InterviewNotFoundException extends RuntimeException {

    public InterviewNotFoundException() {
        super("면접 기록을 찾을 수 없습니다.");
    }
}