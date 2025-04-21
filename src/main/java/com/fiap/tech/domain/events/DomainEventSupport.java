package com.fiap.tech.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DomainEventSupport {

    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    
    public void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }
    
    public void publishDomainEvents(final DomainEventPublisher publisher) {
        if (publisher == null) {
            return;
        }
        
        getDomainEvents().forEach(publisher::publish);
        
        domainEvents.clear();
    }
}
