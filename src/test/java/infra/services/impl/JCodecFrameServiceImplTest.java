package infra.services.impl;

import com.fiap.tech.domain.enums.ImageFormatEnum;
import com.fiap.tech.infra.exception.compress.ZipCompressionException;
import com.fiap.tech.infra.exception.frame.FrameExtractionException;
import com.fiap.tech.infra.exception.frame.FrameSaveException;
import com.fiap.tech.infra.services.impl.JCodecFrameServiceImpl;
import org.jcodec.api.JCodecException;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JCodecFrameServiceImplTest {

    @Test
    void shouldExtractFramesSuccessfully() throws IOException {
        Path outputDir = Files.createTempDirectory("frames-output");
        JCodecFrameServiceImpl service = new JCodecFrameServiceImpl(outputDir);

        byte[] videoBytes = Files.readAllBytes(Path.of("src/test/resources/sample-video.mp4"));

        List<File> frames = service.extract(videoBytes, ImageFormatEnum.PNG);

        assertNotNull(frames);
        assertFalse(frames.isEmpty());
        assertTrue(frames.get(0).getName().endsWith(".png"));
    }

    @Test
    void shouldThrowExceptionWhenVideoBytesAreNull() {
        Path outputDir = mock(Path.class);
        JCodecFrameServiceImpl service = new JCodecFrameServiceImpl(outputDir);

        FrameExtractionException exception = assertThrows(FrameExtractionException.class, () -> service.extract(null, ImageFormatEnum.PNG));

        assertEquals("Vídeo vazio ou nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenVideoBytesAreEmpty() {
        Path outputDir = mock(Path.class);
        JCodecFrameServiceImpl service = new JCodecFrameServiceImpl(outputDir);

        FrameExtractionException exception = assertThrows(FrameExtractionException.class, () -> service.extract(new byte[0], ImageFormatEnum.PNG));

        assertEquals("Vídeo vazio ou nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOutputDirCannotBeCreated() throws IOException {
        Path outputDir = mock(Path.class);
        doThrow(new IOException("Permission denied")).when(outputDir).toFile();

        assertThrows(ZipCompressionException.class, () -> new JCodecFrameServiceImpl(outputDir));
    }

    @Test
    void shouldThrowExceptionWhenFrameCannotBeProcessed() throws IOException, JCodecException {
        Path outputDir = Files.createTempDirectory("frames-output");
        JCodecFrameServiceImpl service = spy(new JCodecFrameServiceImpl(outputDir));

        doThrow(new JCodecException("Frame processing error")).when(service).extract(any(), any());

        FrameExtractionException exception = assertThrows(FrameExtractionException.class, () -> service.extract(new byte[10], ImageFormatEnum.PNG));

        assertTrue(exception.getMessage().contains("Erro ao extrair frames"));
    }

    @Test
    void shouldThrowExceptionWhenFrameCannotBeSaved() throws IOException {
        Path outputDir = Files.createTempDirectory("frames-output");
        JCodecFrameServiceImpl service = spy(new JCodecFrameServiceImpl(outputDir));

        BufferedImage image = mock(BufferedImage.class);
        doThrow(new IOException("Save error")).when(service).saveFrames(image, ImageFormatEnum.PNG);

        FrameSaveException exception = assertThrows(FrameSaveException.class, () -> service.saveFrames(image, ImageFormatEnum.PNG));

        assertTrue(exception.getMessage().contains("Não foi possível salvar frame"));
    }
}