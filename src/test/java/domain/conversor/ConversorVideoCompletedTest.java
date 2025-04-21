package domain.conversor;

import com.fiap.tech.domain.conversor.ConversorVideoCompleted;
import com.fiap.tech.domain.resource.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversorVideoCompletedTest {

    @Test
    void shouldCreateEventWithValidParameters() {
        String resourceId = "123";
        String id = "456";
        Resource zipResource = mock(Resource.class);
        Resource videoResource = mock(Resource.class);

        ConversorVideoCompleted event = new ConversorVideoCompleted(resourceId, id, zipResource, videoResource);

        assertEquals("COMPLETED", event.getStatus());
        assertEquals(resourceId, event.getResourceId());
        assertEquals(zipResource, event.getZipResource());
        assertEquals(videoResource, event.getVideoResource());
        assertNotNull(event.occurredOn());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        String resourceId = "123";
        String id = "456";
        Resource zipResource = mock(Resource.class);
        Resource videoResource = mock(Resource.class);

        when(zipResource.folderPath()).thenReturn("/encoded_videos");
        when(zipResource.name()).thenReturn("video.zip");

        ConversorVideoCompleted event = new ConversorVideoCompleted(resourceId, id, zipResource, videoResource);

        String expected = "{\n" +
                "  \"status\": \"COMPLETED\",\n" +
                "  \"video\": {\n" +
                "    \"encoded_video_folder\": \"/encoded_videos\",\n" +
                "    \"resource_id\": \"123\",\n" +
                "    \"file_path\": \"video.zip\"\n" +
                "  },\n" +
                "  \"id\": \"456\"\n" +
                "}";
        assertEquals(expected, event.toString());
    }
}