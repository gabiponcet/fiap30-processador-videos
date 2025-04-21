package com.fiap.tech.domain.conversor;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "status")
public sealed interface ConversorVideoResult permits ConversorVideoError, ConversorVideoCompleted, ConversorVideoProcessing {

    String getStatus();
}