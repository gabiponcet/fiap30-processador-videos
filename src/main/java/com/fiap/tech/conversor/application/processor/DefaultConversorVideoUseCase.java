package com.fiap.tech.conversor.application.processor;

import com.fiap.tech.conversor.infra.gateway.S3Gateway;

public class DefaultConversorVideoUseCase extends ConversorVideoUseCase {

    private final S3Gateway s3Gateway;

    public DefaultConversorVideoUseCase(S3Gateway s3Gateway) {
        this.s3Gateway = s3Gateway;
    }

    @Override
    public ConversorOutput execute(ConversorCommand cCmd) {
        return new ConversorOutput(true, "/zip/file.zip");
    }
}