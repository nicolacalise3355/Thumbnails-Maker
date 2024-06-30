package com.calise.tb_backend.mapper;

import com.calise.tb_backend.models.dtos.response.VideoResponseDto;
import com.calise.tb_backend.models.entities.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public VideoResponseDto toDto(Video video) {
        return new VideoResponseDto(
                video.getId(),
                video.getTitle(),
                video.getUri()
        );
    }

}
