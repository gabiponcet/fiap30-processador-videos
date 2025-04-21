package com.fiap.tech.infra.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiap.tech.domain.enums.CompressContentTypeEnum;
import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import com.fiap.tech.infra.exception.compress.ZipCompressionException;
import com.fiap.tech.infra.services.CompressFileService;

public class CompressFileServiceImpl implements CompressFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompressFileServiceImpl.class);
    
    private static final int BUFFER_SIZE = 1024;

    private final String filenamePattern;
    private final String locationPattern;
    private final Path outputDir;

    public CompressFileServiceImpl(Path outputDir, final StorageProperties props) {
        this.filenamePattern = props.getFileNamePattern();
        this.locationPattern = props.getLocationPattern();
        this.outputDir = outputDir;
        
        ensureOutputDir();
    }

    @Override
    public Resource zip(String id, String clientId, List<File> files) {
        validate(files);
        
        var zipName = filename(CompressContentTypeEnum.ZIP);
        var zipPath = buildZipPath(folder(id, clientId));
        
        try (var zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            compressAll(files, zos);

            byte[] zipBytes = Files.readAllBytes(zipPath);
            String contentType = CompressContentTypeEnum.ZIP.mimeType();
            String fileName = zipName;
            String folderPath = folder(id, clientId);
            String filePath = filepath(id, CompressContentTypeEnum.ZIP, clientId);
            
            return Resource.with(zipBytes, contentType, fileName, folderPath, filePath);
        } catch (IOException e) {
            LOGGER.error("Falha ao gerar o ZIP em {}", zipPath, e);
            throw new ZipCompressionException("Erro ao gerar o arquivo ZIP", e);
        }
    }

    private void ensureOutputDir() {
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new ZipCompressionException("Não foi possível criar o diretório de saída: " + outputDir, e);
        }
    }

    private void validate(List<File> files) {
        if (files == null || files.isEmpty()) {
            throw new ZipCompressionException("Lista de arquivos para compactar está vazia ou nula");
        }
    }

    private Path buildZipPath(String name) {
        try {
            var zipPath = outputDir.resolve(name);

            Files.createDirectories(zipPath.getParent());
            
            return zipPath;

        } catch (IOException e) {
            throw new ZipCompressionException("Erro ao criar o arquivo ZIP. Verifique se o diretório de saída existe e tem permissões de escrita");
        }
    }

    private String filename(final CompressContentTypeEnum type) {
        return filenamePattern.replace("{type}", type.name());
    }

    private String folder(final String anId, final String clientId) {
        return locationPattern
                .replace("{videoId}", anId)
                .replace("{clientId}", clientId);
    }

    private String filepath(final String anId, final CompressContentTypeEnum type, final String clientId) {
        return folder(anId, clientId) + "/" + filename(type);
    }

    private void compressAll(List<File> files, ZipOutputStream zos) throws IOException {
        var buffer = new byte[BUFFER_SIZE];

        for (var file : files) {
            addFileToZip(zos, file, buffer);
        }
    }

    private void addFileToZip(ZipOutputStream zos, File file, byte[] buffer) {
        var entry = new ZipEntry(file.getName());
        
        try {
            zos.putNextEntry(entry);
            
            try (var in = Files.newInputStream(file.toPath())) {
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
            }
        
            zos.closeEntry();
        
        } catch (IOException e) {
            LOGGER.error("Erro ao compactar o arquivo {}", file.getName(), e);
            throw new ZipCompressionException("Falha ao adicionar arquivo ao ZIP: " + file.getName(), e);
        }
    }
}
