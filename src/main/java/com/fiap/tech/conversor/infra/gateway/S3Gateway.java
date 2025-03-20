package com.fiap.tech.conversor.infra.gateway;

import org.springframework.stereotype.Component;

import com.fiap.tech.conversor.infra.service.impl.s3.S3DataReaderService;
import com.fiap.tech.conversor.infra.service.impl.s3.S3DataUploaderService;

@Component
public class S3Gateway {

    private final S3DataReaderService s3DataReaderService;
    private final S3DataUploaderService s3DataUploaderService;

    public S3Gateway(final S3DataReaderService s3DataReaderService, final S3DataUploaderService s3DataUploaderService) {
        this.s3DataUploaderService = s3DataUploaderService;
        this.s3DataReaderService = s3DataReaderService;
    }   
}