package com.fiap.tech.infra.services;

import java.io.File;
import java.util.List;

import com.fiap.tech.domain.enums.ImageFormatEnum;

public interface FrameExtractorService {
    
    List<File> extract(byte[] video, ImageFormatEnum imageFormat);
}