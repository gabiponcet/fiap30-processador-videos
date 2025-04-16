package com.fiap.tech.domain.events;

@FunctionalInterface
public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
