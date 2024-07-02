package com.calise.tb_backend.services.sockets;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.models.utility.ThumbSize;
import com.calise.tb_backend.services.utils.Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;
import java.util.Random;

@Service
public class ThumbnailsService {

    @Value("${data.assetdir.thumbnails}")
    private String thumbnailsAssetsDir;

    @Autowired
    private VideoDAO videoRepository;

    /**
     *
     * @param id of video
     * @param ts size
     * @return Resource with thumbnail
     * @throws IOException
     * From video id, take a random thumb from that video and resize that if needed
     */
    public InputStreamResource getThumbnail(int id, ThumbSize ts) throws IOException {
        Optional<Video> v = videoRepository.findById(id);
        if(!v.isPresent()){
            return null;
        }
        String specificThumbnailDirectoryPath = "." + thumbnailsAssetsDir + Utils.removeFileExtension(v.get().getFilename()) + "/";
        File dir = new File(specificThumbnailDirectoryPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".png"));
        if (files == null || files.length == 0) {
            return null;
        }
        File selectedFile = files[new Random().nextInt(files.length)];
        InputStreamResource resource;
        if(ts != null){
            BufferedImage originalImage = ImageIO.read(selectedFile);
            BufferedImage resizedImage = resizeImage(originalImage, ts.getWidth(), ts.getHeight());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            resource = new InputStreamResource(bais);
        }else{
            resource = new InputStreamResource(new FileInputStream(selectedFile));
        }
        return resource;
    }

    /**
     *
     * @param originalImage
     * @param width
     * @param height
     * @return
     * Resize the image
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }


}
