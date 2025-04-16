package com.fiap.tech.infra.exception.compress;

import com.fiap.tech.infra.exception.InfraException;

public sealed interface CompressionException extends InfraException permits ZipCompressionException {

}