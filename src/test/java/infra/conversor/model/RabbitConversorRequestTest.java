package infra.conversor.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.infra.conversor.model.RabbitConversorRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabbitConversorRequestTest {

    @Test
    void shouldDeserializeValidJson() throws JsonProcessingException {
        String json = """
            {
                "resource_id": "123",
                "id": "456",
                "client_id": "789",
                "file_path": "/videos/video.mp4",
                "occurred_on": "2023-10-01T10:00:00Z"
            }
        """;

        ObjectMapper objectMapper = new ObjectMapper();
        RabbitConversorRequest request = objectMapper.readValue(json, RabbitConversorRequest.class);

        assertEquals("123", request.resourceId());
        assertEquals("456", request.id());
        assertEquals("789", request.clientId());
        assertEquals("/videos/video.mp4", request.filePath());
        assertEquals("2023-10-01T10:00:00Z", request.occurredOn());
    }

    @Test
    void shouldSerializeToJson() throws JsonProcessingException {
        RabbitConversorRequest request = new RabbitConversorRequest("123", "456", "789", "/videos/video.mp4", "2023-10-01T10:00:00Z");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        assertTrue(json.contains("\"resource_id\":\"123\""));
        assertTrue(json.contains("\"id\":\"456\""));
        assertTrue(json.contains("\"client_id\":\"789\""));
        assertTrue(json.contains("\"file_path\":\"/videos/video.mp4\""));
        assertTrue(json.contains("\"occurred_on\":\"2023-10-01T10:00:00Z\""));
    }

    @Test
    void shouldHandleMissingOptionalFields() throws JsonProcessingException {
        String json = """
            {
                "resource_id": "123",
                "id": "456"
            }
        """;

        ObjectMapper objectMapper = new ObjectMapper();
        RabbitConversorRequest request = objectMapper.readValue(json, RabbitConversorRequest.class);

        assertEquals("123", request.resourceId());
        assertEquals("456", request.id());
        assertNull(request.clientId());
        assertNull(request.filePath());
        assertNull(request.occurredOn());
    }

    @Test
    void shouldThrowExceptionForInvalidJson() {
        String invalidJson = """
            {
                "resource_id": 123,
                "id": "456"
            }
        """;

        ObjectMapper objectMapper = new ObjectMapper();

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(invalidJson, RabbitConversorRequest.class));
    }
}