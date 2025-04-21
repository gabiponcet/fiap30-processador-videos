package com.fiap.tech.infra.conversor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RabbitConversorRequest(@JsonProperty("resource_id") String resourceId, @JsonProperty("id") String id, @JsonProperty("client_id") String clientId, @JsonProperty("file_path") String filePath, @JsonProperty("occurred_on") String occurredOn) {
    
}
