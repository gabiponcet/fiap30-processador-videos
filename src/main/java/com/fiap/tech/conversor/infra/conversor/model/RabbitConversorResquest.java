package com.fiap.tech.conversor.infra.conversor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RabbitConversorResquest(@JsonProperty("bucket") String bucket, @JsonProperty("videoKey") String videoKey) {
}