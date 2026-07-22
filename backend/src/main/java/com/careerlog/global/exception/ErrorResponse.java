package com.careerlog.global.exception;

public record ErrorResponse(
        String code,
        String message
) {
}