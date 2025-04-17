package application.conversor;

import com.fiap.tech.application.conversor.ConversorVideoUseCase;
import com.fiap.tech.application.conversor.ConversorVideoCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversorVideoUseCaseTest {

    @Test
    void shouldExecuteUseCaseWithValidCommand() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);
        ConversorVideoCommand command = mock(ConversorVideoCommand.class);

        doNothing().when(useCase).execute(command);

        assertDoesNotThrow(() -> useCase.execute(command));
        verify(useCase).execute(command);
    }

    @Test
    void shouldThrowExceptionWhenCommandIsNull() {
        ConversorVideoUseCase useCase = mock(ConversorVideoUseCase.class);

        assertThrows(NullPointerException.class, () -> useCase.execute(null));
    }
}