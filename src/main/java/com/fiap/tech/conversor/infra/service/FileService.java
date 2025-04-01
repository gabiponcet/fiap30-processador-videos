package com.fiap.tech.conversor.infra.service;

import java.io.File;

public interface FileService {
    void download(String bucket, String key) throws Exception;
    String upload(String bucket, File file, String key) throws Exception;
}
