package infra.configuration;

import com.fiap.tech.infra.configuration.EventServiceConfiguration;
import com.fiap.tech.infra.configuration.properties.amqp.QueueProperties;
import com.fiap.tech.infra.services.EventService;
import com.fiap.tech.infra.services.impl.ConversorEventServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceConfigurationTest {

    @Test
    void shouldCreateConversorEventServiceBeanSuccessfully() {
        QueueProperties props = mock(QueueProperties.class);
        RabbitOperations ops = mock(RabbitOperations.class);
        EventServiceConfiguration configuration = new EventServiceConfiguration();

        when(props.getExchange()).thenReturn("test-exchange");
        when(props.getRoutingKey()).thenReturn("test-routingKey");

        EventService service = configuration.conversorEventService(props, ops);

        assertNotNull(service);
        assertTrue(service instanceof ConversorEventServiceImpl);
    }

    @Test
    void shouldThrowExceptionWhenQueuePropertiesIsNull() {
        RabbitOperations ops = mock(RabbitOperations.class);
        EventServiceConfiguration configuration = new EventServiceConfiguration();

        assertThrows(NullPointerException.class, () -> configuration.conversorEventService(null, ops));
    }

    @Test
    void shouldThrowExceptionWhenRabbitOperationsIsNull() {
        QueueProperties props = mock(QueueProperties.class);
        EventServiceConfiguration configuration = new EventServiceConfiguration();

        assertThrows(NullPointerException.class, () -> configuration.conversorEventService(props, null));
    }
}