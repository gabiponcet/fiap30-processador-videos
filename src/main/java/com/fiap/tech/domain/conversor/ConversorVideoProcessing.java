package com.fiap.tech.domain.conversor;

import java.time.Instant;

import com.fiap.tech.domain.events.DomainEvent;
import com.fiap.tech.domain.events.DomainEventSupport;
import com.fiap.tech.domain.util.InstantUtils;

public final class ConversorVideoProcessing extends DomainEventSupport implements ConversorVideoResult, DomainEvent {

    private static final String PROCESSING = "PROCESSING";

    private final String resourceId;
    private final String id;
    private final Instant occurredOn;

    public ConversorVideoProcessing(String resourceId, String id) {
        this.resourceId = resourceId;
        this.id = id;
        this.occurredOn = InstantUtils.now();

        registerEvent(this);
    }

    @Override
    public String getStatus() {
        return PROCESSING;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(
            "{\n" +
            "  \"status\": \"%s\",\n" +
            "  \"video\": {\n" +
            "    \"encoded_video_folder\": \"%s\",\n" +
            "    \"resource_id\": \"%s\",\n" +
            "    \"file_path\": \"%s\"\n" +
            "  },\n" +
            "  \"id\": \"%s\"\n" +
            "}", 
            getStatus(), 
            "",
            resourceId, 
            "", 
            id
        );
    }
}
