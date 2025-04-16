package com.fiap.tech.infra.services.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.services.StorageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3StorageServiceImpl implements StorageService {

    private final String bucket;
    private final S3Client s3;

    public S3StorageServiceImpl(final String bucket, final S3Client s3) {
        this.bucket = bucket;
        this.s3 = Objects.requireNonNull(s3);
    }

    @Override
    public Optional<Resource> get(final String filePath) {
        try {
            final var response = 
                s3.getObject(
                    GetObjectRequest.builder()
                                    .bucket(bucket)
                                    .key(filePath)
                                    .build());

            final var content = response.readAllBytes();

            final var metadata = 
                s3.headObject(
                    HeadObjectRequest.builder()
                                     .bucket(bucket)
                                     .key(filePath)
                                     .build());

            final var folderPath = Paths.get(filePath).getParent().toString();

            final var fileName = Paths.get(filePath).getFileName().toString();

            return Optional.of(Resource.with(content, metadata.contentType(), fileName, folderPath, filePath));
            
        } catch (NoSuchKeyException e) {
            return Optional.empty();
        } catch (IOException | S3Exception e) {
            throw new RuntimeException("Falha ao recuperar recurso do S3", e);
        }
    }

    @Override
    public void store(final Resource resource) {
        s3.putObject(
            PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(resource.name())
                            .contentType(resource.contentType())
                            .build(), RequestBody.fromBytes(resource.content()));
    }
}