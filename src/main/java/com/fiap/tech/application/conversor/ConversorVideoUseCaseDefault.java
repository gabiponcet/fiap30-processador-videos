package com.fiap.tech.application.conversor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiap.tech.domain.conversor.ConversorVideoCompleted;
import com.fiap.tech.domain.conversor.ConversorVideoError;
import com.fiap.tech.domain.conversor.ConversorVideoProcessing;
import com.fiap.tech.domain.enums.ImageFormatEnum;
import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.StorageService;

public class ConversorVideoUseCaseDefault extends ConversorVideoUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversorVideoUseCaseDefault.class);
    
    private final StorageService storageService;
    private final FrameExtractorService frameExtractorService;
    private final CompressFileService compressFileService;
    private final EventService eventService;

    public ConversorVideoUseCaseDefault(
            StorageService storageService, 
            FrameExtractorService frameExtractorService,
            CompressFileService compressFileService,
            EventService eventService) {
        this.storageService = storageService;
        this.frameExtractorService = frameExtractorService;
        this.compressFileService = compressFileService;
        this.eventService = eventService;
    }

    @Override
    public void execute(ConversorVideoCommand command) {
        storageService.get(command.filePath())
            .ifPresentOrElse(
                videoResource -> processVideoCommand(command, videoResource),
                () -> logResourceNotFound(command)
            );
    }
    
    private void processVideoCommand(ConversorVideoCommand command, Resource videoResource) {
        try {
            var eventProcessing = new ConversorVideoProcessing(command.resourceId(), command.id());
            eventProcessing.publishDomainEvents(eventService::send);

            var content = videoResource.content();
            var frames = frameExtractorService.extract(content, ImageFormatEnum.JPEG);
            var zipResource = compressFileService.zip(command.id(), command.clientId(), frames);
            
            storageService.store(zipResource);

            var eventCompleted = new ConversorVideoCompleted(command.resourceId(), command.id(), zipResource, videoResource);
            eventCompleted.publishDomainEvents(eventService::send);

        } catch (Exception ex) {
            LOGGER.error("Erro ao processar conversão de vídeo para o recurso '{}': {}", command.resourceId(), ex.getMessage(), ex);
            var eventError = new ConversorVideoError(command.resourceId(), command.id(), ex.getMessage(), videoResource);
            eventError.publishDomainEvents(eventService::send);
        }
    }
    
    private void logResourceNotFound(ConversorVideoCommand command) {
        LOGGER.warn("Recurso não encontrado para o caminho: {}", command.filePath());
    }
}