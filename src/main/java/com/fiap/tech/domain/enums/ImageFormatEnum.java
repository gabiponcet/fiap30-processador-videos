package com.fiap.tech.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ImageFormatEnum {
    PNG("png"),
    JPEG("jpeg");

    private final String mimeType;

    ImageFormatEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String mimeType() {
        return mimeType;
    }

    public static Optional<ImageFormatEnum> of(final String value) {
        return Arrays.stream(values()).filter(type -> type.name().equalsIgnoreCase(value)).findFirst();
    }
}
