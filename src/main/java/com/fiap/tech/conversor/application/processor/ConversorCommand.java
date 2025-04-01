package com.fiap.tech.conversor.application.processor;

public record ConversorCommand(String bucket, String videoKey) {

    public static ConversorCommand with(String bucket, String videoKey) {
        return new ConversorCommand(bucket, videoKey);
    }
}
