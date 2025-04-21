package com.fiap.tech.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VideoMediaTypeEnum {
    VIDEO;

    public static Optional<VideoMediaTypeEnum> of(final String value) {
        return Arrays.stream(values()).filter(type -> type.name().equalsIgnoreCase(value)).findFirst();
    }
}