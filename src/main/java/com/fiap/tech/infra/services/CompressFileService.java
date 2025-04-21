package com.fiap.tech.infra.services;

import java.io.File;
import java.util.List;

import com.fiap.tech.domain.resource.Resource;

public interface CompressFileService {

    Resource zip(String id, String clientId, List<File> files);
}
