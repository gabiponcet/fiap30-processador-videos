package domain.conversor;

import com.fiap.tech.domain.conversor.ConversorVideoProcessing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversorVideoProcessingTest {

    @Test
    void shouldCreateProcessingEventWithValidParameters() {
        String resourceId = "123";
        String id = "456";

        ConversorVideoProcessing event = new ConversorVideoProcessing(resourceId, id);

        assertEquals("PROCESSING", event.getStatus());
        assertEquals(resourceId, event.getResourceId());
        assertEquals(id, event.getId());
        assertNotNull(event.occurredOn());
    }

    @Test
    void shouldThrowExceptionWhenResourceIdIsNull() {
        String id = "456";

        assertThrows(NullPointerException.class, () -> new ConversorVideoProcessing(null, id));
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        String resourceId = "123";

        assertThrows(NullPointerException.class, () -> new ConversorVideoProcessing(resourceId, null));
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        String resourceId = "123";
        String id = "456";

        ConversorVideoProcessing event = new ConversorVideoProcessing(resourceId, id);

        String expected = "{\n" +
                "  \"status\": \"PROCESSING\",\n" +
                "  \"video\": {\n" +
                "    \"encoded_video_folder\": \"\",\n" +
                "    \"resource_id\": \"123\",\n" +
                "    \"file_path\": \"\",\n" +
                "  },\n" +
                "  \"id\": \"456\"\n" +
                "}";
        assertEquals(expected, event.toString());
    }
}