package com.fiap.tech.infra.configuration;

import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.impl.JCodecFrameServiceImpl;

@Configuration
public class FrameExtractorConfiguration {

    @Bean
    public FrameExtractorService jCodecFrame() {
        return new JCodecFrameServiceImpl(Path.of(System.getProperty("java.io.tmpdir")));
    }
}
