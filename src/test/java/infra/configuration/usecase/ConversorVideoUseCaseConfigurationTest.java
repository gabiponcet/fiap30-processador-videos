package infra.configuration.usecase;

import com.fiap.tech.application.conversor.ConversorVideoUseCase;
import com.fiap.tech.application.conversor.ConversorVideoUseCaseDefault;
import com.fiap.tech.infra.configuration.usecase.ConversorVideoUseCaseConfiguration;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.FrameExtractorService;
import com.fiap.tech.infra.services.StorageService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversorVideoUseCaseConfigurationTest {

    @Test
    void shouldCreateConversorVideoUseCaseBeanSuccessfully() {
        StorageService storageService = mock(StorageService.class);
        FrameExtractorService frameExtractorService = mock(FrameExtractorService.class);
        CompressFileService compressFileService = mock(CompressFileService.class);
        EventService eventService = mock(EventService.class);

        ConversorVideoUseCaseConfiguration configuration = new ConversorVideoUseCaseConfiguration(
                storageService, frameExtractorService, compressFileService, eventService
        );

        ConversorVideoUseCase useCase = configuration.converterVideoUseCase();

        assertNotNull(useCase);
        assertTrue(useCase instanceof ConversorVideoUseCaseDefault);
    }

    @Test
    void shouldThrowExceptionWhenDependenciesAreNull() {
        assertThrows(NullPointerException.class, () ->
                new ConversorVideoUseCaseConfiguration(null, null, null, null)
        );
    }
}