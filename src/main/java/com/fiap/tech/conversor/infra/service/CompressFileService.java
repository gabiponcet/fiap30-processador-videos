package com.fiap.tech.conversor.infra.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CompressFileService {
    File zip(List<File> files, String videoKey) throws IOException;
}
