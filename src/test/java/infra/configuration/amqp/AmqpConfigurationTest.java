package infra.configuration.amqp;

import com.fiap.tech.infra.configuration.amqp.AmqpConfiguration;
import com.fiap.tech.infra.configuration.annotations.ConversorReponseQueue;
import com.fiap.tech.infra.configuration.annotations.ConversorRequestQueue;
import com.fiap.tech.infra.configuration.properties.amqp.QueueProperties;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmqpConfigurationTest {

    @Test
    void shouldCreateConversorResponseQueuePropertiesSuccessfully() {
        AmqpConfiguration configuration = new AmqpConfiguration();

        QueueProperties props = configuration.conversorResponseQueueProperties();

        assertNotNull(props);
    }

    @Test
    void shouldCreateConversorRequestQueuePropertiesSuccessfully() {
        AmqpConfiguration configuration = new AmqpConfiguration();

        QueueProperties props = configuration.conversorRequestQueueProperties();

        assertNotNull(props);
    }

    @Test
    void shouldCreateConversorResponseExchangeSuccessfully() {
        QueueProperties props = mock(QueueProperties.class);
        when(props.getExchange()).thenReturn("response-exchange");

        AmqpConfiguration configuration = new AmqpConfiguration();

        DirectExchange exchange = configuration.conversorResponseExchange(props);

        assertNotNull(exchange);
        assertEquals("response-exchange", exchange.getName());
    }

    @Test
    void shouldCreateConversorResponseQueueSuccessfully() {
        QueueProperties props = mock(QueueProperties.class);
        when(props.getQueue()).thenReturn("response-queue");

        AmqpConfiguration configuration = new AmqpConfiguration();

        Queue queue = configuration.conversorResponseQueue(props);

        assertNotNull(queue);
        assertEquals("response-queue", queue.getName());
    }

    @Test
    void shouldCreateConversorRequestExchangeSuccessfully() {
        QueueProperties props = mock(QueueProperties.class);
        when(props.getExchange()).thenReturn("request-exchange");

        AmqpConfiguration configuration = new AmqpConfiguration();

        DirectExchange exchange = configuration.conversorRequestExchange(props);

        assertNotNull(exchange);
        assertEquals("request-exchange", exchange.getName());
    }

    @Test
    void shouldCreateConversorRequestQueueSuccessfully() {
        QueueProperties props = mock(QueueProperties.class);
        when(props.getQueue()).thenReturn("request-queue");

        AmqpConfiguration configuration = new AmqpConfiguration();

        Queue queue = configuration.conversorRequestQueue(props);

        assertNotNull(queue);
        assertEquals("request-queue", queue.getName());
    }

}