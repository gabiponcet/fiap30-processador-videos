package com.fiap.tech.infra.exception.compress;

public final class ZipCompressionException extends RuntimeException implements CompressionException {
    public ZipCompressionException(String message) {
        super(message);
    }
    public ZipCompressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
