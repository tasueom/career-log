package com.careerlog.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI careerLogOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CareerLog API")
                        .description("취업 지원 현황과 면접 질문 회고를 관리하는 CareerLog API 문서입니다.")
                        .version("v0.1.0"));
    }
}