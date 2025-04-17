package infra.services.impl;

import com.fiap.tech.infra.services.impl.ConversorEventServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConversorEventServiceImplTest {

    @Test
    void shouldSendEventSuccessfully() {
        RabbitOperations ops = mock(RabbitOperations.class);
        Logger logger = mock(Logger.class);
        ConversorEventServiceImpl service = new ConversorEventServiceImpl("exchange", "routingKey", ops);

        Object event = new Object();

        service.send(event);

        verify(ops, times(1)).convertAndSend("exchange", "routingKey", event.toString());
    }

    @Test
    void shouldLogErrorWhenSendingFails() {
        RabbitOperations ops = mock(RabbitOperations.class);
        doThrow(new RuntimeException("RabbitMQ error")).when(ops).convertAndSend(anyString(), anyString(), anyString());
        Logger logger = mock(Logger.class);
        ConversorEventServiceImpl service = new ConversorEventServiceImpl("exchange", "routingKey", ops);

        Object event = new Object();

        service.send(event);

        verify(ops, times(1)).convertAndSend("exchange", "routingKey", event.toString());
    }

    @Test
    void shouldThrowExceptionWhenExchangeIsNull() {
        RabbitOperations ops = mock(RabbitOperations.class);

        assertThrows(NullPointerException.class, () -> new ConversorEventServiceImpl(null, "routingKey", ops));
    }

    @Test
    void shouldThrowExceptionWhenRoutingKeyIsNull() {
        RabbitOperations ops = mock(RabbitOperations.class);

        assertThrows(NullPointerException.class, () -> new ConversorEventServiceImpl("exchange", null, ops));
    }

    @Test
    void shouldThrowExceptionWhenRabbitOperationsIsNull() {
        assertThrows(NullPointerException.class, () -> new ConversorEventServiceImpl("exchange", "routingKey", null));
    }
}