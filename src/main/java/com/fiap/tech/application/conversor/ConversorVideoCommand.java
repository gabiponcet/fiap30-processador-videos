package com.fiap.tech.application.conversor;

public record ConversorVideoCommand(String resourceId, String id, String clientId, String filePath) {

    public static ConversorVideoCommand with(String resourceId, String id, String clientId, String filePath) {
        return new ConversorVideoCommand(resourceId, id, clientId, filePath);
    }
}