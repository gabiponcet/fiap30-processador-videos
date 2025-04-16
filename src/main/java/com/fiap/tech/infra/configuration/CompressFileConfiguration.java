package com.fiap.tech.infra.configuration;

import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import com.fiap.tech.infra.services.CompressFileService;
import com.fiap.tech.infra.services.impl.CompressFileServiceImpl;

@Configuration
public class CompressFileConfiguration {

    @Bean
    public CompressFileService compressFile(StorageProperties storageProperties) {
        return new CompressFileServiceImpl(Path.of(System.getProperty("java.io.tmpdir")), storageProperties);
    }
}
