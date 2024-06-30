package com.calise.tb_backend.controllers.sockets;

import com.calise.tb_backend.services.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/video")
public class VideoUploadController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/upload")
    public Map<String, String> uploadVideo(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        String fileName = file.getOriginalFilename();
        String generatedFileName = Utils.generateFileName(fileName);
        try {
            messagingTemplate.convertAndSend("/wsconnection/status", "init");

            File convFile = new File(System.getProperty("user.dir") + "/assets/videos/" + generatedFileName);
            convFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }

            // Simulate some processing time
            Thread.sleep(3000);
            messagingTemplate.convertAndSend("/wsconnection/status", "doing");

            // More processing...
            Thread.sleep(3000);
            messagingTemplate.convertAndSend("/wsconnection/status", "end");

            response.put("message", "Caricamento completato con successo!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            response.put("message", "Errore durante il caricamento del video.");
            messagingTemplate.convertAndSend("/wsconnection/status", "error");
        }

        return response;
    }
}
