package application.conversor;

import com.fiap.tech.application.conversor.ConversorVideoCommand;
import com.fiap.tech.application.conversor.ConversorVideoUseCaseDefault;
import com.fiap.tech.domain.conversor.ConversorVideoCompleted;
import com.fiap.tech.domain.conversor.ConversorVideoError;
import com.fiap.tech.domain.conversor.ConversorVideoProcessing;
import com.fiap.tech.domain.enums.ImageFormatEnum;
import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.StorageService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConversorVideoUseCaseDefaultTest {

    @Test
    void shouldLogErrorWhenFrameExtractionFails() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        Resource videoResource = mock(Resource.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn("/videos/video.mp4");
        when(command.resourceId()).thenReturn("123");
        when(command.id()).thenReturn("456");
        when(storageService.get("/videos/video.mp4")).thenReturn(Optional.of(videoResource));
        when(videoResource.content()).thenReturn(new byte[0]);
        when(frameExtractorService.extract(any(), eq(ImageFormatEnum.JPEG))).thenThrow(new RuntimeException("Frame extraction error"));

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(eventService, times(1)).send(any(ConversorVideoProcessing.class));
        verify(eventService, times(1)).send(any(ConversorVideoError.class));
    }

    @Test
    void shouldNotStoreZipWhenCompressionFails() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        Resource videoResource = mock(Resource.class);
        File mockFile = mock(File.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn("/videos/video.mp4");
        when(command.resourceId()).thenReturn("123");
        when(command.id()).thenReturn("456");
        when(command.clientId()).thenReturn("789");
        when(storageService.get("/videos/video.mp4")).thenReturn(Optional.of(videoResource));
        when(videoResource.content()).thenReturn(new byte[0]);
        when(frameExtractorService.extract(any(), eq(ImageFormatEnum.JPEG))).thenReturn(Collections.singletonList(mockFile));
        when(compressFileService.zip("456", "789", Collections.singletonList(mockFile)))
                .thenThrow(new RuntimeException("Compression error"));

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(eventService, times(1)).send(any(ConversorVideoProcessing.class));
        verify(eventService, times(1)).send(any(ConversorVideoError.class));
        verify(storageService, never()).store(any());
    }

    @Test
    void shouldNotSendCompletedEventWhenStorageFails() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        Resource videoResource = mock(Resource.class);
        Resource zipResource = mock(Resource.class);
        File mockFile = mock(File.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn("/videos/video.mp4");
        when(command.resourceId()).thenReturn("123");
        when(command.id()).thenReturn("456");
        when(command.clientId()).thenReturn("789");
        when(storageService.get("/videos/video.mp4")).thenReturn(Optional.of(videoResource));
        when(videoResource.content()).thenReturn(new byte[0]);
        when(frameExtractorService.extract(any(), eq(ImageFormatEnum.JPEG))).thenReturn(Collections.singletonList(mockFile));
        when(compressFileService.zip("456", "789", Collections.singletonList(mockFile))).thenReturn(zipResource);
        doThrow(new RuntimeException("Storage error")).when(storageService).store(zipResource);

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(eventService, times(1)).send(any(ConversorVideoProcessing.class));
        verify(eventService, times(1)).send(any(ConversorVideoError.class));
        verify(eventService, never()).send(any(ConversorVideoCompleted.class));
    }

    @Test
    void shouldHandleNullContentFromVideoResource() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        Resource videoResource = mock(Resource.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn("/videos/video.mp4");
        when(command.resourceId()).thenReturn("123");
        when(command.id()).thenReturn("456");
        when(storageService.get("/videos/video.mp4")).thenReturn(Optional.of(videoResource));
        when(videoResource.content()).thenReturn(null);

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(eventService, times(1)).send(any(ConversorVideoProcessing.class));
    }

    @Test
    void shouldHandleExceptionWhenEventServiceFails() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        Resource videoResource = mock(Resource.class);
        Resource zipResource = mock(Resource.class);
        File mockFile = mock(File.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn("/videos/video.mp4");
        when(command.resourceId()).thenReturn("123");
        when(command.id()).thenReturn("456");
        when(command.clientId()).thenReturn("789");
        when(storageService.get("/videos/video.mp4")).thenReturn(Optional.of(videoResource));
        when(videoResource.content()).thenReturn(new byte[0]);
        when(frameExtractorService.extract(any(), eq(ImageFormatEnum.JPEG))).thenReturn(Collections.singletonList(mockFile));
        when(compressFileService.zip("456", "789", Collections.singletonList(mockFile))).thenReturn(zipResource);
        doThrow(new RuntimeException("Event service error")).when(eventService).send(any());

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertThrows(RuntimeException.class, () -> useCase.execute(command));
        verify(eventService, times(1)).send(any(ConversorVideoError.class));
        verify(eventService, never()).send(any(ConversorVideoCompleted.class));
    }

    @Test
    void shouldNotProcessWhenFilePathIsNull() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        when(command.filePath()).thenReturn(null);

        ConversorVideoUseCaseDefault useCase = new ConversorVideoUseCaseDefault(
                storageService, frameExtractorService, compressFileService, eventService);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(eventService, never()).send(any());
    }
}