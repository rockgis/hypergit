package com.goodmit.hypergit.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        Info info = new Info().title("온라인 모델 분석  API").version(appVersion)
                .description("온라인 모델 서비스를 위한  애플리케이션 API입니다.")
                .contact(new Contact().name("jini").email("jini@jiniworld.me"))
                .license(new License().name("Apache License Version 2.0"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
