package com.fiap.tech.conversor.infra.configuration.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.fiap.tech.conversor.application.processor.DefaultConversorVideoUseCase;
import com.fiap.tech.conversor.infra.service.CompressFileService;
import com.fiap.tech.conversor.infra.service.EventService;
import com.fiap.tech.conversor.infra.service.FileService;
import com.fiap.tech.conversor.infra.service.FrameExtractorService;
import com.fiap.tech.conversor.infra.service.impl.compress.CompressFileServiceImpl;
import com.fiap.tech.conversor.infra.service.impl.jcodec.JCodecFrameExtractorServiceImpl;
import com.fiap.tech.conversor.infra.service.impl.s3.S3FileServiceImpl;

@Configuration
public class ConversorVideoUseCaseConfiguration {

    @Bean
    public FileService fileService(AmazonS3 amazonS3) {
        return new S3FileServiceImpl(amazonS3);
    }

    @Bean
    public CompressFileService compressFileService() {
        return new CompressFileServiceImpl();
    }

    @Bean
    public FrameExtractorService frameExtractorService() {
        return new JCodecFrameExtractorServiceImpl();
    }

    @Bean
    public DefaultConversorVideoUseCase conversorVideoUseCase(
        FileService fileService,
        EventService eventService,
        CompressFileService compressFileService,
        FrameExtractorService frameExtractorService

    ) {
        return new DefaultConversorVideoUseCase(fileService, eventService, compressFileService, frameExtractorService);
    }
}