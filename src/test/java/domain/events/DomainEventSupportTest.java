package domain.events;

import com.fiap.tech.domain.events.DomainEvent;
import com.fiap.tech.domain.events.DomainEventPublisher;
import com.fiap.tech.domain.events.DomainEventSupport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DomainEventSupportTest {

    @Test
    void shouldReturnUnmodifiableListOfDomainEvents() {
        DomainEventSupport domainEventSupport = new DomainEventSupport() {};
        DomainEvent event = mock(DomainEvent.class);
        domainEventSupport.registerEvent(event);

        List<DomainEvent> events = domainEventSupport.getDomainEvents();

        assertThrows(UnsupportedOperationException.class, () -> events.add(mock(DomainEvent.class)));
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }

    @Test
    void shouldAddEventToDomainEvents() {
        DomainEventSupport domainEventSupport = new DomainEventSupport() {};
        DomainEvent event = mock(DomainEvent.class);

        domainEventSupport.registerEvent(event);

        assertEquals(1, domainEventSupport.getDomainEvents().size());
        assertEquals(event, domainEventSupport.getDomainEvents().get(0));
    }

    @Test
    void shouldPublishAllDomainEventsAndClearList() {
        DomainEventSupport domainEventSupport = new DomainEventSupport() {};
        DomainEvent event1 = mock(DomainEvent.class);
        DomainEvent event2 = mock(DomainEvent.class);
        DomainEventPublisher publisher = mock(DomainEventPublisher.class);

        domainEventSupport.registerEvent(event1);
        domainEventSupport.registerEvent(event2);
        domainEventSupport.publishDomainEvents(publisher);

        verify(publisher).publish(event1);
        verify(publisher).publish(event2);
        assertTrue(domainEventSupport.getDomainEvents().isEmpty());
    }

    @Test
    void shouldNotThrowExceptionWhenPublisherIsNull() {
        DomainEventSupport domainEventSupport = new DomainEventSupport() {};
        DomainEvent event = mock(DomainEvent.class);

        domainEventSupport.registerEvent(event);
        assertDoesNotThrow(() -> domainEventSupport.publishDomainEvents(null));
        assertEquals(1, domainEventSupport.getDomainEvents().size());
    }
}