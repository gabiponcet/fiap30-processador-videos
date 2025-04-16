package com.fiap.tech.infra.exception.frame;

import com.fiap.tech.infra.exception.InfraException;

public sealed interface FrameException extends InfraException permits FrameExtractionException, FrameSaveException {

}