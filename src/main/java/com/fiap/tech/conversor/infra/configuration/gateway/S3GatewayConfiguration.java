package com.fiap.tech.conversor.infra.configuration.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.conversor.infra.gateway.S3Gateway;
import com.fiap.tech.conversor.infra.service.impl.s3.S3DataReaderService;
import com.fiap.tech.conversor.infra.service.impl.s3.S3DataUploaderService;

@Configuration
public class S3GatewayConfiguration {

    private S3DataReaderService s3DataReaderService;
    private S3DataUploaderService s3DataUploaderService;    

    public S3GatewayConfiguration(S3DataReaderService s3DataReaderService, S3DataUploaderService s3DataUploaderService) {
        this.s3DataReaderService = s3DataReaderService;
        this.s3DataUploaderService = s3DataUploaderService;
    }

    @Bean
    public S3Gateway s3Gateway() {
        return new S3Gateway(s3DataReaderService, s3DataUploaderService);
    }

}
