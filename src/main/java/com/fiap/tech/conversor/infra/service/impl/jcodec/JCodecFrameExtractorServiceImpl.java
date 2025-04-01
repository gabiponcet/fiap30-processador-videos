package com.fiap.tech.conversor.infra.service.impl.jcodec;

import com.fiap.tech.conversor.infra.service.FrameExtractorService;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JCodecFrameExtractorServiceImpl implements FrameExtractorService {

    private static final Logger logger = LoggerFactory.getLogger(JCodecFrameExtractorServiceImpl.class);

    @Override
    public List<File> extract(String videoKey) throws FileNotFoundException, IOException, JCodecException {
        try {
            List<File> frameFiles = new ArrayList<>();
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(new File("./tmp/" + videoKey)));
    
            double durationInSeconds = grab.getVideoTrack().getMeta().getTotalDuration();
    
            int frameNumber = 0;
            for (int i = 0; i < durationInSeconds; i++) {
                grab.seekToSecondPrecise(i);
                Picture picture = grab.getNativeFrame();
                if (picture == null) break;
    
                BufferedImage image = AWTUtil.toBufferedImage(picture);
                File outputFile = new File("./tmp", "frame_" + frameNumber + ".png");
                ImageIO.write(image, "png", outputFile);
    
                frameFiles.add(outputFile);
                frameNumber++;
            }
    
            return frameFiles;
        } catch (FileNotFoundException e) {
            logger.error("Erro ao abrir o arquivo de video '{}'",  e.getMessage(), e);
            throw new FileNotFoundException("Erro ao abrir o arquivo de video");
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo de video '{}'",  e.getMessage(), e);
            throw new IOException("Erro ao ler o arquivo de video", e);
        } catch (JCodecException e) {
            logger.error("Erro ao processar o arquivo de video '{}'",  e.getMessage(), e);
            throw new JCodecException("Erro ao processar o arquivo de video");
        }
    }
}