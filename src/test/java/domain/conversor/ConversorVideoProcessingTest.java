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

}