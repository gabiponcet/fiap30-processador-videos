package com.fiap.tech.conversor.application.processor;

import java.io.File;
import java.util.List;

import com.fiap.tech.conversor.infra.service.CompressFileService;
import com.fiap.tech.conversor.infra.service.EventService;
import com.fiap.tech.conversor.infra.service.FileService;
import com.fiap.tech.conversor.infra.service.FrameExtractorService;

public class DefaultConversorVideoUseCase extends ConversorVideoUseCase {

    private FileService fileService;
    private EventService eventService;
    private CompressFileService compressFileService;
    private FrameExtractorService frameExtractorService;

    public DefaultConversorVideoUseCase(
        FileService fileService,
        EventService eventService,
        CompressFileService compressFileService,
        FrameExtractorService frameExtractorService
    ) {
        this.fileService = fileService;
        this.eventService = eventService;
        this.compressFileService = compressFileService;
        this.frameExtractorService = frameExtractorService;
    }

    @Override
    public Void execute(ConversorCommand cCmd) {
        try {
            fileService.download(cCmd.bucket(), cCmd.videoKey());

            List<File> frames = frameExtractorService.extract(cCmd.videoKey());

            File zipFile = compressFileService.zip(frames, cCmd.videoKey());

            String uploadKey = fileService.upload(cCmd.bucket(), zipFile, cCmd.videoKey());

            eventService.send(new ConversorOutput(true, "Sucesso", uploadKey));
        } catch (Exception e) {
            eventService.send(new ConversorOutput(false, e.getMessage(), null));
        }   

        return null;
    }
}