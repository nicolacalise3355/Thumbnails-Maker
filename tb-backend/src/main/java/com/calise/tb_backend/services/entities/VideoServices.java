package com.calise.tb_backend.services.entities;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.exceptions.entities.VideoException;
import com.calise.tb_backend.mapper.VideoMapper;
import com.calise.tb_backend.models.dtos.response.VideoResponseDto;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.services.utils.Utils;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.entities.VideoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@Service
public class VideoServices {

    private VideoDAO videoDAO;
    private VideoMapper videoMapper;

    @Value("${data.assetdir.video}")
    private String videoAssetsDir;
    @Value("${data.assetdir.thumbnails}")
    private String thumbnailsAssetsDir;

    @Autowired
    public VideoServices(VideoDAO videoDAO, VideoMapper videoMapper) {
        this.videoDAO = videoDAO;
        this.videoMapper = videoMapper;
    }

    public List<Video> getAllVideos() {
        return this.videoDAO.findAll();
    }

    public VideoResponseDto getVideoById(int id) throws VideoException {
        return this.videoMapper.toDto(this.videoDAO.findById(id).orElseThrow(() -> new VideoException(VideoMessage.VIDEO_NOT_FOUND, Codes.ERROR_CODE)));
    }

    public void deleteVideoById(int id) throws VideoException {
        Video v = this.videoDAO.findById(id).orElseThrow(() -> new VideoException(VideoMessage.VIDEO_NOT_FOUND, Codes.ERROR_CODE));
        this.videoDAO.delete(v);
        Path filePath = Paths.get("." + videoAssetsDir + v.getFilename());
        String thumbDir = "." + thumbnailsAssetsDir + Utils.removeFileExtension(v.getFilename());
        Path directoryThuPath = Paths.get(thumbDir);
        try {
            Files.delete(filePath);
            Files.walk(directoryThuPath)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.err.println("Failed to delete: " + path + " due to " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new VideoException(VideoMessage.ERROR, Codes.ERROR_CODE);
        }
    }

}
