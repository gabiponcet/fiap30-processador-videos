package com.fiap.tech.infra.exception.frame;

public final class FrameExtractionException extends RuntimeException implements FrameException {
    public FrameExtractionException(String message) {
        super(message);
    }
    public FrameExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
