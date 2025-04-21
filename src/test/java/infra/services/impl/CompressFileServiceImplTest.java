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
    void shouldThrowExceptionWhenFileListIsEmpty() throws IOException {
        Path outputDir = Files.createTempDirectory("test-output");
        StorageProperties props = mock(StorageProperties.class);

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        ZipCompressionException exception = assertThrows(ZipCompressionException.class, () -> service.zip("123", "456", Collections.emptyList()));

        assertEquals("Lista de arquivos para compactar está vazia ou nula", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFileCannotBeCompressed() throws IOException {
        Path outputDir = Files.createTempDirectory("test-output");
        StorageProperties props = mock(StorageProperties.class);
        when(props.getFileNamePattern()).thenReturn("{type}.zip");
        when(props.getLocationPattern()).thenReturn("output/{videoId}/{clientId}");

        File file = mock(File.class);
        when(file.getName()).thenReturn("test-file.txt");
        doAnswer(invocation -> {
            throw new IOException("File read error");
        }).when(file).toPath();

        CompressFileServiceImpl service = new CompressFileServiceImpl(outputDir, props);

        ZipCompressionException exception = assertThrows(ZipCompressionException.class, () -> service.zip("123", "456", List.of(file)));

        assertTrue(exception.getMessage().contains("Falha ao adicionar arquivo ao ZIP"));
    }

}