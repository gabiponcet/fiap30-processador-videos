package com.fiap.tech.infra.exception.frame;

public final class FrameSaveException extends RuntimeException implements FrameException {
    public FrameSaveException(String message) {
        super(message);
    }
    public FrameSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
