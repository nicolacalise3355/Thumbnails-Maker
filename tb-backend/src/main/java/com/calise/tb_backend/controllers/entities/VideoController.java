package com.calise.tb_backend.controllers.entities;

import com.calise.tb_backend.exceptions.entities.UserException;
import com.calise.tb_backend.exceptions.entities.VideoException;
import com.calise.tb_backend.models.dtos.response.UserResponseDto;
import com.calise.tb_backend.models.dtos.response.VideoResponseDto;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.models.http.HttpResponse;
import com.calise.tb_backend.models.http.HttpResponseInvalid;
import com.calise.tb_backend.models.http.HttpResponseValid;
import com.calise.tb_backend.services.entities.VideoServices;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.GeneralMessages;
import com.calise.tb_backend.staticdata.messages.entities.VideoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/video")
public class VideoController {

    private VideoServices videoServices;

    @Autowired
    public VideoController(VideoServices videoServices) {
        this.videoServices = videoServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getVideoById(@PathVariable int id) {
        if(id < 0) return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, GeneralMessages.ID_NOT_VALID));
        try{
            VideoResponseDto video = this.videoServices.getVideoById(id);
            return ResponseEntity.ok(new HttpResponseValid(Codes.OK_CODE, video));
        }catch(VideoException e){
            return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllVideos(){
        return ResponseEntity.ok(new HttpResponseValid(Codes.OK_CODE, this.videoServices.getAllVideos()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideoById(@PathVariable int id) {
        try {
            videoServices.deleteVideoById(id);
            return ResponseEntity.ok(new HttpResponseValid(HttpStatus.OK.value(), VideoMessage.VIDEO_DELETED));
        } catch (VideoException e) {
            return ResponseEntity.ok(new HttpResponseInvalid(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}
