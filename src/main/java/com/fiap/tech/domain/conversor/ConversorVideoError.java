package com.fiap.tech.domain.conversor;

import java.time.Instant;

import com.fiap.tech.domain.events.DomainEvent;
import com.fiap.tech.domain.events.DomainEventSupport;
import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.domain.util.InstantUtils;

public final class ConversorVideoError extends DomainEventSupport implements ConversorVideoResult, DomainEvent {

    private static final String ERROR = "ERROR";
    
    private final String resourceId;
    private final String id;
    private final String message;
    private final Resource videoResource;
    private final Instant occurredOn;
    
    public ConversorVideoError(String resourceId, String id, String message, Resource videoResource) {
        this.resourceId = resourceId;
        this.id = id;
        this.message = message;
        this.videoResource = videoResource;
        this.occurredOn = InstantUtils.now();
        
        registerEvent(this);
    }
    
    @Override
    public String getStatus() {
       return ERROR;
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

    public String getMessage() {
        return message;
    }

    public Resource getVideoResource() {
        return videoResource;
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