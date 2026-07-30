package com.careerlog.auth.security;

import com.careerlog.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityErrorResponseWriter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void writeUnauthorizedResponse(
            HttpServletResponse response,
            String code,
            String message
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ErrorResponse errorResponse = new ErrorResponse(code, message);

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}