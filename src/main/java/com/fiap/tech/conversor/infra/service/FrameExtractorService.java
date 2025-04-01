package com.fiap.tech.conversor.infra.service;

import java.io.File;
import java.util.List;

public interface FrameExtractorService {
    List<File> extract(String videoKey) throws Exception;
}
