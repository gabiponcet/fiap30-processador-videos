package com.fiap.tech.application.conversor;

import java.util.Objects;

public class ConversorVideoCommand {
    private final String resourceId;
    private final String id;
    private final String clientId;
    private final String filePath;

    private ConversorVideoCommand(String resourceId, String id, String clientId, String filePath) {
        this.resourceId = resourceId;
        this.id = id;
        this.clientId = clientId;
        this.filePath = filePath;
    }

    public static ConversorVideoCommand with(String resourceId, String id, String clientId, String filePath) {
        Objects.requireNonNull(resourceId, "ResourceId cannot be null");
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(clientId, "ClientId cannot be null");
        Objects.requireNonNull(filePath, "FilePath cannot be null");

        return new ConversorVideoCommand(resourceId, id, clientId, filePath);
    }

    public String resourceId() {
        return resourceId;
    }

    public String id() {
        return id;
    }

    public String clientId() {
        return clientId;
    }

    public String filePath() {
        return filePath;
    }
}