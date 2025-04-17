package infra.amqp;

import com.fiap.tech.application.conversor.ConversorVideoCommand;
import com.fiap.tech.application.conversor.ConversorVideoUseCase;
import com.fiap.tech.infra.amqp.ConversorEventListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class ConversorEventListenerTest {

    @Test
    void shouldNotProcessWhenMessageIsNull() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        Logger logger = mock(Logger.class);
        ConversorEventListener listener = new ConversorEventListener(useCase);

        listener.consumeConversorEvent(null);

        verifyNoInteractions(useCase);
    }

    @Test
    void shouldNotProcessWhenMessageIsEmpty() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        Logger logger = mock(Logger.class);
        ConversorEventListener listener = new ConversorEventListener(useCase);

        listener.consumeConversorEvent("   ");

        verifyNoInteractions(useCase);
    }

    @Test
    void shouldNotProcessWhenPayloadIsInvalid() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        Logger logger = mock(Logger.class);
        ConversorEventListener listener = new ConversorEventListener(useCase);

        listener.consumeConversorEvent("invalid-json");

        verifyNoInteractions(useCase);
    }

    @Test
    void shouldProcessValidMessage() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        ConversorEventListener listener = new ConversorEventListener(useCase);

        String validMessage = "{\"resourceId\":\"123\",\"id\":\"456\",\"clientId\":\"789\",\"filePath\":\"/videos/video.mp4\"}";

        listener.consumeConversorEvent(validMessage);

        verify(useCase, times(1)).execute(any(ConversorVideoCommand.class));
    }

    @Test
    void shouldLogErrorWhenUseCaseThrowsException() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        doThrow(new RuntimeException("Use case error")).when(useCase).execute(any());
        ConversorEventListener listener = new ConversorEventListener(useCase);

        String validMessage = "{\"resourceId\":\"123\",\"id\":\"456\",\"clientId\":\"789\",\"filePath\":\"/videos/video.mp4\"}";

        listener.consumeConversorEvent(validMessage);

        verify(useCase, times(1)).execute(any(ConversorVideoCommand.class));
    }
}