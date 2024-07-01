package com.calise.tb_backend.controllers.sockets;

import com.calise.tb_backend.exceptions.entities.VideoUploadException;
import com.calise.tb_backend.handlers.FileUploadWebSocketHandler;
import com.calise.tb_backend.models.http.HttpResponse;
import com.calise.tb_backend.models.http.HttpResponseInvalid;
import com.calise.tb_backend.models.http.HttpResponseValid;
import com.calise.tb_backend.services.UploadService;
import com.calise.tb_backend.services.utils.Utils;
import com.calise.tb_backend.staticdata.Codes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/video")
public class VideoUploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<HttpResponse> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("session") String sessionId) {
        try {
            boolean result = uploadService.saveFile(file, sessionId);
            return ResponseEntity.ok(new HttpResponseValid(Codes.OK_CODE, result));
        } catch (VideoUploadException e) {
            return ResponseEntity.badRequest().body(new HttpResponseInvalid(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}
