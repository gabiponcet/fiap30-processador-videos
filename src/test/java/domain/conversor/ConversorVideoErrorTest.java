package domain.conversor;

import com.fiap.tech.domain.conversor.ConversorVideoError;
import com.fiap.tech.domain.resource.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversorVideoErrorTest {

    @Test
    void shouldCreateErrorEventWithValidParameters() {
        String resourceId = "123";
        String id = "456";
        String message = "Conversion failed";
        Resource videoResource = mock(Resource.class);

        ConversorVideoError event = new ConversorVideoError(resourceId, id, message, videoResource);

        assertEquals("ERROR", event.getStatus());
        assertEquals(resourceId, event.getResourceId());
        assertEquals(id, event.getId());
        assertEquals(message, event.getMessage());
        assertEquals(videoResource, event.getVideoResource());
        assertNotNull(event.occurredOn());
    }

    @Test
    void shouldThrowExceptionWhenResourceIdIsNull() {
        String id = "456";
        String message = "Conversion failed";
        Resource videoResource = mock(Resource.class);

        assertThrows(NullPointerException.class, () -> new ConversorVideoError(null, id, message, videoResource));
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        String resourceId = "123";
        String message = "Conversion failed";
        Resource videoResource = mock(Resource.class);

        assertThrows(NullPointerException.class, () -> new ConversorVideoError(resourceId, null, message, videoResource));
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        String resourceId = "123";
        String id = "456";
        Resource videoResource = mock(Resource.class);

        assertThrows(NullPointerException.class, () -> new ConversorVideoError(resourceId, id, null, videoResource));
    }

    @Test
    void shouldThrowExceptionWhenVideoResourceIsNull() {
        String resourceId = "123";
        String id = "456";
        String message = "Conversion failed";

        assertThrows(NullPointerException.class, () -> new ConversorVideoError(resourceId, id, message, null));
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        String resourceId = "123";
        String id = "456";
        String message = "Conversion failed";
        Resource videoResource = mock(Resource.class);

        when(videoResource.folderPath()).thenReturn("/failed_videos");
        when(videoResource.name()).thenReturn("video.mp4");

        ConversorVideoError event = new ConversorVideoError(resourceId, id, message, videoResource);

        String expected = "{\n" +
                "  \"status\": \"ERROR\",\n" +
                "  \"video\": {\n" +
                "    \"encoded_video_folder\": \"/failed_videos\",\n" +
                "    \"resource_id\": \"123\",\n" +
                "    \"file_path\": \"video.mp4\"\n" +
                "  },\n" +
                "  \"id\": \"456\"\n" +
                "}";
        assertEquals(expected, event.toString());
    }
}