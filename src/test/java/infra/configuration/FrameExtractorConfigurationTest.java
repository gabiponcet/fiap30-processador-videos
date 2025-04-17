package infra.configuration;

import com.fiap.tech.infra.configuration.FrameExtractorConfiguration;
import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.impl.JCodecFrameServiceImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FrameExtractorConfigurationTest {

    @Test
    void shouldCreateJCodecFrameServiceBeanSuccessfully() {
        FrameExtractorConfiguration configuration = new FrameExtractorConfiguration();

        FrameExtractorService service = configuration.jCodecFrame();

        assertNotNull(service);
        assertTrue(service instanceof JCodecFrameServiceImpl);
    }

    @Test
    void shouldUseSystemTempDirectoryAsDefaultPath() {
        FrameExtractorConfiguration configuration = new FrameExtractorConfiguration();

        JCodecFrameServiceImpl service = (JCodecFrameServiceImpl) configuration.jCodecFrame();

        assertEquals(Path.of(System.getProperty("java.io.tmpdir")), true);
    }
}