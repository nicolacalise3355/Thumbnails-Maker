package com.calise.tb_backend.services;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.exceptions.entities.VideoUploadException;
import com.calise.tb_backend.handlers.FileUploadWebSocketHandler;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.services.utils.AWTUtil;
import com.calise.tb_backend.services.utils.Utils;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.entities.VideoMessage;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {

    @Autowired
    private FileUploadWebSocketHandler webSocketHandler;

    @Autowired
    private VideoDAO videoRepository;

    @Value("${data.assetdir.video}")
    private String videoAssetsDir;
    @Value("${data.assetdir.thumbnails}")
    private String thumbnailsAssetsDir;

    public boolean saveFile(MultipartFile file, String sessionId) throws VideoUploadException{
        if (file.isEmpty()) {
            throw new VideoUploadException(VideoMessage.VIDEO_NOT_VALID, Codes.ERROR_CODE);
        }

        try {
            webSocketHandler.sendUploadStatus(sessionId, "Starting Video upload",1);
            Thread.sleep(2000);
            String fileName = file.getOriginalFilename();
            String generatedFileName = Utils.generateFileName(fileName);

            File convFile = new File(System.getProperty("user.dir") + videoAssetsDir + generatedFileName);
            convFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }

            Video newVideoEntity = saveVideoEntity(fileName, "localhost:8080" + videoAssetsDir + generatedFileName, generatedFileName);
            if(newVideoEntity != null) {
                webSocketHandler.sendUploadStatus(sessionId, "Video Upload successful", 2);
            }
            generateThumbnails(convFile, generatedFileName, sessionId);
            webSocketHandler.closeConnection(sessionId);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                webSocketHandler.sendUploadStatus(sessionId, "Upload failed", -1);
                webSocketHandler.closeConnection(sessionId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void generateThumbnails(File file, String filename, String sessionId) throws Exception {
        String videoFileName = file.getName();
        String baseFileName = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
        String specificThumbnailDirectoryPath = "." + thumbnailsAssetsDir + baseFileName + "/";
        webSocketHandler.sendUploadStatus(sessionId, "Analyzing to generate thumbnails", 3);

        Path path = Paths.get(specificThumbnailDirectoryPath);
        Path newP = Files.createDirectories(path);
        if(newP != null) {

            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            Picture picture;
            double fps = grab.getVideoTrack().getMeta().getTotalFrames() / grab.getVideoTrack().getMeta().getTotalDuration();
            int frameNumber = 0;
            int interval = (int) (fps * 3);
            boolean go = true;
            while (go) {
                try{
                    picture = grab.seekToFramePrecise(frameNumber).getNativeFrame();
                } catch(NullPointerException e){
                    go = false;
                    break;
                }

                if (picture == null) {
                    go = false;
                    break;
                }
                BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
                File thumbnailFile = new File(specificThumbnailDirectoryPath + "thumbnail-" + String.format("%03d", frameNumber / interval) + ".png");
                ImageIO.write(bufferedImage, "png", thumbnailFile);
                frameNumber += interval;
            }
        }
        webSocketHandler.sendUploadStatus(sessionId, "Completed generation of thumbnails", 4);
    }


    public Video saveVideoEntity(String title, String uri, String fileName) {
        return videoRepository.save(new Video(uri, title, fileName));
    }



}
