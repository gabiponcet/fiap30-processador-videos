package infra.configuration.properties.amqp;

import com.fiap.tech.infra.configuration.properties.amqp.QueueProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueuePropertiesTest {

    @Test
    void shouldSetAndGetExchangeSuccessfully() {
        QueueProperties properties = new QueueProperties();
        properties.setExchange("test-exchange");

        assertEquals("test-exchange", properties.getExchange());
    }

    @Test
    void shouldSetAndGetRoutingKeySuccessfully() {
        QueueProperties properties = new QueueProperties();
        properties.setRoutingKey("test-routingKey");

        assertEquals("test-routingKey", properties.getRoutingKey());
    }

    @Test
    void shouldSetAndGetQueueSuccessfully() {
        QueueProperties properties = new QueueProperties();
        properties.setQueue("test-queue");

        assertEquals("test-queue", properties.getQueue());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        QueueProperties properties = new QueueProperties()
                .setExchange("test-exchange")
                .setRoutingKey("test-routingKey")
                .setQueue("test-queue");

        String expected = "QueueProperties{exchange='test-exchange', routingKey='test-routingKey', queue='test-queue'}";
        assertEquals(expected, properties.toString());
    }

    @Test
    void shouldLogPropertiesAfterInitialization() throws Exception {
        QueueProperties properties = new QueueProperties()
                .setExchange("test-exchange")
                .setRoutingKey("test-routingKey")
                .setQueue("test-queue");

        properties.afterPropertiesSet();

        assertNotNull(properties.toString()); // Ensures no exception is thrown during logging
    }
}