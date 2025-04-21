package com.fiap.tech.domain.resource;

import com.fiap.tech.domain.ValueObject;

import java.util.Objects;

public class Resource extends ValueObject {

    private final byte[] content;
    private final String contentType;
    private final String name;
    private final String folderPath;
    private final String filePath;

    private Resource(
            final byte[] content,
            final String contentType,
            final String name,
            final String folderPath,
            final String filePath) {
        this.content = Objects.requireNonNull(content);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
        this.folderPath = Objects.requireNonNull(folderPath);
        this.filePath = Objects.requireNonNull(filePath);
    }

    public static Resource with(
            final byte[] content,
            final String contentType,
            final String name,
            final String folderPath,
            final String filePath) {
        return new Resource(content, contentType, name, folderPath, filePath);
    }

    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public String name() {
        return name;
    }

    public String folderPath() {
        return folderPath;
    }

    public String filePath() {
        return filePath;
    }
}