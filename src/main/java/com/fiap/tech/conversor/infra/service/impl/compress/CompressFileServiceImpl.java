package com.fiap.tech.conversor.infra.service.impl.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiap.tech.conversor.infra.service.CompressFileService;
import com.fiap.tech.conversor.infra.service.impl.jcodec.JCodecFrameExtractorServiceImpl;

public class CompressFileServiceImpl implements CompressFileService {

    private static final Logger logger = LoggerFactory.getLogger(JCodecFrameExtractorServiceImpl.class);

    @Override
    public File zip(List<File> files, String videoKey) throws IOException {
        try {
            File zipFile = new File("./tmp/" + videoKey + "_frames.zip");

            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
    
                byte[] buffer = new byte[1024];
            
                for (File file : files) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zos.putNextEntry(zipEntry);
    
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                    }
                }
            }
            
            return zipFile;
        } catch (IOException e) {
            logger.error("Erro ao gerar o arquivo zip '{}'",  e.getMessage(), e);
            throw new IOException("Erro ao gerar o arquivo zip", e);
        }
    }
}
