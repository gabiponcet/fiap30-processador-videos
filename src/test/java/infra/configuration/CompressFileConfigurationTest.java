package infra.configuration;

import com.fiap.tech.infra.configuration.CompressFileConfiguration;
import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.impl.CompressFileServiceImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompressFileConfigurationTest {

    @Test
    void shouldCreateCompressFileServiceBeanSuccessfully() {
        StorageProperties storageProperties = mock(StorageProperties.class);
        CompressFileConfiguration configuration = new CompressFileConfiguration();

        CompressFileService service = configuration.compressFile(storageProperties);

        assertNotNull(service);
        assertTrue(service instanceof CompressFileServiceImpl);
    }

    @Test
    void shouldUseSystemTempDirectoryAsDefaultPath() {
        StorageProperties storageProperties = mock(StorageProperties.class);
        CompressFileConfiguration configuration = new CompressFileConfiguration();

        CompressFileServiceImpl service = (CompressFileServiceImpl) configuration.compressFile(storageProperties);

        assertEquals(Path.of(System.getProperty("java.io.tmpdir")), true);
    }
}