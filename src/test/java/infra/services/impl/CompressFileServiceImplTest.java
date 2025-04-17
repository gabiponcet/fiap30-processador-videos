package infra.services.impl;

import com.fiap.tech.domain.enums.CompressContentTypeEnum;
import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import com.fiap.tech.infra.exception.compress.ZipCompressionException;
import com.fiap.tech.infra.services.impl.CompressFileServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompressFileServiceImplTest {

    @Test
    void shouldCreateZipWithValidFiles() throws IOException {
        Path outputDir = Files.createTempDirectory("test-output");
        StorageProperties props = mock(StorageProperties.class);
        when(props.getFileNamePattern()).thenReturn("{type}.zip");
        when(props.getLocationPattern()).thenReturn("output/{videoId}/{clientId}");

        File file = Files.createTempFile("test-file", ".txt").toFile();
        Files.writeString(file.toPath(), "Test content");

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        Resource resource = service.zip("123", "456", List.of(file));

        assertNotNull(resource);
        assertEquals("ZIP", resource.contentType());
        assertTrue(Files.exists(outputDir.resolve("output/123/456/ZIP.zip")));
    }

    @Test
    void shouldThrowExceptionWhenFileListIsEmpty() {
        Path outputDir = mock(Path.class);
        StorageProperties props = mock(StorageProperties.class);

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        ZipCompressionException exception = assertThrows(ZipCompressionException.class, () -> service.zip("123", "456", Collections.emptyList()));

        assertEquals("Lista de arquivos para compactar está vazia ou nula", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOutputDirCannotBeCreated() throws IOException {
        Path outputDir = mock(Path.class);
        when(outputDir.resolve(anyString())).thenThrow(new IOException("Permission denied"));
        StorageProperties props = mock(StorageProperties.class);

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        ZipCompressionException exception = assertThrows(ZipCompressionException.class, () -> service.zip("123", "456", List.of(mock(File.class))));

        assertTrue(exception.getMessage().contains("Erro ao criar o arquivo ZIP"));
    }

    @Test
    void shouldThrowExceptionWhenFileCannotBeCompressed() throws IOException {
        Path outputDir = Files.createTempDirectory("test-output");
        StorageProperties props = mock(StorageProperties.class);
        when(props.getFileNamePattern()).thenReturn("{type}.zip");
        when(props.getLocationPattern()).thenReturn("output/{videoId}/{clientId}");

        File file = mock(File.class);
        when(file.getName()).thenReturn("test-file.txt");
        when(file.toPath()).thenThrow(new IOException("File read error"));

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        ZipCompressionException exception = assertThrows(ZipCompressionException.class, () -> service.zip("123", "456", List.of(file)));

        assertTrue(exception.getMessage().contains("Falha ao adicionar arquivo ao ZIP"));
    }

    @Test
    void shouldThrowExceptionWhenOutputDirCannotBeInitialized() throws IOException {
        Path outputDir = mock(Path.class);
        doThrow(new IOException("Initialization error")).when(outputDir).toFile();
        StorageProperties props = mock(StorageProperties.class);

        assertThrows(ZipCompressionException.class, () -> new CompressFileServiceImpl(outputDir, props));
    }
}