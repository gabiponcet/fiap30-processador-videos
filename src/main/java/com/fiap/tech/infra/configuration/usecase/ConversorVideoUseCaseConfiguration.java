package com.fiap.tech.infra.configuration.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.application.conversor.ConversorVideoUseCase;
import com.fiap.tech.application.conversor.ConversorVideoUseCaseDefault;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.StorageService;

@Configuration
public class ConversorVideoUseCaseConfiguration {

    private final StorageService storageService;
    private final FrameExtractorService frameExtractorService;
    private final CompressFileService compressFileService;
    private final EventService eventService;

    public ConversorVideoUseCaseConfiguration(
            StorageService storageService, 
            FrameExtractorService frameExtractorService,
            CompressFileService compressFileService,
            EventService eventService) {
        this.storageService = storageService;
        this.frameExtractorService = frameExtractorService;
        this.compressFileService = compressFileService;
        this.eventService = eventService;
    }

    @Bean
    public ConversorVideoUseCase converterVideoUseCase() {
        return new ConversorVideoUseCaseDefault(storageService, frameExtractorService, compressFileService, eventService);
    }
}