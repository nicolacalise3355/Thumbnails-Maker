package com.calise.tb_backend.controllers.entities;

import com.calise.tb_backend.dao.VideoDAO;
import com.calise.tb_backend.models.entities.Video;
import com.calise.tb_backend.models.http.HttpResponse;
import com.calise.tb_backend.models.http.HttpResponseInvalid;
import com.calise.tb_backend.models.utility.ThumbSize;
import com.calise.tb_backend.services.sockets.ThumbnailsService;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.GeneralMessages;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/t")
public class ThumbnailsController {

    @Autowired
    ThumbnailsService thumbnailsService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getThumbnail(@PathVariable int id, @RequestParam Optional<Integer> width, @RequestParam Optional<Integer> height) {
        try{
            ThumbSize ts = null;
            if(width.isPresent() && height.isPresent()) ts = new ThumbSize(width.get(), height.get());
            InputStreamResource resource = thumbnailsService.getThumbnail(id, ts);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=thumbnail.png");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
