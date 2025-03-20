package com.fiap.tech.conversor.infra.configuration.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.conversor.application.processor.DefaultConversorVideoUseCase;
import com.fiap.tech.conversor.infra.gateway.S3Gateway;

@Configuration
public class ConversorVideoUseCaseConfiguration {

    private final S3Gateway s3Gateway;

    public ConversorVideoUseCaseConfiguration(S3Gateway s3Gateway) {
        this.s3Gateway = s3Gateway;
    }

    @Bean
    public DefaultConversorVideoUseCase conversorVideoUseCase() {
        return new DefaultConversorVideoUseCase(s3Gateway);
    }
}