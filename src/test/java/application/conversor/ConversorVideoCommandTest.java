package application.conversor;

import com.fiap.tech.application.conversor.ConversorVideoCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversorVideoCommandTest {

    @Test
    void shouldCreateCommandWithValidParameters() {
        String resourceId = "123";
        String id = "456";
        String clientId = "789";
        String filePath = "/videos/video.mp4";

        ConversorVideoCommand command = ConversorVideoCommand.with(resourceId, id, clientId, filePath);

        assertEquals(resourceId, command.resourceId());
        assertEquals(id, command.id());
        assertEquals(clientId, command.clientId());
        assertEquals(filePath, command.filePath());
    }

    @Test
    void shouldThrowExceptionWhenResourceIdIsNull() {
        String id = "456";
        String clientId = "789";
        String filePath = "/videos/video.mp4";

        assertThrows(NullPointerException.class, () -> ConversorVideoCommand.with(null, id, clientId, filePath));
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        String resourceId = "123";
        String clientId = "789";
        String filePath = "/videos/video.mp4";

        assertThrows(NullPointerException.class, () -> ConversorVideoCommand.with(resourceId, null, clientId, filePath));
    }

    @Test
    void shouldThrowExceptionWhenClientIdIsNull() {
        String resourceId = "123";
        String id = "456";
        String filePath = "/videos/video.mp4";

        assertThrows(NullPointerException.class, () -> ConversorVideoCommand.with(resourceId, id, null, filePath));
    }

    @Test
    void shouldThrowExceptionWhenFilePathIsNull() {
        String resourceId = "123";
        String id = "456";
        String clientId = "789";

        assertThrows(NullPointerException.class, () -> ConversorVideoCommand.with(resourceId, id, clientId, null));
    }
}