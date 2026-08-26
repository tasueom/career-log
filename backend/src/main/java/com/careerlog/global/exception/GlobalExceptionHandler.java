package com.careerlog.global.exception;

import com.careerlog.application.exception.ApplicationNotFoundException;
import com.careerlog.auth.exception.DuplicateEmailException;
import com.careerlog.auth.exception.InvalidLoginException;
import com.careerlog.auth.exception.InvalidRefreshTokenException;
import com.careerlog.interview.exception.InterviewNotFoundException;
import com.careerlog.question.exception.InterviewQuestionNotFoundException;
import com.careerlog.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleApplicationNotFoundException(ApplicationNotFoundException e) {
        return new ErrorResponse(
                "APPLICATION_NOT_FOUND",
                e.getMessage()
        );
    }

    @ExceptionHandler(InterviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleInterviewNotFoundException(InterviewNotFoundException e) {
        return new ErrorResponse(
                "INTERVIEW_NOT_FOUND",
                e.getMessage()
        );
    }

    @ExceptionHandler(InterviewQuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleInterviewQuestionNotFoundException(InterviewQuestionNotFoundException e) {
        return new ErrorResponse(
                "INTERVIEW_QUESTION_NOT_FOUND",
                e.getMessage()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse(
                "USER_NOT_FOUND",
                e.getMessage()
        );
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateEmailException(DuplicateEmailException e) {
        return new ErrorResponse(
                "DUPLICATE_EMAIL",
                e.getMessage()
        );
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidLoginException(InvalidLoginException e) {
        return new ErrorResponse(
                "INVALID_LOGIN",
                e.getMessage()
        );
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return new ErrorResponse(
                "INVALID_REFRESH_TOKEN",
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));

        return new ErrorResponse(
                "INVALID_REQUEST",
                message
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(
                "INVALID_REQUEST",
                e.getName() + ": 올바르지 않은 요청 값입니다."
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ErrorResponse(
                "INVALID_REQUEST",
                "요청 본문 형식이 올바르지 않습니다."
        );
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}