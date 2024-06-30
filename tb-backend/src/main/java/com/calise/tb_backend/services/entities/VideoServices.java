package com.calise.tb_backend.services.entities;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.exceptions.entities.VideoException;
import com.calise.tb_backend.mapper.VideoMapper;
import com.calise.tb_backend.models.dtos.response.VideoResponseDto;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.entities.VideoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServices {

    private VideoDAO videoDAO;
    private VideoMapper videoMapper;

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

}
