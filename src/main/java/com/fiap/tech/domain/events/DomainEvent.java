package com.fiap.tech.domain.events;

import java.io.Serializable;
import java.time.Instant;

public interface DomainEvent extends Serializable {

    Instant occurredOn();
}
