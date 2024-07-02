package com.calise.tb_backend.services;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.exceptions.entities.VideoUploadException;
import com.calise.tb_backend.handlers.FileUploadWebSocketHandler;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.services.utils.AWTUtil;
import com.calise.tb_backend.services.utils.Utils;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.entities.ThumbnailsMessages;
import com.calise.tb_backend.staticdata.messages.entities.VideoMessage;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
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

    /**
     *
     * @param file File to upload and save as entity
     * @param sessionId session id used to send message to the socket
     * @return true if upload is done
     * @throws VideoUploadException
     * The method save the file inside a directory, then call a method to generate thumbnails for that file
     */
    public boolean saveFile(MultipartFile file, String sessionId) throws VideoUploadException{
        if (file.isEmpty()) {
            throw new VideoUploadException(VideoMessage.VIDEO_NOT_VALID, Codes.ERROR_CODE);
        }

        try {
            webSocketHandler.sendUploadStatus(sessionId, VideoMessage.NOT_UPLOADED,1);
            Thread.sleep(1000);
            String fileName = file.getOriginalFilename();
            String generatedFileName = Utils.generateFileName(fileName);

            File convFile = new File(System.getProperty("user.dir") + videoAssetsDir + generatedFileName);
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            try {
                fos.write(file.getBytes());
            } catch(IOException e){
                e.printStackTrace();
            } finally {
                fos.close();
            }

            Video newVideoEntity = saveVideoEntity(fileName, "localhost:8080" + videoAssetsDir + generatedFileName, generatedFileName);
            if(newVideoEntity != null) {
                webSocketHandler.sendUploadStatus(sessionId, VideoMessage.VIDEO_UPLOAD_FINISH, 2);
            }
            generateThumbnails(convFile, sessionId);
            webSocketHandler.closeConnection(sessionId);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                webSocketHandler.sendUploadStatus(sessionId, VideoMessage.VIDEO_UPLOAD_FAIL, -1);
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

    /**
     *
     * @param file file to extract thumbnails
     * @param sessionId session id to send message to the socket channel
     * @throws Exception
     * Generate a directory basse in the file, then ins in the folder some thumbnails extracted from the file
     */
    private void generateThumbnails(File file, String sessionId) throws Exception {
        String videoFileName = file.getName();
        String baseFileName = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
        String specificThumbnailDirectoryPath = "." + thumbnailsAssetsDir + baseFileName + "/";
        webSocketHandler.sendUploadStatus(sessionId, ThumbnailsMessages.ANALYZING, 3);

        Path path = Paths.get(specificThumbnailDirectoryPath);
        Path newP = Files.createDirectories(path);
        if(newP != null) {

            try (SeekableByteChannel channel = NIOUtils.readableChannel(file)) {
                FrameGrab grab = FrameGrab.createFrameGrab(channel);
                Picture picture;
                double fps = grab.getVideoTrack().getMeta().getTotalFrames() / grab.getVideoTrack().getMeta().getTotalDuration();
                int frameNumber = 0;
                int interval = (int) (fps * 3);
                boolean go = true;
                while (go) {
                    try {
                        picture = grab.seekToFramePrecise(frameNumber).getNativeFrame();
                    } catch (NullPointerException e) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        webSocketHandler.sendUploadStatus(sessionId, ThumbnailsMessages.COMPLETED_GENERATION, 4);
    }


    /**
     *
     * @param title
     * @param uri
     * @param fileName
     * @return Video entity save
     * Save an entity in the db
     */
    public Video saveVideoEntity(String title, String uri, String fileName) {
        return videoRepository.save(new Video(uri, title, fileName));
    }



}
