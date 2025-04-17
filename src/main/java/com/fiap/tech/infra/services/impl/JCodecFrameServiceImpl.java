package com.fiap.tech.infra.services.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.ByteBufferSeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiap.tech.domain.enums.ImageFormatEnum;
import com.fiap.tech.infra.exception.compress.ZipCompressionException;
import com.fiap.tech.infra.exception.frame.FrameExtractionException;
import com.fiap.tech.infra.exception.frame.FrameSaveException;
import com.fiap.tech.infra.services.FrameExtractorService;

public class JCodecFrameServiceImpl implements FrameExtractorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCodecFrameServiceImpl.class);

    private final Path outputDir;

    public JCodecFrameServiceImpl(Path outputDir) {
        this.outputDir = outputDir;
        ensureOutputDir();
    }

    @Override
    public List<File> extract(byte[] videoBytes, ImageFormatEnum imageFormat) {
        validate(videoBytes);

        try (var channel = new ByteBufferSeekableByteChannel(ByteBuffer.wrap(videoBytes), videoBytes.length)) {
            var grab = FrameGrab.createFrameGrab(channel);
            return processFrames(grab, imageFormat);
        } catch (IOException | RuntimeException | JCodecException e) {
            LOGGER.error(String.format("Falha ao extrair frames do vídeo: %s", e.getMessage()), e);
            throw new FrameExtractionException("Erro ao extrair frames", e);
        }
    }

    private void ensureOutputDir() {
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new ZipCompressionException("Não foi possível criar o diretório de saída: " + outputDir, e);
        }
    }

    private void validate(byte[] videoBytes) {
        if (videoBytes == null || videoBytes.length == 0) {
            throw new FrameExtractionException("Vídeo vazio ou nulo");
        }
    }

    private List<File> processFrames(FrameGrab grab, ImageFormatEnum imageFormat) {
        var frames = new ArrayList<File>();

        try {
            Picture picture;

            while ((picture = grab.getNativeFrame()) != null) {
                var img = AWTUtil.toBufferedImage(picture);
                frames.add(saveFrames(img, imageFormat));
            }
        } catch (IOException e) {
            LOGGER.error("Erro ao processar frames", e);
            throw new FrameExtractionException("Erro ao processar frames", e);
        }

        return frames;
    }

    public File saveFrames(BufferedImage image, ImageFormatEnum imageFormat) {
        var filename = String.format("%s.%s", UUID.randomUUID(), imageFormat.mimeType());
        var target = outputDir.resolve(filename);

        try {
            ImageIO.write(image, imageFormat.mimeType(), target.toFile());
            return target.toFile();
        } catch (IOException e) {
            LOGGER.error(String.format("Falha ao salvar frame em %s: %s", target, e.getMessage()), e);
            throw new FrameSaveException(String.format("Não foi possível salvar frame: %s", target), e);
        }
    }
}