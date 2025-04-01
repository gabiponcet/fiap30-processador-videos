package com.fiap.tech.conversor.infra.service.impl.s3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fiap.tech.conversor.infra.service.FileService;

public class S3FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(S3FileServiceImpl.class);

    private final AmazonS3 s3Client;

    public S3FileServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void download(String bucket, String key) throws Exception {
        logger.info("Iniciando o download do arquivo '{}' do bucket '{}'", key, bucket);

        Path destinationPath = Paths.get("./tmp/", key);
        
        try (S3Object s3Object = s3Client.getObject(bucket, key);
             S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("Download concluído com sucesso. Arquivo salvo em: {}", destinationPath);
            
        } catch (IOException e) {
            logger.error("Erro ao fazer download do arquivo '{}': {}", key, e.getMessage(), e);
            throw new IOException("Erro ao fazer download do arquivo de video", e);
        }
    }

    @Override
    public String upload(String bucket, File file, String key) {
        String uploadKey = key + "_frames.zip";

        logger.info("Iniciando o upload do arquivo '{}' do bucket '{}'", uploadKey, bucket);

        s3Client.putObject(new PutObjectRequest(bucket, uploadKey, file));

        logger.info("Upload concluído com sucesso.");

        return uploadKey;
    }
}
