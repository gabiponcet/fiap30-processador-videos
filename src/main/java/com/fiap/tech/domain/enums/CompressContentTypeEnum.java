package com.fiap.tech.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CompressContentTypeEnum {
    ZIP("application/zip"),
    RAR("application/x-rar-compressed"),
    TAR("application/x-tar");

    private final String mimeType;

    CompressContentTypeEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String mimeType() {
        return mimeType;
    }

    public static Optional<CompressContentTypeEnum> of(final String value) {
        return Arrays.stream(values()).filter(type -> type.name().equalsIgnoreCase(value)).findFirst();
    }
}
